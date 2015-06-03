package com.example.pascalshairdroid;


import utils.Utils;
import login_register.Login;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
			
			WebSettings webSettings = view.getSettings();
			webSettings.setJavaScriptEnabled(true);
			
//			web.setWebChromeClient(new WebChromeClient()  
//			    {  
//			           //The undocumented magic method override  
//			           //Eclipse will swear at you if you try to put @Override here  
//			        // For Android 3.0+
//			        public void openFileChooser(ValueCallback<Uri> uploadMsg) {  
//
//			            mUploadMessage = uploadMsg;  
//			            Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
//			            i.addCategory(Intent.CATEGORY_OPENABLE);  
//			            i.setType("image/*");  
//			            MyWb.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), FILECHOOSER_RESULTCODE);  
//
//			           }
//
//			        // For Android 3.0+
//			           public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
//			           mUploadMessage = uploadMsg;
//			           Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//			           i.addCategory(Intent.CATEGORY_OPENABLE);
//			           i.setType("*/*");
//			           MyWb.this.startActivityForResult(
//			           Intent.createChooser(i, "File Browser"),
//			           FILECHOOSER_RESULTCODE);
//			           }
//
//			        //For Android 4.1
//			           public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
//			               mUploadMessage = uploadMsg;  
//			               Intent i = new Intent(Intent.ACTION_GET_CONTENT);  
//			               i.addCategory(Intent.CATEGORY_OPENABLE);  
//			               i.setType("image/*");  
//			               MyWb.this.startActivityForResult(Intent.createChooser( i, "File Chooser" ), MyWb.FILECHOOSER_RESULTCODE );
//
//			           }
//
//			    });  

			if (Utils.isInternetAvailable(getActivity())) {
				view.loadUrl("http://pascals.at/v2/Seiten/terminvergabe.php?web=1");
			} else {
				view.loadData("<html><head><style>body{background-color:lightgray;margin-top:25%;}h2{color:orange;text-align:center;Font-Family:Calibri;}</style><title></title></head><body><h2>Es konnte keine Internetverbindung hergestellt werden!</h2></body></html>", "text/html", "UTF-8");
			}
			
			return rootView;
		}

		

}
