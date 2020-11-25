package material.tree;

import java.util.Deque;
import material.Position;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jvelez
 * @param <T>
 */
public class PosOrderTreeIterator<T> implements Iterator<Position<T>> {

    private Deque<Position<T>> queue;
    private Tree<T> tree;

    public PosOrderTreeIterator(Tree<T> tree) {
        this(tree, tree.root());
    }

    public PosOrderTreeIterator(Tree<T> tree, Position<T> root) {
        this.tree = tree;
        Deque<Position<T>> aux = new LinkedList();
        queue = new LinkedList<>();

        if (root == null) {
            aux.add(root);
        } else {
            aux.add(tree.root());
        }

        while (!aux.isEmpty()) {
            Position<T> current = aux.pop();
            for (Position<T> child : tree.children(current)) {
                aux.push(child);
            }
            queue.push(current);
        }
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    /**
     * This method visits the nodes of a tree by following a pos-order
     */
    @Override
    public Position<T> next() {
        return queue.pop();
    }
}
