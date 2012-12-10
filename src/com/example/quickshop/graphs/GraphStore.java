package com.example.quickshop.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.util.Log;

/**
 * Holds information about the store layout. Finds optimal paths through the
 * store.
 * 
 * @author sam
 * 
 */
public class GraphStore<T extends IGraphCategory> {
	private PrimalGraph P;
	private DualGraph D;
	private PathFinderAllPairsShortest<DualNode> APSP_Finder;
	private ArrayList<T> categories;
	private IGraphCategoryFactory<T> graphCatFactory;
	private static final String TAG = "QuickShop.GraphStore";

	public GraphStore(int aisleCount, int nodesPerAisle,
			Coordinates startCoords, IGraphCategoryFactory<T> graphCatFactory) {
		P = new PrimalGraph(aisleCount, nodesPerAisle, startCoords);
		D = new DualGraph(P);
		categories = new ArrayList<T>();
		this.graphCatFactory = graphCatFactory;
		
		DijkstraFactory<DualNode> dijFact = new DijkstraFactory<DualNode>();
		APSP_Finder = new PathFinderAllPairsShortest<DualNode>(D, dijFact);
		APSP_Finder.findShortestPaths();
	}
	
	public DualGraph getDualGraph() {
		return D;
	}
	
	public T getCategory(String categoryName) {
		for (T cat: this.categories) {
			if (cat.getName().equals(categoryName)) {
				return cat;
			}
		}
		Log.d(TAG, "Cannot find category " + categoryName);
		return null;
	}

	public T addCategory(String catName, List<Segment> segments) {
		T category = graphCatFactory.makeGraphCategory(catName);
		addNodesFromAllSegments(category, segments);
		this.categories.add(category);
		return category;
	}

	private void addNodesFromAllSegments(T category, List<Segment> segments) {
		for (Segment segment : segments) {
			addNodeFromSegment(category, segment);
		}
	}

	private void addNodeFromSegment(T category, Segment segment) {
		for (DualNode node : D.matchSegment(segment)) {
			category.addNode(node);
		}
	}
	
	/** returns the distance from source to dest */
	public int getDistance(Node source, Node dest) {
		return APSP_Finder.getShortestPathLength(source, dest);
	}
	
	/** returns path String from source the category*/
	public String getPathString(DualNode source, T cat) {
		return cat.getPathString(source, APSP_Finder);
	}

	/**
	 * Sorts `targets` in place. The resulting sort is optimal for this store.
	 */
	public void optimalPathSort(List<T> categories) {
		DualNode start = (DualNode) D.getSource();
		for (int i = 0; i < categories.size(); i++) {
			List<T> sublist = categories.subList(i, categories.size());
			updateDistances(start, sublist);
			Collections.sort(sublist);
			System.out.println("Min: " + categories.get(i));
			start = categories.get(i).getClosestNode();
		}
	}

	private void updateDistances(DualNode source, List<T> categories) {
		for (T cat : categories) {
			if (cat == null) {
				Log.d(TAG, "Category is NULL!!!");
			} else {
				Log.d(TAG, "updating distances for " + cat.getName());
			}
			cat.updateDistance(source, this.APSP_Finder);
		}
	}
}
