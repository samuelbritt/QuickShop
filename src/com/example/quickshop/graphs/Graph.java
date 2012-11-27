package com.example.quickshop.graphs;

import java.util.List;


public abstract class Graph<T extends Node> {
	private AdjacencyFactory adjFactory;

	public Graph() {
		this.adjFactory = new AdjacencyFactory();
    }

	
	abstract public T addNode(T n);
	
	abstract public List<T> getNodes();
	
	abstract public int nodeCount();
	
	public int edgeCount() {
		return adjFactory.maxID();
	}
	
	public void createDoubleEdge(T n1, T n2, int weight) {
		createSingleEdge(n1, n2, weight);
		createSingleEdge(n2, n1, weight);
	}

	public void createSingleEdge(T n1, T n2, int weight) {
		Adjacency adj = adjFactory.createAdjacency(n2, weight);
		n1.addAdjacency(adj);
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Graph {\n");
		for (T n : getNodes()) {
			result.append("  " + n + ",\n");
		}
		result.append("}");
		return result.toString();
	}
}
