
public interface SingleSourcePathFinderFactory<T extends Node> {
	SingleSourceShortestPathFinder<T> makePathFinder(Graph<T> G, T source);
}
