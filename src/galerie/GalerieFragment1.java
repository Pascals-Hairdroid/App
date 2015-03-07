package galerie;


import com.example.pascalshairdroid.R;
import com.example.pascalshairdroid.R.layout;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GalerieFragment1 extends Fragment {
	
	
		private static final String ARG_SECTION_NUMBER = "section_number";

		

		public GalerieFragment1() {
			// TODO Auto-generated constructor stub
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.activity_grid_view, container,
					false);
			return rootView;
		}

		

}
