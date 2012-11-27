package com.example.quickshop;

public class Item {
	
	//private variables
	int itemID;
	String itemName;
	
	public Item(){
		
	}
	
	public Item(int itemID, String itemName){
		this.itemID = itemID;
		this.itemName = itemName;
		
	}
	
	public Item(String itemName){
		this.itemName = itemName;
	}
	
	public int getItemID(){
		return this.itemID;
	}
	
	public void setItemID(int itemID){
		this.itemID = itemID;
	}
	
	public String getItemName(){
		return this.itemName;
		
	}
	
	public void setItemName(String itemName){
		this.itemName = itemName;
	}
	

}
