package com.example.quickshop;

public class CategoryNew {
	
	String categoryName;
	int anchorPoint;
	
	
	public CategoryNew(){
		
	}
	
	public CategoryNew(String categoryName, int anchorPoint){
		this.categoryName = categoryName;
		this.anchorPoint = anchorPoint;
	}
	
	
	public String getCatName(){
		return this.categoryName;
	}
	
	public void setCatName(String categoryName){
		this.categoryName = categoryName;
	}
	
	public int getAnchPoint(){
		return this.anchorPoint;
	}
	
	public void setAnchPoint(int anchorPoint){
		this.anchorPoint = anchorPoint;
	}
}
