package com.example.pascalshairdroid;


import utils.Utils;
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
			
			return rootView;
		}
}
