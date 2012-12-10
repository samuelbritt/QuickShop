package com.example.quickshop;

/**
 * Data access object. Use this class to create Stores.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class StoreDAO extends BaseDAO<Store, Long> {
	private Context context;

	public StoreDAO(Context context) {
		super(context);
		this.context = context;
	}

	public StoreDAO(DatabaseHandler databaseHandler, SQLiteDatabase db) {
		super(databaseHandler, db);
	}

	@Override
	public long create(Store store) {
		ContentValues values = extractContentExceptID(store);
		return db.insert(StoreTable.TABLE_NAME, null, values);
	}

	@Override
	public void update(Store store) {
		long id = store.getID();
		ContentValues values = extractContentExceptID(store);
		db.update(StoreTable.TABLE_NAME, values, StoreTable._ID + " = " + id,
		          null);
	}
	
	private ContentValues extractContentExceptID(Store store) {
		ContentValues values = new ContentValues();
		values.put(StoreTable.NAME, store.getName());
		values.put(StoreTable.STARTCOORDX, store.getStoreStartCoordX());
		values.put(StoreTable.STORE_STARTCOORDY, store.getStoreStartCoordY());
		return values;
	}

	@Override
	public void delete(Store store) {
		long id = store.getID();
		db.delete(StoreTable.TABLE_NAME, StoreTable._ID + " = " + id, null);
	}

	@Override
	public Store findByID(Long id) {
		String[] allColumns = null; // get all columns
		Cursor cursor = db.query(StoreTable.TABLE_NAME,
		                         allColumns, StoreTable._ID + " = " + id,
		                         null, null, null, null);
		cursor.moveToFirst();
		return cursorToStore(cursor);
	}

	@Override
	public List<Store> findAll() {
		List<Store> stores = new ArrayList<Store>();
		String[] allColumns = null; // get all columns
		Cursor cursor = db.query(StoreTable.TABLE_NAME,
		                         allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Store store = cursorToStore(cursor);
			stores.add(store);
			cursor.moveToNext();
		}
		cursor.close();
		return stores;
	}

	private Store cursorToStore(Cursor cursor) {
		Store store = new Store();
		store.setID(cursor.getInt(cursor.getColumnIndex(StoreTable._ID)));
		store.setName(cursor.getString(cursor.getColumnIndex(StoreTable.NAME)));
		store.setStoreStartCoordX(cursor.getInt(cursor.getColumnIndex(StoreTable.STARTCOORDX)));
		store.setStoreStartCoordY(cursor.getInt(cursor.getColumnIndex(StoreTable.STORE_STARTCOORDY)));
		setAilseCountAndNodesPerAisle(store);
		return store;
	}
	
	private void setAilseCountAndNodesPerAisle(Store store) {
		long id = store.getID();
		CatInStoreDAO catInStore = new CatInStoreDAO(this.context);
		catInStore.open();
		store.setAisleCount(catInStore.getAisleCount(id));
		store.setNodesPerAisle(catInStore.getNodesPerAisle(id));
		catInStore.close();
	}
}
