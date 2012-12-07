package com.example.quickshop;

import java.io.Serializable;
import java.util.List;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseDAO<T, ID extends Serializable> {

	protected SQLiteDatabase db;
	private DatabaseHandler dbHelper;

	public BaseDAO(Context context) {
		dbHelper = new DatabaseHandler(context);
	}
	
	public BaseDAO(DatabaseHandler dbHelper, SQLiteDatabase db) {
		this.dbHelper = dbHelper;
		this.db = db;
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
	

	/** Adds a new object to the database and returns the row index */
	public abstract long create(T obj);

	/**
	 * updates the object in the database with the same primary key as `obj`
	 * with the values from `obj
	 */
	public abstract void update(T obj);

	/** removes `obj` from database */
	public abstract void delete(T obj);

	/** returns a new object from database with the given id */
	public abstract T findByID(ID id);
	
	/** returns a list of all objects in database */
	public abstract List<T> findAll();
}
