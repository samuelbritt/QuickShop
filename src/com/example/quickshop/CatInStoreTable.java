package com.example.quickshop;

import android.database.sqlite.SQLiteDatabase;

public class CatInStoreTable {
	private static final String TABLE_NAME = "categoryInStore";
	private static final String CATINSTORE_CATNAME = "catInStore_categoryName";
	private static final String CATINSTORE_STORE_ID = "catInStore_storeID";
	private static final String CATINSTORE_STARTX = "catInStore_startX";
	private static final String CATINSTORE_STARTY = "catInStore_startY";
	private static final String CATINSTORE_ENDX = "catInStore_endX";
	private static final String CATINSTORE_ENDY = "catInStore_endY";

	private static final String CREATE =
			"CREATE TABLE " + TABLE_NAME + "("
					+ CATINSTORE_CATNAME + " TEXT,"
					+ CATINSTORE_STORE_ID + " INTEGER,"
					+ CATINSTORE_STARTX + " INTEGER," + CATINSTORE_STARTY
					+ " INTEGER," + CATINSTORE_ENDX + " INTEGER,"
					+ CATINSTORE_ENDY + " INTEGER" + ");";
	
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	                             int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
