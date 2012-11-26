
public interface NodeData extends Comparable<Object> {

	abstract public String toString();
	
	@Override
	abstract public int compareTo(Object o);
}
