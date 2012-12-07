package com.example.quickshop;

public class ItemCategory {
	long _id;
	String itemName;
	String categoryName;
	
	
	public ItemCategory(){
		
	}
	
	public ItemCategory(String itemName, String categoryName){
		this.itemName = itemName;
		this.categoryName = categoryName;
	}
	
	public long getID() {
		return this._id;
	}
	
	public void setID(long id) {
		this._id = id;
	}
	
	public String getItemName(){
		return this.itemName;
	}
	
	public void setItemName(String itemName){
		this.itemName = itemName;
	}
	
	public String getCatName(){
		return this.categoryName;
	}
	
	public void setCatName(String categoryName){
		this.categoryName = categoryName;
	}
}
