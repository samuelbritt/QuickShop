package com.example.quickshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.util.Log;

import com.example.quickshop.graphs.Coordinates;
import com.example.quickshop.graphs.GraphStore;
import com.example.quickshop.graphs.Segment;

public class GraphStoreAdapter {

	private com.example.quickshop.Store store;
	private CategoryDAO catDAO;
	private CatInStoreDAO catInStoreDAO;
	private static final String TAG = "QuickShop.GraphStoreAdapter";

	public GraphStoreAdapter(com.example.quickshop.Store store,
	        CategoryDAO catDAO, CatInStoreDAO catInStoreDAO) {
		this.store = store;
		this.catDAO = catDAO;
		this.catInStoreDAO = catInStoreDAO;
	}

	public GraphStore createGraphStore() {
		Coordinates startCoords = new Coordinates(store.getStoreStartCoordX(),
		                                          store.getStoreStartCoordY());
		GraphStore gstore =
		        new GraphStore(store.getAisleCount(),
		                       store.getNodesPerAisle(), startCoords);

		List<Category> catList = catDAO.findAll();
		HashMap<String, ArrayList<Segment>> map =
		        new HashMap<String, ArrayList<Segment>>();
		for (Category cat : catList) {
			map.put(cat.getName(), new ArrayList<Segment>());
		}

		List<CatInStore> catsInStore =
		        catInStoreDAO.findAllByStoreID(store.getID());
		for (CatInStore catInStore : catsInStore) {
			ArrayList<Segment> arr = map.get(catInStore.getCatName());
			arr.add(new Segment(catInStore.getStartCoordX(),
			                    catInStore.getStartCoordY(),
			                    catInStore.getEndCoordX(),
			                    catInStore.getEndCoordY()));
		}

		for (String catName : map.keySet()) {
			gstore.addCategory(catName, map.get(catName));
		}
		return gstore;
	}

	public void sortCategories(List<Category> categories) {
		GraphStore gstore = createGraphStore();
		List<String> catNames = cats2names(categories);
		gstore.optimalPathSortByName(catNames);
		names2cats(catNames, categories);
	}
	
	private List<String> cats2names(List<Category> categories) {
		List<String> res = new ArrayList<String>(categories.size());
		for (Category cat: categories) {
			res.add(cat.getName());
		}
		return res;
	}
	
	private void names2cats(List<String> catNames, List<Category> categories) {
		for (int i = 0; i < catNames.size(); i++) {
	        String name = catNames.get(i);
	        for (int j = i; j < categories.size(); j++) {
	            Category cat = categories.get(j);
	        	if (cat.getName().equals(name)) {
	        		swap(categories, i ,j);
	        		continue;
	        	}
            }
        }
	}
	
	private void swap(List<Category> list, int i, int j) {
		Category tmp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, tmp);
	}
}
