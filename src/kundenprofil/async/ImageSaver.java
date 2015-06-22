package kundenprofil.async;

import java.io.File;
import java.io.IOException;
import kundenprofil.KundenProfil;
import login_register.Login;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import utils.PrefUtils;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

public class ImageSaver extends AsyncTask<String, Integer, JSONObject> {

	private KundenProfil kundenProfil;
	private String sessionId;

	public ImageSaver(KundenProfil kundenProfil, String sessionId) {
		super();
		this.kundenProfil = kundenProfil;
		this.sessionId = sessionId;
	}

	// run in background
	@Override
	protected JSONObject doInBackground(String... params) {
		try {
			// http parameter setzten (HTTP-version, ..)
			HttpParams httpParams = new BasicHttpParams();
			httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpClient mHttpClient = new DefaultHttpClient(httpParams);

			HttpPost httppost = new HttpPost("http://www.pascals.at/v2/PHD_DBA/DBA.php?f=kundeUpdaten");
			//zu PHP ($_FILES) übertragen der body oder content des post requestes (übergabespeicher)
			MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			//session id mit übertragen
			multipartEntity.addPart("sessionId", new StringBody(sessionId));
			//neues bild an den server schicken
			multipartEntity.addPart("foto",new FileBody(new File(kundenProfil.getFilesDir()+ "/myImage.jpg")));
			httppost.setEntity(multipartEntity);
			//http post abschicken
			HttpResponse httpResponse = mHttpClient.execute(httppost);
			//antwort json laden
			String s = EntityUtils.toString(httpResponse.getEntity());
//			Log.d("test", s);
			return new JSONObject(s);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		if (result != null) {
			try {
				result = result.getJSONObject("res");
				if (result.has("errc")) {
					//wenn die antwort ein error feld beinhaltet ist etwas schief gegangen und es wird ein toast angezeigt
					Toast.makeText(kundenProfil, result.getString("viewmsg"),Toast.LENGTH_LONG).show();
				} else {
					SharedPreferences preferences = PrefUtils.getPreferences(kundenProfil, Login.PREF_TAG);
					//das neue last-update datum speichern und die neue image url speichern in den SP
					preferences
							.edit()
							.putLong(Login.LOGIN_LAST_IMAGE_UPDATE,
									result.getLong("picdate"))
							.putString(Login.LOGIN_IMAGE_URL,
									result.getJSONObject("kunde").getString("foto"))
							.commit();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		//kundenprofil AC informieren das der Imagesaver fertig ist.
		kundenProfil.onHttpFin(KundenProfil.IMAGE_CHANGED, result);
	}
}
