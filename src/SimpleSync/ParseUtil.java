package SimpleSync;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ParseUtil {
    private final String TAG = "ParseUtil";

    public ParseUser userSignUp(String name, String email, String pass) throws Exception {

        Log.i(TAG, "userSignUp");
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("email", email);
        List<ParseUser> lstPU = parseQuery.find();
        if (lstPU.size() > 0) {
            Log.i(TAG, "Parse User Already Exist");
            return lstPU.get(0);
        } else {
            Log.i(TAG, "Create new Parse User");
            ParseUser pu = new ParseUser();
            pu.setEmail(email);
            pu.setUsername(name);
            pu.setPassword(pass);

            pu.signUp();
            createNotificationClass();
            return pu;
        }

    }
    public void createNotificationClass(){
        try {
            ParseObject notificationClass = new ParseObject(DatabaseHelper.TABLE_WERBUNG);
            notificationClass.put(DatabaseHelper.KEY_NUMMER, Integer.MAX_VALUE - 1);
            notificationClass.put(DatabaseHelper.KEY_TITEL, "titel 1");
            notificationClass.put(DatabaseHelper.KEY_TEXT, "text 1");
            notificationClass.put(DatabaseHelper.KEY_DATUM, 12345678);
            notificationClass.put(DatabaseHelper.KEY_FOTO, "foto 1");
            notificationClass.put(DatabaseHelper.KEY_STATUS, 0);
            
            notificationClass.save();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public ParseUser userSignIn(String user, String pass) throws Exception {

        Log.d(TAG, "userSignIn");

        ParseUser pu = ParseUser.logIn(user, pass);
        Log.d(TAG, "Session Token " + pu.getSessionToken());

        return pu;

    }

    public void pullWerbung(Context ctx, ParseObject po, ContentProviderClient provider) {
        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.KEY_NUMMER, po.getInt(DatabaseHelper.KEY_NUMMER));
            values.put(DatabaseHelper.KEY_TITEL, po.getString(DatabaseHelper.KEY_TITEL));
            values.put(DatabaseHelper.KEY_TEXT, po.getString(DatabaseHelper.KEY_TEXT));
            values.put(DatabaseHelper.KEY_DATUM, po.getLong(DatabaseHelper.KEY_DATUM));
            values.put(DatabaseHelper.KEY_FOTO, po.getString(DatabaseHelper.KEY_FOTO));
            values.put(DatabaseHelper.KEY_STATUS, po.getInt(DatabaseHelper.KEY_STATUS));
            
            
            Uri uri = provider.insert(SimpleContentProvider.CONTENT_URI_WERBUNG, values);
            Log.i(TAG,"Insert URI :" + uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
