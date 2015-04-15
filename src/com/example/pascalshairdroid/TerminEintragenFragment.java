package com.example.pascalshairdroid;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TerminEintragenFragment extends Fragment {
	
	
		private static final String ARG_SECTION_NUMBER = "section_number";

		

		public TerminEintragenFragment() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_termin_eintragen, container,
					false);
			
			WebView view = (WebView)rootView.findViewById(R.id.termin_webview);
			view.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return false;
				}
			});
			view.loadUrl("http:/http://www.pascals.at/v2/Seiten/terminvergabe.php");
			
			
		
			return rootView;
		}

		

}
