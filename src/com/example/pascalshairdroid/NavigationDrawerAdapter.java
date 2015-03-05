package com.example.pascalshairdroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationDrawerAdapter extends BaseExpandableListAdapter {

	public ArrayList<String> groupItem, tempChild;
	public HashMap<Integer, ArrayList<String>> childtem = new HashMap<Integer, ArrayList<String>>(); // in
																										// hashmap
																										// ist
																										// eine
																										// Arraylist
	public LayoutInflater minflater;
	public ExpandableListView expandableListView;
	private final Context context;

	public NavigationDrawerAdapter(Context context, ArrayList<String> grList,
			HashMap<Integer, ArrayList<String>> childItem) {
		this.context = context;
		this.groupItem = grList;
		this.childtem = childItem;
	}

	public void setInflater(LayoutInflater mInflater) {
		this.minflater = mInflater;
	}

	public void setExpandableListView(ExpandableListView e) {
		this.expandableListView = e;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	// diese Methode wird von den ExpandableListView
	// (fragmen_navigation_drawer.xml) für jedes Submenu (Childmenu) aufgerufen
	// und sagt ihm die Position und die Anordnung
	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		tempChild = (ArrayList<String>) childtem.get(groupPosition);
		if (convertView == null) {
			convertView = minflater.inflate(
					android.R.layout.simple_list_item_1, parent, false);
		}
		TextView text = (TextView) convertView.findViewById(android.R.id.text1);
		text.setText(tempChild.get(childPosition));
		convertView.setTag(tempChild.get(childPosition));
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (childtem.get(groupPosition) != null) {
			return ((ArrayList<String>) childtem.get(groupPosition)).size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return groupItem.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	// diese Methode wird von den ExpandableListView
	// (fragmen_navigation_drawer.xml) für jedes Hauptmenu aufgerufen
	// und sagt ihm die Position und die Anordnung
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String s = groupItem.get(groupPosition);

		if (s.equals("Zeichen")) {

			ImageView i = new ImageView(context);
			i.setImageResource(R.drawable.ic_launcher);
			convertView = i;

		} else {
			if (convertView == null || !(convertView instanceof TextView)) { // convertView ist ein bestehender View
										// der der nur abgeändert wird
				convertView = minflater.inflate(
						android.R.layout.simple_expandable_list_item_1, parent,
						false);
			}

			TextView text = (TextView) convertView
					.findViewById(android.R.id.text1);
			// text.setPadding(200, text.getPaddingTop(),
			// text.getPaddingRight(), text.getPaddingBottom());
			text.setText(s);
			convertView.setTag(s);
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public ArrayList<String> getGroupItem() {
		return groupItem;
	}

	public HashMap<Integer, ArrayList<String>> getChildtem() {
		return childtem;
	}

}
