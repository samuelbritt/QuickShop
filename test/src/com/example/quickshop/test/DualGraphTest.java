package com.example.quickshop.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.quickshop.graphs.Coordinates;
import com.example.quickshop.graphs.DualGraph;
import com.example.quickshop.graphs.PrimalGraph;


public class DualGraphTest {
	private PrimalGraph P;
	private DualGraph D;

	@Before
	public void setUp() throws Exception {
		int aisleCount = 3;
		int nodesPerAisle = 4;
		P = new PrimalGraph(aisleCount, nodesPerAisle, new Coordinates(0,0));
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
