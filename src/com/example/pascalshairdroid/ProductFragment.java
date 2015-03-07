package com.example.pascalshairdroid;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ProductFragment extends Fragment {
	private String name; private int id;
	
		private static final String ARG_SECTION_NUMBER = "section_number";

		

		public ProductFragment(String name, int id) {


				this.name = name;
				this.id = id;
		}
		public ProductFragment() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_products, container,
					false);
			WebView view = (WebView)rootView.findViewById(R.id.product_webview);
			view.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return false;
				}
			});
			
			if (id==0)
			view.loadUrl("http://www.google.com"+this.id);
			if (id==1)
				view.loadUrl("http://www.youtube.com"+this.id);
			if(id==2)
				view.loadUrl("http://www.spengergasse.com"+this.id);
				
			
			
			return rootView;
		}

		

}
