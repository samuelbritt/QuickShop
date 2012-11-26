public class AllPairsShortestPathFinder<T extends Node> {
	private SingleSourcePathFinderFactory<T> singleSourceFactory;
	private Graph<T> graph;

	public AllPairsShortestPathFinder(Graph<T> graph,
			SingleSourcePathFinderFactory<T> singleSourceFactory) {
		this.graph = graph;
		this.singleSourceFactory = singleSourceFactory;
	}

	public void findShortestPaths() {
		for (T source : graph.getNodes()) {
			SingleSourceShortestPathFinder<T> finder;
			finder = singleSourceFactory.makePathFinder(graph, source);
			finder.findShortestPaths();
		}
	}
}
