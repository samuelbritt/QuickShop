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
		this.aisleCount = aisleCount;
		this.nodesPerAisle = nodesPerAisle;
		create();
	}

	private void create() {
		PrimalNode[] currentAisle = createAisle(0);
		for (int x = 1; x < this.aisleCount; x++) {
			PrimalNode[] nextAisle = createAisle(x);
			connectAisle(currentAisle, nextAisle);
			currentAisle = nextAisle;
		}
	}

	/* creates a single aisle, an array of (doubly) connected nodes */
	private PrimalNode[] createAisle(int x) {
		PrimalNode[] aisle = new PrimalNode[this.nodesPerAisle];
		aisle[0] = addNewNode(x, 0);
		for (int y = 1; y < this.nodesPerAisle; y++) {
			aisle[y] = addNewNode(x, y);
			createDoubleEdge(aisle[y-1], aisle[y], edgeWeight);
		}
		return aisle;
	}

	/* given the two aisles of nodes, connects their first and last nodes */
	private void connectAisle(PrimalNode[] aisle1, PrimalNode[] aisle2) {
		createDoubleEdge(aisle1[0], aisle2[0], edgeWeight);
		createDoubleEdge(aisle1[aisle1.length - 1], aisle2[aisle2.length - 1], edgeWeight);
	}
	
	private PrimalNode addNewNode(int x, int y) {
		PrimalNode n = new PrimalNode(x, y);
		super.addNode(n);
		return n;
	}

	@Override
	public String toString() {
		return "Primal " + super.toString();
	}
}
