/**
 * Creates new SingeSourceShortestPathFinders. Use to pass in arbitrary single
 * source finders into allPairs finders.
 * 
 */
public interface SingleSourcePathFinderFactory<T extends Node> {
	SingleSourceShortestPathFinder<T> makePathFinder(Graph<T> G, T source);
}
