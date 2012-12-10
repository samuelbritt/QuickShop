package com.example.quickshop;

public class DatabaseStores {

	public static void createStores(StoreDAO storeDAO, CatInStoreDAO catInStoreDAO) {
		PublixAtlStation.create(storeDAO, catInStoreDAO);
		KrogerHowellMill.create(storeDAO, catInStoreDAO);
	}
}

class PublixAtlStation {
	private static final String name = "Publix, Atl. Station";
	
	static void create(StoreDAO storeDAO, CatInStoreDAO catInStoreDAO) {
	    long storeID = storeDAO.create(new Store(name,0,0));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.breads,storeID,0,0,0,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.wineCoolers,storeID,0,1,0,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.wine,storeID,0,2,0,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.petFood,storeID,1,0,1,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.peanutButter,storeID,1,0,1,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.paperPlates,storeID,1,1,1,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.coldBeer,storeID,1,1,1,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.insecticides,storeID,1,2,1,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.cereals,storeID,1,2,1,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.fruitJuices,storeID,2,0,2,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.snacks,storeID,2,0,2,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.cannedMilk,storeID,2,1,2,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.cookies,storeID,2,1,2,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.cannedFruits,storeID,2,2,2,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.rice,storeID,2,2,2,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.coffeeTea,storeID,3,0,3,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.cannedVegetables,storeID,3,1,3,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.condiments,storeID,3,2,3,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.water,storeID,3,0,3,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.softDrinks,storeID,3,1,3,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.softDrinks,storeID,3,2,3,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.milk,storeID,3,0,4,0));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.milk,storeID,4,0,4,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.vitamins,storeID,4,1,4,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.babyFood,storeID,4,2,4,3));
	}
}

class KrogerHowellMill {
	private static final String name = "Kroger, Howell Mill";

	static void create(StoreDAO storeDAO, CatInStoreDAO catInStoreDAO) {
		long storeID = storeDAO.create(new Store(name, 0, 0));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.dryBeans,storeID,0,0,0,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.peanutButter,storeID,0,1,0,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.breads,storeID,0,2,0,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.milk,storeID,1,0,1,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.wineCoolers,storeID,1,0,1,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.vitamins,storeID,1,1,1,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.coffeeTea,storeID,1,1,1,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.babyFood,storeID,1,2,1,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.wine,storeID,1,2,1,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.snacks,storeID,2,0,2,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.fruitJuices,storeID,2,0,2,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.fruitJuices,storeID,2,0,3,0));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.cookies,storeID,2,1,2,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.cannedMilk,storeID,2,1,2,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.cannedFruits,storeID,2,2,2,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.rice,storeID,2,2,2,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.coldBeer,storeID,3,0,3,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.cereals,storeID,3,0,3,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.water,storeID,3,1,3,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.cannedVegetables,storeID,3,1,3,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.softDrinks,storeID,3,2,3,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.condiments,storeID,3,2,3,3));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.paperPlates,storeID,4,0,4,1));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.insecticides,storeID,4,1,4,2));
	    catInStoreDAO.create(new CatInStore(DatabaseCategories.petFood,storeID,4,2,4,3));
	}
}