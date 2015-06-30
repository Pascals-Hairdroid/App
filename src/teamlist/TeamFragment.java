package teamlist;

import utils.Utils;
import com.example.pascalshairdroid.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TeamFragment extends Fragment {
	private String name;
	private int id;
//	private static final String ARG_SECTION_NUMBER = "section_number";
	public TeamFragment(String name, int id) 
	{
		this.name = name;
		this.id = id;
	}
	public TeamFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// layout Fragment finden
		View rootView = inflater.inflate(R.layout.fragment_team, container,
				false);
		// Webview finden
		WebView view = (WebView) rootView.findViewById(R.id.team_webview);
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		// neuen view initialisieren
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;
			}
		});

		// Überprüfung ob Internet vorhanden 
		if (Utils.isInternetAvailable(getActivity())) {
			// aufzählung im Listview
			if (id == 0) {
				view.loadUrl("http://www.pascals.at/v2/Seiten/Profil.php?SVNr=1713270187&web=1"
						+ this.id);
			}
			if (id == 1) {
				view.loadUrl("http://www.pascals.at/v2/Seiten/Profil.php?SVNr=1662120287&web=1"
						+ this.id);
			}
			if (id == 2) {
				view.loadUrl("http://www.pascals.at/v2/Seiten/Profil.php?SVNr=3071240769&web=1"
						+ this.id);
			}
			// Wenn keine Internet verbindung HTML Seite no Internet Connection anzeigen 
		} else {
			view.loadData("<html><head><style>body{background-color:lightgray;margin-top:25%;}h2{color:orange;text-align:center;Font-Family:Calibri;}</style><title></title></head><body><h2>Es konnte keine Internetverbindung hergestellt werden!</h2></body></html>", "text/html", "UTF-8");
		}
		return rootView;
	}
}
