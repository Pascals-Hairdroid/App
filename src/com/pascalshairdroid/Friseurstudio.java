package com.pascalshairdroid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.pascalshairdroid.R;

import kundenprofil.async.DataReloader;
import products.ProductFragment;
import teamlist.TeamListFragment;
import utils.PrefUtils;
import login_register.Login;
import login_register.LogoutTask;
import galerie.GalerieFragment;
import android.app.Activity;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

public class Friseurstudio extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			mNavigationDrawerFragment.reloadImage();
		}
	};
	private CharSequence mTitle;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Broadcast receiver registrieren auf alle BC die "reloadimage" als action haben h�ren
		LocalBroadcastManager.getInstance(this).registerReceiver(
				mMessageReceiver, new IntentFilter("reloadImage"));
		
		setContentView(R.layout.activity_home);
		//Fragment Manager initalisieren
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);

		// getTitle f�r ActionBar, damit Fragment Titel angezeigt wird
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		/*
		 * Toast.makeText( this, "Hallo " + getSharedPreferences(Login.PREF_TAG,
		 * MODE_PRIVATE) .getString(Login.LOGIN_USERNAME, ""),
		 * Toast.LENGTH_LONG).show();
		 */
		
		// Damit bei Internetverbindung die Daten immer aktualsiert werden
			String sessionId = PrefUtils.getPreferences(this, Login.PREF_TAG)
					.getString(Login.LOGIN_SESSION_ID, "");
			DataReloader dataReloader = new DataReloader(this,sessionId);
			// damit SessionID mit Post mitgeschickt werden kann
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("sessionId",
					sessionId));
			//PostRequest schicken
			dataReloader.execute(nameValuePairs);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Damit FragmentStatus wieder hergestellt wird (damit wenn Activity im Background ist wieder in den Vordergrund kommt )
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.reloadImage();
	}

	// wechselt die einzelnen Fragmente 
	@Override
	public void onNavigationDrawerItemSelected(int position, int childPosition) {
		// update the main content by replacing fragments
		// �berpr�ft ob Freischaltung gegenben f�r Termineintragen
		Boolean isFreigeschalten =  PrefUtils.getPreferences(this, Login.PREF_TAG).getBoolean(Login.LOGIN_FREIGESCHALTEN, false);
		List<Integer> integers = Arrays.asList(new Integer[] { 2 });
		if (!isFreigeschalten && integers.contains(Integer.valueOf(position))) {
			Toast.makeText(this, "Sie sind nicht freigeschaltet!", Toast.LENGTH_LONG).show();
			return;
		}
		// addToBackStack ist reihenfolge f�r Zur�ck Taste
		FragmentManager fragmentManager = getFragmentManager();
		switch (position) {
		case 5:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new GalerieFragment())
					.addToBackStack("1").commit();
			break;
		case 4:
			switch (childPosition) {
			case 0:
				fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								new ProductFragment("F�rben", 0))
						.addToBackStack("2").commit();
				break;
			case 1:
				fragmentManager
						.beginTransaction()
						.replace(R.id.container,
								new ProductFragment("Pflegen", 1))
						.addToBackStack("3").commit();
				break;
			}
			break;
		case 3:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new AngebotFragment())
					.addToBackStack("4").commit();
			break;
		case 2:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new TerminEintragenFragment())
					.addToBackStack("5").commit();
			break;
		case 0:
			fragmentManager.beginTransaction()
					.replace(R.id.container, new FriseurstudioFragment())
					.commit();
			break;
		case 1:
		default:
			switch (childPosition) {
			case 4:
				fragmentManager.beginTransaction()
						.replace(R.id.container, new KontaktFragment())
						.addToBackStack("7").commit();
				break;
			case 3:
				fragmentManager.beginTransaction()
						.replace(R.id.container, new OpentimeFragment())
						.addToBackStack("8").commit();
				break;
			case 2:
				fragmentManager
						.beginTransaction()
						.replace(R.id.container, new DienstleistungenFragment())
						.addToBackStack("9").commit();
				break;
			case 1:
				fragmentManager.beginTransaction()
						.replace(R.id.container, new TeamListFragment())
						.addToBackStack("10").commit();
				break;
			case 0:
				fragmentManager.beginTransaction()
						.replace(R.id.container, new DasStudioFragment())
						.addToBackStack("6").commit();
				break;
			}
		}
	}

	/*
	 * public void onSectionAttached(int number) { switch (number) { case 1:
	 * mTitle = getString(R.string.title_section1); break; case 2: mTitle =
	 * getString(R.string.title_section2); break; case 3: mTitle =
	 * getString(R.string.title_section3); break; case 4: mTitle =
	 * getString(R.string.title_section4); break; case 5: mTitle =
	 * getString(R.string.title_section5); break; case 6: mTitle =
	 * getString(R.string.title_section6); break;
	 * 
	 * } }
	 */
	// Titel des Fragments anzeigen 
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.home, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	// Logout Button
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.logout:
			// Shared Preferences holen
			SharedPreferences sharedPreferences =  PrefUtils.getPreferences(this, Login.PREF_TAG);
			// Session id und Username l�schen
			LogoutTask logoutTask = new LogoutTask();
			// Logout Task ausf�hren
			logoutTask.execute("http://www.pascals.at/v2/PHD_DBA/login.php",
					sharedPreferences.getString(Login.LOGIN_SESSION_ID, ""));
			//Daten aus SP l�schen
			sharedPreferences.edit().remove(Login.LOGIN_SESSION_ID)
					.remove(Login.LOGIN_USERNAME).commit();
			// auf Login Fragment weiterleiten
			Intent i = new Intent(Friseurstudio.this, Login.class);
			// Extra Logout setzen damit die Login AC einen Logout ausf�hrt
			i.putExtra("logout", "false");
			// Intent starten 
			startActivity(i);
			//FSAC beenden
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// BroadcastManager abmelden
		LocalBroadcastManager.getInstance(this).unregisterReceiver(
				mMessageReceiver);
		super.onDestroy();
	}
}
