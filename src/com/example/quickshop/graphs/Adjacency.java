package com.example.quickshop.graphs;

/**
 * A simple class to hold an adjacent node and its weight; that is, an "edge"
 */

/**
 * @author sam
 *
 */
public class Adjacency {
	private int id; // unique ID to identify the edge
	private	Node node;
	private int edgeWeight;
	
	public Adjacency(int id, Node node, int edgeWeight) {
		this.id = id;
		this.node = node;
		this.edgeWeight = edgeWeight;
	}
	
	public int getId() {
		return id;
	}

	public Node getNode() {
		return node;
	}

	public int getEdgeWeight() {
		return edgeWeight;
	}
	
	public String toString() {
		return "(" +id + ": "+ node.dataToString() + ", " + edgeWeight + ")";
	}
}
