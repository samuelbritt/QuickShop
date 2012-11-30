package com.example.quickshop.graphs;

import java.util.ArrayList;
import java.util.List;

public class Category implements Comparable<Object> {
	private String name;
	private ArrayList<DualNode> nodes;
	private DualNode closestNode; // node in `nodes` that is closest to source
	private int minDistance;

	public Category(String name) {
		this.name = name;
		this.nodes = new ArrayList<DualNode>();
		this.closestNode = null;
		clearMinDistance();
	}

	public int getMinDistance() {
		return this.minDistance;
	}
	
	public DualNode getClosestNode() {
		return this.closestNode;
	}

	public List<DualNode> getNodes() {
		return nodes;
	}

	public void addNode(DualNode node) {
		nodes.add(node);
	}

	public String getPathString(DualNode source,
	                            PathFinderAllPairsShortest<DualNode> APSP_Finder) {
		return APSP_Finder.getPathString(source, closestNode);
	}

	/**
	 * Updates the minDistance by calculating the distance from `source` to each
	 * node in `nodes`.
	 */
	public void updateDistance(DualNode source,
	                           PathFinderAllPairsShortest<DualNode> APSP_Finder) {
		clearMinDistance();
		for (DualNode dest : nodes) {
			int pathLength = APSP_Finder.getShortestPathLength(source, dest);
			if (pathLength < minDistance) {
				minDistance = pathLength;
				closestNode = dest;
			}
		}
		System.out.println("  "+this.name + " from " + source.dataToString() + "d " + minDistance);
	}

	private void clearMinDistance() {
		this.minDistance = Integer.MAX_VALUE;
		this.closestNode = null;
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
