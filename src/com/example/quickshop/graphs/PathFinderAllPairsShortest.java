package com.example.quickshop.graphs;

import java.util.HashMap;

public class PathFinderAllPairsShortest<T extends Node> {
	private PathFinderSingleSourceShortestFactory<T> singleSourceFactory;
	private Graph<T> graph;
	private HashMap<T, PathFinderSingleSourceShortest<T>> sourceToPaths;

	public PathFinderAllPairsShortest(Graph<T> graph,
			PathFinderSingleSourceShortestFactory<T> singleSourceFactory) {
		this.graph = graph;
		this.singleSourceFactory = singleSourceFactory;
		this.sourceToPaths = new HashMap<T, PathFinderSingleSourceShortest<T>>();
	}

	/** Updates the shortest paths from each node to every other node */
	public void findShortestPaths() {
		for (T source : graph.getNodes()) {
			PathFinderSingleSourceShortest<T> finder;
			finder = singleSourceFactory.makePathFinder(graph, source);
			System.out.println();
			System.out.println("Finding paths from source: " + source);
			finder.findShortestPaths();
			sourceToPaths.put(source, finder);
		}
	}
	
	public String getPathString(T source, T dest) {
		return sourceToPaths.get(source).getPathString(dest);
	}
	
	/** returns the length of the shortest path from source to dest */
	public int getShortestPathLength(Node source, Node dest) {
		return sourceToPaths.get(source).getShortestPathWeight(dest);
	}
}
