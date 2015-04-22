package login_register;


import kundenprofil.KundenProfil;

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
				Log.d("test2",email);
				// hintergrund prozess starten, url ersetzten durch (König)
				checker.execute("http://www.pascals.at/v2/PHD_DBA/DBA.php?f=kundeEintragen",vorname,nachname,passw,email, phoneNr);
			}
		});
	}
	
	public void doReg(JSONObject j, String username) {
		try {
//			Log.d("dsfd",j.toString());
			if (j == null) {
				Toast.makeText(this, "Regist faild", Toast.LENGTH_LONG).show();
			} else {
				
				// wenn error gesetzt ist dann hol error aus json und zeig ihn an
				if (j.has("errc")) {
					Toast.makeText(this, j.getString("viewmsg"),
							Toast.LENGTH_LONG).show();
				}else{
					// wenn alles ok dann
					Boolean res = j.getBoolean("res"); // session id aus Json holen
					if (!res)
						Toast.makeText(this, "Regist faild", Toast.LENGTH_LONG).show();
					else
					{
						Toast.makeText(this, "Regist", Toast.LENGTH_LONG).show();
						startActivity(new Intent(Register.this, Login.class));
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
