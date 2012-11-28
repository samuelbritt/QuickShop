package com.example.quickshop.graphs;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 
 */

/**
 * For a provided directed graph G and source node `s` in G, will find the
 * shortest path from `s` to every other node in G. Holds a list of all nodes in
 * G, each with the the distance from `s` to the node, and the predecessor node
 * on the path.
 * 
 * @author sam
 * 
 */
public abstract class PathFinderSingleSourceShortest<T extends Node> {
	private Graph<T> graph;
	private Node source;
	private PriorityQueue<AugmentedNode> pathList;
	private HashMap<Node, AugmentedNode> nodeToAugmented;

	public PathFinderSingleSourceShortest() {
		this.graph = null;
		this.source = null;
		this.pathList = new PriorityQueue<AugmentedNode>();
		this.nodeToAugmented = new HashMap<Node, AugmentedNode>();
	}

	public PathFinderSingleSourceShortest(Graph<T> G, Node source) {
		this.graph = G;
		this.source = source;
		this.pathList = new PriorityQueue<AugmentedNode>();
		this.nodeToAugmented = new HashMap<Node, AugmentedNode>();

		initialize();
	}

	/** Algorithm to run the single pairs shortest path algorithm */
	abstract void findShortestPaths();

	/** Given a node in G, returns the corresponding AugmentedNode */
	public AugmentedNode getAugmentedNode(Node node) {
		return nodeToAugmented.get(node);
	}

	/**
	 * Given node v in G, returns the weight of the shortest path from source to
	 * v
	 */
	public int getShortestPathWeight(Node node) {
		return getAugmentedNode(node).getPathWeight();
	}

	public String getPathString(Node node) {
		return getAugmentedNode(node).pathString();
	}

	/** returns the source node for all the stored paths */
	public Node getSource() {
		return source;
	}

	/**
	 * removes and returns the AugmentedNode with the shortest path from the
	 * source node
	 */
	public AugmentedNode removeMin() {
		return pathList.poll();
	}

	public Boolean remove(AugmentedNode n) {
		return pathList.remove(n);
	}

	public Boolean add(AugmentedNode n) {
		return pathList.add(n);
	}

	public boolean isEmpty() {
		return pathList.isEmpty();
	}

	public int nodeCount() {
		return pathList.size();
	}

	private void initialize() {
		for (Node node : this.graph.getNodes()) {
			addNewAugmentedNode(node);
		}
	}

	private AugmentedNode addNewAugmentedNode(Node node) {
		AugmentedNode augNode = createAugmentedNode(node);
		nodeToAugmented.put(node, augNode);
		this.add(augNode);
		return augNode;
	}

	private AugmentedNode createAugmentedNode(Node node) {
		AugmentedNode augNode = new AugmentedNode(node);
		augNode.setInfPath();
		if (node.equals(source)) {
			augNode.setPathWeight(0);
		}
		return augNode;
	}

	public void relax(AugmentedNode u, AugmentedNode v) {
		this.remove(v);
		u.relax(v);
		this.add(v);
	}

}