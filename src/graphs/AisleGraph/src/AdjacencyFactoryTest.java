import static org.junit.Assert.*;

import org.junit.Test;


public class AdjacencyFactoryTest {

	@Test
	public void testCreateAdjacency() {
		SimpleNode n0 = new SimpleNode(0);
		
		AdjacencyFactory AF = new AdjacencyFactory();
		for (int i = 0; i < 10; i++) {
			Adjacency a = AF.createAdjacency(n0, 0);
			assertEquals(i, a.getId());
        }

		AF = new AdjacencyFactory();
		for (int i = 0; i < 10; i++) {
			Adjacency a = AF.createAdjacency(n0, 0);
			assertEquals(i, a.getId());
		}
	}

}
