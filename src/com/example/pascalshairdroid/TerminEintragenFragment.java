package com.example.pascalshairdroid;

import utils.PrefUtils;
import utils.Utils;
import login_register.Login;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.ZoomDensity;

public class TerminEintragenFragment extends Fragment {
		public TerminEintragenFragment() {

		}
		
		// layout Fragment finden
		// Webview finden
		// neuen view initialisieren
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			// Cookies setzen damit im webview die selbe php session verwendet wird wie im app
			SharedPreferences preferences = PrefUtils.getPreferences(getActivity(), Login.PREF_TAG);
			String sessionID = preferences.getString(Login.LOGIN_SESSION_ID, null);
			CookieManager cookieManager = CookieManager.getInstance();
			cookieManager.removeAllCookie();
			cookieManager.setCookie("pascals.at", "PHPSESSID="+sessionID+";Version=1");
			CookieSyncManager.createInstance(getActivity()).getInstance().sync();

			View rootView = inflater.inflate(R.layout.fragment_termin_eintragen, container,
					false);
			WebView view = (WebView)rootView.findViewById(R.id.termin_webview);
			view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
			
			// enabler Javasricpt im Webview
			WebSettings webSettings = view.getSettings();
			webSettings.setJavaScriptEnabled(true);

			
			// Zoomen des Webviews
			view.getSettings().setLoadWithOverviewMode(true);
			view.getSettings().setUseWideViewPort(true);
			view.getSettings().setBuiltInZoomControls(true);
			view.getSettings().setSupportZoom(true);

			// Überprüfung ob Internet vorhanden 
			// Wenn keine Internet verbindung HTML Seite no Internet Connection anzeigen 
			if (Utils.isInternetAvailable(getActivity())) {
				view.loadUrl("http://pascals.at/v2/Seiten/terminvergabe.php?web=1&sessionId="+sessionID);
			} else {
				//fallback seite anzeigen falls offline
				view.loadData("<html><head><style>body{background-color:lightgray;margin-top:25%;}h2{color:orange;text-align:center;Font-Family:Calibri;}</style><title></title></head><body><h2>Es konnte keine Internetverbindung hergestellt werden!</h2></body></html>", "text/html", "UTF-8");
			}
			
			return rootView;
		}
}
