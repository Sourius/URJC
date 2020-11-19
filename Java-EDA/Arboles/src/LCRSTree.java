import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import material.Position;

/**
 *
 * @author mayte
 * @param <E>
 */
public class LCRSTree<E> implements NAryTree<E> {
    
    private class LCRSNode<T> implements Position<T>{
        private T element;
        private LCRSNode<T> parent;
        private LCRSNode<T> child;
        private LCRSNode<T> sibling;
        
        public LCRSNode(T value){
            this(value,null,null,null);
        }
        
        public LCRSNode(T value, LCRSNode<T> par){
            this(value ,par ,null, null);
        }
        
        public LCRSNode(T value, LCRSNode<T> par, LCRSNode<T> left, LCRSNode<T> right){
            element = value;
            parent = par;
            child = left;
            sibling = right;
        }
        
        @Override
        public T getElement() {
            return element;
        }
        
        public void setElement(T value){
            element = value;
        }

        public LCRSNode<T> getParent() {
            return parent;
        }

        public void setParent(LCRSNode<T> parent) {
            this.parent = parent;
        }

        public LCRSNode<T> getChild() {
            return child;
        }

        public void setChild(LCRSNode<T> left) {
            child = left;
        }

        public boolean isChild(LCRSNode<T> node){
            return child.equals(node);
        }
        
        public LCRSNode<T> getSibling() {
            return sibling;
        }

        public void setSibling(LCRSNode<T> right) {
            sibling = right;
        }        
        
        public boolean isSibling(LCRSNode<T> node){
            return sibling.equals(node);
        }
        
        public boolean hasChild(){
            return child != null;
        }
        
        public boolean hasSibling(){
            return sibling != null;
        }
    }
    
    private class LCRSTreeIterator<T> implements Iterator<Position<T>>{
        private Queue<Position<T>> it = new LinkedList<>();
        private LCRSTree<T> tree;
        
        public LCRSTreeIterator(LCRSTree<T> tree){
            this(tree, tree.root());
        }
        
        public LCRSTreeIterator(LCRSTree tree, Position<T> node){
            if(tree.isEmpty()){
                throw new RuntimeException("Tree is empty");
            }
            this.tree = tree;
            it.add(tree.checkPosition(node));
        }
        
        @Override
        public boolean hasNext() {
            return !it.isEmpty();
        }

        @Override
        public Position<T> next() {
            Position<T> element = it.poll();
            Iterable <? extends Position<T>> children = tree.children(element);
            for(Position<T> p: children){
                it.add(p);
            }
            return element;
        }
    }
    
    private class LCRSChildrenIterator<T> implements Iterator<Position<T>>{
        private LCRSNode<T> current;
        
        public LCRSChildrenIterator(LCRSNode<T> node){
            current = node.getChild();
        }
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Position<T> next() {
            Position<T> aux = current;
            current = current.getSibling();
            return aux;
        }
    }
    
    private LCRSNode<E> root;
    
    public LCRSTree(){
        root = null;
    }
    
    @Override
    public Position<E> addRoot(E e) {
        if(!isEmpty()) throw new RuntimeException("Tree is not empty.");
        root = new LCRSNode(e);
        return root;
    }

    @Override
    public Position<E> add(E element, Position<E> p) {
        LCRSNode<E> node = checkPosition(p);
        LCRSNode<E> sibling = null;
        LCRSNode<E> newNode = new LCRSNode(element);
        
        if(!node.hasChild()){
            node.setChild(newNode);
        }
        else{
            sibling = node.getChild();
            while(sibling.hasSibling()){
                sibling = sibling.getSibling();
            }
            sibling.setSibling(newNode);
        }
        newNode.setParent(node);
        return newNode;
    }

    @Override
    public Position<E> add(E element, Position<E> p, int n) {
        LCRSNode<E> node = checkPosition(p);
        int current = 0;
        LCRSNode<E> sibling = null;
        LCRSNode<E> newNode = new LCRSNode(element);
        
        if(!node.hasChild()){
            if(n == 0) node.setChild(newNode);
            else throw new RuntimeException("Index is not valid");
        }
        else{
            sibling = node.getChild();
            current = 1;
            
            while(current < n){
                if(sibling.hasSibling()){
                    sibling = sibling.getSibling();
                }
                else{
                    throw new RuntimeException("Index is not valid");
                }
                current++;
            }
            if(sibling.hasSibling()) newNode.setSibling(sibling.getSibling());
            sibling.setSibling(newNode);
        }
        newNode.setParent(node);
        return newNode;
    }

    @Override
    public void swapElements(Position<E> p1, Position<E> p2) {
        LCRSNode<E> node1 = checkPosition(p1);
        LCRSNode<E> node2 = checkPosition(p2);
        E aux = node1.getElement();
        node1.setElement(node2.getElement());
        node2.setElement(aux);
    }

    @Override
    public E replace(Position<E> p, E e) {
        LCRSNode<E> node = checkPosition(p);
        E oldValue = node.getElement();
        node.setElement(e);
        return oldValue;
    }

    @Override
    public void remove(Position<E> p) {
        LCRSNode<E> node = checkPosition(p);
        LCRSNode<E> parent = null;
        LCRSNode<E> sibling = null;
        
        if(isRoot(node)){
            root = null;
        }
        else{
            // quitar descendientes
            for(Position<E> aux: children(node)){
                remove(aux);
            }
            // actualizar hermanos
            if(node.getParent() != null) {
                parent = node.getParent();

                if(parent.isChild(node)){
                    if(node.hasSibling()){
                        parent.setChild(node.getSibling());
                    }
                }
                else{
                    sibling = parent.getChild();
                    //actualizar hermanos
                    while(sibling.hasSibling() && !sibling.isSibling(node)){
                        sibling = sibling.getSibling();
                    }
                    if(node.hasSibling()){
                        sibling.setSibling(node.getSibling());
                    }
                    else{
                        sibling.setSibling(null);
                    }
                }
                // quitar hermano y padre
                node.setSibling(null);
                node.setParent(null);
            }
        }
    }

    @Override
    public NAryTree<E> subTree(Position<E> v) {
        LCRSNode<E> node = checkPosition(v);
        remove(node);
        LCRSTree<E> newTree = new LCRSTree<>();
        newTree.root = node;
        return newTree;
    }

    @Override
    public void attach(Position<E> p, NAryTree<E> t) {
        if(t == null || !(t instanceof LCRSTree))
            throw new RuntimeException("Tree is not valid");
        LCRSNode<E> newChild = checkPosition(t.root());
        
        if(p == null){
           this.root = newChild;
        }
        else{
            LCRSNode<E> parent = checkPosition(p);
            // add parent
            newChild.setParent(parent);
            // add node 
            if(parent.hasChild()){
                LCRSNode<E> sibling = parent.getChild();
                while(sibling.hasSibling()) sibling = sibling.getSibling();
                sibling.setSibling(newChild);
            }
            else{
                parent.setChild(newChild);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public Position<E> root() {
        return root;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        LCRSNode<E> node = checkPosition(v);
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        LCRSNode<E> node = checkPosition(v);
        return new Iterable (){
            @Override
            public Iterator iterator() { 
                return new LCRSChildrenIterator(node);
            }
        };
    }

    @Override
    public boolean isInternal(Position<E> v) {
        return !isLeaf(v);
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        LCRSNode<E> node = checkPosition(v);
        return node.getChild() == null && node.getSibling() == null;
    }

    @Override
    public boolean isRoot(Position<E> v) {
        LCRSNode<E> node = checkPosition(v);
        return root == node;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new LCRSTreeIterator(this);
    }

    public int size() {
        if(isEmpty()) return 0;
        return countDescendents(root);
    }
    
    private LCRSNode<E> checkPosition(Position<E> p){
        if(p != null && p instanceof LCRSNode){
            return (LCRSNode<E>)p;
        }
        throw new RuntimeException("Position is not valid");
    }
    
    private int countDescendents(LCRSNode<E> node){
        int numDescendents = 0;
        LCRSTreeIterator<E> it = new LCRSTreeIterator(this,node);
        while(it.hasNext()){
            it.next();
            numDescendents++;
        }
        return numDescendents;
    }
}