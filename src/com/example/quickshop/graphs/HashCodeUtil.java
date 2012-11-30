package com.example.quickshop.graphs;

/**
 * Just helps with making hash codes. See also
 * http://stackoverflow.com/questions/113511/hash-code-implementation
 * and
 * http://www.javapractices.com/topic/TopicAction.do?Id=28
 * 
 * Use like:
 * 
 * public int hashCode() {
 * 		int result = HashCodeUtil.SEED;
 * 		result = HashCodeUtil.hash(result, x);
 * 		result = HashCodeUtil.hash(result, y);
 * 		return result;
 * }
 * 
 * @author sam
 *
 */

public final class HashCodeUtil {
	public static final int SEED = 37;
	
	public static int hash(int seed, int x) {
		return firstTerm(seed) + x;
	}

	public static int hash(int seed, boolean x) {
		return firstTerm(seed) + (x ? 0 : 1);
	}

	public static int hash(int seed, Object o) {
		return firstTerm(seed) + (o == null ? 0 : o.hashCode());
	}
	
	private static final int PRIME = 102653;
	private static int firstTerm(int seed) {
		return seed * PRIME;
	}

}
