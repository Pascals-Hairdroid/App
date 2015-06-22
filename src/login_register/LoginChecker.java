package login_register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

											// <generic typ arguments> 
public class LoginChecker extends AsyncTask<String, Integer, JSONObject> {
	
	private Login login;
	private String email;
	
	public LoginChecker(Login login) {
		super();
		this.login = login;
	}

	// run in background 
	@Override
	protected JSONObject doInBackground(String... params) //String... = String array damit ich aufruf doinBackground("sfsdf","sdfd","sdfsd") 
	{
		HttpClient client = new DefaultHttpClient(); // Http Client erstellen
		try {
			// Sever Abfrage
			HttpPost httpPost = new HttpPost(params[0]); // Url
			// NameValuePair damit mehrere Parameter abgescheichert werden können
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("email", params[1]));
	        nameValuePairs.add(new BasicNameValuePair("passwort", Utils.MD5(params[2])));
	        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse httpResponse = client.execute(httpPost); // ausführen von httpreqeuest return HttpResponse (antwort von Server)
			String s = EntityUtils.toString(httpResponse.getEntity());
			email= params[1];
			
			//datei aus antwort von Server laden und in ein Json object umwandeln 
			return new JSONObject(s);
			//return EntityUtils.toString(httpResponse.getEntity()); 
			
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
		//doLogin() in Login-AC aufrufen
		login.doLogin(result,email);
	}
}
