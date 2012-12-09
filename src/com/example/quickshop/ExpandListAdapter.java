package com.example.quickshop;


import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandListAdapter extends BaseExpandableListAdapter{
	private static final String TAG = "QuickShop.ExpandListAdapter";
	private Context context;
	private ItemCategoryDAO itemCatDAO;
	private ArrayList<Category> categories;

	public ExpandListAdapter(Context context) {
		super();
		this.context = context;
		this.categories = new ArrayList<Category>();
		itemCatDAO = new ItemCategoryDAO(context);
		itemCatDAO.open();
	}
	
	public void addCategoryIfNew(Category category) {
		if (!categories.contains(category)) {
			categories.add(category);
		}
	}
	
	public void addItem(ItemCategory itc, Category category) {
		itemCatDAO.create(itc);
		category.addItem(itc);
		addCategoryIfNew(category);
	}
	
	public void addItem(String itemName, Category category) {
		ItemCategory itc = new ItemCategory(itemName, category.getName());
		addItem(itc, category);
	}

	public Object getChild(int groupPosition, int childPosition) {
		return categories.get(groupPosition).getItems().get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
			ViewGroup parent) {
		ItemCategory item = (ItemCategory) getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.expandlist_child_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvChild);
		tv.setText(item.getItemName());
		return view;
	}

	public int getChildrenCount(int groupPosition) {
		return categories.get(groupPosition).itemCount();
	}

	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return categories.get(groupPosition);
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return categories.size();
	}

	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View view,
	                         ViewGroup parent) {
		Category category = (Category) getGroup(groupPosition);
		Log.d(TAG, "getting group view for category " + category);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.expandlist_group_item, null);
		}
		TextView tv = (TextView) view.findViewById(R.id.tvGroup);
		tv.setText(category.getName());
		
		ImageView iv = (ImageView) view.findViewById(R.id.ivGroup);
		setGroupExpandIcon(iv, isExpanded);

		return view;
	}
	
	private void setGroupExpandIcon(ImageView iv, boolean isExpanded) {
		int imageID = isExpanded ? R.drawable.ic_action_collapse : R.drawable.ic_action_expand;
		iv.setImageResource(imageID);
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void sortCategories(Store chosenStore) {
		CatInStoreDAO catInStoreDAO = new CatInStoreDAO(this.context);
		catInStoreDAO.open();
		CategoryDAO categoryDAO = new CategoryDAO(this.context);
		categoryDAO.open();
		GraphStoreAdapter adapter =
		        new GraphStoreAdapter(chosenStore, categoryDAO, catInStoreDAO);
		adapter.sortCategories(categories);

		catInStoreDAO.close();
		categoryDAO.close();
		this.notifyDataSetChanged();
	}

}
