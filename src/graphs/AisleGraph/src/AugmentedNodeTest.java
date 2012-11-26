import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;


public class AugmentedNodeTest extends TestCase {
	SimpleNode n1, n2, n3;
	AugmentedNode a1, a2, a3;
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		n1 = new SimpleNode(1);
		n2 = new SimpleNode(2);
		n3 = new SimpleNode(3);
		
		a1 = new AugmentedNode(n1);
		a2 = new AugmentedNode(n2);
		a3 = new AugmentedNode(n3);
		
		// Paths using a1 as source: 
		// n1 -(2)-> n2 -(4)-> n3
		// n1 <-(3)- n2
		n1.addAdjacency(n2, 2);
		n2.addAdjacency(n3, 4);
		n2.addAdjacency(n1, 3);
		
		a1.setShortestPathEstimate(0);
		
		a3.setPredecessor(a2);
		a3.setShortestPathEstimate(6);

		a2.setPredecessor(a1);
		a2.setShortestPathEstimate(2);

	}

	@Test
	public void testGetPredecessor() {
		assertEquals(a1, a2.getPredecessor());
		assertEquals(null, a1.getPredecessor());
	}

	@Test
	public void testPathString() {
		assertEquals("(1, 0) > (2, 2)", a2.pathString());
		assertEquals("(1, 0) > (2, 2) > (3, 6)", a3.pathString());
	}

	@Test
	public void testRelax() {
		int weight = a2.getShortestPathEstimate();
		a2.setPredecessor(a3);
		a2.setShortestPathEstimate(weight + 10);

		a1.relax(a2);
		
		assertEquals(a1, a2.getPredecessor());
		assertEquals(weight, a2.getShortestPathEstimate());
		
	}

}
