package com.example.quickshop;

import java.util.List;

import android.util.Log;

import com.example.quickshop.graphs.DualNode;
import com.example.quickshop.graphs.GraphCategory;
import com.example.quickshop.graphs.IGraphCategory;
import com.example.quickshop.graphs.PathFinderAllPairsShortest;

public class GraphCategoryAdapter implements IGraphCategory {
	private GraphCategory graphCategory;
	private Category category;
	private static final String TAG = "QuickShop.GraphCategoryAdapter";
	
	// minimum distance for anchor points
	private static final int ANCHOR_MIN = 2048;
	
	public GraphCategoryAdapter(String catName) {
		this.graphCategory = new GraphCategory(catName);
	}

	public GraphCategory getGraphCategory() {
		return this.graphCategory;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int getMinDistance() {
		int res;
		int baseMin = graphCategory.getMinDistance();
		if (category.getAnchPoint()) {
			res = max(baseMin, baseMin + ANCHOR_MIN);
		} else {
			res = baseMin;
		}
		Log.d(TAG, "GCA " + getName() + " returning d " + res);
		return res;
	}

	private int max(int x, int y) {
		return x > y ? x : y;
	}

	@Override
	public DualNode getClosestNode() {
		return graphCategory.getClosestNode();
	}

	@Override
	public List<DualNode> getNodes() {
		return graphCategory.getNodes();
	}

	@Override
	public void addNode(DualNode node) {
		graphCategory.addNode(node);
	}

	@Override
	public String getName() {
		return graphCategory.getName();
	}

	@Override
	public String getPathString(DualNode source,
			PathFinderAllPairsShortest<DualNode> APSP_Finder) {
		return graphCategory.getPathString(source, APSP_Finder);
	}

	@Override
	public void updateDistance(DualNode source,
			PathFinderAllPairsShortest<DualNode> APSP_Finder) {
		graphCategory.updateDistance(source, APSP_Finder);
	}

	@Override
	public int compareTo(Object other) {
		return this.getMinDistance() - ((IGraphCategory) other).getMinDistance();
	}

	@Override
	public String toString() {
		return "GCA " + getName();
	}
}
