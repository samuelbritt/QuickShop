import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class StoreTest {
	Store S;
	private final static int aisleCount = 10;
	private final static int nodesPerAisle = 4;

	@Before
	public void setUp() throws Exception {
		S = new Store(aisleCount, nodesPerAisle);
	}

	@Test
	public void testOptimalPathSort() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindMinPath() {
		fail("Not yet implemented");
	}

}
