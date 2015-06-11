package notificationSync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context ctx;
    private static final String TAG = "DatabaseHelper";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PHD";

    // Table Names
    public static final String TABLE_WERBUNG = "Werbung";

    // Common column names
    public static final String KEY_NUMMER = "nummer";

    // Routes Table - column names
    public static final String KEY_TITEL = "titel";
    public static final String KEY_TEXT = "text";
    //public static final String KEY_DATUM = "datum";
    public static final String KEY_FOTO = "fotos";
    public static final String KEY_STATUS = "status";
    
    public static final int V_STATUS_NEU = 0;
    public static final int V_STATUS_GEZEIGT = 1;
    public static final int V_STATUS_FERTIG = 2;
    
    // Table Create Statements
    // route table create statement
    private static final String CREATE_TABLE_ROUTE = "CREATE TABLE "
            + TABLE_WERBUNG + "(" + KEY_NUMMER + " INTEGER PRIMARY KEY,"
            + KEY_TITEL + " TEXT,"
            + KEY_TEXT + " TEXT," 
//            + KEY_DATUM + " INTEGER,"
            + KEY_STATUS + " INTEGER,"
            + KEY_FOTO + " TEXT" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_ROUTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

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
    
    public Cursor getNewNotifications(){
    	SQLiteDatabase db = getReadableDatabase();
    	Cursor c = db.rawQuery("SELECT * FROM " + TABLE_WERBUNG + " WHERE " + KEY_STATUS + "<" + V_STATUS_FERTIG, null);
		return c;
    }
    
/*
    public ArrayList<ToDoModel> getAllToDos() {
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();
            Cursor c = db.rawQuery("select * from " + TABLE_TODO, null);
            ArrayList<ToDoModel> arrToDos = new ArrayList<ToDoModel>();
            if (c.moveToFirst()) {
                do {
                    ToDoModel rm = new ToDoModel();
                    rm.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                    rm.setTitle(c.getString(c.getColumnIndex(KEY_TITLE)));
                    rm.setDesc(c.getString(c.getColumnIndex(KEY_DETAIL)));

                    arrToDos.add(rm);

                } while (c.moveToNext());
            }
            db.close();
            return arrToDos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            db.close();
        }
    }
*/
}
