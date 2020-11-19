package material.tree;

import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import material.Position;

/**
 *
 * @author mayte
 * @param <T>
 */
public class LeafIterator<T> implements Iterator<Position<T>> {

    private Deque<Position<T>> queue;
    private Tree<T> tree;

    public LeafIterator(Tree<T> tree, Position<T> root) {
        this.tree = tree;
        Deque<Position<T>> aux = new LinkedList<>();
        queue = new LinkedList<>();

        if (root == null) {
            aux.add(tree.root());
        } else {
            aux.add(root);
        }

        while (!aux.isEmpty()) {
            Position<T> current = aux.pop();
            List<Position<T>> childrenList = new LinkedList<>();
            // para mantener el correcto orden de los hijos
            // invertir el orden
            for (Position<T> child : tree.children(current)) {
                childrenList.add(child);
            }
            Collections.reverse(childrenList);
            // añadir
            for (Position<T> child : childrenList) {
                aux.push(child);
            }

            if (tree.isLeaf(current)) {
                queue.add(current);
            }
        }
    }

    public LeafIterator(Tree<T> tree) {
        this(tree, tree.root());
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    /**
     * This method only visits the leaf nodes
     */
    @Override
    public Position<T> next() {
        return queue.poll();
    }
}
