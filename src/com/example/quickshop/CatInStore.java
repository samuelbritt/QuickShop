package com.example.quickshop;

public class CatInStore {
	
	String categoryName;
	int storeID;
	int startCoordX;
	int startCoordY;
	int endCoordX;
	int endCoordY;
	
	public CatInStore(){
	
	}
	
	public CatInStore(String categoryName, int storeID, int startCoordX, int startCoordY, int endCoordX, int endCoordY){
		this.categoryName = categoryName;
		this.storeID = storeID;
		this.startCoordX = startCoordX;
		this.startCoordY = startCoordY;
		this.endCoordX = endCoordX;
		this.endCoordY = endCoordY;
	}
	
	public String getCatName(){
		return this.categoryName;
	}
	
	public void setCatName(String categoryName){
		this.categoryName = categoryName;
	}
	
	public int getStoreID(){
		return this.storeID;
	}
	
	public void setStoreID(int storeID){
		this.storeID = storeID;
	}

	public int getStartCoordX(){
		return this.startCoordX;
	}
	
	public void setStartCoordX(int startCoordX){
		this.startCoordX = startCoordX;
	}
	
	public int getStartCoordY(){
		return this.startCoordY;
	}
	
	public void setStartCoordY(int startCoordY){
		this.startCoordY = startCoordY;
	}
	
	public int getEndCoordX(){
		return this.endCoordX;
	}
	
	public void setEndCoordX(int endCoordX){
		this.endCoordX = endCoordX;
	}
	
	public int getEndCoordY() {
		return this.endCoordY;
	}
	
	public void setEndCoordY(int endCoordY){
		this.endCoordY = endCoordY;
	}
}
