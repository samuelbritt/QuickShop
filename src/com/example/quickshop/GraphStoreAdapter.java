package com.example.quickshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.example.quickshop.graphs.Coordinates;
import com.example.quickshop.graphs.GraphStore;
import com.example.quickshop.graphs.Segment;

public class GraphStoreAdapter {
	private Context context;
	private Store store;
	private List<GraphCategoryAdapter> allGraphCategoryAdapters;
	private List<Category> allCategories;
	private static final String TAG = "QuickShop.GraphStoreAdapter";

	public GraphStoreAdapter(Context context, Store store,
			List<Category> allCategories) {
		this.context = context;
		this.store = store;
		this.allCategories = allCategories;
		this.allGraphCategoryAdapters = new ArrayList<GraphCategoryAdapter>();
	}

	public GraphStore<GraphCategoryAdapter> createGraphStore() {
		Log.i(TAG, "Creating new GraphStore corresponding to " + store.getName());
		Coordinates startCoords = new Coordinates(store.getStoreStartCoordX(),
				store.getStoreStartCoordY());
		GraphCatAdapterFactory gCatFactory = new GraphCatAdapterFactory();
		GraphStore<GraphCategoryAdapter> gstore = new GraphStore<GraphCategoryAdapter>(
				store.getAisleCount(), store.getNodesPerAisle(), startCoords,
				gCatFactory);

		Map<String, ArrayList<Segment>> map = gatherAllSegmentsByCatName();

		// add GraphCategoryAdapters to store, along with setting the normal
		// Category for each new GraphCategory
		for (Category cat : allCategories) {
			GraphCategoryAdapter gca = (GraphCategoryAdapter) gstore
					.addCategory(cat.getName(), map.get(cat.getName()));
			gca.setCategory(cat);
			allGraphCategoryAdapters.add(gca);
		}

		return gstore;
	}
	
	private Map<String, ArrayList<Segment>> gatherAllSegmentsByCatName() {
		Map<String, ArrayList<Segment>> map = new HashMap<String, ArrayList<Segment>>();

		// init hash map
		for (Category cat : allCategories) {
			map.put(cat.getName(), new ArrayList<Segment>());
		}

		// add all segments for each category
		List<CatInStore> catsInStore = getCatsInStore(store.getID());
		for (CatInStore catInStore : catsInStore) {
			ArrayList<Segment> arr = map.get(catInStore.getCatName());
			arr.add(new Segment(catInStore.getStartCoordX(),
								catInStore.getStartCoordY(),
								catInStore.getEndCoordX(),
								catInStore.getEndCoordY()));
		}
		return map;
	}

	private List<CatInStore> getCatsInStore(long storeID) {
		CatInStoreDAO catInStoreDAO = new CatInStoreDAO(context);
		catInStoreDAO.open();
		List<CatInStore> catsInStore = catInStoreDAO.findAllByStoreID(storeID);
		catInStoreDAO.close();
		return catsInStore;
	}

	public void sortCategories(List<Category> categoriesToSort) {
		GraphStore<GraphCategoryAdapter> gstore = createGraphStore();
		List<GraphCategoryAdapter> gcasToSort = catsToGCAs(categoriesToSort);
		gstore.optimalPathSort(gcasToSort);
		GCAsToCats(categoriesToSort, gcasToSort);
	}
	
	private List<GraphCategoryAdapter> catsToGCAs(List<Category> cats) {
		List<GraphCategoryAdapter> gcas = new ArrayList<GraphCategoryAdapter>();
		for (Category cat : cats) {
			for (GraphCategoryAdapter gca: allGraphCategoryAdapters) {
				if (gca.getCategory() == cat) {
					gcas.add(gca);
				}
			}
		}
		return gcas;
	}
	
	private void GCAsToCats(List<Category> cats, List<GraphCategoryAdapter> gcats) {
		for (int i = 0; i < gcats.size(); i++) {
			cats.set(i, gcats.get(i).getCategory());
		}
	}
}
