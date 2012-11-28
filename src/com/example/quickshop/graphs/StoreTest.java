package com.example.quickshop.graphs;

import java.util.ArrayList;

public class StoreTest {
	private static Store S;
	private final static int aisleCount = 10;
	private final static int nodesPerAisle = 4;
	private static Coordinates[][] coords;

	public static void main(String[] args) {
		setUp();
		testOptimalPathSort();
	}

	public static void setUp() {
		S = new Store(aisleCount, nodesPerAisle);
		coords = new Coordinates[aisleCount][nodesPerAisle];
		for (int aisle = 0; aisle < aisleCount; aisle++) {
			for (int node = 0; node < nodesPerAisle; node++) {
				coords[aisle][node] = new Coordinates(aisle, node);
			}
		}
	}

	public static void testOptimalPathSort() {
		Segment seg0203 = makeSegment(0,2,0,3);
		Segment seg1011 = makeSegment(1,0,1,1);
		Segment seg1112 = makeSegment(1,1,1,2);
		Segment seg1213 = makeSegment(1,2,1,3);
		Segment seg2021 = makeSegment(2,0,2,1);
		Segment seg2122 = makeSegment(2,1,2,2);
		Segment seg2223 = makeSegment(2,2,2,3);
		
		Category bread = S.addCategory("Bread", new Segment[] {seg1011, seg1112}); 
		Category milk = S.addCategory("Milk", new Segment[] {seg1213}); 
		Category juice = S.addCategory("Juice", new Segment[] {seg0203});
		Category paper = S.addCategory("Paper", new Segment[] {seg2021, seg2122});
		Category cereal = S.addCategory("Cereal", new Segment[] {seg2122, seg2223});
		
		ArrayList<Category> catList = new ArrayList<Category>();
		catList.add(bread);
		catList.add(milk);
		catList.add(juice);
		System.out.println(catList);
		S.optimalPathSort(catList);
		System.out.println(catList);
	}
	
	private static Segment makeSegment(int x1, int y1, int x2, int y2) {
		return new Segment(coords[x1][y1], coords[x2][x2]);
	}
}
