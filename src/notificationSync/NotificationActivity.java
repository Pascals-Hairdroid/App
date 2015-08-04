package notificationSync;

import login_register.Login;
import utils.Utils;

import com.pascalshairdroid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class NotificationActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_angebot);
	    getActionBar().setDisplayHomeAsUpEnabled(true);

		
		if(getIntent()!=null && getIntent().getBooleanExtra(SyncAdapter.SHOW_WEB, false)){
			long nummer = getIntent().getLongExtra(DatabaseHelper.KEY_NUMMER, -1);
			if(nummer != -1){
				// TODO: Zeige Werbung an
				
				WebView view = (WebView) findViewById(R.id.notification_webview);
				view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
				if (Utils.isInternetAvailable(this)) {
					view.loadUrl(SyncAdapter.WERBUNG_URL_BEGINN + nummer);
				} else {
					view.loadData("<html><head><style>body{background-color:lightgray;margin-top:25%;}h2{color:orange;text-align:center;Font-Family:Calibri;}</style><title></title></head><body><h2>Es konnte keine Internetverbindung hergestellt werden!</h2></body></html>", "text/html", "UTF-8");
				}
				
			}
		}
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    // Respond to the action bar's Up/Home button
	    case android.R.id.home:
	    	Intent i = new Intent(this, Login.class);
	        startActivity(i);
	    	this.finish();
	        return true;
	    }
	    return super.onOptionsItemSelected(item);
	}
}
