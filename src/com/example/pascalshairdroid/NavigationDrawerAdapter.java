package com.example.pascalshairdroid;

import java.util.ArrayList;
import java.util.HashMap;
import utils.Utils;
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
	public HashMap<Integer, ArrayList<String>> childItem = new HashMap<Integer, ArrayList<String>>(); // in hashmpa ist eine Arraylist
	public LayoutInflater minflater;
	public ExpandableListView expandableListView;
	private final Context context;

	public NavigationDrawerAdapter(Context context, ArrayList<String> grList,
			HashMap<Integer, ArrayList<String>> childItem) {
		this.context = context;
		this.groupItem = grList;
		this.childItem = childItem;
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
		tempChild = (ArrayList<String>) childItem.get(groupPosition);
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
		if (childItem.get(groupPosition) != null) {
			return ((ArrayList<String>) childItem.get(groupPosition)).size();
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

		if (convertView == null || convertView.getId() != R.id.drawer) {
			convertView = minflater.inflate(R.layout.drawer_row, parent, false);
		}

		TextView text = (TextView) convertView.findViewById(R.id.text1);
		// text.setPadding(200, text.getPaddingTop(),
		// text.getPaddingRight(), text.getPaddingBottom());
		text.setText(s);

		if (!childItem.containsKey(groupPosition)) {
			convertView.findViewById(R.id.indecator).setVisibility(
					View.INVISIBLE);
		} else {
			convertView.findViewById(R.id.indecator)
					.setVisibility(View.VISIBLE);
			if (isExpanded) {
				((ImageView) convertView.findViewById(R.id.indecator))
						.setImageResource(android.R.drawable.arrow_up_float);

			} else {
				((ImageView) convertView.findViewById(R.id.indecator))
						.setImageResource(android.R.drawable.arrow_down_float);
			}
		}
		
		// Home Register von NaviDrawer
		if (s.equals("Zeichen")) {
			convertView.setId(-80085);
			ImageView i = (ImageView) convertView.findViewById(R.id.indecator);
			i.setVisibility(View.VISIBLE);
			i.getLayoutParams().height = (int) Utils.convertDpToPixel(48,
					context);
			i.getLayoutParams().width = (int) Utils.convertDpToPixel(48,
					context);
			i.setPadding(0, 0, 0, 0);
			i.invalidate();
			i.requestLayout();
			i.setImageResource(R.drawable.ic_launcher2);
			text.setText("Home");
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
		return childItem;
	}

}
