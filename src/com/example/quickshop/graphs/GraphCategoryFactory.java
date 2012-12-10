package com.example.quickshop.graphs;

public class GraphCategoryFactory implements IGraphCategoryFactory<GraphCategory> {

	public GraphCategory makeGraphCategory(String catName) {
		return new GraphCategory(catName);
	}
}