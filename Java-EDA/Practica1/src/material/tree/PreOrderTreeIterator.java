package material.tree;

import java.util.Collections;
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
public class PreOrderTreeIterator<T> implements Iterator<Position<T>> {

    private Deque<Position<T>> queue;
    private Tree<T> tree;

    public PreOrderTreeIterator(Tree<T> tree) {
        this(tree, tree.root());
    }

    public PreOrderTreeIterator(Tree<T> tree, Position<T> root) {
        this.tree = tree;
        queue = new LinkedList<>();
        if (root == null) {
            queue.push(tree.root());
        } else {
            queue.push(root);
        }
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    /**
     * This method visits the nodes of a tree by following a pre-order
     */
    @Override
    public Position<T> next() {
        Position<T> current = queue.remove();
        // para mantener el orden correcto de los hijos
        // invertir el orden
        List<Position<T>> aux = new LinkedList<>();
        for (Position<T> child : tree.children(current)) {
            aux.add(child);
        }
        Collections.reverse(aux);
        // añadir
        for (Position<T> child : aux) {
            queue.push(child);
        }
        return current;
    }

}
