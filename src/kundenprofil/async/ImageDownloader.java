package kundenprofil.async;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.example.pascalshairdroid.Friseurstudio;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class ImageDownloader extends AsyncTask<String, Integer, Boolean> {

	private String url;
	private Context c;

	public ImageDownloader(String url, Context c) {
		super();
		this.url = url;
		this.c = c;
	}

	// run shit in background
	@Override
	protected Boolean doInBackground(String... params) {
		try {
			/* Open a connection to that URL. */
			URLConnection ucon = new URL(url).openConnection();
			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			/* Convert the Bytes read to a String. */
			FileOutputStream fos = c.openFileOutput("myImage.jpg",
					Context.MODE_PRIVATE);
			int current = 0;
			while ((current = is.read()) != -1) {
				fos.write(current);
			}

			fos.flush();
			fos.close();
			return true;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		Intent intent = new Intent("reloadImage");
		LocalBroadcastManager.getInstance(c).sendBroadcast(intent);
		Toast.makeText(c, "done", Toast.LENGTH_LONG).show();

	}
}
