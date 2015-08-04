package galerie;

import com.pascalshairdroid.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class FullScreenViewActivity extends Activity {

	private PagerAdapter mPagerAdapter;
	private ViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);
		// get intent data
		Intent i = getIntent();
		// Selected image id
		int position = i.getExtras().getInt("position");
		// initialisiere neuen adapter
		mPagerAdapter = new FullScreenImageAdapter(this,
				AppConstantGalerie.images);
		// finde ViewPager in dem contenView (setContentView)
		mPager = (ViewPager) findViewById(R.id.pager);
		// sag dem ViewPager aus welchem adapter er die daten laden soll
		mPager.setAdapter(mPagerAdapter);
		// setze das bild welches als erstes angezeit werden soll (das bild
		// welches in der gallerie angeklickt wurde)
		mPager.setCurrentItem(position);

	}
	@Override
	protected void onNewIntent(Intent i) {
		// Selected image id
		int position = i.getExtras().getInt("position");
		// setze das bild welches als erstes angezeit werden soll (das bild
		// welches in der gallerie angeklickt wurde)
		mPager.setCurrentItem(position);
		
		
	}
}
