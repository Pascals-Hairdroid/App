package notificationSync;

import com.example.pascalshairdroid.Friseurstudio;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class UpdateNotificationStatus extends BroadcastReceiver{
	
	private static final String TAG = "UpdateNotificationStatus";
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "Statuschange aufgerufen für Nr.:" + intent.getLongExtra(DatabaseHelper.KEY_NUMMER, -1));
		new DatabaseHelper(context).setStatus(intent.getLongExtra(DatabaseHelper.KEY_NUMMER, -1), DatabaseHelper.V_STATUS_FERTIG);
		((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(intent.getIntExtra(SyncAdapter.NOTIFICATION_ID, -1));
		if(intent.getAction()!=null && intent.getAction().equals(SyncAdapter.SHOW_WEB)){
			Log.i(TAG, "Zeige Werbung...");
			
			context.startActivity(new Intent(context, Friseurstudio.class).putExtra(DatabaseHelper.KEY_NUMMER, intent.getLongExtra(DatabaseHelper.KEY_NUMMER, -1)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			
			context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(SyncAdapter.WERBUNG_URL_BEGINN + intent.getLongExtra(DatabaseHelper.KEY_NUMMER, -1))).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			
		}
		Log.i(TAG, "Fertig!");
	}
}
