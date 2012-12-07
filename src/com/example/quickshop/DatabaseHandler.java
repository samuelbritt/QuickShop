package com.example.quickshop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "QuickShopDB";
	private final String TAG = "QuickShop.DatabaseHandler";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i(TAG, "Creating new database");
		StoreTable.onCreate(db);
		CategoryTable.onCreate(db);
		CatInStoreTable.onCreate(db);
		ItemCategoryTable.onCreate(db);

		insertDefaultData(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from " + oldVersion + " to "
		           + newVersion);
		StoreTable.onUpgrade(db, oldVersion, newVersion);
		CategoryTable.onUpgrade(db, oldVersion, newVersion);
		ItemCategoryTable.onUpgrade(db, oldVersion, newVersion);
		CatInStoreTable.onUpgrade(db, oldVersion, newVersion);

		insertDefaultData(db);
	}

	private void insertDefaultData(SQLiteDatabase db) {
		CategoryDAO cd = new CategoryDAO(this, db);
		DatabaseCategories.createCategories(cd);
		
		StoreDAO sd = new StoreDAO(this, db);
		CatInStoreDAO cisd = new CatInStoreDAO(this, db);
		DatabaseStores.createStores(sd, cisd);
	}
}