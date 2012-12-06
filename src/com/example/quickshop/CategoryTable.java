package com.example.quickshop;

import android.database.sqlite.SQLiteDatabase;

public class CategoryTable {
	private static final String TABLE_NAME = "category";
	private static final String KEY_CATEGORY_NAME = "category_name";
	private static final String CATEGORY_ANCHOR_POINT = "category_anchorPoint";

	private static final String CREATE =
			"CREATE TABLE " + TABLE_NAME + "("
					+ KEY_CATEGORY_NAME + " STRING PRIMARY KEY,"
					+ CATEGORY_ANCHOR_POINT + " INTEGER" + ");)";
	
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	                             int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
