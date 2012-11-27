import java.util.ArrayList;
import java.util.List;


public class SimpleGraph extends Graph<SimpleNode> {
	private ArrayList<SimpleNode> nodes;

	public SimpleGraph() {
		super();
		nodes = new ArrayList<SimpleNode>();
	}
	
	@Override
	public SimpleNode addNode(SimpleNode n) {
		nodes.add(n);
		return n;
	}

	@Override
	public List<SimpleNode> getNodes() {
		return nodes;
	}

	@Override
	public int nodeCount() {
		return nodes.size();
	}

}
