package com.example.quickshop;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;

public class TestExpand extends Activity implements OnItemSelectedListener {
	public final static String EXTRA_MESSAGE = "com.example.quickshop.MESSAGE";
	private final String TAG = "QuickShop";

	private ExpandListAdapter ExpAdapter;
	private ExpandableListView expandList;
	private Spinner spinner;
	private String itemChild;
	private String dropDownCat;
	private StoreDAO storeDAO;
	private CategoryDAO categoryDAO;
	private ItemCategoryDAO itemCatDAO;

	private List<Store> availableStores;
	private Store chosenStore;
	private List<String> sortedCatList;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_expand);
		
		expandList = (ExpandableListView) findViewById(R.id.ExpList);
		spinner = (Spinner) findViewById(R.id.spinner);

		loadDAOs();
		loadSpinnerData();
		loadStoreChooser();
		loadCategoryList();

		displayList();
	}
	
	private void loadCategoryList() {
		sortedCatList = itemCatDAO.findCatNamesWithItems();
	}
	
	private void loadDAOs() {
		storeDAO = new StoreDAO(this);
		storeDAO.open();

		categoryDAO = new CategoryDAO(this);
		categoryDAO.open();

		itemCatDAO = new ItemCategoryDAO(this);
		itemCatDAO.open();
	}

	private void loadStoreChooser() {
		availableStores = storeDAO.findAll();
		ArrayList<String> storeNames = new ArrayList<String>();

		for (Store s : availableStores) {
			String name = s.getName();
			storeNames.add(name);
		}

		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		bar.setListNavigationCallbacks(
		        // Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(bar.getThemedContext(),
						android.R.layout.simple_list_item_1, storeNames),

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

		List<Category> catData = categoryDAO.findAll();
		ArrayList<String> catStringData = new ArrayList<String>();

		for (Category c : catData) {
			String var = c.getName();
			catStringData.add(var);
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, catStringData);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		spinner.setOnItemSelectedListener(this);
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
		CatInStoreDAO catInStoreDAO = new CatInStoreDAO(this);
		catInStoreDAO.open();
		GraphStoreAdapter adapter = new GraphStoreAdapter(chosenStore,
				categoryDAO, catInStoreDAO, sortedCatList);
		adapter.sortCategories();

		catInStoreDAO.close();
		displayList();
	}

	private void displayList() {
		ArrayList<ExpandListGroup> groupList = new ArrayList<ExpandListGroup>();
		for (String catName : sortedCatList) {
			ExpandListGroup group = new ExpandListGroup();
			group.setName(catName);
			group.setItems(getItemChildren(catName));
			groupList.add(group);
		}

		ExpAdapter = new ExpandListAdapter(TestExpand.this, groupList);
		expandList.setAdapter(ExpAdapter);
	}
	
	private ArrayList<ExpandListChild> getItemChildren(String categoryName) {
		List<ItemCategory> itemsInCat = itemCatDAO.findAllByCatName(categoryName);
		ArrayList<ExpandListChild> childList = new ArrayList<ExpandListChild>();
		for (ItemCategory itc : itemsInCat) {
			ExpandListChild child = new ExpandListChild();
			child.setName(itc.getItemName());
			childList.add(child);
		}
		return childList;
	}

	public void btnAddItem(View view) {
		EditText editText = (EditText) findViewById(R.id.editText2);
		itemChild = editText.getText().toString();
		if (!itemChild.isEmpty()) {
			addNewItem(itemChild, dropDownCat);
			editText.setText("");
		}
	}
	
	private void addNewItem(String itemName, String categoryName) {
		itemCatDAO.create(new ItemCategory(itemChild, categoryName));
		if (!sortedCatList.contains(categoryName)) {
			sortedCatList.add(categoryName);
		}
		displayList();
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
		chosenStore = availableStores.get(itemPosition);
		Log.i(TAG, "User chose a store " + chosenStore.getName());
		return true;
	}
}
