package com.example.quickshop;

/**
 * Data access object. Use this class to create Stores.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StoreDAO {
	// Database fields
	private SQLiteDatabase db;
	private DatabaseHandler dbHelper;

	public StoreDAO(Context context) {
		dbHelper = new DatabaseHandler(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Store createStore(int storeID, String storeName, int startX,
	                         int startY) {
		ContentValues values = new ContentValues();
		values.put(StoreTable.ID, storeID);
		values.put(StoreTable.NAME, storeName);
		values.put(StoreTable.STARTCOORDX, startX);
		values.put(StoreTable.STORE_STARTCOORDY, startY);
		long insertId = db.insert(StoreTable.TABLE_NAME, null,
		                          values);

		Cursor cursor =
		        db.query(StoreTable.TABLE_NAME, StoreTable.allColumns,
		                 StoreTable.ID + " = " + insertId, null, null, null,
		                 null);
		cursor.moveToFirst();
		Store store = cursorToStore(cursor);
		cursor.close();
		return store;
	}

	public void deleteStore(Store store) {
		long id = store.getID();
		db.delete(StoreTable.TABLE_NAME, StoreTable.ID + " = " + id, null);
	}

	public List<Store> getAllStores() {
		List<Store> stores = new ArrayList<Store>();

		Cursor cursor =
		        db.query(StoreTable.TABLE_NAME,
		                 StoreTable.allColumns, null, null, null, null, null);

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
		store.setID(cursor.getInt(cursor.getColumnIndex(StoreTable.ID)));
		store.setName(cursor.getString(cursor.getColumnIndex(StoreTable.NAME)));
		store.setStoreStartCoordX(cursor.getColumnIndex(StoreTable.STARTCOORDX));
		store.setStoreStartCoordY(cursor.getColumnIndex(StoreTable.STORE_STARTCOORDY));
		return store;
	}
}
