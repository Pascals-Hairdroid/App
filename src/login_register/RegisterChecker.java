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

											// <generic typ arguments> 
public class RegisterChecker extends AsyncTask<String, Integer, JSONObject> {
	
	private Register regi;
	private String username;
	
	public RegisterChecker(Register reg) {
		super();
		this.regi = reg;
	}

	// run in background 
	@Override
	protected JSONObject doInBackground(String... params) //String... = String array damit ich aufruf doinBackground("sfsdf","sdfd","sdfsd") 
	{
		HttpClient client = new DefaultHttpClient(); // Http Client erstellen
		try {
			
			HttpPost httpPost = new HttpPost(params[0]); // Url
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
			nameValuePairs.add(new BasicNameValuePair("vorname", params[1]));
			nameValuePairs.add(new BasicNameValuePair("nachname", params[2]));
			nameValuePairs.add(new BasicNameValuePair("passwort", Utils.MD5(params[3])));
			nameValuePairs.add(new BasicNameValuePair("email", params[4]));
			nameValuePairs.add(new BasicNameValuePair("telnr", params[5]));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse httpResponse = client.execute(httpPost); // ausführen von httpreqeuest return HttpResponse (antwort von Server)
			username = params[4];
			//datei aus antwort von Server laden und in ein Json object umwandeln 
			String s =EntityUtils.toString(httpResponse.getEntity());
			//Log.d("test3",s);
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

	// nach hintergrund arbeit im vordergrund do login von login
	@Override
	protected void onPostExecute(JSONObject result) {
		regi.doReg(result,username);
	}
}
