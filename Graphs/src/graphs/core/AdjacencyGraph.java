package graphs.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A graph implementation that uses an adjacency list for storage. This graph
 * accepts a {@link EdgeGenerator} at construction.
 * 
 * @author William Chargin
 * 
 * @param <T>
 *            the type of value stored in the nodes of this graph
 * @param <N>
 *            the type of node in this graph
 * @param <E>
 *            the type of edge in this graph
 */
public class AdjacencyGraph<T, N extends Node<T>, E extends Edge<T>> implements
		MutableGraph<T, N, E> {

	/**
	 * The adjacency list for this graph.
	 */
	private Map<N, List<E>> adjacencyList = new HashMap<N, List<E>>();

	/**
	 * The edge generator for this graph.
	 */
	private EdgeGenerator<T, ? extends E> generator;

	public void setGenerator(EdgeGenerator<T, ? extends E> generator) {
		this.generator = generator;
	}

	/**
	 * Creates the graph with the given edge generator.
	 * 
	 * @param generator
	 *            the edge generator for the graph
	 */
	public AdjacencyGraph(EdgeGenerator<T, ? extends E> generator) {
		super();
		this.generator = generator;
	}

	@Override
	public void add(N node) {
		if (!contains(node)) {
			adjacencyList.put(node, new LinkedList<E>());
		}
	}

	private void addIsolatedVertices(List<N> toBeRemoved) {
		outerLoop: for (N n : adjacencyList.keySet()) {
			List<E> nlist = adjacencyList.get(n);
			if (nlist.isEmpty()) {
				// Check all other nodes as well.
				for (N m : adjacencyList.keySet()) {
					List<E> mlist = adjacencyList.get(m);
					for (E e : mlist) {
						if (e.connects(m, n)) {
							continue outerLoop;
						}
					}
				}
				toBeRemoved.add(n);
			}
		}
	}

	@Override
	public void clean() {
		List<N> toBeRemoved = new LinkedList<N>();
		addIsolatedVertices(toBeRemoved);
		do {
			Iterator<N> it = toBeRemoved.iterator();
			while (it.hasNext()) {
				unlink(it.next());
				it.remove();
			}
			addIsolatedVertices(toBeRemoved);
		} while (!toBeRemoved.isEmpty());
	}

	@Override
	public boolean connected(N n1, N n2) {
		List<E> list = adjacencyList.get(n1);
		if (list == null) {
			return false;
		}
		for (E e : list) {
			if (e.connects(n1, n2)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean contains(N node) {
		return adjacencyList.containsKey(node);
	}

	@Override
	public void foreachEdge(Operation<? super E> op) {
		for (E e : getEdges()) {
			op.invoke(e);
		}
	}

	@Override
	public void foreachNode(Operation<? super N> op) {
		for (N n : getNodes()) {
			op.invoke(n);
		}
	}

	@Override
	public Set<E> getEdges() {
		Set<E> edges = new HashSet<>();
		for (Map.Entry<N, List<E>> entry : adjacencyList.entrySet()) {
			for (E e : entry.getValue()) {
				edges.add(e);
			}
		}
		return edges;
	}

	@Override
	public Set<E> getNeighboringEdges(N node) {
		return new HashSet<E>(adjacencyList.get(node));
	}

	@Override
	public Set<Node<T>> getNeighbors(N node) {
		Set<Node<T>> neighbors = new HashSet<>();
		for (E edge : adjacencyList.get(node)) {
			neighbors.add(edge.getTail());
		}
		return neighbors;
	}

	@Override
	public Set<N> getNodes() {
		return new HashSet<>(adjacencyList.keySet());
	}

	@Override
	public void link(N n1, N n2) {
		adjacencyList.get(n1).add(generator.createEdge(n1, n2));
	}

	@Override
	public void remove(N node) {
		unlink(node);
		adjacencyList.remove(node);
	}

	@Override
	public void unlink(N n) {
		adjacencyList.get(n).clear();
		for (N m : adjacencyList.keySet()) {
			Iterator<E> it = adjacencyList.get(m).iterator();
			while (it.hasNext()) {
				E e = it.next();
				if (e.connects(m, n)) {
					it.remove();
				}
			}
		}
	}

	@Override
	public void unlink(N n1, N n2) {
		Iterator<E> it = adjacencyList.get(n1).iterator();
		while (it.hasNext()) {
			if (it.next().connects(n1, n2)) {
				it.remove();
			}
		}
	}

	@Override
	public void unlinkAll() {
		for (List<E> edgeList : adjacencyList.values()) {
			edgeList.clear();
		}
	}

}
