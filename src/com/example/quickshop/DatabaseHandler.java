package com.example.quickshop;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("NewApi")
public class DatabaseHandler extends SQLiteOpenHelper {

	// All static variables
	// Database version
	private static final int DATABASE_VERSION = 1;
	private static String DB_PATH =
	        "/data/data/com.example.quickshop/databases/";
	static final String DATABASE_NAME = "QuickShopDB_NEW";

	private static final String TABLE_STORE = "store";
	private static final String KEY_STORE_ID = "store_id";
	private static final String STORE_NAME = "store_name";
	private static final String STORE_LOC_COORDINATES = "location_coordinates";
	private static final String STORE_STARTCOORDX = "store_startCoordX";
	private static final String STORE_STARTCOORDY = "store_startCoordY";

	private static final String TABLE_CATEGORY = "category";
	private static final String KEY_CATEGORY_NAME = "category_name";
	private static final String CATEGORY_ANCHOR_POINT = "category_anchorPoint";

	private static final String TABLE_CATEGORYINSTORE = "categoryInStore";
	private static final String CATINSTORE_CATNAME = "catInStore_categoryName";
	private static final String CATINSTORE_STORE_ID = "catInStore_storeID";
	private static final String CATINSTORE_STARTX = "catInStore_startX";
	private static final String CATINSTORE_STARTY = "catInStore_startY";
	private static final String CATINSTORE_ENDX = "catInStore_endX";
	private static final String CATINSTORE_ENDY = "catInStore_endY";

	private static final String TABLE_ITEMCAT = "item_category";
	private static final String KEY_ITEM = "item_name";
	private static final String KEY_CATEGORY = "category_name";

	private SQLiteDatabase myDataBase;
	private final Context myContext;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	// Creating tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_ITEM_CAT_TABLE =
		        "CREATE TABLE " + TABLE_ITEMCAT + "(" + KEY_ITEM
		                + " STRING PRIMARY KEY," + KEY_CATEGORY + " TEXT"
		                + ");";
		db.execSQL(CREATE_ITEM_CAT_TABLE);

		String CREATE_STORE_TABLE =
		        "CREATE TABLE " + TABLE_STORE + "(" + KEY_STORE_ID
		                + " INTEGER PRIMARY KEY," + STORE_NAME + " TEXT,"
		                + STORE_STARTCOORDX + " INTEGER,"
		                + STORE_STARTCOORDY + " INTEGER" + ");";
		db.execSQL(CREATE_STORE_TABLE);

		String CREATE_CATEGORY_TABLE =
		        "CREATE TABLE " + TABLE_CATEGORY + "("
		                + KEY_CATEGORY_NAME + " STRING PRIMARY KEY,"
		                + CATEGORY_ANCHOR_POINT + " INTEGER" + ");)";
		db.execSQL(CREATE_CATEGORY_TABLE);

		String CREATE_CATINSTORE_TABLE =
		        "CREATE TABLE " + TABLE_CATEGORYINSTORE + "("
		                + CATINSTORE_CATNAME + " TEXT,"
		                + CATINSTORE_STORE_ID + " INTEGER,"
		                + CATINSTORE_STARTX + " INTEGER," + CATINSTORE_STARTY
		                + " INTEGER," + CATINSTORE_ENDX + " INTEGER,"
		                + CATINSTORE_ENDY + " INTEGER" + ");";
		db.execSQL(CREATE_CATINSTORE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMCAT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORYINSTORE);

		// create tables again
		onCreate(db);
	}

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
			System.out.println("DB already exists");
			// do nothing - database already exist
		} else {
			System.out.println("DB Does not exist");
			// By calling this method and empty database will be created into
			// the default system path
			// of your application so we are gonna be able to overwrite that
			// database with our database.
			this.getReadableDatabase();

			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copying database");
			}
		}
	}

	/**
	 * Check if the database already exist to avoid re-copying the file each
	 * time you open the application.
	 * 
	 * @return true if it exists, false if it doesn't
	 */
	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DATABASE_NAME;
			checkDB =
			        SQLiteDatabase.openDatabase(myPath, null,
			                                    SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {
			// database does't exist yet.
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created
	 * empty database in the system folder, from where it can be accessed and
	 * handled. This is done by transfering bytestream.
	 * */
	private void copyDataBase() throws IOException {
		// Open your local db as the input stream
		InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {
		// Open the database
		String myPath = DB_PATH + DATABASE_NAME;
		myDataBase =
		        SQLiteDatabase.openDatabase(myPath, null,
		                                    SQLiteDatabase.OPEN_READONLY);
	}

	@Override
	public synchronized void close() {
		if (myDataBase != null)
			myDataBase.close();
		super.close();
	}
	
	/**
	 * THE INSERT FUNCTIONS
	 */

	void addStore(StoreNew store) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_STORE_ID, store.getID());
		values.put(STORE_NAME, store.getName());
		values.put(STORE_STARTCOORDX, store.getStoreStartCoordX());
		values.put(STORE_STARTCOORDY, store.getStoreStartCoordY());

		db.insert(TABLE_STORE, null, values);
		db.close();
	}

	void addCategory(CategoryNew cat) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_CATEGORY_NAME, cat.getCatName());
		values.put(CATEGORY_ANCHOR_POINT, cat.getAnchPoint());

		db.insert(TABLE_CATEGORY, null, values);
		db.close();
	}

	void addCategoryInStore(CatInStore catInStore) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CATINSTORE_CATNAME, catInStore.getCatName());
		values.put(CATINSTORE_STORE_ID, catInStore.getStoreID());
		values.put(CATINSTORE_STARTX, catInStore.getStartCoordX());
		values.put(CATINSTORE_STARTY, catInStore.getStartCoordY());
		values.put(CATINSTORE_ENDX, catInStore.getEndCoordX());
		values.put(CATINSTORE_ENDY, catInStore.getEndCoordY());

		db.insert(TABLE_CATEGORYINSTORE, null, values);
		db.close();
	}

	void addItemCat(ItemCatNew itemCat) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_ITEM, itemCat.getItemName());
		values.put(KEY_CATEGORY, itemCat.getCatName());
		db.insert(TABLE_ITEMCAT, null, values);
		db.close();
	}

	/**
	 * THE SELECT FROM TABLE FUNCTIONS (READING ROWS OF THE TABLE)
	 */

	public List<ItemCatNew> getItemsFromCategoryName(String category) {
		List<ItemCatNew> itemList = new ArrayList<ItemCatNew>();
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor =
		        db.query(TABLE_ITEMCAT, new String[] { KEY_ITEM,
		                                                 KEY_CATEGORY },
		                 KEY_CATEGORY + "=?",
		                 new String[] { String.valueOf(category) }, null, null,
		                 null, null);

		if (cursor.moveToFirst()) {
			do {
				ItemCatNew itemcat = new ItemCatNew();
				itemcat.setItemName(cursor.getString(0));
				itemcat.setCatName(cursor.getString(1));
				itemList.add(itemcat);
			} while (cursor.moveToNext());
		}
		return itemList;
	}

	public void deleteItems() {
		SQLiteDatabase db = this.getWritableDatabase();
		String deleteSQL = "DELETE FROM " + TABLE_ITEMCAT;
		db.execSQL(deleteSQL);
	}

	public List<StoreNew> getAllStores() {
		List<StoreNew> storeList = new ArrayList<StoreNew>();

		String selectQuery = "SELECT * FROM " + TABLE_STORE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				StoreNew storeNew = new StoreNew();
				storeNew.setID(cursor.getInt(0));
				storeNew.setName(cursor.getString(1));
				storeNew.setStartCoordX(cursor.getInt(2));
				storeNew.setStoreStartCoordY(cursor.getInt(3));

				storeList.add(storeNew);
			} while (cursor.moveToNext());
		}
		return storeList;
	}

	// Need to get store name from action bar
	public List<StoreNew> getStoreID(String storeName) {
		List<StoreNew> storeList = new ArrayList<StoreNew>();

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor =
		        db.query(TABLE_STORE, new String[] { KEY_STORE_ID,
		                                               STORE_NAME,
		                                               STORE_STARTCOORDX,
		                                               STORE_STARTCOORDY },
		                 STORE_NAME + "=?",
		                 new String[] { String.valueOf(storeName) }, null,
		                 null, null, null);

		if (cursor.moveToFirst()) {
			do {
				StoreNew stNew = new StoreNew();
				stNew.setID(cursor.getInt(0));
				stNew.setName(cursor.getString(1));
				stNew.setStartCoordX(cursor.getInt(2));
				stNew.setStoreStartCoordY(cursor.getInt(3));

				storeList.add(stNew);
			} while (cursor.moveToNext());
		}

		return storeList;
	}

	public List<CategoryNew> getAllCategories() {
		List<CategoryNew> categoryList = new ArrayList<CategoryNew>();

		String selectCat = "SELECT * FROM " + TABLE_CATEGORY;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectCat, null);

		if (cursor.moveToFirst()) {
			do {
				CategoryNew cat = new CategoryNew();
				cat.setCatName(cursor.getString(0));
				cat.setAnchPoint(cursor.getInt(1));
				categoryList.add(cat);

			} while (cursor.moveToNext());
		}
		return categoryList;
	}

	public List<CatInStore> getCatInStore() {
		List<CatInStore> catInStoreList = new ArrayList<CatInStore>();

		String selectCatInStore = "SELECT * FROM " + TABLE_CATEGORYINSTORE;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectCatInStore, null);

		if (cursor.moveToFirst()) {
			do {
				CatInStore catInStore = new CatInStore();
				catInStore.setCatName(cursor.getString(0));
				catInStore.setStoreID(cursor.getInt(1));
				catInStore.setStartCoordX(cursor.getInt(2));
				catInStore.setStartCoordY(cursor.getInt(3));
				catInStore.setEndCoordX(cursor.getInt(4));
				catInStore.setEndCoordY(cursor.getInt(5));

				catInStoreList.add(catInStore);
			} while (cursor.moveToNext());
		}
		return catInStoreList;
	}

	public void deleteItemsCatInStore() {
		SQLiteDatabase db = this.getWritableDatabase();
		String deleteSQL = "DELETE FROM " + TABLE_CATEGORYINSTORE;
		db.execSQL(deleteSQL);
	}

	public int getAisleCount(int storeID) {
		SQLiteDatabase db = this.getWritableDatabase();
		String countSQL =
		        "SELECT COUNT (DISTINCT " + CATINSTORE_STARTX + ") FROM "
		                + TABLE_CATEGORYINSTORE + " WHERE "
		                + CATINSTORE_STORE_ID + " = " + storeID + ";";
		Cursor cursor = db.rawQuery(countSQL, null);

		cursor.moveToFirst();

		int aisleCount = cursor.getInt(0);
		return aisleCount;
	}

	public int getNodesPerAisle(int storeID) {
		SQLiteDatabase db = this.getWritableDatabase();
		String countSQL =
		        "SELECT COUNT (DISTINCT " + CATINSTORE_ENDY + ") FROM "
		                + TABLE_CATEGORYINSTORE + " WHERE "
		                + CATINSTORE_STORE_ID + " = " + storeID + ";";

		Cursor cursor = db.rawQuery(countSQL, null);
		cursor.moveToFirst();

		int nodesPerAisle = cursor.getInt(0);
		return nodesPerAisle;
	}
}
