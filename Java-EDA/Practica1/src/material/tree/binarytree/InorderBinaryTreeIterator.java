package material.tree.binarytree;

import java.util.Iterator;
import material.Position;

/**
 *
 * @author jvelez
 * @param <T>
 */
public class InorderBinaryTreeIterator<T> implements Iterator<Position<T>> {

    private BinaryTree<T> bTree;
    private Position<T> current;

    public InorderBinaryTreeIterator(BinaryTree<T> tree) {
        this(tree, tree.root());
    }

    public InorderBinaryTreeIterator(BinaryTree<T> tree, Position<T> node) {
        bTree = tree;
        Position<T> aux = null;

        if (node != null) {
            aux = node;
        } else {
            aux = bTree.root();
        }

        while (bTree.hasLeft(aux)) {
            aux = bTree.left(aux);
        }
        current = aux;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    /**
     * This method visits the nodes of a binary tree first left-child, then the
     * node and at last right-child
     */
    @Override
    public Position<T> next() {
        Position<T> aux = current;
        Position<T> next = null;
        if (bTree.hasRight(aux)) {
            next = bTree.right(aux);
            while (bTree.hasLeft(next)) {
                next = bTree.left(next);
            }
            current = next;
        } else if (bTree.isRoot(aux)) {
            current = null;
        } else {
            Position<T> parent = bTree.parent(aux);
            if (bTree.left(parent) == aux) {
                current = parent;
            } else {
                next = parent;
                current = null;
                while (!bTree.isRoot(next)) {
                    Position<T> ancestor = bTree.parent(next);
                    if (bTree.left(ancestor) == next) {
                        current = ancestor;
                        break;
                    }
                    next = ancestor;
                }
            }
        }
        return aux;
    }
}
