package kundenprofil;

import com.example.pascalshairdroid.R;
import com.example.pascalshairdroid.R.id;
import com.example.pascalshairdroid.R.layout;
import com.example.pascalshairdroid.R.menu;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class KundenProfil extends Activity {
	
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kunden_profil);
		
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void showImage()
	{	
			FragmentManager manager = getFragmentManager();
			FotoDialog fd = new FotoDialog();
			fd.show(manager, "asdf");
	}
	
	public void showDaten()
	{
			FragmentManager manager = getFragmentManager();
			DatenDialog dd = new DatenDialog();
			dd.show(manager, "asdf");
	}
	
	public void showInteressen()
	{
			FragmentManager manager = getFragmentManager();
			InteressenDialog id = new InteressenDialog();
			id.show(manager, "asdf");
	}
		
		
	
}
