package notificationSync;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UpdateNotificationStatusActivity extends Activity{
	
	private static final String TAG = "UpdateNotificationStatusActivity";

	protected void onNewIntent(Intent intent) {
		new DatabaseHelper(getApplicationContext()).setStatus(intent.getLongExtra(DatabaseHelper.KEY_NUMMER, -1), DatabaseHelper.V_STATUS_FERTIG);
		Log.i(TAG, "Statuschange Completed.");
	}
	
}
