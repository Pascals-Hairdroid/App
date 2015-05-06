package com.example.pascalshairdroid;


import utils.Utils;
import login_register.Login;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TerminEintragenFragment extends Fragment {

		public TerminEintragenFragment() {

		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			SharedPreferences preferences = getActivity().getSharedPreferences(Login.PREF_TAG, Context.MODE_PRIVATE);
			String sessionID = preferences.getString(Login.LOGIN_SESSION_ID, null);
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.removeAllCookie();
			cookieManager.setCookie("pascals.at", "PHPSESSID="+sessionID+";Version=1");
		    CookieSyncManager.getInstance().sync();

			
			View rootView = inflater.inflate(R.layout.fragment_termin_eintragen, container,
					false);
			WebView view = (WebView)rootView.findViewById(R.id.termin_webview);
			if (Utils.isInternetAvailable(getActivity())) {
				view.loadUrl("http://pascals.at/v2/Seiten/terminvergabe.php?web=1");
			} else {
				view.loadData("<h1>Y U NO haV internezzzz?</h1>", "text/html", "UTF-8");
			}
			
			return rootView;
		}

		

}
