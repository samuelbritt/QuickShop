package com.example.quickshop.graphs;

import java.util.ArrayList;

public class Category implements Comparable<Object> {
	private String name;
	private ArrayList<DualNode> nodes;
	private int minDistance;

	public Category(String name) {
		this.name = name;
		this.nodes = new ArrayList<DualNode>();
		clearMinDistance();
	}

	public int getMinDistance() {
		return this.minDistance;
	}
	
	public void addNode(DualNode node) {
		nodes.add(node);
	}

	/**
	 * Updates the minDistance by calculating the distance from `source` to each
	 * node in `nodes`.
	 */
	public void updateDistance(DualNode source,
	                           PathFinderAllPairsShortest<DualNode> APSP_Finder) {
		clearMinDistance();
		for (Node dest : nodes) {
			int pathLength = APSP_Finder.getShortestPath(source, dest);
			if (pathLength < minDistance) {
				minDistance = pathLength;
			}
		}
	}
	
	private void clearMinDistance() {
		this.minDistance = Integer.MAX_VALUE;
	}

	@Override
	public int compareTo(Object other) {
		return this.minDistance - ((Category) other).minDistance;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
