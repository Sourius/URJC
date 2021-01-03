package material.tree.binarysearchtree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import material.Position;

/**
 *
 * @author mayte
 */
public class AVLTree<E> implements BinarySearchTree<E> {

    //Esta clase es necesaria para guardar el valor de la altura AVL en los nodos BTNodes
    private class AVLInfo<T> implements Comparable<AVLInfo<T>>, Position<T> {

        private T element;
        private Position<AVLInfo<T>> treePosition;
        private int height;

        private AVLInfo(T value) {
            element = value;
            treePosition = null;
            height = 1;
        }

        public void setTreePosition(Position<AVLInfo<T>> pos) {
            treePosition = pos;
        }

        public Position<AVLInfo<T>> getTreePosition() {
            return treePosition;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        @Override
        public T getElement() {
            return element;
        }

        @Override
        public int compareTo(AVLInfo<T> o) {
            Comparable c1 = (Comparable) element;
            Comparable c2 = (Comparable) o.getElement();
            return c1.compareTo(c2);
        }

        @Override
        public String toString() {
            return "" + element;
        }

        @Override
        public boolean equals(Object obj) {
            return this.compareTo((AVLInfo<T>) obj) == 0;
        }

    }

    private class AVLTreeIterator<E> implements Iterator<Position<E>> {

        private Iterator<Position<AVLInfo<E>>> it;

        public AVLTreeIterator(Iterator<Position<AVLInfo<E>>> it) {
            this.it = it;
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<E> next() {
            Position<AVLInfo<E>> p = it.next();
            return p.getElement();
        }

    }

    private LinkedBinarySearchTree<AVLInfo<E>> tree;
    private Reestructurator reestructurator;

    public AVLTree() {
        tree = new LinkedBinarySearchTree<>();
        reestructurator = new Reestructurator();
    }

    @Override
    public Position<E> find(E value) {
        Position<AVLInfo<E>> aux = tree.find(new AVLInfo(value));
        if (aux == null) {
            return null;
        }
        return aux.getElement();
    }

    @Override
    public Iterable<? extends Position<E>> findAll(E value) {
        List<AVLInfo<E>> elements = new LinkedList<>();
        Iterator<Position<AVLInfo<E>>> it = tree.findAll(new AVLInfo(value)).iterator();
        while (it.hasNext()) {
            Position<AVLInfo<E>> pos = it.next();
            elements.add(pos.getElement());
        }
        return elements;
    }

    @Override
    public Position<E> insert(E value) {
        Position<AVLInfo<E>> newElement = tree.insert(new AVLInfo(value));
        newElement.getElement().setTreePosition(newElement);
        rebalance(newElement);
        return newElement.getElement();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void remove(Position<E> pos) {
        checkPosition(pos);
        AVLInfo<E> node = (AVLInfo<E>) pos;
        boolean isRoot = tree.tree.isRoot(node.getTreePosition());
        tree.remove(node.getTreePosition());
        //reestructurar si es necesario
        if (!isRoot) {
            rebalance(tree.tree.parent(node.getTreePosition()));
        }
    }

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public Iterable<? extends Position<E>> rangeIterator(E m, E n) {
        List<AVLInfo<E>> elements = new LinkedList<>();
        for (Object obj : tree.rangeIterator(new AVLInfo(m), new AVLInfo(n))) {
            Position<AVLInfo<E>> pos = (Position<AVLInfo<E>>) obj;
            elements.add(pos.getElement());
        }
        return elements;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new AVLTreeIterator(tree.iterator());
    }

    private void checkPosition(Position<E> pos) {
        if (pos == null || !(pos instanceof AVLInfo)) {
            throw new RuntimeException("Invalid position");
        }
    }

    //Obtener altura de hijo izquierdo, derecho, me quedo con la más grande y sumo uno
    private void calculateHeight(Position<AVLInfo<E>> myNode) {
        int aizq = tree.tree.hasLeft(myNode) ? tree.tree.left(myNode).getElement().getHeight() : 0;
        int ader = tree.tree.hasRight(myNode) ? tree.tree.right(myNode).getElement().getHeight() : 0;
        int altura = aizq < ader ? ader + 1 : aizq + 1;
        myNode.getElement().setHeight(altura);
    }

    private int isBalanced(Position<AVLInfo<E>> myNode) {
        int aizq = tree.tree.hasLeft(myNode) ? tree.tree.left(myNode).getElement().getHeight() : 0;
        int ader = tree.tree.hasRight(myNode) ? tree.tree.right(myNode).getElement().getHeight() : 0;
        int equilibrio = aizq - ader;
        return equilibrio;
    }

    private Position<AVLInfo<E>> getLargestChild(Position<AVLInfo<E>> node) {
        int aizq = tree.tree.hasLeft(node) ? tree.tree.left(node).getElement().getHeight() : 0;
        int ader = tree.tree.hasRight(node) ? tree.tree.right(node).getElement().getHeight() : 0;
        if (aizq > ader) {
            return tree.tree.left(node);
        } else {
            return tree.tree.right(node);
        }
    }

    private void rebalance(Position<AVLInfo<E>> myNode) {
        if (tree.tree.isLeaf(myNode)) {
            myNode.getElement().setHeight(1);
        } else {
            this.calculateHeight(myNode);
        }
        boolean seguir = !tree.tree.isRoot(myNode);
        while (seguir) {
            if (isBalanced(myNode) > 1) {
                Position<AVLInfo<E>> nieto = this.getLargestChild(tree.tree.right(myNode));
                this.reestructurator.restructure(nieto, tree.tree);
            } else if (isBalanced(myNode) < -1) {
                Position<AVLInfo<E>> nieto = getLargestChild(tree.tree.left(myNode));
                this.reestructurator.restructure(nieto, tree.tree);
            }
            seguir = !tree.tree.isRoot(myNode);
            if (seguir) {
                myNode = tree.tree.parent(myNode);
            }
        }
    }
}
