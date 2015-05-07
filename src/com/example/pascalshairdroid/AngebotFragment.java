package com.example.pascalshairdroid;


import java.io.FileOutputStream;

import utils.Utils;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AngebotFragment extends Fragment {
	
	
		private static final String ARG_SECTION_NUMBER = "section_number";

		

		public AngebotFragment() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_angebot, container,
					false);
			WebView view = (WebView)rootView.findViewById(R.id.angebot_webview);
			view.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return false;
				}
			});
			if (Utils.isInternetAvailable(getActivity())) {
				view.loadUrl("http://pascals.at/v2/Seiten/Angebote.php?web=1");
			} else {
				view.loadData("<html><head><style>body {background-color:lightgray;margin-top:25%;}h1   {color:blue;text-align:center;Font-Family:Calibri;}p    {color:green}</style><title></title></head><body><h1>Bitte &uuml;berpr&uuml;fen Sie die Verbindung zum Internet!</h1></body></html>", "text/html", "UTF-8");
			}
			
			
			
			return rootView;
		}

		

}
