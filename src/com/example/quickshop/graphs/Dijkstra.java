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
	public void findShortestPaths() {
		while (!this.isEmpty()) {
			AugmentedNode u = this.removeMin();
			solvedPaths.add(u);
			Node n = u.getNode();
//			System.out.println("  Solved " + n + ", d " + u.getPathWeight());
			for (Adjacency adj : n.getAdjacencies()) {
				AugmentedNode v = getAugmentedNode(adj.getNode());
				if (!solvedPaths.contains(v)) {
//					System.out.println("    Relaxing "
//					                   + v.getNode().dataToString() + ", d "
//					                   + v.getPathWeight());
					relax(u, v);
//					System.out.println("      New distance: " + v.getPathWeight());
				}
			}
		}
	}

}
