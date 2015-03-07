package com.example.pascalshairdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login);
	}
	
	@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.splash_screen, menu);
	        Action();
	        return true;
	        
	    }
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
	    
	    private void Action()
		{
			Button b= (Button) findViewById(R.id.login);
	        b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(Login.this, Friseurstudio.class));
					
				}
			}); 
		}

}
