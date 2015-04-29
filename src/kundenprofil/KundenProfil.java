package kundenprofil;

import login_register.Login;
import login_register.Register;

import com.example.pascalshairdroid.Friseurstudio;
import com.example.pascalshairdroid.NavigationDrawerFragment;
import com.example.pascalshairdroid.R;
import com.example.pascalshairdroid.R.id;
import com.example.pascalshairdroid.R.layout;
import com.example.pascalshairdroid.R.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;


public class KundenProfil extends Activity {
	
	private int id;
	private EditText vor;
	private EditText nach;
	private EditText pw;
	private EditText mail;
	private EditText tele;
	final Context context = this;
	


	
	protected void onCreate(Bundle savedInstanceState) 
	{
				
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kunden_profil);
		
		 vor = (EditText) findViewById(R.id.vorname);
		 nach = (EditText) findViewById(R.id.nachname);
		 pw = (EditText) findViewById(R.id.passwort);
		 mail = (EditText) findViewById(R.id.email);
		 tele = (EditText) findViewById(R.id.phoneNr);
		 SharedPreferences sharedPreferences = getSharedPreferences(Login.PREF_TAG, Context.MODE_PRIVATE);
		 
		 vor.setText(sharedPreferences.getString(Login.LOGIN_VORNAME, ""));
		 nach.setText(sharedPreferences.getString(Login.LOGIN_NACHNAME, ""));
		 mail.setText(sharedPreferences.getString(Login.LOGIN_USERNAME, ""));
		 tele.setText(sharedPreferences.getString(Login.LOGIN_TELEFON, ""));
		 
		 vor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				LayoutInflater layoutInflater = LayoutInflater.from(context);
				View promptView = layoutInflater.inflate(R.layout.fragment_kundendaten, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			 // set prompts.xml to be the layout file of the alertdialog builder				
				alertDialogBuilder.setView(promptView);
				final EditText input = (EditText) promptView.findViewById(R.id.userInput);
		// setup a dialog window
				alertDialogBuilder
				        .setCancelable(false)
				        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog, int id) {
				               				 // get user input and set it to result
				                				       vor.setText(input.getText());
				                  }
				                	 })
				        .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
				                public void onClick(DialogInterface dialog, int id) 
				                {
				                	dialog.cancel();
				                }
				          });
				                		                 
				// create an alert dialog
				 AlertDialog alertD = alertDialogBuilder.create();
				 alertD.show();
				              
				    }
		});

		
	}
	
	public void showImage(View v)
	{	
			FragmentManager manager = getFragmentManager();
			FotoDialog fd = new FotoDialog();
			fd.show(manager, "asdf");
	}
	
	public void showInteressen(View v)
	{
			FragmentManager manager = getFragmentManager();
			InteressenDialog id = new InteressenDialog();
			id.show(manager, "asdf");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kunden_profil, menu);
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
		case R.id.cancel:
			intent = new Intent(KundenProfil.this, Friseurstudio.class);
	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	        this.startActivity(intent);
			break;
		case R.id.save:
			
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
		
		
	
}
