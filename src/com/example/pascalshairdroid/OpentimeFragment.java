package com.example.pascalshairdroid;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class OpentimeFragment extends Fragment {
	
	
		private static final String ARG_SECTION_NUMBER = "section_number";

		public OpentimeFragment() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_opentime, container,
					false);
			
			ImageView view = (ImageView)rootView.findViewById(R.id.imageView1);
			
			return rootView;
		}
}
