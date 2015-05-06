package kundenprofil.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kundenprofil.KundenProfil;

import login_register.Login;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import utils.Utils;
import android.os.AsyncTask;
import android.util.Log;

public class DataSaver extends AsyncTask<List<NameValuePair>, Integer, JSONObject> {

	private KundenProfil kundenProfil;

	public DataSaver(KundenProfil kundenProfil) {
		this.kundenProfil = kundenProfil;
	}

	// run shit in background
	@Override
	protected JSONObject doInBackground(List<NameValuePair>... params)
	{
		HttpClient client = new DefaultHttpClient(); // Http Client erstellen
		try {

			HttpPost httpPost = new HttpPost("http://www.pascals.at/v2/PHD_DBA/DBA.php?f=kundeUpdaten"); // Url
			httpPost.setEntity(new UrlEncodedFormEntity(params[0]));
			HttpResponse httpResponse = client.execute(httpPost);
			String s = EntityUtils.toString(httpResponse.getEntity());
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
		kundenProfil.onHttpFin(KundenProfil.DATA_CHANGED, result);
	}
}
