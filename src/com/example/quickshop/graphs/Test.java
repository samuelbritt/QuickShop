package com.example.quickshop.graphs;

import java.util.ArrayList;

/**
 * @author sam
 * 
 */
public class Test {

	public static void main(String[] args) {
		int aisleCount = 12;
		int nodesPerAisle = 4;
		PrimalGraph P = new PrimalGraph(aisleCount, nodesPerAisle);
		System.out.println(P);

		System.out.println();

		DualGraph D = new DualGraph(P);
		System.out.println(D);

		PrimalNode n1 = new PrimalNode(0, 0);
		PrimalNode n2 = new PrimalNode(1, 0);
		System.out.println(n1 == n2);
		System.out.println(n1.equals(n2));
		System.out.println(n1.compareTo(n2));
		System.out.println(n1.hashCode());
		System.out.println(n2.hashCode());
		
		PrimalNode p00 = new PrimalNode(0, 0);
		PrimalNode p01 = new PrimalNode(0, 1);
		PrimalNode p10 = new PrimalNode(1, 0);
		System.out.println(new DualNode(1, p01, p00, 0).hashCode());
		System.out.println(new DualNode(2, p00, p10, 0).hashCode());
		
		Integer h = new Integer(0);
		ArrayList<Integer> hashes = new ArrayList<Integer>(D.getNodes().size());
		hashes.clear();
		int collCount = 0;
		int index = 0;
		for (Node n: D.getNodes()) {
			index++;
			h = Integer.valueOf(n.hashCode());
			int collIndex = hashes.indexOf(h);
			if (collIndex != -1) {
				DualNode coll = (DualNode) D.getNodes().get(collIndex);
				System.out.println("bad hashcode " + h + ": Node " + n.dataToString() + " collides with " + coll.dataToString());
//				System.out.println(i + ", " + j + ", " + ii + ", " + jj 
//						+ " collides with index " + collIndex);
				collCount++;
			}
			hashes.add(h);
		}
		System.out.println("collisions: " + collCount + "/" + index + ", " + ((float) collCount)/index + "%");

/*
		int max = 10;
		Integer h = new Integer(0);
		ArrayList<Integer> hashes = new ArrayList<Integer>(max * max * max * max);
		hashes.clear();
		int collCount = 0;
		int index = 0;
		for (int i = 0; i < max; i++) {
			for (int j = 0; j < max; j++) {
				PrimalNode p1 = new PrimalNode(i, j);
				for (int ii = 0; ii < max; ii++) {
					for (int jj = 0; jj < max; jj++) {
						index++;
						PrimalNode p2 = new PrimalNode(ii, jj);
						
						DualNode n = new DualNode(p1, p2, 0);
						h = Integer.valueOf(n.hashCode());
						System.out.println(index + 
								" hash(" + i + ", "	+ j + ", " + ii + ", " + jj + ") = " + h);

						int collIndex = hashes.indexOf(h);
						if (collIndex != -1) {
							System.out.print("bad hashcode " + h + ": ");
							System.out.println(i + ", " + j + ", " + ii + ", " + jj 
									+ " collides with index " + collIndex);
							collCount++;
						}
						hashes.add(h);
					}
				}
			}
		}
		System.out.println("collisions: " + collCount + "/" + index + ", " + ((float) collCount)/index + "%");
//		System.out.println(hashes);
	*/
	}
}
