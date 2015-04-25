package com.example.pascalshairdroid;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class KontaktFragment extends Fragment {
	
	
		private static final String ARG_SECTION_NUMBER = "section_number";

		

		public KontaktFragment() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_kontakt, container,
					false);
			WebView view = (WebView)rootView.findViewById(R.id.kontakt_webview);
			view.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return false;
				}
			});
			
			view.loadUrl("http://pascals.at/v2/Seiten/kontakt.php?web=1");
			return rootView;
		}

		

}
