package com.example.quickshop;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{

	
	//All static variables
	// Database version
	private static final int DATABASE_VERSION = 1;
	
	// Database Name
	private static final String DATABASE_NAME = "QuickShopDB";
	
	//store table names
	private static final String TABLE_STORE = "store";
	
	//store table columns 
	static final String KEY_STOREID = "store_id";
	private static final String STORE_LOC_COORDINATES = "location_coordinates";
	static final String STORE_NAME = "store_name";
	
	// item table name
	private static final String TABLE_ITEM = "item";
	
	// item table columns 
	private static final String KEY_ITEMID = "item_id";
	private static final String ITEM_NAME = "item_name";
	
	// cartegory table name
	private static final String TABLE_CATEGORY = "category";
	
	// category table columns
	static final String KEY_CATEGORYNAME = "category_name";
	private static final String CATEGORY_LOCATION = "category_location";
	private static final String CATEGORY_ANCHOR_POINT = "anchor_point";
	private static final String CATEGORY_STORE_ID = "category_store_id";
	
	// Item Category table
	private static final String TABLE_ITEMCATEGORY = "item_category";
    
	//Item Category Columns
	private static final String KEY_ITEM = "item_id";
	private static final String KEY_CATEGORY = "category_name";
	
	
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	// Creating tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String CREATE_STORE_TABLE = "CREATE TABLE " + TABLE_STORE + "(" + KEY_STOREID + " INTEGER PRIMARY KEY," + STORE_NAME + " TEXT," + STORE_LOC_COORDINATES + " TEXT" + ");";
		
		String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM + "(" + KEY_ITEMID + " INTEGER PRIMARY KEY," + ITEM_NAME + " TEXT" + ");";
		
		String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "(" + KEY_CATEGORYNAME + " STRING PRIMARY KEY," + CATEGORY_LOCATION + " TEXT," + CATEGORY_ANCHOR_POINT + " TEXT," + CATEGORY_STORE_ID + " INTEGER," + "FOREIGN KEY" + "(" + CATEGORY_STORE_ID + ") REFERENCES " + TABLE_STORE + "(" + KEY_STOREID + "));";  		
		
	    String CREATE_ITEM_CAT_TABLE = "CREATE TABLE " + TABLE_ITEMCATEGORY + "(" + KEY_ITEM + " INTEGER PRIMARY KEY," + KEY_CATEGORY + " TEXT," + "FOREIGN KEY(" + KEY_ITEM + ") REFERENCES " + TABLE_ITEM + "(" + KEY_ITEMID + ")," + "FOREIGN KEY(" +  KEY_CATEGORY + ") REFERENCES " + TABLE_CATEGORY + "(" + KEY_CATEGORYNAME + "));";
	    
		db.execSQL(CREATE_STORE_TABLE);
		db.execSQL(CREATE_ITEM_TABLE);
		db.execSQL(CREATE_CATEGORY_TABLE);
		db.execSQL(CREATE_ITEM_CAT_TABLE);
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_STORE);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_ITEM);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_ITEMCATEGORY);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_CATEGORY);
		
		// create tables again
		onCreate(db);
		
	}
	
	/**
	 * THE INSERT TABLE FUNCTIONS
	 * **/
	// Adding Store details
	void addStore(Store store){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_STOREID, store.getID()); // getting store id
		values.put(STORE_NAME, store.getName()); // getting Store name
		values.put(STORE_LOC_COORDINATES, store.getCordinates()); // getting store coordinates
		
		
		// Inserting new row
		db.insert(TABLE_STORE, null, values);
		db.close(); // closing the database connection 
	}
	
	//adding item details 
	
	void addItem(Item item){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_ITEMID , item.getItemID());
		values.put(ITEM_NAME, item.getItemName());
		
		db.insert(TABLE_ITEM, null, values);
		db.close();
	}
	
	// adding category details
	void addCategory(Category category) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORYNAME, category.getCatName());
		values.put(CATEGORY_LOCATION, category.getCatLoc());
		values.put(CATEGORY_ANCHOR_POINT, category.getAnchPt());
		values.put(CATEGORY_STORE_ID, category.getStoreID());
		
		db.insert(TABLE_CATEGORY, null, values);
		db.close();
	}
	
	// adding ItemCategory Details
	
	void addItemCategory(ItemCategory itemCat){
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_ITEM , itemCat.getItemID());
		values.put(KEY_CATEGORY, itemCat.getCatName());
		
		db.insert(TABLE_ITEMCATEGORY, null, values);
		db.close();
		
	}

	/**
	 * THE SELECT FROM TABLE FUNCTIONS (READING ROWS OF THE TABLE)
	 */
	
	// Getting single store 
	public Store getStore(int id){
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_STORE, new String[] { KEY_STOREID , STORE_NAME, STORE_LOC_COORDINATES}, KEY_STOREID + "=?" , new String[] {String.valueOf(STORE_NAME)}, null, null, null, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		Store store = new Store(cursor.getInt(0) , cursor.getString(1), cursor.getString(2));
		
		return store;
		
	}
	
	
   // getting all stores 
	
	public List<Store> getAllStores(){
		List<Store> storeList = new ArrayList<Store>();
		
		String selectQuery = "SELECT * FROM " + TABLE_STORE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to the list
		if(cursor.moveToFirst()){
			
			do  {
				Store store = new Store();
				store.setID(cursor.getInt(0));
				store.setName(cursor.getString(1));
				store.setCoordinates(cursor.getString(2));
				
			storeList.add(store);
			} while(cursor.moveToNext());
		}
		
		return storeList;
	}
	
	// Need to write a method for retrieving category list .... 
	
	// getting all Categories 
	
	public List<Category> getAllCategories(){
		
		List<Category> categoryList = new ArrayList<Category>();
		
		String selectCat = "SELECT * FROM " + TABLE_CATEGORY;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectCat, null);
		
		// looping through all rows and adding to the list
		
		if(cursor.moveToFirst()){
	
			do {
				Category category = new Category();
				category.setCatName(cursor.getString(0)); // name loca, anch, store id
				category.setCatLoc(cursor.getString(1));
				category.setAnchPt(cursor.getString(2));
				category.setStoreID(cursor.getInt(3));
				
				categoryList.add(category);
			}  while(cursor.moveToNext());
	}

		return categoryList;
	}
}
