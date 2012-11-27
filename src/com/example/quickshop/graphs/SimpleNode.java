package com.example.quickshop.graphs;


/** simple node type used for testing graph algos */
class SimpleNode extends Node {
	private int x;
	
	public SimpleNode(int x) {
		this.x = x;
	}

	@Override
	public int compareTo(Object o) {
		return this.x - ((SimpleNode) o).x;
	}
	
	public boolean equals(SimpleNode n) {
		return this.x == n.x;
	}
	
	@Override
	public int hashCode() {
		int result = HashCodeUtil.SEED;
		return HashCodeUtil.hash(result, x);
	}
	
	public String dataToString() {
		return "" + x;
	}
}