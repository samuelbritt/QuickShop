import java.util.HashMap;

public class AllPairsShortestPathFinder<T extends Node> {
	private SingleSourcePathFinderFactory<T> singleSourceFactory;
	private Graph<T> graph;
	private HashMap<T, SingleSourceShortestPathFinder<T>> sourceToPaths;

	public AllPairsShortestPathFinder(Graph<T> graph,
			SingleSourcePathFinderFactory<T> singleSourceFactory) {
		this.graph = graph;
		this.singleSourceFactory = singleSourceFactory;
		this.sourceToPaths = new HashMap<T, SingleSourceShortestPathFinder<T>>();
	}

	public void findShortestPaths() {
		for (T source : graph.getNodes()) {
			SingleSourceShortestPathFinder<T> finder;
			finder = singleSourceFactory.makePathFinder(graph, source);
			finder.findShortestPaths();
			sourceToPaths.put(source, finder);
		}
	}
	
	public int getShortestPath(Node source, Node dest) {
		return sourceToPaths.get(source).getShortestPathWeight(dest);
	}
}
