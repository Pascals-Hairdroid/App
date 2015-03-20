package login_register;

import org.json.JSONException;
import org.json.JSONObject;

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

public class Login extends Activity {

	//Shared Preferences DB = Name von DB
	public static final String PREF_TAG = "iudsahlifahdsliuf";
	//Feldname von der PHP Session ID
	public static final String LOGIN_SESSION_ID = "dskhkhfdskfdsfdskkds";
	
	public static final String LOGIN_USERNAME = "sdfsdfdsfgkljöii5ertz";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences preferences = this.getSharedPreferences(PREF_TAG, MODE_PRIVATE); // lade shared pref db
		if(preferences.contains(LOGIN_SESSION_ID)){
			startActivity(new Intent(Login.this, Friseurstudio.class));
			finish();
		}
		setContentView(R.layout.fragment_login);
		Action();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);

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
				//Loginchecker init
				LoginChecker checker = new LoginChecker(Login.this);
				
				// auslesen felder
				String email = ((EditText)findViewById(R.id.email)).getText().toString();
				String password = ((EditText)findViewById(R.id.password)).getText().toString();
				
				// hintergrund prozess starten, url ersetzten durch (König)
				checker.execute("http://pastebin.com/raw.php?i=HSV5jBWG",email,password);
			}
		});
	}

	// Login vorgang
	public void doLogin(JSONObject j) {
		try {
			if (j == null) {
				Toast.makeText(this, "Login faild", Toast.LENGTH_LONG).show();
			} else {
				
				// wenn error gesetzt ist dann hol error aus json und zeig ihn an
				if (j.has("error")) {
					Toast.makeText(this, j.getString("error"),
							Toast.LENGTH_LONG).show();
				}else{
					// wenn alles ok dann
					String sessionId = j.getString("sessionId"); // session id aus Json holen
					String user = j.getString("username"); // lade username aus json
					SharedPreferences preferences = this.getSharedPreferences(PREF_TAG, MODE_PRIVATE); // lade shared pref db
					// öffne db zum bearbeiten (edit()), speicher session id , speichere username, sichere db
					preferences.edit().putString(LOGIN_SESSION_ID, sessionId).putString(LOGIN_USERNAME, user).commit();
					
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
