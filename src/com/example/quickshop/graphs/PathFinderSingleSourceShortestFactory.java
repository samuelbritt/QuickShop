package com.example.quickshop.graphs;

/**
 * Creates new SingeSourceShortestPathFinders. Use to pass in arbitrary single
 * source finders into allPairs finders.
 * 
 */
public interface PathFinderSingleSourceShortestFactory<T extends Node> {
	PathFinderSingleSourceShortest<T> makePathFinder(Graph<T> G, T source);
}
