package com.example.pascalshairdroid;

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
public class RegisterChecker extends AsyncTask<String, Integer, JSONObject> {
	
	private Register regi;
	
	
	
	public RegisterChecker(Register reg) {
		super();
		this.regi = reg;
	}

	// run shit in background 
	@Override
	protected JSONObject doInBackground(String... params) //String... = String array damit ich aufruf doinBackground("sfsdf","sdfd","sdfsd") 
	{
		HttpClient client = new DefaultHttpClient(); // Http Client erstellen
		try {
			
			HttpPost httpPost = new HttpPost(params[0]); // Url
			httpPost.getParams().setParameter("vorname", params[1]); // vorname
			httpPost.getParams().setParameter("nachname", params[2]); // nachname
			httpPost.getParams().setParameter("password", params[3]); // pass
			httpPost.getParams().setParameter("email", params[4]); // email
			httpPost.getParams().setParameter("telNr", params[5]);// telenr
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
		regi.doReg(result);
	}
}
