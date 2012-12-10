package com.example.quickshop;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CategoryDAO extends BaseDAO<Category, Long> {

	public CategoryDAO(Context context) {
	    super(context);
    }

	public CategoryDAO(DatabaseHandler databaseHandler, SQLiteDatabase db) {
		super(databaseHandler, db);
	}

	@Override
    public long create(Category cat) {
		ContentValues values = extractContentExceptID(cat);
		return db.insert(CategoryTable.TABLE_NAME, null, values);
    }
	
	@Override
	public void update(Category cat) {
		ContentValues values = extractContentExceptID(cat);
		long id = cat.getID();
		db.update(CategoryTable.TABLE_NAME, values, CategoryTable._ID + " = " + id,
		          null);
	}
	
	private ContentValues extractContentExceptID(Category cat) {
		ContentValues values = new ContentValues();
		values.put(CategoryTable.NAME, cat.getName());
		values.put(CategoryTable.ANCHOR_POINT, bool2int(cat.getAnchPoint()));
		return values;
	}


	@Override
    public void delete(Category cat) {
		long id = cat.getID();
		db.delete(CategoryTable.TABLE_NAME, CategoryTable._ID + " = " + id, null);
    }
	
	@Override
	public Category findByID(Long id) {
		String[] allColumns = null; // get all columns
		Cursor cursor = db.query(CategoryTable.TABLE_NAME,
		                         allColumns, CategoryTable._ID + " = " + id,
		                         null, null, null, null);
		cursor.moveToFirst();
		return cursorToCategory(cursor);
	}

	@Override
    public List<Category> findAll() {
		List<Category> categories = new ArrayList<Category>();
		String[] allColumns = null; // get all columns
		Cursor cursor = db.query(CategoryTable.TABLE_NAME,
		                         allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Category cat = cursorToCategory(cursor);
			categories.add(cat);
			cursor.moveToNext();
		}
		cursor.close();
		return categories;
    }

	private Category cursorToCategory(Cursor cursor) {
		Category cat = new Category();
		cat.setID(cursor.getInt(cursor.getColumnIndex(CategoryTable._ID)));
		cat.setName(cursor.getString(cursor.getColumnIndex(CategoryTable.NAME)));
		int anchAsInt = cursor.getInt(cursor.getColumnIndex(CategoryTable.ANCHOR_POINT));
		cat.setAnchPoint(int2bool(anchAsInt));
		return cat;
	}
	
	
	private int bool2int(boolean b) {
		return b ? 1 : 0;
	}
	
	private boolean int2bool(int i) {
		return i != 0;
	}
}
