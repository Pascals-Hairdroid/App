package com.example.pascalshairdroid;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Home extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
//		if(true){
//			startActivity(new Intent(this, Register.class));
//		}
		
		
		setContentView(R.layout.activity_home);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		
		
		FragmentManager fragmentManager = getFragmentManager();
		switch(position){
		case 8:
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,new DienstleistungenFragment()).commit();
			break;
		case 7:
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,new GalerieFragment()).commit();
			break;
		case 6:
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,new ProductFragment()).commit();
			break;
		case 5:
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,new OpentimeFragment()).commit();
			break;
		case 4:
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,new KontaktFragment()).commit();
			break;
		case 3:
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,new TeamListFragment()).commit();
			break;
		case 2:
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,new AngebotFragment()).commit();
			break;
		
		case 1:
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,new TerminEintragenFragment()).commit();
			break;
		case 0:
		default:
			fragmentManager
			.beginTransaction()
			.replace(R.id.container,new HomeFragment()).commit();
			break;
			
			
		
		
		
		}
		
		
		
		
		
	}

	/*public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
		case 5:
			mTitle = getString(R.string.title_section5);
			break;
		case 6:
			mTitle = getString(R.string.title_section6);
			break;
		
		}
	}
*/
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

	

}
