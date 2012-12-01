package com.example.quickshop;

public class ItemCatNew {
	String itemName;
	String categoryName;
	
	
	public ItemCatNew(){
		
	}
	
	public ItemCatNew(String itemName, String categoryName){
		this.itemName = itemName;
		this.categoryName = categoryName;
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
