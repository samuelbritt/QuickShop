import java.util.HashMap;

public class PathFinderAllPairsShortest<T extends Node> {
	private PathFinderSingleSourceShortestFactory<T> singleSourceFactory;
	private Graph<T> graph;
	private HashMap<T, PathFinderSingleSourceShortest<T>> sourceToPaths;

	public PathFinderAllPairsShortest(Graph<T> graph,
			PathFinderSingleSourceShortestFactory<T> singleSourceFactory) {
		this.graph = graph;
		this.singleSourceFactory = singleSourceFactory;
		this.sourceToPaths = new HashMap<T, PathFinderSingleSourceShortest<T>>();
	}

	public void findShortestPaths() {
		for (T source : graph.getNodes()) {
			PathFinderSingleSourceShortest<T> finder;
			finder = singleSourceFactory.makePathFinder(graph, source);
			finder.findShortestPaths();
			sourceToPaths.put(source, finder);
		}
	}
	
	public int getShortestPath(Node source, Node dest) {
		return sourceToPaths.get(source).getShortestPathWeight(dest);
	}
}
