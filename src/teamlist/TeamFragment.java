package teamlist;


import com.example.pascalshairdroid.R;
import com.example.pascalshairdroid.R.id;
import com.example.pascalshairdroid.R.layout;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
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
			WebView view = (WebView)rootView.findViewById(R.id.team_webview);
			view.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);
					return false;
				}
			});
			view.loadUrl("http://myfirsttrysodontblameme.ddns.net/v0.3/Seiten/team.php"+this.id);
			
			
			return rootView;
		}

		

}
