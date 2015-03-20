package teamlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.pascalshairdroid.R;
import com.example.pascalshairdroid.R.array;
import com.example.pascalshairdroid.R.id;
import com.example.pascalshairdroid.R.layout;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TeamListFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_teamlist, container,
				false);
		
		
		ListView listView = (ListView)rootView.findViewById(R.id.listView1);
		listView.setAdapter(new TeamListAdapter(getActivity(), R.layout.listview_teamlist_layout, getActivity().getResources().getStringArray(R.array.teamlist)));
		
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					FragmentManager f = getActivity().getFragmentManager();
					f.beginTransaction().replace(R.id.container, new TeamFragment(getActivity().getResources().getStringArray(R.array.teamlist)[position],position)).commit();
			}
		});
		return rootView;
	}

}
