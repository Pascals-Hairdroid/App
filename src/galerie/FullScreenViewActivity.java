package galerie;

import java.util.ArrayList;

import com.example.pascalshairdroid.R;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class FullScreenViewActivity extends Activity {

	private PagerAdapter mPagerAdapter;
	private ViewPager mPager;

	@Override

	protected void onCreate(Bundle savedInstanceState) 
		{
			super.onCreate(savedInstanceState);
			
			setContentView(R.layout.activity_fullscreen_view);
	
			// get intent data
			Intent i = getIntent();
		
		
			// Selected image id
			int position = i.getExtras().getInt("id");
		
			mPagerAdapter = new FullScreenImageAdapter(this, AppConstantGalerie.images);
			mPager = (ViewPager) findViewById(R.id.pager);
			mPager.setAdapter(mPagerAdapter);
			mPager.setCurrentItem(i.getExtras().getInt("position"));
		
			// ImageView imageView = (ImageView) findViewById(R.id.imgDisplay);
		
			// imageView.setImageResource(imageAdapter.mThumbIds[position]);
		}
	}

