package com.example.quickshop.graphs;

/**
 * Class used to augment Nodes for path-finding algorithms. Holds the Node
 * itself, the current path distance to whatever source node is required, and
 * the predecessor on the path from source to this node.
 * 
 * @author sam
 * 
 */
public class AugmentedNode implements Comparable<AugmentedNode> {
	private Node node;
	private int pathWeight;
	private static final int INF = Integer.MAX_VALUE;
	private AugmentedNode predecessor;

	public AugmentedNode(Node node) {
		this.node = node;
		this.pathWeight = INF;
		this.predecessor = null;
	}

	public Node getNode() {
		return node;
	}
	
	public AugmentedNode getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(AugmentedNode predecessor) {
		this.predecessor = predecessor;
	}

	public int getPathWeight() {
		return pathWeight;
	}

	public void setPathWeight(int pathWeight) {
		this.pathWeight = pathWeight;
	}

	public void setInfPath() {
		this.setPathWeight(INF);
	}

	/** returns a String representing the path from `source` to `node` */
	public String pathString() {
		StringBuilder result = new StringBuilder();
		result.append(this);
		AugmentedNode p = predecessor;
		while (p != null) {
			result.insert(0, p + " > ");
			p = p.predecessor;
		}
		
		return result.toString();
	}

	/**
	 * if the current estimated distance to `v` is more than the current
	 * distance to `this` plus the link from `this` to `v`, then update the path
	 * to `v` to go through `this`.
	 */
	public void relax(AugmentedNode v) {

		int linkWeight = this.node.getEdgeWeight(v.node);
		assert linkWeight < INF;

		int candidateWeight = this.pathWeight;
		if (candidateWeight < INF) {
			candidateWeight += linkWeight;
		}

		if (v.pathWeight > candidateWeight) {
			v.pathWeight = candidateWeight;
			v.predecessor = this;
		}
	}

	/* Returns negative if this < n, positive if this > n, 0 otherwise */
	@Override
	public int compareTo(AugmentedNode other) {
		return this.pathWeight - other.pathWeight;
	}

	@Override
	public boolean equals(Object o) {
		return this.node.equals(((AugmentedNode) o).node);
	}

	public String toString() {
		String data = this.node.dataToString();
		String distance;
		if (this.pathWeight == INF) {
			distance = "inf";
		} else {
			distance = String.valueOf(this.pathWeight);
		}
		return "(" + data + ", " + distance + ")";
	}
}
