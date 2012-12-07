package com.example.quickshop;

import android.database.sqlite.SQLiteDatabase;

public class ItemCategoryTable {
	public static final String TABLE_NAME = "item_category";
	public static final String _ID = "_id";
	public static final String ITEM = "item_name";
	public static final String CATEGORY = "category_name";

	private static final String CREATE =
	        "CREATE TABLE " + TABLE_NAME + "("
	        		+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
	        		+ ITEM + " STRING,"
	        		+ CATEGORY + " STRING"
	                + ");";

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE);
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	                             int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
