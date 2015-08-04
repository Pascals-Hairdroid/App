package teamlist;

import com.pascalshairdroid.R;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TeamListFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_teamlist, container,
				false);
		
		// listview finden
		ListView listView = (ListView)rootView.findViewById(R.id.listView1);
		// auf listview den Adapter setzen 
		listView.setAdapter(new TeamListAdapter(getActivity(), R.layout.listview_teamlist_layout, getActivity().getResources().getStringArray(R.array.teamlist)));
		
		// on click auf listview anwenden
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//team mitglied namen aus array laden
				String mitgliedName = getActivity().getResources().getStringArray(R.array.teamlist)[position];
				//neues team fragment erstellen
					TeamFragment t = new TeamFragment(mitgliedName,position);
					FragmentManager f = getActivity().getFragmentManager();
					//teamfragment setzen
					f.beginTransaction().replace(R.id.container, t).commit();
			}
		});
		return rootView;
	}
}
