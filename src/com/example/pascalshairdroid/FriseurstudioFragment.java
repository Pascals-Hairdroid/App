package com.example.pascalshairdroid;

import utils.Utils;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FriseurstudioFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	public FriseurstudioFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_friseurstudio,
				container, false);
		WebView view = (WebView) rootView
				.findViewById(R.id.frieseurstudio_webview);
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;
			}
		});
		WebSettings webSettings = view.getSettings();
		webSettings.setJavaScriptEnabled(true);
		if (Utils.isInternetAvailable(getActivity())) {
			view.loadUrl("http://pascals.at/v2/?web=1");
		} else {
			view.loadData("<h1>No Internet connection</h1>", "text/html", "UTF-8");
		}

		return rootView;
	}

}
