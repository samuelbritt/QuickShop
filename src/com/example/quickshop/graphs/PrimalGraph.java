package com.example.quickshop.graphs;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

/**
 * Holds the primal graph data
 * 
 * @author sam
 * 
 */

public class PrimalGraph extends Graph<PrimalNode> {
	private int aisleCount;
	private int nodesPerAisle;
	private PrimalNode[][] aisleNodes;
	private PrimalNode sourceNode;
	private static final int edgeWeight = 1;
	private static final String TAG = "QuickShop.PrimalGraph";

	public PrimalGraph(int aisleCount, int nodesPerAisle, Coordinates startCoords) {
		super();
		this.aisleCount = aisleCount;
		this.nodesPerAisle = nodesPerAisle;
		this.aisleNodes = new PrimalNode[aisleCount][nodesPerAisle];
		this.sourceNode = null;
		createGraph();
		this.setSource(nodeAt(startCoords));
	}

	@Override
	public PrimalNode addNode(PrimalNode n) {
		aisleNodes[n.getX()][n.getY()] = n;
		return n;
	}

	/* given the two aisles of nodes, connects their first and last nodes */
	private void connectAisle(PrimalNode[] aisle1, PrimalNode[] aisle2) {
		createDoubleEdge(aisle1[0], aisle2[0], edgeWeight);
		createDoubleEdge(aisle1[nodesPerAisle - 1], aisle2[nodesPerAisle - 1],
		                 edgeWeight);
	}

	private void connectAisles() {
		for (int aisle = 1; aisle < this.aisleCount; aisle++) {
			connectAisle(aisleNodes[aisle - 1], aisleNodes[aisle]);
		}
	}

	private void createAisles() {
		for (int aisle = 0; aisle < this.aisleCount; aisle++) {
			createSingleAisle(aisle);
		}
	}

	private void createGraph() {
		createAisles();
		connectAisles();
	}

	/* creates a single aisle, an array of (doubly) connected nodes */
	private void createSingleAisle(int aisleNumber) {
		PrimalNode[] aisle = aisleNodes[aisleNumber];
		aisle[0] = new PrimalNode(aisleNumber, 0);
		for (int y = 1; y < this.nodesPerAisle; y++) {
			aisle[y] = new PrimalNode(aisleNumber, y);
			createDoubleEdge(aisle[y - 1], aisle[y], edgeWeight);
		}
	}

	@Override
	public List<PrimalNode> getNodes() {
		LinkedList<PrimalNode> list = new LinkedList<PrimalNode>();
		for (int aisle = 0; aisle < aisleCount; aisle++) {
			list.addAll(Arrays.asList(aisleNodes[aisle]));
		}
		return list;
	}

	public PrimalNode getSource() {
		return sourceNode;
	}

	/** returns the node at the specified coordinates */
	public PrimalNode nodeAt(Coordinates coord) {
		Log.d(TAG, "returning node at x = " + coord.getX() + ", y = " + coord.getY());
		if (coord.getX() < 0) {
			return getSource();
		}
		return aisleNodes[coord.getX()][coord.getY()];
	}
	
	@Override
	public int nodeCount() {
		return aisleCount * nodesPerAisle;
	}
	
	/** Sets the node `startNode` to be the source of paths in this graph */
	public void setSource(PrimalNode node) {
		sourceNode = node;
	}

	@Override
	public String toString() {
		return "Primal " + super.toString();
	}
}
