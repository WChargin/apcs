package graphs.core;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TreeNode<T> implements Iterable<TreeNode<T>> {

	private Set<TreeNode<T>> children;

	public TreeNode() {
		children = new HashSet<TreeNode<T>>();
	}

	public boolean addChild(TreeNode<T> n) {
		return children.add(n);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeNode<?> other = (TreeNode<?>) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		return result;
	}

	public Iterator<TreeNode<T>> iterator() {
		return children.iterator();
	}

	public boolean removeChild(TreeNode<T> n) {
		return children.remove(n);
	}
}
