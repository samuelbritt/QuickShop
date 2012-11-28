package com.example.quickshop.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.quickshop.graphs.Adjacency;
import com.example.quickshop.graphs.AdjacencyFactory;
import com.example.quickshop.graphs.SimpleNode;


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
