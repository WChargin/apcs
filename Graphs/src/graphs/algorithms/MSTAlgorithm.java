package graphs.algorithms;

import graphs.core.Graph;
import graphs.core.Node;
import graphs.core.WeightedEdge;

import java.util.List;

/**
 * An algorithm that can find a minimum spanning tree from a
 * {@link graphs.core.Graph Graph}.
 * 
 * @author William Chargin
 * 
 */
public interface MSTAlgorithm {

	/**
	 * Finds a minimum spanning tree for the given graph.
	 * 
	 * @param graph
	 *            the graph
	 * @return a minimum spanning tree
	 */
	public <T, W extends Comparable<W>> List<WeightedEdge<T, W>> findMST(
			Graph<T, Node<T>, ? extends WeightedEdge<T, W>> graph);

}
