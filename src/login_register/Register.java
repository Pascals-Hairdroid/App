package login_register;

import java.util.HashSet;

import kundenprofil.KundenProfil;

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
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		Action();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Intent intent;
		switch (id) {
		case R.id.back:
			intent = new Intent(Register.this, Login.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			this.startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void Action() {
		Button b = (Button) findViewById(R.id.b_registierung);
		b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Registchecker init
				if (Utils.isInternetAvailable(Register.this)) {
					RegisterChecker checker = new RegisterChecker(Register.this);

					// auslesen felder
					String vorname = ((EditText) findViewById(R.id.t_vorname))
							.getText().toString();
					String nachname = ((EditText) findViewById(R.id.t_nachname))
							.getText().toString();
					String passw = ((EditText) findViewById(R.id.t_passwort))
							.getText().toString();
					String email = ((EditText) findViewById(R.id.email))
							.getText().toString();
					String phoneNr = ((EditText) findViewById(R.id.t_phoneNr))
							.getText().toString();
					// Log.d("test2",email);
					// hintergrund prozess starten, url ersetzten durch (König)
					checker.execute(
							"http://www.pascals.at/v2/PHD_DBA/DBA.php?f=kundeEintragen",
							vorname, nachname, passw, email, phoneNr);
				} else {
					Toast.makeText(Register.this, "Keine Internetverbindung", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public void doReg(JSONObject j, String username) {
		try {
			// Log.d("dsfd",j.toString());
			if (j == null) {
				Toast.makeText(this, "Regist faild", Toast.LENGTH_LONG).show();
			} else {
				// wenn error gesetzt ist dann hol error aus json und zeig ihn
				// an
				if (j.has("errc")) {
					Toast.makeText(this, j.getString("viewmsg"),
							Toast.LENGTH_LONG).show();
				} else {
					// wenn alles ok dann
					Boolean hasSessionId = j.has("sessionId"); // session id aus
																// Json holen
					if (!hasSessionId)
						Toast.makeText(this, "Regist faild", Toast.LENGTH_LONG)
								.show();
					else {
						// für automatischen Login nach Registrierung
						String sessionId = j.getString("sessionId"); // session
																		// id
																		// aus
																		// Json
																		// holen
						JSONObject kunde = j.getJSONObject("kunde");
						HashSet<String> interessen = new HashSet<String>();
						for (int index = 0; index < kunde.getJSONArray(
								"interessen").length(); index++) {
							interessen.add(kunde.getJSONArray("interessen")
									.getJSONObject(index)
									.getString("bezeichnung"));

						}
						SharedPreferences preferences = this
								.getSharedPreferences(Login.PREF_TAG,
										MODE_PRIVATE); // lade shared pref db
						// öffne db zum bearbeiten (edit()), speicher session id
						// , speichere username, sichere db
						preferences
								.edit()
								.putString(Login.LOGIN_SESSION_ID, sessionId)
								.putString(Login.LOGIN_USERNAME, username)
								.putString(Login.LOGIN_VORNAME,
										kunde.getString("vorname"))
								.putString(Login.LOGIN_NACHNAME,
										kunde.getString("nachname"))
								.putString(Login.LOGIN_TELEFON,
										kunde.getString("telNr"))
								.putStringSet(Login.LOGIN_INTERESSEN,
										interessen)
								.putBoolean(Login.LOGIN_FREIGESCHALTEN,
										kunde.getBoolean("freischaltung"))

								.commit();
						Toast.makeText(this, "Registriert", Toast.LENGTH_LONG)
								.show();
						startActivity(new Intent(Register.this,
								Friseurstudio.class));
						// sofortiges finishen von dieser activity (beenden)
						finish();
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
