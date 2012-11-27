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
	
	/* returns the pair of dual nodes that correspond to the two edges implied by Target */
	public DualNode[] matchTarget(Target target) {
		PrimalNode pstart = primalGraph.getNode(target.getStart());
		PrimalNode pend = primalGraph.getNode(target.getEnd());
		DualNode[] arr = new DualNode[2];
		arr[0] = correspondingDualNode(pstart, pend);
		arr[1] = correspondingDualNode(pend, pstart);
		return arr;
	}

	/*
	 * returns the dual node corresponding to the primal edge between p1 and p2,
	 * or null if it does not exist
	 */
	private DualNode correspondingDualNode(PrimalNode p1, PrimalNode p2) {
		int edgeID = p1.getEdgeID(p2);
		if (edgeID >= 0) {
			return nodes[edgeID];
		} else {
			return null;
		}
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
