package utils;

import login_register.Login;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtils {
	
	
	public static SharedPreferences getPreferences(Context c, String prefTag){
		return c.getSharedPreferences(prefTag, Context.MODE_PRIVATE|Context.MODE_MULTI_PROCESS);
	}
	public static SharedPreferences getPreferences(Context c){
		return getPreferences(c, Login.PREF_TAG);
	}
}
