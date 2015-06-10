package login_register;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import kundenprofil.async.ImageDownloader;

import org.json.JSONException;
import org.json.JSONObject;

import utils.Utils;

import com.example.pascalshairdroid.Friseurstudio;
import com.example.pascalshairdroid.R;
import com.example.pascalshairdroid.R.id;
import com.example.pascalshairdroid.R.layout;
import com.example.pascalshairdroid.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class Login extends Activity {

	// Shared Preferences DB = Name von DB
	public static final String PREF_TAG = "abc";
	// Feldname von der PHP Session ID
	public static final String LOGIN_SESSION_ID = "def";
	public static final String LOGIN_USERNAME = "ghi";
	public static final String LOGIN_VORNAME = "sfdgsdfg ";
	public static final String LOGIN_NACHNAME = "gsdfg sdhi";
	public static final String LOGIN_TELEFON = "shsasdf";
	public static final String LOGIN_INTERESSEN = "5arhje 7e";
	public static final String LOGIN_FREIGESCHALTEN = "abc";
	public static final String LOGIN_LAST_IMAGE_UPDATE = "dsdf";
	public static final String LOGIN_IMAGE_URL = "sdfdsfew";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SharedPreferences preferences = this.getSharedPreferences(PREF_TAG,
				MODE_PRIVATE); // lade shared pref db
		// wenn die Session in der DB gespeichert dann überspringe login vorgang
		// unnd wechsle sofort in friseurstudio class

		if (preferences.contains(LOGIN_SESSION_ID)) {
			startActivity(new Intent(Login.this, Friseurstudio.class));
			finish();
		} else {
			setContentView(R.layout.fragment_login);
			Action();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);

		return true;
	}

	// Sign up aufruf
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.sign_up:
			startActivity(new Intent(Login.this, Register.class));
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// actionlistener für login Button und auslesen von email, pw feld
	private void Action() {
		Button b = (Button) findViewById(R.id.login);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Utils.isInternetAvailable(Login.this)) {

					// Loginchecker init
					LoginChecker checker = new LoginChecker(Login.this);

					// auslesen felder
					String email = ((EditText) findViewById(R.id.email))
							.getText().toString();
					String password = ((EditText) findViewById(R.id.password))
							.getText().toString();

					// hintergrund prozess starten, url ersetzten durch (König)

					checker.execute(
							"http://www.pascals.at/v2/PHD_DBA/login.php",
							email, password);
				} else {
					Toast.makeText(Login.this,
							"Keine Internetverbindung",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	// Login vorgang
	public void doLogin(JSONObject j, String username) {

		try {
//			 j = new JSONObject("{\"sessionId\":4568765467886546788654678, \"kunde\": {\"username\":\"username\",\"interessen\":[],\"vorname\":\"\",\"nachname\":\"\",\"freischaltung\":true,\"telNr\":\"\"}}");

			if (j == null) {
				Toast.makeText(this, "Login faild", Toast.LENGTH_LONG).show();

			} else {

				// wenn error gesetzt ist dann hol error aus json und zeig ihn
				// an
				if (j.has("errc")) {
					Toast.makeText(this, j.getString("viewmsg"),
							Toast.LENGTH_LONG).show();
				} else {
					// wenn alles ok dann
					String sessionId = j.getString("sessionId"); // session id
																	// aus Json
																	// holen
					JSONObject kunde = j.getJSONObject("kunde");
					HashSet<String> interessen = new HashSet<String>();
					for (int index = 0; index < kunde
							.getJSONArray("interessen").length(); index++) {
						interessen.add(kunde.getJSONArray("interessen")
								.getJSONObject(index).getString("bezeichnung"));
					}

					// für Kundenprofil die gespeicherten Daten in die
					// Preferences schreiben
					SharedPreferences preferences = this.getSharedPreferences(
							PREF_TAG, MODE_PRIVATE); // lade shared pref db
					// öffne db zum bearbeiten (edit()), speicher session id ,
					// speichere username, sichere db
					long lastImgUpdate = preferences.getLong(LOGIN_LAST_IMAGE_UPDATE, 0);
					String lastImagePath = preferences.getString(LOGIN_IMAGE_URL, "abc");
					preferences
							.edit()
							.putString(LOGIN_SESSION_ID, sessionId)
							.putString(LOGIN_USERNAME, username)
							.putString(LOGIN_VORNAME,
									kunde.getString("vorname"))
							.putString(LOGIN_NACHNAME,
									kunde.getString("nachname"))
							.putString(LOGIN_TELEFON, kunde.getString("telNr"))
							.putLong(LOGIN_LAST_IMAGE_UPDATE, j.getLong("picdate"))
							.putString(LOGIN_IMAGE_URL, kunde.getString("foto"))
							.putStringSet(LOGIN_INTERESSEN, interessen)
							.putBoolean(LOGIN_FREIGESCHALTEN,
									kunde.getBoolean("freischaltung")).commit();
					
					// Bei Gleichen User neuerstes Bild anzeigen
					boolean sameUser = lastImagePath.equals(preferences.getString(LOGIN_IMAGE_URL, "abc")) ;
					if(lastImgUpdate <  preferences.getLong(LOGIN_LAST_IMAGE_UPDATE, 0) || !sameUser){
						if(!sameUser){
							File f = new File(getFilesDir() + "/myImage.jpg");
							f.delete();
						}
						
						ImageDownloader downloader = new ImageDownloader(preferences.getString(LOGIN_IMAGE_URL, "abc"), this);
						downloader.execute();
					}
					
					startActivity(new Intent(Login.this, Friseurstudio.class));
					// sofortiges finishen von dieser activity (beenden)
					finish();
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
