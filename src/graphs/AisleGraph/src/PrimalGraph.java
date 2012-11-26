/**
 * Holds the primal graph data
 * 
 * @author sam
 *
 */

public class PrimalGraph extends Graph<PrimalNode> {
	private int aisleCount;
	private int nodesPerAisle;
	private static final int edgeWeight = 1;

	public PrimalGraph(int aisleCount, int nodesPerAisle) {
		super();
		this.setAisleCount(aisleCount);
		this.nodesPerAisle = nodesPerAisle;
		create();
	}

	private void create() {
		PrimalNode[] prev = createAisle(0);
		for (int x = 1; x < this.aisleCount; x++) {
			PrimalNode[] next = createAisle(x);
			connectAisle(prev, next);
			prev = next;
		}
	}

	/* creates a single aisle, a string of (doubly) connected nodes */
	private PrimalNode[] createAisle(int x) {
		PrimalNode[] aisle = new PrimalNode[this.nodesPerAisle];
		aisle[0] = createNode(x, 0);
		for (int y = 1; y < this.nodesPerAisle; y++) {
			aisle[y] = createNode(x, y);
			createDoubleEdge(aisle[y-1], aisle[y], edgeWeight);
		}
		return aisle;
	}

	/* given the two aisles of nodes, connects their first and last nodes */
	private void connectAisle(PrimalNode[] aisle1, PrimalNode[] aisle2) {
		createDoubleEdge(aisle1[0], aisle2[0], edgeWeight);
		createDoubleEdge(aisle1[aisle1.length - 1], aisle2[aisle2.length - 1], edgeWeight);
	}
	
	private PrimalNode createNode(int x, int y) {
		PrimalNode n = new PrimalNode(x, y);
		super.addNode(n);
		return n;
	}

	public int getAisleCount() {
		return aisleCount;
	}

	public void setAisleCount(int aisleCount) {
		this.aisleCount = aisleCount;
	}

	@Override
	public String toString() {
		return "Primal " + super.toString();
	}
}
