package SimpleSync;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


import java.util.HashMap;

public class SimpleContentProvider extends ContentProvider {

    private String TAG = "SimpleContentProvider";
    public static final String PROVIDER_NAME = "SimpleSync.SimpleSyncAdapter";
    public static final Uri CONTENT_URI_WERBUNG = Uri.parse("content://" + PROVIDER_NAME + "/werbung");
    private static HashMap<String, String> WERBUNG_PROJECTION_MAP;
    private SQLiteDatabase db;

    static final int WERBUNG = 1;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "werbung", WERBUNG);
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return (db == null) ? false : true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(DatabaseHelper.TABLE_WERBUNG);
        qb.setProjectionMap(WERBUNG_PROJECTION_MAP);
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.dir/vnd.simplesyncadapter.werbung";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID=0;
        rowID=  db.insert(DatabaseHelper.TABLE_WERBUNG,"",values);
        if (rowID > 0)
        {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI_WERBUNG, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        else{
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        count = db.delete(DatabaseHelper.TABLE_WERBUNG, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
