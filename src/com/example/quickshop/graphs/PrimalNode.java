package com.example.quickshop.graphs;

/**
 * @author sam
 * 
 */
public class PrimalNode extends Node {
	private Coordinates coords;

	public PrimalNode(int x, int y) {
		super();
		this.coords = new Coordinates(x, y);
	}

	public int getX() {
		return coords.getX();
	}

	public int getY() {
		return coords.getY();
	}

	public boolean onSameAisle(PrimalNode node) {
		return this.coords.getX() == node.coords.getX();
	}

	@Override
	public String dataToString() {
		return coords.toString();
	}

	@Override
	public int compareTo(Object o) {
		return coords.compareTo(((PrimalNode) o).coords);
	}

	@Override
	public boolean equals(Object o) {
		return coords.equals(((PrimalNode) o).coords);
	}

	@Override
	public int hashCode() {
		return coords.hashCode();
	}
}