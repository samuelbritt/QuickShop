package com.example.quickshop;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CatInStoreDAO extends BaseDAO<CatInStore, Long> {

	public CatInStoreDAO(Context context) {
		super(context);
	}

	public CatInStoreDAO(DatabaseHandler databaseHandler, SQLiteDatabase db) {
		super(databaseHandler, db);
	}

	@Override
	public long create(CatInStore catInStore) {
		ContentValues values = extractContentExceptID(catInStore);
		return db.insert(CatInStoreTable.TABLE_NAME, null, values);
	}

	@Override
	public void update(CatInStore catInStore) {
		ContentValues values = extractContentExceptID(catInStore);
		long id = catInStore.getID();
		db.update(CatInStoreTable.TABLE_NAME, values, CatInStoreTable._ID + " = " + id,
		          null);
	}
	
	private ContentValues extractContentExceptID(CatInStore catInStore) {
		ContentValues values = new ContentValues();
		values.put(CatInStoreTable.CATEGORY_NAME, catInStore.getCatName());
		values.put(CatInStoreTable.STORE_ID, catInStore.getStoreID());
		values.put(CatInStoreTable.STARTX, catInStore.getStartCoordX());
		values.put(CatInStoreTable.STARTY, catInStore.getStartCoordY());
		values.put(CatInStoreTable.ENDX, catInStore.getEndCoordX());
		values.put(CatInStoreTable.ENDY, catInStore.getEndCoordY());
		return values;
	}

	@Override
	public void delete(CatInStore catInStore) {
		long id = catInStore.getID();
		db.delete(CatInStoreTable.TABLE_NAME, CatInStoreTable._ID + " = " + id, null);
	}

	@Override
	public CatInStore findByID(Long id) {
		String[] allColumns = null; // get all columns
		Cursor cursor = db.query(CatInStoreTable.TABLE_NAME,
		                         allColumns, CatInStoreTable._ID + " = " + id,
		                         null, null, null, null);
		return cursorToStore(cursor);
	}

	@Override
	public List<CatInStore> findAll() {
		List<CatInStore> catInStores = new ArrayList<CatInStore>();
		String[] allColumns = null; // get all columns
		Cursor cursor = db.query(CatInStoreTable.TABLE_NAME,
		                         allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			CatInStore catInStore = cursorToStore(cursor);
			catInStores.add(catInStore);
			cursor.moveToNext();
		}
		cursor.close();
		return catInStores;
	}
	
	private CatInStore cursorToStore(Cursor cursor) {
		CatInStore catInStore = new CatInStore();
		catInStore.setID(cursor.getInt(cursor.getColumnIndex(CatInStoreTable._ID)));
		catInStore.setCatName(cursor.getString(cursor.getColumnIndex(CatInStoreTable.CATEGORY_NAME)));
		catInStore.setStoreID(cursor.getColumnIndex(CatInStoreTable.STORE_ID));
		catInStore.setStartCoordX(cursor.getColumnIndex(CatInStoreTable.STARTX));
		catInStore.setStartCoordY(cursor.getColumnIndex(CatInStoreTable.STARTY));
		catInStore.setEndCoordX(cursor.getColumnIndex(CatInStoreTable.ENDX));
		catInStore.setEndCoordY(cursor.getColumnIndex(CatInStoreTable.ENDY));
		return catInStore;
	}

	public int getAisleCount(long storeID) {
		String countSQL =
		        "SELECT COUNT (DISTINCT " + CatInStoreTable.STARTX + ") FROM "
		                + CatInStoreTable.TABLE_NAME + " WHERE "
		                + CatInStoreTable.STORE_ID + " = " + storeID + ";";
		Cursor cursor = db.rawQuery(countSQL, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}

	public int getNodesPerAisle(long storeID) {
		String countSQL =
		        "SELECT COUNT (DISTINCT " + CatInStoreTable.ENDY + ") FROM "
		                + CatInStoreTable.TABLE_NAME + " WHERE "
		                + CatInStoreTable.STORE_ID + " = " + storeID + ";";
		Cursor cursor = db.rawQuery(countSQL, null);
		cursor.moveToFirst();
		return cursor.getInt(0);
	}
}
