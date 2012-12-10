package com.example.quickshop;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class ItemCategoryDAO extends BaseDAO<ItemCategory, Long> {
	private static final String TAG = "QuickShop.ItemCategoryDAO";
	private static final String[] ALL_COLUMNS = null; // use in queries to select all columns
	
	public ItemCategoryDAO(Context context) {
	    super(context);
    }

	@Override
	public long create(ItemCategory ic) {
		ContentValues values = extractContentExceptID(ic);
		return db.insert(ItemCategoryTable.TABLE_NAME, null, values);
	}

	@Override
	public void update(ItemCategory ic) {
		long id = ic.getID();
		ContentValues values = extractContentExceptID(ic);
		db.update(ItemCategoryTable.TABLE_NAME, values, ItemCategoryTable._ID + " = " + id,
		          null);
	}
	
	private ContentValues extractContentExceptID(ItemCategory ic) {
		ContentValues values = new ContentValues();
		values.put(ItemCategoryTable.CATEGORY, ic.getCatName());
		values.put(ItemCategoryTable.ITEM, ic.getItemName());
		return values;
	}

	@Override
	public void delete(ItemCategory ic) {
		long id = ic.getID();
		db.delete(ItemCategoryTable.TABLE_NAME, ItemCategoryTable._ID + " = " + id, null);
	}

	@Override
	public ItemCategory findByID(Long id) {
		Cursor cursor = db.query(ItemCategoryTable.TABLE_NAME,
		                         ALL_COLUMNS, ItemCategoryTable._ID + " = " + id,
		                         null, null, null, null);
		cursor.moveToFirst();
		return cursorToIC(cursor);
	}

	@Override
	public List<ItemCategory> findAll() {
		Cursor cursor = db.query(ItemCategoryTable.TABLE_NAME,
		                         ALL_COLUMNS, null, null, null, null, null);
		List<ItemCategory> ics = cursorToList(cursor);
		cursor.close();
		return ics;
	}
	
	public List<ItemCategory> findAllByCatName(String catName) {
		Cursor cursor = db.query(ItemCategoryTable.TABLE_NAME,
		                         ALL_COLUMNS, ItemCategoryTable.CATEGORY + " = ?",
		                         new String[] {catName}, null, null, null);
		List<ItemCategory> ics = cursorToList(cursor);
		cursor.close();
		return ics;
	}
	
	public List<String> findCatNamesWithItems() {
		boolean distinct = true;
		String table = ItemCategoryTable.TABLE_NAME;
		String[] columns = { ItemCategoryTable.CATEGORY };
		String selection = null;
		Cursor cursor = db.query(distinct, table, columns, selection,
		                         null, null, null, null, null);
		
		ArrayList<String> catNames = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String catName = cursor.getString(0);
			Log.d(TAG, "Retreived category " + catName + " from db.");
			catNames.add(catName);
		}
		cursor.close();
		return catNames;
	}
	
	private List<ItemCategory> cursorToList(Cursor cursor) {
		List<ItemCategory> ics = new ArrayList<ItemCategory>();
		cursor.moveToPosition(-1);
		while (cursor.moveToNext()) {
			ItemCategory ic = cursorToIC(cursor);
			ics.add(ic);
		}
		return ics;
	}
	
	private ItemCategory cursorToIC(Cursor cursor) {
		ItemCategory ic = new ItemCategory();
		ic.setID(cursor.getInt(cursor.getColumnIndex(ItemCategoryTable._ID)));
		ic.setCatName(cursor.getString(cursor.getColumnIndex(ItemCategoryTable.CATEGORY)));
		ic.setItemName(cursor.getString(cursor.getColumnIndex(ItemCategoryTable.ITEM)));
		return ic;
	}

}
