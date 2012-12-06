package com.example.quickshop;

import android.database.sqlite.SQLiteDatabase;

public class StoreTable {
	public static final String TABLE_NAME = "store";
	public static final String ID = "_id";
	public static final String NAME = "store_name";
	public static final String LOC_LATITUDE = "location_latitude";
	public static final String LOC_LONGITUDE = "location_longitude";
	public static final String STARTCOORDX = "store_startCoordX";
	public static final String STORE_STARTCOORDY = "store_startCoordY";
	public static final String[] allColumns = {ID,
	                                           NAME,
	                                           LOC_LATITUDE,
	                                           LOC_LONGITUDE,
	                                           STARTCOORDX,
	                                           STORE_STARTCOORDY };

	private static String CREATE =
	        "CREATE TABLE " + TABLE_NAME + "("
	                + ID + " INTEGER PRIMARY KEY,"
	                + NAME + " TEXT,"
	                + LOC_LATITUDE + " REAL,"
	                + LOC_LONGITUDE + " REAL,"
	                + STARTCOORDX + " INTEGER,"
	                + STORE_STARTCOORDY + " INTEGER"
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
