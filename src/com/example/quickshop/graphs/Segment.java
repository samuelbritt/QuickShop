package com.example.quickshop.graphs;

/**
 * A Segment is a pair of Coordinates in the store; the (undirected) edge
 * between which corresponds to two DualNodes.
 * 
 * @author sam
 * 
 */
public class Segment {
	private Coordinates start;
	private Coordinates end;
	
	public Segment(int startx, int starty, int endx, int endy) {
		setCoords(new Coordinates(startx, starty), new Coordinates(endx, endy));
	}

	public Segment(Coordinates start, Coordinates end) {
		setCoords(start, end);
	}
	
	private void setCoords(Coordinates start, Coordinates end) {
		this.start = start;
		this.end = end;
		
	}

	public Coordinates getStart() {
		return start;
	}

	public void setStart(Coordinates start) {
		this.start = start;
	}

	public Coordinates getEnd() {
		return end;
	}

	public void setEnd(Coordinates end) {
		this.end = end;
	}
	
	@Override
	public String toString() {
		return "Segment: " + start.toString() + ", " + end.toString();
	}

}
