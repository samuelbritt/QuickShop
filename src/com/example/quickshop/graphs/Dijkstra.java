package com.example.quickshop.graphs;

import java.util.ArrayList;

public class Dijkstra<T extends Node> extends PathFinderSingleSourceShortest<T> {
	private ArrayList<AugmentedNode> solvedPaths;

	public Dijkstra(Graph<T> G, Node source) {
		super(G, source);
		this.solvedPaths = new ArrayList<AugmentedNode>(this.nodeCount());
	}

	public ArrayList<AugmentedNode> GetSolvedPaths() {
		return this.solvedPaths;
	}

	@Override
	void findShortestPaths() {
		while (!this.isEmpty()) {
			AugmentedNode u = this.removeMin();
			solvedPaths.add(u);
			Node n = u.getNode();
			for (Adjacency adj : n.getAdjacencies()) {
				AugmentedNode v = getAugmentedNode(adj.getNode());
				u.relax(v);
			}
		}
	}

}
