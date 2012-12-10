package com.example.quickshop.test;

import java.util.ArrayList;

import junit.framework.TestCase;

import com.example.quickshop.graphs.Coordinates;
import com.example.quickshop.graphs.DualNode;
import com.example.quickshop.graphs.GraphCategory;
import com.example.quickshop.graphs.GraphCategoryFactory;
import com.example.quickshop.graphs.GraphStore;
import com.example.quickshop.graphs.Node;
import com.example.quickshop.graphs.Segment;

public class StoreTest extends TestCase {
	private GraphStore<GraphCategory> S;
	private final static int aisleCount = 3;
	private final static int nodesPerAisle = 4;
	private final static Coordinates startCoords = new Coordinates(0,0);
	private Coordinates[][] coords;

	public StoreTest() {
		super();
	}

	@Override
	protected void setUp() throws Exception {
		GraphCategoryFactory fact = new GraphCategoryFactory();
		S = new GraphStore<GraphCategory>(aisleCount, nodesPerAisle, startCoords, fact);
		coords = new Coordinates[aisleCount][nodesPerAisle];
		for (int aisle = 0; aisle < aisleCount; aisle++) {
			for (int node = 0; node < nodesPerAisle; node++) {
				coords[aisle][node] = new Coordinates(aisle, node);
			}
		}

	}

	public void testPathSort() {
		Segment seg0203 = makeSegment(0, 2, 0, 3);
		Segment seg1011 = makeSegment(1, 0, 1, 1);
		Segment seg1112 = makeSegment(1, 1, 1, 2);
		Segment seg1213 = makeSegment(1, 2, 1, 3);
		Segment seg2021 = makeSegment(2, 0, 2, 1);
		Segment seg2122 = makeSegment(2, 1, 2, 2);
		Segment seg2223 = makeSegment(2, 2, 2, 3);

		ArrayList<Segment> segList = new ArrayList<Segment>();
		segList.add(seg1011);
		segList.add(seg1112);
		GraphCategory bread = S.addCategory("Bread", segList);
		
		segList.clear();
		segList.add(seg1213);
		GraphCategory milk = S.addCategory("Milk", segList);
		
		segList.clear();
		segList.add(seg0203);
		GraphCategory juice = S.addCategory("Juice", segList);
		
		segList.clear();
		segList.add(seg2021);
		segList.add(seg2122);
		GraphCategory paper = S.addCategory("Paper", segList);
		
		segList.clear();
		segList.add(seg2122);
		segList.add(seg2223);
		GraphCategory cereal = S.addCategory("Cereal", segList);

		for (DualNode dest: S.getDualGraph().getNodes()) {
			int distance = S.getDistance(S.getDualGraph().getSource(), (Node) dest);
			System.out.println("d = " + distance + ", " + dest.toString());
		}

		ArrayList<GraphCategory> catList = new ArrayList<GraphCategory>();

		catList.add(bread);
		catList.add(milk);
		System.out.println(catList);
		S.optimalPathSort(catList);
		System.out.println(catList);
		
		catList.add(juice);
		System.out.println(catList);
		S.optimalPathSort(catList);
		System.out.println(catList);

		catList.clear();
		catList.add(milk);
		catList.add(cereal);
		catList.add(bread);
		catList.add(juice);
		catList.add(paper);
		System.out.println(catList);
		S.optimalPathSort(catList);
		System.out.println(catList);
	}

	private Segment makeSegment(int x1, int y1, int x2, int y2) {
		return new Segment(coords[x1][y1], coords[x2][y2]);
	}

}
