package com.example.quickshop.graphs;

import java.util.List;

public interface IGraphCategory extends Comparable<Object> {
	
	public abstract int getMinDistance();

	public abstract DualNode getClosestNode();

	public abstract List<DualNode> getNodes();

	public abstract void addNode(DualNode node);

	public abstract String getName();

	public abstract String getPathString(DualNode source,
			PathFinderAllPairsShortest<DualNode> APSP_Finder);

	/**
	 * Updates the minDistance by calculating the distance from `source` to each
	 * node in `nodes`.
	 */
	public abstract void updateDistance(DualNode source,
			PathFinderAllPairsShortest<DualNode> APSP_Finder);

	@Override
	public abstract int compareTo(Object other);

	public abstract String toString();

}