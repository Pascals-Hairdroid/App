package com.pascalshairdroid;

import com.pascalshairdroid.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KontaktFragment extends Fragment {
	
		public KontaktFragment() {
			// TODO Auto-generated constructor stub
		}
		// layout Fragment finden
		// neuen view initialisieren
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_kontakt, container,
					false);
			
			return rootView;
		}
}
