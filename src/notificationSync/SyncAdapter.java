package notificationSync;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import login_register.Login;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.PrefUtils;

import com.pascalshairdroid.R;

import android.accounts.Account;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class SyncAdapter extends AbstractThreadedSyncAdapter {

	public static final String LASTSYNC = "lastSync";
	public static final String NOTIFICATION_ID = "nid";
	public static final String WERBUNG_URL_BEGINN = "http://pascals.at/v2/Seiten/Angebote.php?web=1&nummer=";
	public static final String SHOW_WEB = "show_werbung";
	public static final String BROWSER_URL = "browser_url";
	private static final String URL = "http://www.pascals.at/v2/PHD_DBA/Notification_Service.php";
	private static final long REMEMBER_ID = 0;

	private String TAG = "SyncAdapter";
	private DatabaseHelper dbh;
	private Context ctx;

	public SyncAdapter(Context context, boolean autoInitialize) {
		super(context, autoInitialize);
		ctx = context;
	}

	public SyncAdapter(Context context, boolean autoInitialize,
			boolean allowParallelSyncs) {
		super(context, autoInitialize, allowParallelSyncs);
		ctx = context;

	}
	// Startmethode für Sync
	@Override
	public void onPerformSync(Account account, Bundle extras, String authority,
			ContentProviderClient provider, SyncResult syncResult) {
		Log.i(TAG, "performing sync...");
		dbh = new DatabaseHelper(getContext());
		try {
			// Daten holen
			JSONObject j = doSync();
			Log.d(TAG, "Json-Object: "+j.toString());
			// Daten Speichern
			boolean success = insert(j.getJSONArray("werbungen"));
			Log.i(TAG, "insertion: " + (success ? "OK" : "FAIL"));
			Log.d(TAG, "LastSync: " + j.getLong(LASTSYNC));
			PrefUtils.getPreferences(ctx, Login.PREF_TAG)
					.edit().putLong(LASTSYNC, j.getLong(LASTSYNC)).commit();
			// Notifications erstellen und anzeigen
			ArrayList<Notification> notifications = doNotifications();
			Log.i(TAG, (notifications!=null?notifications.size():0) + " new Notifications.");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// Vorberteitung zum holen der Daten
	private JSONObject doSync() {

		
		SharedPreferences sp = PrefUtils.getPreferences(ctx, Login.PREF_TAG);
		
		// Daten aus shared Preferences holen
		long date = sp.getLong(LASTSYNC, 0);

		String uid = sp.getString(Login.LOGIN_USERNAME, null);
		return doRequest(URL, date, uid);
	}
	// Abfrage abschicken und Daten holen
	private JSONObject doRequest(String url, long date, String uid) {
		HttpClient client = new DefaultHttpClient();

		try {
			HttpPost httpPost = new HttpPost(url); // Url
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
			if(date!=0){
				Log.d(TAG, "date: " + date);
				nameValuePairs.add(new BasicNameValuePair("date", date + ""));
			}
			if(uid!=null){
				Log.d(TAG, "uid: " + uid);
				nameValuePairs.add(new BasicNameValuePair("email", uid));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			Log.d(TAG, "Execute HttpPost...");
			HttpResponse httpResponse = client.execute(httpPost);
			String s = EntityUtils.toString(httpResponse.getEntity(),"UTF-8");
			Log.d(TAG, "Response: " + s);
			try{
				s=unescape(s);
				Log.d(TAG, "Encoded Response: "+s);
				 JSONObject j = new JSONObject(s);
				 return j;
			}
			catch(Exception exc){
				Log.d(TAG, "im o.");
			}
			
			
			
			return new JSONObject(s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// Hilfsmethode zum escapen 
	private static String unescape(String s) {
		return s.replace("\\u00c3\\u0083\\u00c2\\u00b6", "ö")
				.replace("\\u00c3\\u0083\\u00e2\\u0080\\u0093", "Ö")
				.replace("\\u00c3\\u0083\\u00c2\\u00a4", "ä")
				.replace("\\u00c3\\u0083\\u00e2\\u0080\\u009e", "Ä")
				.replace("\\u00c3\\u0083\\u00c2\\u00bc", "ü")
				.replace("\\u00c3\\u0083\\u00c5\\u0093", "Ü")
				.replace("\\u00c3\\u0083\\u00c5\\u00b8", "ß")
				.replace("\\u00c3\\u00a2\\u00e2\\u0080\\u009a\\u00c2\\u00ac", "€")
				.replace("\\u00c3\\u0082\\u00c2\\u00a7", "§")
				.replace("\\u00c3\\u0082\\u00c2\\u00b4", "´")
				.replace("\\u00c3\\u0083\\u00c2\\u00a1", "á")
				.replace("\\u00c3\\u0083\\u00c2\\u00a0", "à")
				.replace("\\u00c3\\u0083\\u00c2\\u00a9", "é")
				.replace("\\u00c3\\u0083\\u00c2\\u00a8", "è")
				.replace("\\u00c3\\u0082\\u00c2\\u00b2", "²")
				.replace("\\u00c3\\u0082\\u00c2\\u00b3", "³")
				.replace("\\u00c3\\u0082\\u00c2\\u00b0", "°")
				.replace("\\r\\n", "\n")
				.replace("\\n", "\n");
		}
	
	// Speichern von Werbung in der Datenbank
	private boolean insert(JSONArray werbungen) {
		SQLiteDatabase db = dbh.getWritableDatabase();
		JSONObject werbung = null;
		ContentValues values = null;
		for (int i = 0; i < werbungen.length(); i++) {
			try {
				werbung = werbungen.getJSONObject(i);
				values = new ContentValues();
				values.put(DatabaseHelper.KEY_NUMMER,
						werbung.getLong(DatabaseHelper.KEY_NUMMER));
				values.put(DatabaseHelper.KEY_TITEL,
						werbung.getString(DatabaseHelper.KEY_TITEL));
				values.put(DatabaseHelper.KEY_TEXT,
						werbung.getString(DatabaseHelper.KEY_TEXT));
				values.put(DatabaseHelper.KEY_FOTO,
						werbung.getString(DatabaseHelper.KEY_FOTO));
				values.put(DatabaseHelper.KEY_STATUS, 0);
				// Handling für Aktualisierung von noch nicht fertigen Notifications (Fertig = angezeigt und weggetippt/wischt bzw. angetippt) 
				if (db.insert(DatabaseHelper.TABLE_WERBUNG, "", values) == -1){
					if(werbung.getLong(DatabaseHelper.KEY_NUMMER)==REMEMBER_ID){
						Cursor c = db.rawQuery("SELECT "+DatabaseHelper.KEY_FOTO+" FROM "+DatabaseHelper.TABLE_WERBUNG+" WHERE "+DatabaseHelper.KEY_NUMMER+"="+werbung.getLong(DatabaseHelper.KEY_NUMMER),null);
						if(c.moveToFirst())
							if(!c.getString(c.getColumnIndex(DatabaseHelper.KEY_FOTO)).equals(werbung.getString(DatabaseHelper.KEY_FOTO))){
								values.remove(DatabaseHelper.KEY_NUMMER);
								db.update(DatabaseHelper.TABLE_WERBUNG, values, DatabaseHelper.KEY_NUMMER + " = " + REMEMBER_ID, null);
							}
					}else{
						values.remove(DatabaseHelper.KEY_NUMMER);
						values.remove(DatabaseHelper.KEY_STATUS);
						db.update(DatabaseHelper.TABLE_WERBUNG, values, DatabaseHelper.KEY_NUMMER + " = " + werbung.getLong(DatabaseHelper.KEY_NUMMER) + " AND " + DatabaseHelper.KEY_STATUS + " < 2", null);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
		
	}
	// Erzeugen von Notifications aus der Datenbank 
	private ArrayList<Notification> doNotifications() {
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		Cursor werbungen = dbh.getNewNotifications();
		NotificationManager nm = (NotificationManager) getContext()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = null;
		long nummer = 0;
		if (werbungen.moveToFirst()) {
			do {
				nummer = werbungen.getLong(werbungen.getColumnIndex(DatabaseHelper.KEY_NUMMER));
				notification = werbungToNotification(nummer,
						werbungen.getString(werbungen
								.getColumnIndex(DatabaseHelper.KEY_TITEL)),
						werbungen.getString(werbungen
								.getColumnIndex(DatabaseHelper.KEY_TEXT)),
						werbungen.getString(werbungen
								.getColumnIndex(DatabaseHelper.KEY_FOTO)));
				Log.i(TAG, "Notifying Notification:" + (int) nummer);
				nm.notify((int) nummer, notification);
				Log.i(TAG, "Notified.");
				notifications.add(notification);
				dbh.setStatus(
						nummer,
						DatabaseHelper.V_STATUS_GEZEIGT);
			} while (werbungen.moveToNext());
		}

		if (notifications.size() < 1) {
			return null;

		}
		return notifications;
	}
	// Erzeugen einer Notification aus einer Werbung
	private Notification werbungToNotification(long nummer, String titel,
			String text, String foto) {
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				getContext())
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(titel)
				.setContentText(text)
				.setContentIntent(
						PendingIntent.getBroadcast(
								getContext(), 
								(int) nummer,
								new Intent(getContext(),
										UpdateNotificationStatus.class)
										.putExtra(DatabaseHelper.KEY_NUMMER, nummer).putExtra(NOTIFICATION_ID, (int) nummer)
										.setAction(SHOW_WEB)
										,PendingIntent.FLAG_UPDATE_CURRENT))
				.setDeleteIntent(
						PendingIntent.getBroadcast(getContext(),(int) nummer, 
								new Intent(getContext(), UpdateNotificationStatus.class)
									.putExtra(DatabaseHelper.KEY_NUMMER, nummer)
									.putExtra(NOTIFICATION_ID, (int) nummer)
									,PendingIntent.FLAG_UPDATE_CURRENT));
		Log.d(TAG, "Notification build. Nr: "+nummer);
		return builder.build();
	}
	
}
