package com.example.quickshop.graphs;

import java.util.Arrays;
import java.util.List;

import android.util.Log;

/**
 * 
 */

/**
 * @author sam
 * 
 */
public class DualGraph extends Graph<DualNode> {
	private PrimalGraph primalGraph;
	private DualNode nodes[];
	private DualNode source;
	private int nodeCount;
	private final String TAG = "QuickShop.DualGraph";

	public DualGraph(PrimalGraph P) {
		super();
		this.primalGraph = P;
		this.nodes = new DualNode[P.edgeCount() + 1]; // account for source node
		this.nodeCount = 0;
		this.source = null;
		create();
	}

	private void create() {
		addDualNodes();
		addDualEdges();
	}

	private void addDualNodes() {
		addAisleNodes();
		addSourceNode();
	}

	private void addAisleNodes() {
		for (Node N : primalGraph.getNodes()) {
			PrimalNode n1 = (PrimalNode) N;
			for (Adjacency adj : n1.getAdjacencies()) {
				createNode(n1, adj);
			}
		}
	}

	private void addSourceNode() {
		PrimalNode start = primalGraph.getSource();
		PrimalNode primalSource = new PrimalNode(-1, -1);
		Adjacency adj = primalGraph.createSingleEdge(primalSource, start, 0);
		this.source = createNode(primalSource, adj);
	}

	public DualNode getSource() {
		return this.source;
	}

	private void addDualEdges() {
		for (Node n : getNodes()) {
			DualNode n1 = (DualNode) n;
			PrimalNode middle = n1.getEnd();
			for (Adjacency adj : middle.getAdjacencies()) {
				PrimalNode end = (PrimalNode) adj.getNode();
				DualNode n2 = correspondingDualNode(middle, end);
				createSingleEdge(n1, n2, n1.edgeWeight(n2));
			}
		}
	}

	/**
	 * returns the pair of dual nodes that correspond to the two edges implied
	 * by Segment
	 */
	public DualNode[] matchSegment(Segment segment) {
		Log.d(TAG, "matching segment" + segment);
		PrimalNode pstart = primalGraph.nodeAt(segment.getStart());
		PrimalNode pend = primalGraph.nodeAt(segment.getEnd());
		DualNode[] arr = new DualNode[2];
		arr[0] = correspondingDualNode(pstart, pend);
		arr[1] = correspondingDualNode(pend, pstart);
		return arr;
	}

	/**
	 * returns the dual node corresponding to the primal edge between p1 and p2,
	 * or null if it does not exist
	 */
	private DualNode correspondingDualNode(PrimalNode p1, PrimalNode p2) {
		int edgeID = p1.getEdgeID(p2);
		if (edgeID >= 0) {
			return nodes[edgeID];
		} else {
			return null;
		}
	}

	private DualNode createNode(PrimalNode n1, Adjacency adj) {
		DualNode n = new DualNode(adj.getId(), n1, (PrimalNode) adj.getNode(),
		                          adj.getEdgeWeight());
		return addNode(n);
	}

	@Override
	public String toString() {
		return "Dual " + super.toString();
	}

	@Override
	public DualNode addNode(DualNode n) {
		assert n.getID() < nodes.length;
		nodes[n.getID()] = n;
		nodeCount++;
		return n;
	}

	@Override
	public List<DualNode> getNodes() {
		return Arrays.asList(nodes);
	}

	@Override
	public int nodeCount() {
		return nodeCount;
	}

}
