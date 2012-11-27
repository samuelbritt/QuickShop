import java.util.LinkedList;
import java.util.List;


public class Graph<T extends Node> {
	private LinkedList<T> nodes;

	public Graph() {
		this.nodes = new LinkedList<T>();
	}
	
	public T addNode(T n) {
		nodes.add(n);
		return n;
	}
	
	public void createDoubleEdge(T n1, T n2, int weight) {
		createSingleEdge(n1, n2, weight);
		createSingleEdge(n2, n1, weight);
	}

	public void createSingleEdge(T n1, T n2, int weight) {
		n1.addAdjacency(n2, weight);
	}
	
	public List<T> getNodes() {
		return nodes;
	}
	
	public int nodeCount() {
		return nodes.size();
	}

	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("Graph {\n");
		for (T n : getNodes()) {
			result.append("  " + n + ",\n");
		}
		result.append("}");
		return result.toString();
	}
}
