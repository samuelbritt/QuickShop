import java.util.ArrayList;

/**
 * Node abstract base class, implements basic methods, handles adjacencies
 * 
 * @author sam
 * 
 */
public abstract class Node implements Comparable<Object> {

	private static final int INF = Integer.MAX_VALUE;
	private ArrayList<Adjacency> adj;

	public Node() {
		createAdjList();
	}

	private void createAdjList() {
		this.adj = new ArrayList<Adjacency>();
	}

	public void addAdjacency(Node n, int weight) {
		this.adj.add(new Adjacency(n, weight));
	}
	
	public ArrayList<Adjacency> getAdjacencies() {
		return this.adj;
	}
	
	/* returns edge weight between `this` and an adjacent node, or Integer.MAX_VALUE if it does not exist */
	public int getEdgeWeight(Node adjacentNode) {
		for (Adjacency adj : this.adj) {
			if (adj.getNode().equals(adjacentNode)) {
				return adj.getEdgeWeight();
			}
		}
		return INF; // node not in adjacency list
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Node");
		result.append(nodeDataString());
		result.append(": [");
		String prefix = "";
		for (Adjacency a : this.adj) {
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
