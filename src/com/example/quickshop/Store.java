package com.example.quickshop;

public class Store {
	
	//private variables
	int storeID;
	String storeName;
	String locationCoordinates;
	
	//Empty Constructor
	public Store() {
		
	}
	
	//constructor
	public Store(int storeID, String storName, String locationCoordinates){
		this.storeID = storeID;
		this.storeName = storeName;
		this.locationCoordinates = locationCoordinates;
	}
	
	//constructor
	public Store(String storeName, String locationCoordinates) {
		this.storeName = storeName;
		this.locationCoordinates = locationCoordinates;
	}
	
	// constructor
	
	public Store(String storeName) {
		this.storeName = storeName;
	}
	
	//getting the store id
	public int getID(){
		return this.storeID;
	}
	
	public void setID(int storeID){
		this.storeID = storeID;
	}
	
	// getting StoreName
	public String getName(){
		return this.storeName;
	}
	
	//setting StoreName
	public void setName(String storeName){
		this.storeName = storeName;
	}
	
	// getting storecoordinates
	public String getCordinates(){
		return this.locationCoordinates = locationCoordinates;
	}
	
	// setting storecoordinates
	
	public void setCoordinates(String locationCoordinates){
		this.locationCoordinates = locationCoordinates;
	}
}
