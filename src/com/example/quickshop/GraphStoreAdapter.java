package com.example.quickshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.quickshop.graphs.Coordinates;
import com.example.quickshop.graphs.GraphStore;
import com.example.quickshop.graphs.Segment;

public class GraphStoreAdapter {

	private com.example.quickshop.Store store;
	private CategoryDAO catDAO;
	private CatInStoreDAO catInStoreDAO;
	private List<String> catsToSort;

	public GraphStoreAdapter(com.example.quickshop.Store store,
			CategoryDAO catDAO,
			CatInStoreDAO catInStoreDAO, List<String> catsToSort) {
		this.store = store;
		this.catDAO = catDAO;
		this.catInStoreDAO = catInStoreDAO;
		this.catsToSort = catsToSort;
	}

	public void sortCategories() {
		Coordinates startCoords = new Coordinates(store.getStoreStartCoordX(),
				store.getStoreStartCoordY());
		GraphStore gstore = new GraphStore(store.getAisleCount(),
				store.getNodesPerAisle(), startCoords);
		
		List<Category> catList = catDAO.findAll();
		HashMap<String, ArrayList<Segment>> map = new HashMap<String, ArrayList<Segment>>();
		for (Category cat : catList) {
			map.put(cat.getName(), new ArrayList<Segment>());
		}
		
		
		List<CatInStore> catsInStore = catInStoreDAO.findAllByStoreID(store.getID());
		for (CatInStore catInStore : catsInStore) {
			ArrayList<Segment> arr = map.get(catInStore.getCatName());
			arr.add(new Segment(catInStore.getStartCoordX(), catInStore.getStartCoordY(),
					catInStore.getEndCoordX(), catInStore.getEndCoordY()));
		}
		
		for (String catName : map.keySet()) {
			gstore.addCategory(catName, map.get(catName));
		}
		
		gstore.optimalPathSortByName(catsToSort);
		
	}
}