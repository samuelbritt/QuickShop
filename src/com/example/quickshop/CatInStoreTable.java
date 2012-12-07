package com.example.quickshop;

import android.database.sqlite.SQLiteDatabase;

public class CatInStoreTable {
	public static final String TABLE_NAME = "categoryInStore";
	public static final String _ID = "_id";
	public static final String CATEGORY_NAME = "categoryName";
	public static final String STORE_ID = "storeID";
	public static final String STARTX = "startX";
	public static final String STARTY = "startY";
	public static final String ENDX = "endX";
	public static final String ENDY = "endY";

	private static final String CREATE =
			"CREATE TABLE " + TABLE_NAME + "("
					+ _ID + " INTEGER primary key autoincrement,"
					+ CATEGORY_NAME + " TEXT,"
					+ STORE_ID + " INTEGER,"
					+ STARTX + " INTEGER,"
					+ STARTY + " INTEGER," 
					+ ENDX + " INTEGER,"
					+ ENDY + " INTEGER" + ");";
	
	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE);
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion,
	                             int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
	}
}
