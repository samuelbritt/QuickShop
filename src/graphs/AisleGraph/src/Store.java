import java.util.Collections;
import java.util.List;

/**
 * Holds information about the store layout. Finds optimal paths through the
 * store.
 * 
 * @author sam
 * 
 */
public class Store {
	PrimalGraph P;
	DualGraph D;
	PathFinderAllPairsShortest<DualNode> APSP_Finder;

	public Store(int aisleCount, int nodesPerAisle) {
		P = new PrimalGraph(aisleCount, nodesPerAisle);
		D = new DualGraph(P);
		APSP_Finder = new PathFinderAllPairsShortest<DualNode>(
				D, new DijkstraFactory<DualNode>());
		APSP_Finder.findShortestPaths();
	}

	/**
	 * Sorts `targets` in place. The resulting sort is optimal for this store.
	 */
	public void optimalPathSort(List<Node> targets) {
		Node start = D.getSource();
		for (int i = 0; i < targets.size(); i++) {
			int nextIndex = i + findMinPath(start,
			                                targets.subList(i, targets.size()));
			Collections.swap(targets, i, nextIndex);
			start = targets.get(nextIndex);
		}
	}

	/**
	 * returns the index of the Node `v` in `targets` with the shortest path
	 * from `source` to `v`.
	 */
	private int findMinPath(Node source, List<Node> targets) {
		int min = 0;
		int minIndex = -1;
		for (int i = 0; i < targets.size(); i++) {
			int pathLength =
			        APSP_Finder.getShortestPath(source, targets.get(i));
			if (pathLength < min) {
				min = pathLength;
				minIndex = i;
			}
		}
		assert minIndex >= 0;
		return minIndex;
	}
}
