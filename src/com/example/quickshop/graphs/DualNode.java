package com.example.quickshop.graphs;

/**
 * @author sam
 * 
 */

enum EdgeAngle {
	STRAIGHT, RIGHTANGLE, UTURN
}

public class DualNode extends Node {
	private int id;
	private PrimalNode start;
	private PrimalNode end;
	private int primalEdgeWeight;

	public DualNode(int id, PrimalNode start, PrimalNode end,
	                int primalEdgeWeight) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.primalEdgeWeight = primalEdgeWeight;
	}

	@Override
	public String dataToString() {
		return start.dataToString() + "-" + end.dataToString();
	}
	
	public int getID() {
		return id;
	}

	public PrimalNode getStart() {
		return start;
	}

	public PrimalNode getEnd() {
		return end;
	}

	public int getPrimalEdgeWeight() {
		return primalEdgeWeight;
	}

	public int edgeWeight(Node o) {
		DualNode other = (DualNode) o;
		int weight = (this.primalEdgeWeight + other.primalEdgeWeight) / 2;
		weight += weightOfAngle(angleBetween(other));
		return weight;
	}

	/*
	 * returns the angle between the primal edges represented by `this` and
	 * `node`
	 */
	public EdgeAngle angleBetween(DualNode other) {
		// Let `this` represent the primal edge (s, t) and let `other`
		// represent the primal edge (t, u)
		PrimalNode s = this.start;
		PrimalNode t = this.end; // = other.start, if `this` and `other` are
								 // adjacent
		PrimalNode u = other.end;
		if (s.equals(u)) {
			return EdgeAngle.UTURN;
		} else if (s.onSameAisle(t) ^ t.onSameAisle(u)) {
			return EdgeAngle.RIGHTANGLE;
		} else {
			// straight path, either down an aisle or across an outer edge
			return EdgeAngle.STRAIGHT;
		}
	}

	private int weightOfAngle(EdgeAngle angle) {
		if (angle == EdgeAngle.UTURN) {
			return 5;
		} else if (angle == EdgeAngle.RIGHTANGLE) {
			return 1;
		} else if (angle == EdgeAngle.STRAIGHT) {
			return 2;
		}
		return 0;
	}

	@Override
	public int compareTo(Object o) {
		DualNode other = (DualNode) o;

		if (start.equals(other.start)) {
			return end.compareTo(other.end);
		} else {
			return start.compareTo(other.end);
		}
	}

	@Override
	public boolean equals(Object o) {
		DualNode other = (DualNode) o;
		return start.equals(other.start) && end.equals(other.end);
	}

	@Override
	public int hashCode() {
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, start);
		result = HashCodeUtil.hash(result, end);
		return result;
	}
}
