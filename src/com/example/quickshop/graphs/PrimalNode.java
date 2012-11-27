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

class Coordinates implements Comparable<Object> {

	private int x_coord;
	private int y_coord;

	public Coordinates(int x, int y) {
		this.x_coord = x;
		this.y_coord = y;
	}

	/*
	 * return 0 if the same, negative if `this` is less than `o`, positive
	 * otherwise
	 */
	@Override
	public int compareTo(Object o) {
		Coordinates other = (Coordinates) o;
		int deltaX = this.x_coord - other.x_coord;
		return deltaX != 0 ? deltaX : this.y_coord - other.y_coord;
	}

	@Override
	public boolean equals(Object o) {
		Coordinates other = (Coordinates) o;
		return this.x_coord == other.x_coord && this.y_coord == other.y_coord;
	}

	@Override
	public int hashCode() {
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash(result, x_coord);
		result = HashCodeUtil.hash(result, y_coord);
		return result;
	}

	@Override
	public String toString() {
		return this.x_coord + "." + this.y_coord;
	}

	public int getX() {
		return x_coord;
	}

	public void setX(int x_coord) {
		this.x_coord = x_coord;
	}

	public int getY() {
		return y_coord;
	}

	public void setY(int y_coord) {
		this.y_coord = y_coord;
	}

	public void setCoords(int x, int y) {
		setX(x);
		setY(y);
	}
}