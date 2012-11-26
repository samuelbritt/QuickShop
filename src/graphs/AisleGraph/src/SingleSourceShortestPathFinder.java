import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * 
 */

/**
 * @author sam
 * 
 */
public abstract class SingleSourceShortestPathFinder<T extends Node> {
	private Graph<T> graph;
	private Node source;
	private PriorityQueue<AugmentedNode> pathList;
	private HashMap<Node, AugmentedNode> nodeToAugmented;
	
	public SingleSourceShortestPathFinder() {
		this.graph = null;
		this.source = null;
		this.pathList = new PriorityQueue<AugmentedNode>();
		this.nodeToAugmented = new HashMap<Node, AugmentedNode>();
	}

	public SingleSourceShortestPathFinder(Graph<T> G, Node source) {
		this.graph = G;
		this.source = source;
		this.pathList = new PriorityQueue<AugmentedNode>();
		this.nodeToAugmented = new HashMap<Node, AugmentedNode>();

		initialize();
	}
	
	/* Algorithm to run the single pairs shortest path algorithm */
	abstract void run();
	
	private void initialize() {
		for (Node node : this.graph.getNodes()) {
			addNewAugmentedNode(node);
		}
	}
	
	private AugmentedNode addNewAugmentedNode(Node node) {
		AugmentedNode augNode = new AugmentedNode(node);
		augNode.setInfPath();
		if (node.equals(source)) {
			augNode.setShortestPathEstimate(0);
		}
		pathList.add(augNode);
		nodeToAugmented.put(node, augNode);
		return augNode;
	}
	
	public AugmentedNode getAugmentedNode(Node node) {
		return nodeToAugmented.get(node);
	}

	public Graph<T> getGraph() {
		return graph;
	}

	public Node getSource() {
		return source;
	}
	
	public AugmentedNode removeMin() {
		return pathList.poll();
	}
	
	public boolean isEmpty() {
		return pathList.isEmpty();
	}
	
	public int nodeCount() {
		return pathList.size();
	}
}