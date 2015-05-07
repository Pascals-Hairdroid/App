package teamlist;

import utils.Utils;

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
	private String name;
	private int id;

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
		WebView view = (WebView) rootView.findViewById(R.id.team_webview);
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;
			}
		});

		if (Utils.isInternetAvailable(getActivity())) {
			if (id == 0) {
				view.loadUrl("http://www.pascals.at/v2/Seiten/Profil.php?SVNr=1000000000"
						+ this.id);
			}
			if (id == 1) {
				view.loadUrl("http://www.pascals.at/v2/Seiten/Profil.php?SVNr=1000000000"
						+ this.id);
			}
			if (id == 2) {
				view.loadUrl("http://www.pascals.at/v2/Seiten/Profil.php?SVNr=1000000000"
						+ this.id);
			}
		} else {
			view.loadData("<h1>No Internet connection</h1>", "text/html",
					"UTF-8");
		}
		return rootView;
	}

}
