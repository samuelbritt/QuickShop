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
	private int shortestPathEstimate;
	private static final int INF = Integer.MAX_VALUE;
	private AugmentedNode predecessor;

	public AugmentedNode(Node node) {
		this.node = node;
		this.shortestPathEstimate = INF;
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

	public int getShortestPathEstimate() {
		return shortestPathEstimate;
	}

	public void setShortestPathEstimate(int shortestPathEstimate) {
		this.shortestPathEstimate = shortestPathEstimate;
	}

	public void setInfPath() {
		this.setShortestPathEstimate(INF);
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

		int candidateWeight = this.shortestPathEstimate;
		if (candidateWeight < INF) {
			candidateWeight += linkWeight;
		}

		if (v.shortestPathEstimate > candidateWeight) {
			v.shortestPathEstimate = candidateWeight;
			v.predecessor = this;
		}
	}

	/* Returns negative if this < n, positive if this > n, 0 otherwise */
	@Override
	public int compareTo(AugmentedNode other) {
		return this.shortestPathEstimate - other.shortestPathEstimate;
	}

	@Override
	public boolean equals(Object o) {
		return this.node.equals(((AugmentedNode) o).node);
	}

	public String toString() {
		String data = this.node.dataToString();
		String distance;
		if (this.shortestPathEstimate == INF) {
			distance = "inf";
		} else {
			distance = String.valueOf(this.shortestPathEstimate);
		}
		return "(" + data + ", " + distance + ")";
	}
}
