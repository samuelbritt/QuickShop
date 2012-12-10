package com.example.quickshop;

import com.example.quickshop.graphs.IGraphCategoryFactory;

public class GraphCatAdapterFactory implements IGraphCategoryFactory<GraphCategoryAdapter> {

	@Override
	public GraphCategoryAdapter makeGraphCategory(String catName) {
		return new GraphCategoryAdapter(catName);
	}
}
