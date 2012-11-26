
public class DijkstraFactory<T extends Node> implements SingleSourcePathFinderFactory<T> {

	@Override
	public SingleSourceShortestPathFinder<T> makePathFinder(Graph<T> G, T source) {
		return new Dijkstra<T>(G, source);
	}

}
