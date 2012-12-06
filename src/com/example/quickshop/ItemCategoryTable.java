package com.example.quickshop;

import android.database.sqlite.SQLiteDatabase;

public class ItemCategoryTable {
	private static final String TABLE_NAME = "item_category";
	private static final String KEY_ITEM = "item_name";
	private static final String KEY_CATEGORY = "category_name";

	private static final String CREATE =
	        "CREATE TABLE " + TABLE_NAME + "(" + KEY_ITEM
	                + " STRING PRIMARY KEY," + KEY_CATEGORY + " TEXT"
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
