package notificationSync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    public static final int DATABASE_VERSION = 1;
    
    public static final String DATABASE_NAME = "PHD";
    
    public static final String TABLE_WERBUNG = "Werbung";

    public static final String KEY_NUMMER = "nummer"; //PK
    public static final String KEY_TITEL = "titel";
    public static final String KEY_TEXT = "text";
    public static final String KEY_FOTO = "fotos";
    public static final String KEY_STATUS = "status";
    
    public static final int V_STATUS_NEU = 0;
    public static final int V_STATUS_GEZEIGT = 1;
    public static final int V_STATUS_FERTIG = 2;
    
    // CREATE TABLE String
    private static final String CREATE_TABLE_ROUTE = "CREATE TABLE "
            + TABLE_WERBUNG + "(" + KEY_NUMMER + " INTEGER PRIMARY KEY,"
            + KEY_TITEL + " TEXT,"
            + KEY_TEXT + " TEXT,"
            + KEY_STATUS + " INTEGER,"
            + KEY_FOTO + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Erstes Ausführen erzeugt Tabelle    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ROUTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
    	
    }
    // Methode zur Statusänderung
    public boolean setStatus(long nummer, int status){
    	if(status!=V_STATUS_NEU && status!=V_STATUS_GEZEIGT && status!=V_STATUS_FERTIG)
    		return false;
    	ContentValues values = new ContentValues();
    	values.put(KEY_STATUS, status);
    	Log.i(TAG, "Statuschange für " + nummer + " auf Status=" + status + "...");
    	boolean ret = getWritableDatabase().update(TABLE_WERBUNG, values, KEY_NUMMER+"="+nummer, null)>0?true:false;
    	if(ret)
    		Log.i(TAG, "Statuschange für " + nummer + " auf Status=" + status + " fertig.");
    	return ret;
    }
    // Methode zum holen der neuen Werbung für Notifications
    public Cursor getNewNotifications(){
    	SQLiteDatabase db = getReadableDatabase();
    	Cursor c = db.rawQuery("SELECT * FROM " + TABLE_WERBUNG + " WHERE " + KEY_STATUS + "<" + V_STATUS_FERTIG, null);
		return c;
    }
}
