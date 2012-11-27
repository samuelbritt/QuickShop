import java.util.Arrays;
import java.util.List;

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
	private int nodeCount;

	public DualGraph(PrimalGraph P) {
		super();
		this.primalGraph = P;
		this.nodes = new DualNode[P.edgeCount()];
		this.nodeCount = 0;
		create();
	}

	// TODO: returns the source node
	public Node getSource() {
		return this.getNodes().get(0);
	}

	private void create() {
		addDualNodes();
		addDualEdges();
	}

	private void addDualNodes() {
		for (Node N : primalGraph.getNodes()) {
			PrimalNode n1 = (PrimalNode) N;
			for (Adjacency adj : n1.getAdjacencies()) {
				createNode(n1, adj);
			}
		}
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

	/*
	 * returns the dual node corresponding to the primal edge between n1 and n2,
	 * or null if it does not exist
	 */
	private DualNode correspondingDualNode(PrimalNode n1, PrimalNode n2) {
		for (Node N : getNodes()) {
			DualNode d = (DualNode) N;
			if (d.getStart().equals(n1) && d.getEnd().equals(n2)) {
				return d;
			}
		}
		return null;
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
