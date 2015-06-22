package kundenprofil.async;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import login_register.Login;

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

import utils.PrefUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

public class DataReloader extends AsyncTask<List<NameValuePair>, Integer, JSONObject> 
{
	private Context context;
	private String sessionId;

	public DataReloader(Context context, String sessionId) {
		super();
		this.context = context;
		this.sessionId = sessionId;
	}
	
	// Class aktualisiert die Daten immer wenn Internetverbindung ist

	// run in background
	@Override
	protected JSONObject doInBackground(List<NameValuePair>... params) {
		HttpClient client = new DefaultHttpClient(); // Http Client erstellen
		try {

			HttpPost httpPost = new HttpPost(
					"http://www.pascals.at/v2/PHD_DBA/DBA.php?f=kundeUpdaten"); // Url
			httpPost.setEntity(new UrlEncodedFormEntity(params[0]));
			HttpResponse httpResponse = client.execute(httpPost);
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
		try {
			JSONObject j = result.getJSONObject("res");
			
			JSONObject kunde;
			kunde = j.getJSONObject("kunde");

			HashSet<String> interessen = new HashSet<String>();
			for (int index = 0; index < kunde.getJSONArray("interessen")
					.length(); index++) {
				interessen.add(kunde.getJSONArray("interessen")
						.getJSONObject(index).getString("bezeichnung"));

			}

			// für Kundenprofil die gespeicherten Daten in die
			// Preferences schreiben
			SharedPreferences preferences =  PrefUtils.getPreferences(context, Login.PREF_TAG); // lade shared pref
															// db
			// öffne db zum bearbeiten (edit()), speicher session id ,
			// speichere username, sichere db
			long lastImgUpdate = preferences.getLong(
					Login.LOGIN_LAST_IMAGE_UPDATE, 0);
			preferences
					.edit()
					.putString(Login.LOGIN_SESSION_ID, sessionId)
					.putString(Login.LOGIN_VORNAME, kunde.getString("vorname"))
					.putString(Login.LOGIN_NACHNAME,
							kunde.getString("nachname"))
					.putString(Login.LOGIN_TELEFON, kunde.getString("telNr"))
					.putLong(Login.LOGIN_LAST_IMAGE_UPDATE,
							j.getLong("picdate"))
					.putString(Login.LOGIN_IMAGE_URL, kunde.getString("foto"))
					.putStringSet(Login.LOGIN_INTERESSEN, interessen)
					.putBoolean(Login.LOGIN_FREIGESCHALTEN,
							kunde.getBoolean("freischaltung")).commit();
			if (lastImgUpdate < preferences.getLong(
					Login.LOGIN_LAST_IMAGE_UPDATE, 0)) {
				ImageDownloader downloader = new ImageDownloader(
						preferences.getString(Login.LOGIN_IMAGE_URL, "abc"),
						context);
				downloader.execute();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
