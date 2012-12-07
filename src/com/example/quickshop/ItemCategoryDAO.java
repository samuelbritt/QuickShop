package com.example.quickshop;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class ItemCategoryDAO extends BaseDAO<ItemCategory, Long> {
	
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
		String[] allColumns = null; // get all columns
		Cursor cursor = db.query(ItemCategoryTable.TABLE_NAME,
		                         allColumns, ItemCategoryTable._ID + " = " + id,
		                         null, null, null, null);
		return cursorToIC(cursor);
	}

	@Override
	public List<ItemCategory> findAll() {
		String[] allColumns = null; // get all columns
		Cursor cursor = db.query(ItemCategoryTable.TABLE_NAME,
		                         allColumns, null, null, null, null, null);
		List<ItemCategory> ics = cursorToList(cursor);
		cursor.close();
		return ics;
	}
	
	public List<ItemCategory> findAllByCatName(String catName) {
		String[] allColumns = null; // get all columns
		Cursor cursor = db.query(ItemCategoryTable.TABLE_NAME,
		                         allColumns, ItemCategoryTable.CATEGORY + " = ?",
		                         new String[] {catName}, null, null, null);
		List<ItemCategory> ics = cursorToList(cursor);
		cursor.close();
		return ics;
	}
	
	private List<ItemCategory> cursorToList(Cursor cursor) {
		List<ItemCategory> ics = new ArrayList<ItemCategory>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ItemCategory ic = cursorToIC(cursor);
			ics.add(ic);
			cursor.moveToNext();
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
