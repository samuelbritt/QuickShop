package com.example.quickshop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import com.example.quickshop.R;

public class TestExpand extends Activity implements OnItemSelectedListener {
	List<String> Cat = new ArrayList<String>();
	public final static String EXTRA_MESSAGE = "com.example.quickshop.MESSAGE";
	String message;

	private ExpandListAdapter ExpAdapter;
	private ArrayList<ExpandListGroup> ExpListItems;
	private ExpandableListView ExpandList;
	Spinner spinner;
	private String itemChild;
	private String dropDownCat;
	private boolean addItemButtonClickflag = false;
	DatabaseHandler db = new DatabaseHandler(this);

	Hashtable<String, Integer> hashCategories =
	        new Hashtable<String, Integer>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_expand);
		String noChild = "";
		String noDrop = "";

		db.deleteItems();
		// db.deleteItemsCatInStore();
		try {
			db.createDataBase();
		} catch (IOException ioe) {
			throw new Error("Unable to create DB");
		}

		try {
			db.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}

		// INSERT STATEMENTS go here for the first time . You need to remove
		// them after inserting to avoid Key constraint.

		db.deleteItems();
		ExpandList = (ExpandableListView) findViewById(R.id.ExpList);
		ExpListItems =
		        SetStandardGroups(noChild, noDrop, addItemButtonClickflag);
		ExpAdapter = new ExpandListAdapter(TestExpand.this, ExpListItems);
		ExpandList.setAdapter(ExpAdapter);

		Intent intent = getIntent();
		message = intent.getStringExtra(TestExpand.EXTRA_MESSAGE);

		// Loading spinner data
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setOnItemSelectedListener(this);
		loadSpinnerData();

		loadStoreChooser();
	}

	private void loadStoreChooser() {
		List<StoreNew> storeData = db.getAllStores();
		ArrayList<String> storeNames = new ArrayList<String>();

		for (StoreNew s : storeData) {
			String name = s.getName();
			storeNames.add(name);
		}

		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		bar.setListNavigationCallbacks(
		    // Specify a SpinnerAdapter to populate the dropdown list.
           new ArrayAdapter<String>(bar.getThemedContext(),
                                    android.R.layout.simple_list_item_1,
                                    storeNames),

           // Provide a listener to be called when an item is selected.
           new ActionBar.OnNavigationListener() {
               public boolean onNavigationItemSelected(int position,
                                                       long id) {
                   // Take action here, e.g. switching to the corresponding 
            	   // fragment.
                   return chooseStore(position, id);
               }
           });
	}

	/**
	 * LOADING THE DROP DOWN LIST
	 */
	private void loadSpinnerData() {

		// DatabaseHandler dbspin = new DatabaseHandler(this);

		List<CategoryNew> catData = db.getAllCategories();
		ArrayList<String> catStringData = new ArrayList<String>();

		for (CategoryNew c : catData) {
			String var = c.getCatName();
			catStringData.add(var);
		}

		ArrayAdapter<String> dataAdapter =
		        new ArrayAdapter<String>(this,
		                                 android.R.layout.simple_spinner_item,
		                                 catStringData);

		dataAdapter
		           .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(dataAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_test_expand, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				NavUtils.navigateUpFromSameTask(this);
				return true;
			case R.id.refresh_sort:
				sort_categories();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void sort_categories() {

		Enumeration<String> e = hashCategories.keys();
		if (hashCategories.isEmpty()) {
			Toast.makeText(getApplicationContext(),
			               "Please select items for sorting",
			               Toast.LENGTH_SHORT).show();
		}
		ArrayList<String> categoryListForSort = new ArrayList<String>();

		while (e.hasMoreElements()) {
			Object k = e.nextElement();
			categoryListForSort.add(k.toString());

		}

		// Need to call the optimal path method - to ask Sam what does he mean
		// by aisle count

		return;
	}

	public ArrayList<ExpandListGroup> SetStandardGroups(String ch, String drop,
	                                                    boolean btnClickflag) {

		ArrayList<ExpandListGroup> groupList = new ArrayList<ExpandListGroup>();
		List<CategoryNew> categories = db.getAllCategories();

		for (CategoryNew c : categories) {
			ExpandListGroup group = new ExpandListGroup();
			String var = c.getCatName();
			group.setName(var);

			if (btnClickflag) {

				List<ItemCatNew> items =
				        (List<ItemCatNew>) db.getItemsFromCategoryName(var);
				ArrayList<ExpandListChild> childList =
				        new ArrayList<ExpandListChild>();
				if (items.isEmpty()) {

				} else {
					hashCategories.put(var, 1);
				}
				for (ItemCatNew itc : items) {
					ExpandListChild child = new ExpandListChild();
					child.setName(itc.getItemName());
					childList.add(child);
				}

				group.setItems(childList);
			}

			List<ItemCatNew> items =
			        (List<ItemCatNew>) db.getItemsFromCategoryName(var);
			if (items.isEmpty()) {

			} else {
				groupList.add(group);
			}

		}
		return groupList;

	}

	public void btnAddItem(View view) {

		EditText editText = (EditText) findViewById(R.id.editText2);
		itemChild = editText.getText().toString();
		editText.setText("");
		addItemButtonClickflag = true;

		db.addItemCat(new ItemCatNew(itemChild, dropDownCat));

		ExpListItems =
		        SetStandardGroups(itemChild, dropDownCat,
		                          addItemButtonClickflag);
		ExpAdapter = new ExpandListAdapter(TestExpand.this, ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
	                           long id) {

		dropDownCat = parent.getItemAtPosition(position).toString();

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	/** Store chooser */
	public boolean chooseStore(int itemPosition, long itemId) {
		System.out.println("Chose a store");
		return false;
	}
}
