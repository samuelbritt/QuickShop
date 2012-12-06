package com.example.quickshop;

public class StoreNew {

	int storeID;
	String storeName;
	int storeStartCoordX;
	int storeStartCoordY;
	
	
	public StoreNew(){
		
	}
	
	public StoreNew(int storeID, String storeName, int storeStartCoordX, int storeStartCoordY){
		this.storeID = storeID;
		this.storeName = storeName;
		this.storeStartCoordX = storeStartCoordX;
		this.storeStartCoordY = storeStartCoordY;
	}
	
	public StoreNew(String storeName, int storeStartCoordX, int storeStartCoordY) {
		this.storeName = storeName;
		this.storeStartCoordX = storeStartCoordX;
		this.storeStartCoordY = storeStartCoordY;
	}
	
	
	public int getID(){
		return this.storeID;
	}
	
	public void setID(int storeID){
		this.storeID = storeID;
	}
	
	public String getName(){
		return this.storeName;
	}
	
	//setting StoreName
	public void setName(String storeName){
		this.storeName = storeName;
	}
	
	
	public int getStoreStartCoordX(){
		return this.storeStartCoordX;
	}
	
	public void setStartCoordX(int storeStartCoordX){
		this.storeStartCoordX = storeStartCoordX;
	}
	
	public int getStoreStartCoordY(){
		return this.storeStartCoordY;
	}
	
	public void setStoreStartCoordY(int storeStartCoordY){
		this.storeStartCoordY = storeStartCoordY;
	}
		
}
