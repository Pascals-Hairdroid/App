package utils;

import java.io.FileNotFoundException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.CursorLoader;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

// Zum MD5 verschlüsseln von PWD
public class Utils {
	public static String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

	// weil ab Android 4.4 gibt es andere Aufrufe des Verzeichnisses
	@SuppressLint("NewApi")
	public static Bitmap getRealPathFromURI_API19(Context context, Uri uri) {
		String filePath = "";
		try {
			String wholeID = DocumentsContract.getDocumentId(uri);

			// Split at colon, use second item in the array
			String id = wholeID.split(":")[1];

			String[] column = { MediaStore.Images.Media.DATA };

			// where id is equal to
			String sel = MediaStore.Images.Media._ID + "=?";

			Cursor cursor = context.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,
					new String[] { id }, null);

			int columnIndex = cursor.getColumnIndex(column[0]);

			if (cursor.moveToFirst()) {
				filePath = cursor.getString(columnIndex);
			}
			cursor.close();
		} catch (IllegalArgumentException e) {

			try {
				String[] projection = { MediaStore.Images.Media.DATA };

				Cursor cursor = context.getContentResolver().query(uri,
						projection, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(projection[0]);
				filePath = cursor.getString(columnIndex); // returns null
				cursor.close();
			} catch (NullPointerException e1) {
				try {
					return BitmapFactory.decodeStream(context
							.getContentResolver().openInputStream(uri));
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				}
			}
		}
		return BitmapFactory.decodeFile(filePath);
	}

	// API 11 bis 18 Storage Path bekommen
	public static Bitmap getRealPathFromURI_API11to18(Context context,
			Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		String result = null;

		CursorLoader cursorLoader = new CursorLoader(context, contentUri, proj,
				null, null, null);
		Cursor cursor = cursorLoader.loadInBackground();

		if (cursor != null) {
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			result = cursor.getString(column_index);
		}
		return BitmapFactory.decodeFile(result);
	}

	//API unter 11 Storage Path bekommen
	public static Bitmap getRealPathFromURI_BelowAPI11(Context context,
			Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj,
				null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return BitmapFactory.decodeFile(cursor.getString(column_index));
	}

	//
	public static Bitmap getRealPathFromURI(Context context, Uri contentUri) {
		Bitmap realPath;
		if (Build.VERSION.SDK_INT < 11) {
			realPath = getRealPathFromURI_BelowAPI11(context, contentUri);
		}
		// SDK >= 11 && SDK < 19
		else if (Build.VERSION.SDK_INT < 19) {
			realPath = getRealPathFromURI_API11to18(context, contentUri);
		}
		// SDK > 19 (Android 4.4)
		else {
			realPath = getRealPathFromURI_API19(context, contentUri);
		}
		return realPath;
	}

	// prüft ob Internet connection vorhanden ist 
	public static boolean isInternetAvailable(Context context) {
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();

		if (info == null) {
			return false;
		} else {
			if (info.isConnected()) {
				return true;
			} else {
				return true;
			}
		}
	}
	
	//convertiert dp in pixel --> nötig für Logos im NaviDrawer
	public static float convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}
}
