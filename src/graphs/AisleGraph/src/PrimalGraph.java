import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Holds the primal graph data
 * 
 * @author sam
 * 
 */

public class PrimalGraph extends Graph<PrimalNode> {
	private int aisleCount;
	private int nodesPerAisle;
	private PrimalNode[][] nodes;
	private static final int edgeWeight = 1;

	public PrimalGraph(int aisleCount, int nodesPerAisle) {
		super();
		this.aisleCount = aisleCount;
		this.nodesPerAisle = nodesPerAisle;
		this.nodes = new PrimalNode[aisleCount][nodesPerAisle];
		create();
	}

	private void create() {
		createAisles();
		connectAisles();
	}

	private void createAisles() {
		for (int aisle = 0; aisle < this.aisleCount; aisle++) {
			createSingleAisle(aisle);
		}
	}

	/* creates a single aisle, an array of (doubly) connected nodes */
	private void createSingleAisle(int aisleNumber) {
		PrimalNode[] aisle = nodes[aisleNumber];
		aisle[0] = new PrimalNode(aisleNumber, 0);
		for (int y = 1; y < this.nodesPerAisle; y++) {
			aisle[y] = new PrimalNode(aisleNumber, y);
			createDoubleEdge(aisle[y - 1], aisle[y], edgeWeight);
		}
	}

	private void connectAisles() {
		for (int aisle = 1; aisle < this.aisleCount; aisle++) {
			connectAisle(nodes[aisle], nodes[aisle - 1]);
		}
	}

	/* given the two aisles of nodes, connects their first and last nodes */
	private void connectAisle(PrimalNode[] aisle1, PrimalNode[] aisle2) {
		createDoubleEdge(aisle1[0], aisle2[0], edgeWeight);
		createDoubleEdge(aisle1[aisle1.length - 1], aisle2[aisle2.length - 1],
		                 edgeWeight);
	}

	@Override
	public String toString() {
		return "Primal " + super.toString();
	}

	@Override
	public PrimalNode addNode(PrimalNode n) {
		nodes[n.getX()][n.getY()] = n;
		return n;
	}

	@Override
	public List<PrimalNode> getNodes() {
		LinkedList<PrimalNode> list = new LinkedList<PrimalNode>();
		for (int aisle = 0; aisle < aisleCount; aisle++) {
			list.addAll(Arrays.asList(nodes[aisle]));
		}
		return list;
	}

	@Override
	public int nodeCount() {
		return aisleCount * nodesPerAisle;
	}
}
