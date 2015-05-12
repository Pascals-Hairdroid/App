package kundenprofil.async;

import java.io.IOException;
import kundenprofil.KundenProfil;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class ImageSaver extends AsyncTask<String, Integer, JSONObject> {

	private KundenProfil kundenProfil;

	public ImageSaver(KundenProfil kundenProfil) {
		super();
		this.kundenProfil = kundenProfil;
	}

	// run shit in background
	@Override
	protected JSONObject doInBackground(String... params) 
	{
		try {
			HttpClient httpclient = new DefaultHttpClient();
			httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpPost httpPost = new HttpPost("http://www.pascals.at/v2/PHD_DBA/DBA.php?f=kundeUpdaten"); // Url
			MultipartEntity mpEntity = new MultipartEntity();
			InputStreamBody isb = new InputStreamBody(kundenProfil.openFileInput("myImage.jpg"), "foto");
			mpEntity.addPart("foto", isb);
			httpPost.setEntity(mpEntity);
			HttpResponse response = httpclient.execute(httpPost);
			String s = EntityUtils.toString(response.getEntity());
			Log.d("test", s);
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
		kundenProfil.onHttpFin(KundenProfil.IMAGE_CHANGED, result);
	}
}
