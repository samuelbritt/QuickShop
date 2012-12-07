package com.example.quickshop;

import android.database.sqlite.SQLiteDatabase;

public class CategoryTable {
	public static final String TABLE_NAME = "category";
	public static final String _ID = "_id";
	public static final String NAME = "name";
	public static final String ANCHOR_POINT = "anchorPoint";

	private static final String CREATE =
			"CREATE TABLE " + TABLE_NAME + "("
					+ _ID + " INTEGER primary key autoincrement,"
					+ NAME + " STRING unique,"
					+ ANCHOR_POINT + " INTEGER" + ");)";
	
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	                             int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
