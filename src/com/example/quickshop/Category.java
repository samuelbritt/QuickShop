package com.example.quickshop;

public class Category {
	long _id;
	String categoryName;
	boolean anchorPoint;
	
	
	public Category(){
		
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
}
