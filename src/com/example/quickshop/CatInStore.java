package com.example.quickshop;

public class CatInStore {
	long _id;
	String categoryName;
	long storeID;
	int startCoordX;
	int startCoordY;
	int endCoordX;
	int endCoordY;
	
	public CatInStore(){
	
	}
	
	public CatInStore(String categoryName, long storeID, int startCoordX, int startCoordY, int endCoordX, int endCoordY){
		this.categoryName = categoryName;
		this.storeID = storeID;
		this.startCoordX = startCoordX;
		this.startCoordY = startCoordY;
		this.endCoordX = endCoordX;
		this.endCoordY = endCoordY;
	}
	
	public long getID() {
		return this._id;
	}
	
	public void setID(long id) {
		this._id = id;
	}
	
	public String getCatName(){
		return this.categoryName;
	}
	
	public void setCatName(String categoryName){
		this.categoryName = categoryName;
	}
	
	public long getStoreID(){
		return this.storeID;
	}
	
	public void setStoreID(long storeID){
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
