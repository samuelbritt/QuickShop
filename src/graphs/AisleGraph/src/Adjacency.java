/**
 * A simple class to hold an adjacent node and its weight; that is, an "edge"
 */

/**
 * @author sam
 *
 */
public class Adjacency {
	private	Node node;
	private int edgeWeight;
	
	public Adjacency(Node node, int edgeWeight) {
		this.node = node;
		this.edgeWeight = edgeWeight;
	}

	public Node getNode() {
		return node;
	}

	public int getEdgeWeight() {
		return edgeWeight;
	}
	
	public String toString() {
		return "(" + node.dataToString() + ", " + edgeWeight + ")";
	}
}
