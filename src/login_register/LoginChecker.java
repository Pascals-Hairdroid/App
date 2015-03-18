package login_register;

import java.io.IOException;


import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

											// <generic typ arguments> 
public class LoginChecker extends AsyncTask<String, Integer, JSONObject> {
	
	private Login login;
	
	
	
	public LoginChecker(Login login) {
		super();
		this.login = login;
	}

	// run shit in background 
	@Override
	protected JSONObject doInBackground(String... params) //String... = String array damit ich aufruf doinBackground("sfsdf","sdfd","sdfsd") 
	{
		HttpClient client = new DefaultHttpClient(); // Http Client erstellen
		try {
			
			HttpPost httpPost = new HttpPost(params[0]); // Url
			httpPost.getParams().setParameter("username", params[1]); // email
			httpPost.getParams().setParameter("passwort", params[2]); // password
			HttpResponse httpResponse = client.execute(httpPost); // ausführen von httpreqeuest return HttpResponse (antwort von Server)
			
			//datei aus antwort von Server laden und in ein Json object umwandeln 
			return new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
			
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

	// nach hintergrund arbeit im vordergrund do login von login
	@Override
	protected void onPostExecute(JSONObject result) {
		login.doLogin(result);
	}
}
