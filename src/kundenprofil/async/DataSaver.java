package kundenprofil.async;

import java.io.IOException;
import java.util.List;
import kundenprofil.KundenProfil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.widget.Toast;

public class DataSaver extends
		AsyncTask<List<NameValuePair>, Integer, JSONObject> {

	private KundenProfil kundenProfil;

	public DataSaver(KundenProfil kundenProfil) {
		this.kundenProfil = kundenProfil;
	}

	// run in background
	@Override
	protected JSONObject doInBackground(List<NameValuePair>... params) {
		HttpClient client = new DefaultHttpClient(); // Http Client erstellen
		try {
			// neue daten an server übertragen
			HttpPost httpPost = new HttpPost(
					"http://www.pascals.at/v2/PHD_DBA/DBA.php?f=kundeUpdaten"); // Url
			httpPost.setEntity(new UrlEncodedFormEntity(params[0]));
			HttpResponse httpResponse = client.execute(httpPost);
			String s = EntityUtils.toString(httpResponse.getEntity());
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
		if (result.has("errc")) {
			try {
				Toast.makeText(kundenProfil, result.getString("viewmsg"),
						Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// onHttpFin aufrufen mit DATA_CHANGED und einmal mit
			// INTERESSEN_CHANGED weil der datasaver beides ändert
			// für zukunft aber möglich dies zu trennen ein einem data und einen
			// interesssen saver
			kundenProfil.onHttpFin(KundenProfil.DATA_CHANGED, result);
			kundenProfil.onHttpFin(KundenProfil.INTERESSEN_CHANGED, result);
		}
	}
}
