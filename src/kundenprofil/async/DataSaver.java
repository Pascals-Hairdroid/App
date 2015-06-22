package kundenprofil.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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

import com.example.pascalshairdroid.R;

import utils.PrefUtils;
import utils.Utils;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DataSaver extends AsyncTask<List<NameValuePair>, Integer, JSONObject> {

	private KundenProfil kundenProfil;

	public DataSaver(KundenProfil kundenProfil) {
		this.kundenProfil = kundenProfil;
	}

	// run in background
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
		if (result.has("errc")) {
			try {
				Toast.makeText(kundenProfil, result.getString("viewmsg"),Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// ÄNDERUNG VON KÖNIG: GEHT NOCH NICHT!!!
			/*
			Login.CreateSyncAccount(kundenProfil.getApplicationContext());

			try {
				JSONObject j = result.getJSONObject("res");
				
				JSONObject kunde;
				kunde = j.getJSONObject("kunde");

				HashSet<String> interessen = new HashSet<String>();
				for (int index = 0; index < kunde.getJSONArray("interessen")
						.length(); index++) {
					interessen.add(kunde.getJSONArray("interessen")
							.getJSONObject(index).getString("id"));

				}
				String[] intIds = new String[interessen.size()];
				interessen.toArray(intIds);
				String[] allInteressen = kundenProfil.getApplicationContext().getResources().getStringArray(R.array.interessen);
				String[] allInteressenIds = kundenProfil.getApplicationContext().getResources().getStringArray(R.array.interessen_ids);
				HashSet<String> ints = new HashSet<String>();
				for(int i = 0; i < intIds.length;i++)
					for(int k = 0; k < allInteressenIds.length;k++)
						if(allInteressenIds[k].equals(intIds[i]))
							ints.add(allInteressen[k]);

				SharedPreferences preferences =  PrefUtils.getPreferences(kundenProfil.getApplicationContext(), Login.PREF_TAG); // lade shared pref
				Log.d("asdf", "mocht");
				preferences
						.edit()
						.putString(Login.LOGIN_VORNAME, kunde.getString("vorname"))
						.putString(Login.LOGIN_NACHNAME,
								kunde.getString("nachname"))
						.putString(Login.LOGIN_TELEFON, kunde.getString("telNr"))
						.putLong(Login.LOGIN_LAST_IMAGE_UPDATE,
								j.getLong("picdate"))
						.putString(Login.LOGIN_IMAGE_URL, kunde.getString("foto"))
						.putStringSet(Login.LOGIN_INTERESSEN, ints)
						.putBoolean(Login.LOGIN_FREIGESCHALTEN,
								kunde.getBoolean("freischaltung")).commit();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d("asdf", "FAIL");
			}
			*/
			//-------------------
			
			kundenProfil.onHttpFin(KundenProfil.DATA_CHANGED, result);
			kundenProfil.onHttpFin(KundenProfil.INTERESSEN_CHANGED, result);
		}
	}
}
