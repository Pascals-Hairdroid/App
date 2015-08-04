package products;

import utils.Utils;
import com.pascalshairdroid.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ProductFragment extends Fragment {
	private String name;
	private int id;

	public ProductFragment(String name, int id) 
	{
		this.name = name;
		this.id = id;
	}

	// layout Fragment finden
	// Webview finden
	// neuen view initialisieren
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_products, container,
				false);
		WebView view = (WebView) rootView.findViewById(R.id.product_webview);
		view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;
			}
		});
		
		// Überprüfung ob Internet vorhanden 
		// Wenn keine Internet verbindung HTML Seite no Internet Connection anzeigen 
		if (Utils.isInternetAvailable(getActivity())) {
			if (id == 0) {
				view.loadUrl("http://pascals.at/v2/Seiten/Produkte.php?web=1&Kat=farbe");
			}
			if (id == 1) {
				view.loadUrl("http://pascals.at/v2/Seiten/Produkte.php?web=1&Kat=pflege");
			}
		} else {
			view.loadData("<html><head><style>body{background-color:lightgray;margin-top:25%;}h2{color:orange;text-align:center;Font-Family:Calibri;}</style><title></title></head><body><h2>Es konnte keine Internetverbindung hergestellt werden!</h2></body></html>", "text/html", "UTF-8");
		}
		return rootView;
	}
}
