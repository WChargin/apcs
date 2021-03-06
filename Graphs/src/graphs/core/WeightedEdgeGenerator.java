package graphs.core;

/**
 * A functional interface for generating a specific type of edge for given
 * values at runtime.
 * 
 * @author William Chargin
 * 
 * @param <T>
 *            the type of values represented by the nodes of the edge
 */
public abstract class WeightedEdgeGenerator<T, W extends Comparable<W>, E extends WeightedEdge<T, W>>
		implements EdgeGenerator<T, E> {

	/**
	 * The next weight to be used by this weight generator.
	 */
	public W nextWeight;

	/**
	 * Generates an edge {@code e} that "connects" the two given nodes,
	 * according to the edge's definition of
	 * {@linkplain Edge#connects(Node, Node) connection}.
	 * 
	 * @param n1
	 *            the first node
	 * @param n2
	 *            the second node
	 * @return some edge {@code e} such that {@link Edge#connects(Node, Node)
	 *         e.connects(n1, n2)} is {@code true}.
	 */
	public abstract E createEdge(Node<T> n1, Node<T> n2);

}
