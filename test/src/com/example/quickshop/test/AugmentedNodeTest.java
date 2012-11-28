package com.example.quickshop.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.example.quickshop.graphs.Adjacency;
import com.example.quickshop.graphs.AugmentedNode;
import com.example.quickshop.graphs.SimpleNode;


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
		n1.addAdjacency(new Adjacency(1, n2, 2));
		n2.addAdjacency(new Adjacency(2, n3, 4));
		n2.addAdjacency(new Adjacency(3, n1, 3));
		
		a1.setPathWeight(0);
		
		a3.setPredecessor(a2);
		a3.setPathWeight(6);

		a2.setPredecessor(a1);
		a2.setPathWeight(2);

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
		int weight = a2.getPathWeight();
		a2.setPredecessor(a3);
		a2.setPathWeight(weight + 10);

		a1.relax(a2);
		
		assertEquals(a1, a2.getPredecessor());
		assertEquals(weight, a2.getPathWeight());
		
	}

}
