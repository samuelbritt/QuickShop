package com.example.quickshop;

public class Store {

	long _id;
	String storeName;
	int storeStartCoordX;
	int storeStartCoordY;
	int aisleCount;
	int nodesPerAisle;
	
	public Store(){
		
	}
	
	public Store(int storeID, String storeName, int storeStartCoordX, int storeStartCoordY){
		this._id = storeID;
		this.storeName = storeName;
		this.storeStartCoordX = storeStartCoordX;
		this.storeStartCoordY = storeStartCoordY;
	}
	
	public Store(String storeName, int storeStartCoordX, int storeStartCoordY) {
		this.storeName = storeName;
		this.storeStartCoordX = storeStartCoordX;
		this.storeStartCoordY = storeStartCoordY;
	}
	
	
	public long getID(){
		return this._id;
	}
	
	public void setID(long storeID){
		this._id = storeID;
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
	
	public void setStoreStartCoordX(int storeStartCoordX){
		this.storeStartCoordX = storeStartCoordX;
	}
	
	public int getStoreStartCoordY(){
		return this.storeStartCoordY;
	}
	
	public void setStoreStartCoordY(int storeStartCoordY){
		this.storeStartCoordY = storeStartCoordY;
	}

	public int getAisleCount() {
		return aisleCount;
	}

	public void setAisleCount(int aisleCount) {
		this.aisleCount = aisleCount;
	}

	public int getNodesPerAisle() {
		return nodesPerAisle;
	}

	public void setNodesPerAisle(int nodesPerAisle) {
		this.nodesPerAisle = nodesPerAisle;
	}
}
