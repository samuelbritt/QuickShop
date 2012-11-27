package com.example.quickshop.graphs;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class StoreTest {
	Store S;
	private final static int aisleCount = 10;
	private final static int nodesPerAisle = 4;
	private Coordinates[][] coords;

	@Before
	public void setUp() throws Exception {
		S = new Store(aisleCount, nodesPerAisle);
		for (int aisle = 0; aisle < aisleCount; aisle++) {
			for (int node = 0; node < nodesPerAisle; node++) {
				coords[aisle][node] = new Coordinates(aisle, node);
			}
		}
	}

	@Test
	public void testOptimalPathSort() {
		int targetCount = 3;
		Target[] targets = new Target[targetCount];
		targets[0] = new Target(coords[0][0], coords[0][1]);
		targets[1] = new Target(coords[1][0], coords[1][1]);
		targets[2] = new Target(coords[1][nodesPerAisle - 2], coords[1][nodesPerAisle - 1]);
		S.optimalSort(targets);
		fail("Not yet implemented");
	}

	@Test
	public void testFindMinPath() {
		fail("Not yet implemented");
	}

}
