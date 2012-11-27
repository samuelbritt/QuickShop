import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class DijkstraTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		/* Example from CLRS 3e, page 659 */
		Graph<SimpleNode> G = new SimpleGraph();
		
		for (int i = 0; i < 5; i++) {
			G.addNode(new SimpleNode(i));
		}
		
		List<SimpleNode> n = G.getNodes();
		G.createSingleEdge(n.get(0), n.get(1), 10);
		assertEquals(10, n.get(0).getEdgeWeight(n.get(1)));
		G.createSingleEdge(n.get(0), n.get(2), 5);
		G.createSingleEdge(n.get(1), n.get(2), 2);
		G.createSingleEdge(n.get(1), n.get(3), 1);
		G.createSingleEdge(n.get(2), n.get(1), 3);
		G.createSingleEdge(n.get(2), n.get(3), 9);
		G.createSingleEdge(n.get(2), n.get(4), 2);
		G.createSingleEdge(n.get(3), n.get(4), 4);
		G.createSingleEdge(n.get(4), n.get(3), 6);
		G.createSingleEdge(n.get(4), n.get(0), 7);

		Dijkstra<SimpleNode> dij = new Dijkstra<SimpleNode>(G, n.get(0));
		dij.findShortestPaths();
		
		checkPredAndPath(dij, n, 4, 2, 7);
		checkPredAndPath(dij, n, 2, 0, 5);
		checkPredAndPath(dij, n, 0, -1, 0);
		checkPredAndPath(dij, n, 1, 2, 8);
	}
	
	private void checkPredAndPath(Dijkstra<SimpleNode> dij, 
			List<SimpleNode> nodes,
			int nodeIndex, int predIndex, int expectedWeight) {
		Node node = getNode(nodes, nodeIndex);
		Node pred = getNode(nodes, predIndex);
		checkPred(dij, node, pred);
		checkPath(dij, node, expectedWeight);
	}
	
	private Node getNode(List<SimpleNode> nodes, int nodeIndex) {
		if (nodeIndex > -1) {
			return nodes.get(nodeIndex);
		}
		return null;
	}
	
	private void checkPred(Dijkstra<SimpleNode> dij, Node n, Node pred) {
		AugmentedNode an = getAug(dij, n);
		AugmentedNode apred = getAug(dij, pred);
		assertEquals(apred, an.getPredecessor());
	}
	
	private void checkPath(Dijkstra<SimpleNode> dij, Node n, int weight) {
		AugmentedNode an = getAug(dij, n);
		assertEquals(weight, an.getPathWeight());
	}
	
	private AugmentedNode getAug(Dijkstra<SimpleNode> dij, Node n) {
		return dij.getAugmentedNode(n);
	}

}