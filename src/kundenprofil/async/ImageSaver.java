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

public class ImageSaver extends AsyncTask<String, Integer, JSONObject> {

	private KundenProfil kundenProfil;
	private String username;
	public static final int TYPE = 1;


	public ImageSaver(KundenProfil kundenProfil) {
		super();
		this.kundenProfil = kundenProfil;
	}

	// run shit in background
	@Override
	protected JSONObject doInBackground(String... params) 
	{
		HttpClient client = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(params[0]);

			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("username", params[1]));
			nameValuePairs.add(new BasicNameValuePair("passwort", Utils
					.MD5(params[2])));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse httpResponse = client.execute(httpPost); 
			String s = EntityUtils.toString(httpResponse.getEntity());
			username = params[1];
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
		kundenProfil.onHttpFin(TYPE, result);
	}
}
