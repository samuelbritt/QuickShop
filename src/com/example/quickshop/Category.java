package com.example.quickshop;

import java.util.ArrayList;
import java.util.List;

public class Category {
	long _id;
	String categoryName;
	boolean anchorPoint;
	ArrayList<ItemCategory> items;
	
	
	public Category(){
		items = new ArrayList<ItemCategory>();
	}
	
	public Category(String categoryName, boolean anchorPoint){
		this.categoryName = categoryName;
		this.anchorPoint = anchorPoint;
	}
	
	public long getID() {
		return _id;
	}
	
	public void setID(long id) {
		this._id = id;
	}
	
	public String getName(){
		return this.categoryName;
	}
	
	public void setName(String categoryName){
		this.categoryName = categoryName;
	}
	
	public boolean getAnchPoint(){
		return this.anchorPoint;
	}
	
	public void setAnchPoint(boolean anchorPoint){
		this.anchorPoint = anchorPoint;
	}
	
	public String toString() {
		return categoryName + "(str)";
	}
	
	public void addItem(ItemCategory item) {
		items.add(item);
	}
	
	public List<ItemCategory> getItems() {
		return items;
	}
	
	public int itemCount() {
		return items.size();
	}
}
