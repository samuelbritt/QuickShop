import java.util.ArrayList;
import java.util.List;

/**
 * Node abstract base class, implements basic methods, handles adjacencies
 * 
 * @author sam
 * 
 */
public abstract class Node implements Comparable<Object> {

	private static final int INF = Integer.MAX_VALUE;
	private ArrayList<Adjacency> adjacencies;

	public Node() {
		createAdjList();
	}

	private void createAdjList() {
		this.adjacencies = new ArrayList<Adjacency>();
	}

	public void addAdjacency(Adjacency a) {
		this.adjacencies.add(a);
	}

	public List<Adjacency> getAdjacencies() {
		return this.adjacencies;
	}

	/*
	 * returns edge weight between `this` and an adjacent node, or
	 * Integer.MAX_VALUE if it does not exist
	 */
	public int getEdgeWeight(Node adjacentNode) {
		Adjacency adj = getAdjacency(adjacentNode);
		if (adj != null) {
			return adj.getEdgeWeight();
		} else {
			return INF; // node not in adjacency list
		}
	}

	public int getEdgeID(Node adjacentNode) {
		Adjacency adj = getAdjacency(adjacentNode);
		if (adj != null) {
			return adj.getId();
		} else {
			return -1; // node not in adjacency list
		}
	}

	public Adjacency getAdjacency(Node adjacentNode) {
		for (Adjacency a : this.adjacencies) {
			if (a.getNode().equals(adjacentNode)) {
				return a;
			}
		}
		return null;
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Node");
		result.append(nodeDataString());
		result.append(": [");
		String prefix = "";
		for (Adjacency a : this.adjacencies) {
			result.append(prefix);
			result.append(a);
			prefix = ", ";
		}
		result.append("]");
		return result.toString();
	}

	/* returns the node data prepended by a space, if any */
	private String nodeDataString() {
		String dataString = dataToString();
		if (dataString != "") {
			dataString = " " + dataString;
		}
		return dataString;
	}

	public String dataToString() {
		return "";
	};

	@Override
	abstract public int compareTo(Object o);
}
