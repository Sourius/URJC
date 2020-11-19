package material.tree.narytree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import material.Position;

/**
 *
 * @author mayte
 */
public class LinkedTree<E> implements NAryTree<E> {

    private class TreeNode<T> implements Position<T> {

        private T element;
        private TreeNode<T> parent;
        private List<TreeNode<T>> children;

        public TreeNode(T value) {
            this(value, null);
        }

        public TreeNode(T value, TreeNode<T> par) {
            element = value;
            parent = par;
            children = new LinkedList<>();
        }

        @Override
        public T getElement() {
            return element;
        }

        public void setElement(T value) {
            element = value;
        }

        public TreeNode<T> getParent() {
            return parent;
        }

        public void setParent(TreeNode<T> parent) {
            this.parent = parent;
        }

        public List<TreeNode<T>> getChildren() {
            return children;
        }

        public void setChildren(LinkedList<TreeNode<T>> children) {
            this.children = children;
        }
    }

    private class LinkedTreeIterator<T> implements Iterator<Position<T>> {

        private Queue<Position<T>> it = new LinkedList<>();
        private LinkedTree<T> tree;

        public LinkedTreeIterator(LinkedTree<T> tree) {
            this(tree, tree.root());
        }

        public LinkedTreeIterator(LinkedTree<T> tree, Position<T> pos) {
            if (tree.isEmpty()) {
                throw new RuntimeException("Tree is empty");
            }
            if (pos == null) {
                throw new RuntimeException("Position is not valid");
            }
            this.tree = tree;
            it.add(tree.checkPosition(pos));
        }

        @Override
        public boolean hasNext() {
            return !it.isEmpty();
        }

        @Override
        public Position<T> next() {
            Position<T> element = it.poll();
            Iterable<? extends Position<T>> children = tree.children(element);
            for (Position<T> p : children) {
                it.add(p);
            }
            return element;
        }
    }

    private TreeNode<E> root;

    public LinkedTree() {
        root = null;
    }

    @Override
    public Position<E> addRoot(E e) {
        if (!isEmpty()) {
            throw new RuntimeException("There is already a root");
        }
        root = new TreeNode(e);
        return root;
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        TreeNode<E> node = checkPosition(p);
        TreeNode<E> newNode = new TreeNode(element);
        newNode.setParent(node);
        node.getChildren().add(newNode);
        return newNode;
    }

    @Override
    public Position<E> add(E element, Position<E> p, int n) {
        TreeNode<E> node = checkPosition(p);
        List<TreeNode<E>> childrenList = node.getChildren();
        TreeNode<E> newNode = new TreeNode(element);
        if (childrenList.size() > n) {
            childrenList.add(n, newNode);
            return newNode;
        }
        throw new RuntimeException("Index is not valid");
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        TreeNode<E> node1 = checkPosition(p1);
        TreeNode<E> node2 = checkPosition(p2);
        E aux = node1.getElement();
        node1.setElement(node2.getElement());
        node2.setElement(aux);
    }

    @Override
    public E replace(Position<E> p, E e) {
        TreeNode<E> node = checkPosition(p);
        E oldValue = node.getElement();
        node.setElement(e);
        return oldValue;
    }

    @Override
    public void remove(Position<E> p) {
        TreeNode<E> node = checkPosition(p);
        if (isRoot(node)) {
            root = null;
        } else {
            //quitar nodo --> el recogedor de basura lo elimina
            if (node.getParent() != null) {
                node.getParent().getChildren().remove(node);
                node.setParent(null);
            }
        }
    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        int des = countDescendents(node);

        //quitar nodo y actualizar size
        remove(v);
        node.setParent(null);

        // crear un nuevo arbol y actualizar size
        LinkedTree<E> newTree = new LinkedTree<>();
        newTree.root = node;
        return newTree;
    }

    private int countDescendents(TreeNode<E> node) {
        int numDescendents = 0;
        LinkedTreeIterator<E> it = new LinkedTreeIterator(this, node);
        while (it.hasNext()) {
            it.next();
            numDescendents++;
        }
        return numDescendents;
    }

    @Override
    public void attach(Position<E> p, NAryTree<E> t) {
        TreeNode<E> newChild = checkPosition(t.root());
        TreeNode<E> parent = checkPosition(p);
        parent.getChildren().add(newChild);
        newChild.setParent(parent);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public Position<E> root() {
        if (isEmpty()) {
            throw new RuntimeException("Tree is empty");
        }
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        if (isRoot(node)) {
            throw new RuntimeException("Invalid operation");
        }
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return node.getChildren();
    }

    @Override
    public boolean isInternal(Position<E> v) {
        return !isLeaf(v);
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return node.getChildren().isEmpty();
    }

    @Override
    public boolean isRoot(Position<E> v) {
        TreeNode<E> node = checkPosition(v);
        return root.equals(node);
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new LinkedTreeIterator(this);
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return countDescendents(root);
    }

    private TreeNode<E> checkPosition(Position<E> p) {
        if (!(p instanceof TreeNode) || p == null) {
            throw new RuntimeException("Position is not valid");
        } else {
            return (TreeNode<E>) p;
        }
    }
}
