package com.example.pascalshairdroid;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeamFragment extends Fragment {
	private String name; private int id;
	
		
	
		private static final String ARG_SECTION_NUMBER = "section_number";

		

		public TeamFragment(String name, int id) {

			this.name = name;
			this.id = id;
			
			
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_team, container,
					false);
			((TextView)rootView.findViewById(R.id.section_label)).setText(this.name + ", "+this.id);
			return rootView;
		}

		

}
