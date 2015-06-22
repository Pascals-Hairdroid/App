package galerie;

import java.util.ArrayList;

import com.example.pascalshairdroid.R;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FullScreenImageAdapter extends PagerAdapter {

	private Activity activity;
	private ArrayList<Integer> images;
	private LayoutInflater inflater;

	// constructor
	public FullScreenImageAdapter(Activity activity, ArrayList<Integer> images) {
		this.activity = activity;
		this.images = images;
	}

	@Override
	public int getCount() {
		return this.images.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// layout von xml laden.
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image,
				container, false);
		//bild und button auf den View holen
		ImageView imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);
		Button btnClose = (Button) viewLayout.findViewById(R.id.btnClose);
		//image laden
		imgDisplay.setImageResource(images.get(position));
		// AC beenden wenn button geklickt wird
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
		//View zu container hinzufügen
		((ViewPager) container).addView(viewLayout);

		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		//element aus den container löschen (menmory freigeben and shit)
		((ViewPager) container).removeView((RelativeLayout) object);

	}
}