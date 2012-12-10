package com.example.quickshop.graphs;

import java.util.ArrayList;
import java.util.List;

public class GraphCategory implements IGraphCategory {
	private String name;
	private ArrayList<DualNode> nodes;
	private DualNode closestNode; // node in `nodes` that is closest to source
	private int minDistance;

	public GraphCategory(String name) {
		this.name = name;
		this.nodes = new ArrayList<DualNode>();
		this.closestNode = null;
		clearMinDistance();
	}

	/* (non-Javadoc)
	 * @see com.example.quickshop.graphs.IGraphCategory#getMinDistance()
	 */
	@Override
	public int getMinDistance() {
		return this.minDistance;
	}
	
	/* (non-Javadoc)
	 * @see com.example.quickshop.graphs.IGraphCategory#getClosestNode()
	 */
	@Override
	public DualNode getClosestNode() {
		return this.closestNode;
	}

	/* (non-Javadoc)
	 * @see com.example.quickshop.graphs.IGraphCategory#getNodes()
	 */
	@Override
	public List<DualNode> getNodes() {
		return nodes;
	}

	/* (non-Javadoc)
	 * @see com.example.quickshop.graphs.IGraphCategory#addNode(com.example.quickshop.graphs.DualNode)
	 */
	@Override
	public void addNode(DualNode node) {
		nodes.add(node);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.example.quickshop.graphs.IGraphCategory#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.example.quickshop.graphs.IGraphCategory#getPathString(com.example.quickshop.graphs.DualNode, com.example.quickshop.graphs.PathFinderAllPairsShortest)
	 */
	@Override
	public String getPathString(DualNode source,
	                            PathFinderAllPairsShortest<DualNode> APSP_Finder) {
		return APSP_Finder.getPathString(source, closestNode);
	}

	/* (non-Javadoc)
	 * @see com.example.quickshop.graphs.IGraphCategory#updateDistance(com.example.quickshop.graphs.DualNode, com.example.quickshop.graphs.PathFinderAllPairsShortest)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.example.quickshop.graphs.IGraphCategory#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object other) {
		return this.minDistance - ((IGraphCategory) other).getMinDistance();
	}

	/* (non-Javadoc)
	 * @see com.example.quickshop.graphs.IGraphCategory#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
