package teamlist;

import java.util.List;

import com.example.pascalshairdroid.R;
import com.example.pascalshairdroid.R.array;
import com.example.pascalshairdroid.R.drawable;
import com.example.pascalshairdroid.R.id;
import com.example.pascalshairdroid.R.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TeamListAdapter extends ArrayAdapter<String>{

	public TeamListAdapter(Context context, int resource,
			String[] objects) {
		super(context, resource, objects);
	}
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = parent.inflate(getContext(), R.layout.listview_teamlist_layout, null);
		}
		
		// text aus array holen und in text speichern
		TextView text = (TextView) convertView.findViewById(R.id.team_name);
		// der position übergeben
		text.setText(getItem(position));
		// text holen und speichern 
		TextView subtext = (TextView) convertView.findViewById(R.id.team_subtext);
		// unter in textfeld schreiben --> wichtig position von ersten textview
		subtext.setText(getContext().getResources().getStringArray(R.array.teamsubinfo)[position]);
		
		//Postion des fotos holen 
		ImageView foto = (ImageView) convertView.findViewById(R.id.team_foto);
		// foto holen
		TypedArray images = getContext().getResources().obtainTypedArray(R.array.team_images);
		System.out.println(images.getText(position));
		
		// foto an der Postion setzen 
		foto.setImageResource(images.getResourceId(position, R.drawable.ph1));
		images.recycle();
		
		return convertView;
		
	}
	
	

}
