package notificationSync;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UpdateNotificationStatus extends BroadcastReceiver{
	
	private static final String TAG = "UpdateNotificationStatus";
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "Statuschange aufgerufen für Nr.:" + intent.getLongExtra(DatabaseHelper.KEY_NUMMER, -1));
		try{
			new DatabaseHelper(context).setStatus(intent.getLongExtra(DatabaseHelper.KEY_NUMMER, -1), DatabaseHelper.V_STATUS_FERTIG);
		}catch(Exception e){
			e.printStackTrace();
		}
		((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(intent.getIntExtra(SyncAdapter.NOTIFICATION_ID, -1));
		if(intent.getAction()!=null && intent.getAction().equals(SyncAdapter.SHOW_WEB)){
			Log.i(TAG, "Zeige Werbung...");
			
			context.startActivity(new Intent(context, NotificationActivity.class)
			.putExtra(DatabaseHelper.KEY_NUMMER, intent.getLongExtra(DatabaseHelper.KEY_NUMMER, -1))
			.putExtra(SyncAdapter.SHOW_WEB, true)
			.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			
		}
		Log.i(TAG, "Fertig!");
	}
}
