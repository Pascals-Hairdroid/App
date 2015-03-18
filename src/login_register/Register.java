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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void Action()
	{
		Button b= (Button) findViewById(R.id.b_registierung);
        b.setOnClickListener(new OnClickListener() {
			
    		@Override
			public void onClick(View v) {
				//Loginchecker init
				RegisterChecker checker = new RegisterChecker(Register.this);
				
				// auslesen felder
				String vorname = ((EditText)findViewById(R.id.t_vorname)).getText().toString();
				String nachname = ((EditText)findViewById(R.id.t_nachname)).getText().toString();
				String passw = ((EditText)findViewById(R.id.t_passwort)).getText().toString();
				String email = ((EditText)findViewById(R.id.email)).getText().toString();
				String phoneNr = ((EditText)findViewById(R.id.t_phoneNr)).getText().toString();
				
				// hintergrund prozess starten, url ersetzten durch (K�nig)
				checker.execute("http://pastebin.com/raw.php?i=HSV5jBWG",vorname,nachname,passw, email, phoneNr);
			}
		});
	}
	
	public void doReg(JSONObject j) {
		try {
			if (j == null) {
				Toast.makeText(this, "Regist faild", Toast.LENGTH_LONG).show();
			} else {
				
				// wenn error gesetzt ist dann hol error aus json und zeig ihn an
				if (j.has("error")) {
					Toast.makeText(this, j.getString("error"),
							Toast.LENGTH_LONG).show();
				}else{
					// wenn alles ok dann
					String sessionId = j.getString("sessionId"); // session id aus Json holen
					String user = j.getString("username"); // lade username aus json
					SharedPreferences preferences = this.getSharedPreferences(Login.PREF_TAG, MODE_PRIVATE); // lade shared pref db
					// �ffne db zum bearbeiten (edit()), speicher session id , speichere username, sichere db
					preferences.edit().putString(Login.LOGIN_SESSION_ID, sessionId).putString(Login.LOGIN_USERNAME, user).commit();
					
					startActivity(new Intent(Register.this, Friseurstudio.class));
					// sofortiges finishen von dieser activity (beenden)
					finish();
				}

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}