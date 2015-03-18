package com.example.pascalshairdroid;

import products.ProductFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ProduktListFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_productslist, container,
				false);
		
		
		ListView listView = (ListView)rootView.findViewById(R.id.listView2);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
					FragmentManager f = getActivity().getFragmentManager();
					f.beginTransaction().replace(R.id.container, new ProductFragment(getActivity().getResources().getStringArray(R.array.produkte)[position],position)).commit();
			}
		});
		return rootView;
	}

}
