import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class DualGraphTest {
	private PrimalGraph P;
	private DualGraph D;

	@Before
	public void setUp() throws Exception {
		int aisleCount = 3;
		int nodesPerAisle = 4;
		P = new PrimalGraph(aisleCount, nodesPerAisle);
		D = new DualGraph(P);
	}

	@Test
	public void testNodeCount() {
		assertEquals(P.edgeCount(), D.nodeCount());
	}

	@Test
	public void testAddNodeDualNode() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNodes() {
		fail("Not yet implemented");
	}

}
