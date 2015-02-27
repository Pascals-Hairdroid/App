package com.example.pascalshairdroid;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProductFragment extends Fragment {
	
	
		private static final String ARG_SECTION_NUMBER = "section_number";

		

		public ProductFragment() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_products, container,
					false);
			return rootView;
		}

		

}
