
/**
 * A Target a pair of Coordinates in the store; the (undirected) edge between which corresponds to two DualNodes.
 * 
 * @author sam
 *
 */
public class Target {
	private Coordinates start;
	private Coordinates end;
	
	public Target(Coordinates start, Coordinates end) {
		this.start = start;
		this.end = end;
	}

	public Coordinates getStart() {
		return start;
	}

	public void setStart(Coordinates start) {
		this.start = start;
	}

	public Coordinates getEnd() {
		return end;
	}

	public void setEnd(Coordinates end) {
		this.end = end;
	}

}
