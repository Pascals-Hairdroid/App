package galerie;

import com.example.pascalshairdroid.R;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;

public class GalerieFragment extends Fragment {

	private Utils utils;
	private GridViewImageAdapter adapter;
	private GridView gridView;
	private int columnWidth;
	private RelativeLayout relativeLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Layout finden
		relativeLayout = (RelativeLayout) inflater.inflate(
				R.layout.activity_grid_view, container, false);

		// passenden Gridview 
		gridView = (GridView) relativeLayout.findViewById(R.id.grid_view);

		utils = new Utils(getActivity());

		// Initilizing Grid View
		InitilizeGridLayout();

		// Gridview adapter
		adapter = new GridViewImageAdapter(getActivity(), AppConstantGalerie.images,
				columnWidth);

		// setting grid view adapter
		gridView.setAdapter(adapter);

		return relativeLayout;
	}

	private void InitilizeGridLayout() {
		
		Resources r = getResources();
		//padding berechnen
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				AppConstantGalerie.GRID_PADDING, r.getDisplayMetrics());
		//anzahl der spalten berechnen
		columnWidth = (int) ((utils.getScreenWidth() - ((AppConstantGalerie.NUM_OF_COLUMNS + 1) * padding)) / AppConstantGalerie.NUM_OF_COLUMNS);
		//anzahl dre splaten setzen
		gridView.setNumColumns(AppConstantGalerie.NUM_OF_COLUMNS);
		//breite der spalten setzen
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}
}