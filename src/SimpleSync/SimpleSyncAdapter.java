package SimpleSync;

import android.R.array;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SyncResult;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.example.pascalshairdroid.R;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

import utils.Utils;

public class SimpleSyncAdapter extends AbstractThreadedSyncAdapter {

    private String TAG = "SimpleSyncAdapter";
    private final AccountManager mAccountManager;
    private final String URL = "http://www.pascals.at/v2/PHD_DBA/Notification_Service.php";
    public static final String LASTSYNC = "lastSync"; 
    private ParseUtil parseUtil;
    private ParseUser parseUser;
    private ContextWrapper cw;
    public SimpleSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mAccountManager = AccountManager.get(context);
        cw = new ContextWrapper(getContext());
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        try{
        	JSONObject j = doSync();
        	insert(j);
        	cw.getSharedPreferences(Login.PREF_TAG, Context.MODE_PRIVATE).edit().putLong(LASTSYNC, j.getLong(LASTSYNC));
        	
//            Log.i(TAG, "Loading Local data to array");
//            // create object of parse utility
//            parseUtil = new ParseUtil();
//            // fetch current user
//            parseUser = ParseUser.getCurrentUser();
//            
//            // For simplicity I am implementing only single side sync (From Parse to Local)
//            // you need to implement by directional sync in this method
//            // delete all local todos
//            // SyncDelete(provider);
//            
//            // pull and add to do in local
//            SyncAdd(provider);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private JSONObject doSync(){
    	
//    	Daten aus shared Preferences holen, aber wie???
    	Date date = new Date(cw.getSharedPreferences(
				Login.PREF_TAG, Context.MODE_PRIVATE).getLong(
							LASTSYNC, 0));
    	Set<String> myInteressen = cw.getSharedPreferences(
				Login.PREF_TAG, Context.MODE_PRIVATE).getStringSet(
				Login.LOGIN_INTERESSEN, new HashSet<String>());
//    	// Testen:
//    	//---
//    	Date date = new Date(0);
//    	List<String> myInteressen = new ArrayList<String>();
//    	myInteressen.add("1");
//    	myInteressen.add("2");
//    	myInteressen.add("3");
//    	myInteressen.add("4");
//    	myInteressen.add("6");
//    	//---
    	
    	String[] interessen = (String[])myInteressen.toArray();
    	
    	Log.d(TAG,date.toString());
    	Log.d(TAG,interessen.toString());
    	
    	
    	return doRequest(URL, date, interessen);
    }
    
    private JSONObject doRequest(String url, Date date, String[] interessen){
    	HttpClient client = new DefaultHttpClient();
    	
    	try {
			HttpPost httpPost = new HttpPost(url); // Url
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
			nameValuePairs.add(new BasicNameValuePair("date", date.getTime() + ""));
			nameValuePairs.addAll(arrayAsNameValuePairs("interessen", interessen));
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse httpResponse = client.execute(httpPost); // ausführen von httpreqeuest return HttpResponse (antwort von Server)
			//datei aus antwort von Server laden und in ein Json object umwandeln 
			String s =EntityUtils.toString(httpResponse.getEntity());
			//Log.d("test3",s);
			return new JSONObject(s);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    private List<BasicNameValuePair> arrayAsNameValuePairs(String name, String[] values){
		List<BasicNameValuePair> basicNameValuePairs = new ArrayList<BasicNameValuePair>();
		for (int i = 0; i < values.length; i++) {
			basicNameValuePairs.add(new BasicNameValuePair("interessen["+i+"]", values[i]));
		}
		return basicNameValuePairs;
	}
    
    private boolean insert(JSONObject j){
    	DatabaseHelper dbh = new DatabaseHelper(getContext());
    	SQLiteDatabase db = dbh.getWritableDatabase();
    	try {
			JSONArray werbungen = j.getJSONArray("werbungen");
			List<String> rows = new ArrayList<String>();
			long r = -1;
			JSONObject werbung = null;
			ContentValues values = null;
			for(int i = 0; i < werbungen.length();i++){
				try{
					werbung = werbungen.getJSONObject(i);
					values = new ContentValues();
					values.put(DatabaseHelper.KEY_NUMMER, werbung.getInt(DatabaseHelper.KEY_NUMMER));
				    values.put(DatabaseHelper.KEY_TITEL, werbung.getString(DatabaseHelper.KEY_TITEL));
				    values.put(DatabaseHelper.KEY_TEXT, werbung.getString(DatabaseHelper.KEY_TEXT));
				    values.put(DatabaseHelper.KEY_DATUM, werbung.getLong(DatabaseHelper.KEY_DATUM));
				    values.put(DatabaseHelper.KEY_FOTO, werbung.getString(DatabaseHelper.KEY_FOTO));
				    values.put(DatabaseHelper.KEY_STATUS, 0);
					r = db.insert(DatabaseHelper.TABLE_WERBUNG, "", values);
					if(r!=-1)rows.add(r+"");
				}
				catch(Exception e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(rows.size() == werbungen.length())
				return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
    
    
    
//    private void SyncDelete(ContentProviderClient provider){
//        try{
//            int count =provider.delete(SimpleContentProvider.CONTENT_URI_WERBUNG,null,null);
//            Log.i(TAG,"Rows Deleted : "+ count);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    private void SyncAdd(ContentProviderClient provider){
//        try{
//            ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery(DatabaseHelper.TABLE_WERBUNG);
//            List<ParseObject> allTodos=  parseQuery.find();
//            for(ParseObject po : allTodos){
//                parseUtil.pullWerbung(getContext(),po,provider);
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
