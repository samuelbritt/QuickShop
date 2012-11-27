package com.example.quickshop;

public class ItemCategory {
	
	
	int itemID;
	String categoryName;
	
	
	public ItemCategory(){
		
	}
	
	public ItemCategory(int itemID, String categoryName){
		this.itemID = itemID;
		this.categoryName = categoryName;
	}
	
	
	public int getItemID(){
		return this.itemID;
	}
	
	public void setItemID(int itemID){
		this.itemID = itemID;
	}
	
	public String getCatName(){
		return this.categoryName;
	}
	
	public void setCatName(String categoryName){
		this.categoryName = categoryName;
	}
}
