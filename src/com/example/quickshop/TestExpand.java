package com.example.quickshop;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Spinner;

public class TestExpand extends Activity implements OnItemSelectedListener {
	public final static String EXTRA_MESSAGE = "com.example.quickshop.MESSAGE";
	private final String TAG = "QuickShop";

	private ExpandListAdapter ExpAdapter;
	private ExpandableListView expandList;
	private Spinner spinner;
	private StoreDAO storeDAO;
	private CategoryDAO categoryDAO;
	private ItemCategoryDAO itemCatDAO;

	private List<Store> allStores;
	private Store chosenStore;
	List<Category> allCategories;
	private Category chosenCategory;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_expand);
		
		expandList = (ExpandableListView) findViewById(R.id.ExpList);
		ExpAdapter = new ExpandListAdapter(TestExpand.this);
		expandList.setAdapter(ExpAdapter);
		
		spinner = (Spinner) findViewById(R.id.spinner);

		loadDAOs();
		loadSpinnerData();
		loadStoreChooser();
		loadExistingData();
		
		expandList.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
			                               int position, long id) {
				int positionType = ExpandableListView.getPackedPositionType(id);
				int groupPosition = ExpandableListView.getPackedPositionGroup(id);
				int childPosition = ExpandableListView.getPackedPositionChild(id);
				if (positionType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
					ExpAdapter.deleteItem(groupPosition, childPosition);
					return true;
				} else if (positionType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
					Category group = (Category) ExpAdapter.getGroup(groupPosition);
					Log.d(TAG, "Long-presed a group " + group.getName());
					DialogFragment dialog = new ChooseAnchorDialog();
					dialog.show(getFragmentManager(), "ChooseAnchor");
					return true;
				}
				return false;
			}
		});

		expandList.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
			                            int groupPosition, long id) {
				Log.d(TAG, "Clicked a group!!!");
				return false;
			}
		});
	}
	
	private void loadExistingData() {
		List<ItemCategory> itemCats = itemCatDAO.findAll();
		for (ItemCategory itc : itemCats) {
			Category cat = getCategoryFromName(itc.getCatName());
			ExpAdapter.addItem(itc, cat);
		}
	}
	
	private Category getCategoryFromName(String catName) {
		for (Category cat : allCategories) {
			if (cat.getName().equals(catName)) {
				return cat;
			}
		}
		return null;
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
		allStores = storeDAO.findAll();
		ArrayList<String> storeNames = new ArrayList<String>();

		for (Store s : allStores) {
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
		allCategories = categoryDAO.findAll();
		ArrayAdapter<Category> dataAdapter = new ArrayAdapter<Category>(this,
				android.R.layout.simple_spinner_item, allCategories);
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
		ExpAdapter.sortCategories(chosenStore);
	}

	public void btnAddItem(View view) {
		EditText editText = (EditText) findViewById(R.id.editText2);
		String itemChild = editText.getText().toString();
		if (!itemChild.isEmpty()) {
			ExpAdapter.addNewItem(itemChild, chosenCategory);
			editText.setText("");
			
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		chosenCategory = (Category) parent.getItemAtPosition(position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	/** Store chooser */
	public boolean chooseStore(int itemPosition, long itemId) {
		chosenStore = allStores.get(itemPosition);
		Log.i(TAG, "User chose a store " + chosenStore.getName());
		return true;
	}
}
