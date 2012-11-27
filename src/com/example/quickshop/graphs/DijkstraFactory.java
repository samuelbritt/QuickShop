package com.example.quickshop.graphs;


public class DijkstraFactory<T extends Node> implements PathFinderSingleSourceShortestFactory<T> {

	@Override
	public PathFinderSingleSourceShortest<T> makePathFinder(Graph<T> G, T source) {
		return new Dijkstra<T>(G, source);
	}

}