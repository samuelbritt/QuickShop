/**
 * A simple class to assist in making Adjacencies (edges). Maintains ID to
 * ensure that all edges created by an object of type AdjacencyFactory have a
 * unique ID.
 * 
 * @author sam
 * 
 */
public class AdjacencyFactory {
	private int id;

	public AdjacencyFactory() {
		id = 0;
	}

	public Adjacency createAdjacency(Node node, int edgeWeight) {
		return new Adjacency(id++, node, edgeWeight);
	}
	
	public int maxID() {
		return id;
	}

}
