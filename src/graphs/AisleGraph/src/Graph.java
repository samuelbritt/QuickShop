import java.util.ArrayList;


public class Graph<T extends Node> {
	private ArrayList<T> nodes;

	public Graph() {
		this.nodes = new ArrayList<T>();
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
	
	public ArrayList<T> getNodes() {
		return nodes;
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
