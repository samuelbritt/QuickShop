package com.example.quickshop.graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds information about the store layout. Finds optimal paths through the
 * store.
 * 
 * @author sam
 * 
 */
public class Store {
	PrimalGraph P;
	DualGraph D;
	PathFinderAllPairsShortest<DualNode> APSP_Finder;
	ArrayList<Category> categories;

	public Store(int aisleCount, int nodesPerAisle, Coordinates startCoords) {
		P = new PrimalGraph(aisleCount, nodesPerAisle, startCoords);
		D = new DualGraph(P);
		categories = new ArrayList<Category>();
		
		DijkstraFactory<DualNode> dijFact = new DijkstraFactory<DualNode>();
		APSP_Finder = new PathFinderAllPairsShortest<DualNode>(D, dijFact);
		APSP_Finder.findShortestPaths();
	}
	
	public DualGraph getDualGraph() {
		return D;
	}

	public Category addCategory(String categoryName, Segment[] segments) {
		Category category = new Category(categoryName);
		addNodesFromAllSegments(category, segments);
		return category;
	}
	
	public Category getCategory(String categoryName) {
		for (Category cat: this.categories) {
			if (cat.getName().equals(categoryName)) {
				return cat;
			}
		}
		return null;
	}

	private void addNodesFromAllSegments(Category category, Segment[] segments) {
		for (Segment segment : segments) {
			addNodeFromSegment(category, segment);
		}
	}

	private void addNodeFromSegment(Category category, Segment segment) {
		for (DualNode node : D.matchSegment(segment)) {
			category.addNode(node);
		}
	}
	
	/** returns the distance from source to dest */
	public int getDistance(Node source, Node dest) {
		return APSP_Finder.getShortestPathLength(source, dest);
	}
	
	/** returns path String from source the category*/
	public String getPathString(DualNode source, Category cat) {
		return cat.getPathString(source, APSP_Finder);
	}
	
	public void optimalPathSortByName(List<String> categoryNames) {
		ArrayList<Category> categories = new ArrayList<Category>();
		for (String name : categoryNames) {
			categories.add(this.getCategory(name));
		}
		optimalPathSort(categories);
		
		for (int i = 0; i < categoryNames.size(); i++) {
			categoryNames.set(i, categories.get(i).getName());
		}
	}

	/**
	 * Sorts `targets` in place. The resulting sort is optimal for this store.
	 */
	public void optimalPathSort(List<Category> categories) {
		DualNode start = (DualNode) D.getSource();
		for (int i = 0; i < categories.size(); i++) {
			List<Category> sublist = categories.subList(i, categories.size());
			updateDistances(start, sublist);
			Collections.sort(sublist);
			System.out.println("Min: " + categories.get(i));
			start = categories.get(i).getClosestNode();
		}
	}

	private void updateDistances(DualNode source, List<Category> categories) {
		for (Category cat : categories) {
			cat.updateDistance(source, this.APSP_Finder);
		}
	}
}
