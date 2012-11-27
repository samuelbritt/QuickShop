package com.example.quickshop;

public class Category {
	
	//private variables
	String category_name;
    String location;
    String anchorPoint;
    int storeID;
	
    // empty constructor
    public Category(){
    	
    }
    
    // Constructor
    public Category(String category_name, String location, String anchorPoint, int storeID) {
    	this.category_name = category_name;
    	this.location = location;
    	this.anchorPoint = anchorPoint;
    	this.storeID = storeID;
    }
    
    
    public String getCatName(){
    	return this.category_name;
    }
    
    public void setCatName(String category_name){
    	this.category_name = category_name;
    }
    
    public String getCatLoc(){
    	return this.location;
    }
    
    public void setCatLoc(String location){
    	this.location = location;
    }
    
    public String getAnchPt(){
    	return this.anchorPoint;
    }
    
    public void setAnchPt(String anchorPoint){
    	this.anchorPoint = anchorPoint;
    }
    
    public int getStoreID(){
    	return this.storeID;
    }
    
    public void setStoreID(int storeID){
    	this.storeID = storeID;
    }
}
