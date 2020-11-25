package Tree.BinaryTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import material.Position;

/**
 *
 * @author mayte
 * @param <E>
 */
public class LinkedBinaryTree<E> implements BinaryTree<E>{
    private class BTNode<T> implements Position<T>{
        private T element;
        private BTNode<T> leftChild;
        private BTNode<T> rightChild;
        private BTNode<T> parent;

        public BTNode(T value){
            this(value, null, null, null);
        }
        
        public BTNode(T value, BTNode<T> left, BTNode<T> right, BTNode<T> par){
            element = value;
            leftChild = left;
            rightChild = right;
            parent = par;
        }
        
        @Override
        public T getElement() {
            return element;
        }

        public void setElement(T value){
            element = value;
        }
        
        public BTNode<T> getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(BTNode<T> leftChild) {
            this.leftChild = leftChild;
        }

        public BTNode<T> getRightChild() {
            return rightChild;
        }

        public void setRightChild(BTNode<T> rightChild) {
            this.rightChild = rightChild;
        }

        public BTNode<T> getParent() {
            return parent;
        }

        public void setParent(BTNode<T> parent) {
            this.parent = parent;
        }
        
        private boolean hasLeftChild(){
            return leftChild != null;
        }
        
        private boolean hasRightChild(){
            return rightChild != null;
        }
        
        private boolean isLeftChild(BTNode<T> child){
            return leftChild == child;
        }
        
        private boolean isRightChild(BTNode<T> child){
            return rightChild == child;
        }
        
        private boolean isParent(BTNode<T> par){
            return parent == par;
        }
    }
    
    private class LinkedBinaryTreeIterator<T> implements Iterator<Position<T>>{
        private BTNode<T> current;
        private LinkedBinaryTree<T> tree;
        
        public LinkedBinaryTreeIterator(LinkedBinaryTree<T> lt){
            this(lt, null);
        }
        
        public LinkedBinaryTreeIterator(LinkedBinaryTree<T> lt, BTNode<T> node){
            if(lt == null)
                throw new RuntimeException("Invalid tree. Tree is null");
            tree = lt;
            BTNode<T> aux;
            if(node == null){
                aux = (BTNode<T>) lt.root();
            }
            else{
                aux = node;
            }
            while(aux.hasLeftChild()){
                aux = aux.getLeftChild();
            }
            
            current = aux;
        }
        
        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Position<T> next() {
            BTNode<T> aux = current;
            if(tree.hasRight(current)){
                current = aux.getRightChild();
            }
            else if(tree.isRoot(current)){
                current = null;
            }
            else{
                BTNode<T> parent = (BTNode) tree.parent(current);
                if(parent.isLeftChild(aux)) current = parent;
                else{
                    if(!tree.isRoot(parent)){
                        current = parent.getParent();
                    }
                    else{
                        current = null;
                    }
                }
            }
            return aux;
        }
    }
    
    private BTNode<E> root;    
    
    public LinkedBinaryTree(){
        root = null;
    }
    
    @Override
    public Position<E> left(Position<E> v) {
        BTNode<E> node = checkPosition(v);
        if(node.hasLeftChild()){
            return node.getLeftChild();
        }
        throw new RuntimeException("Invalid operation. There is no left child");
    }

    @Override
    public Position<E> right(Position<E> v) {
        BTNode<E> node = checkPosition(v);
        if(node.hasRightChild()){
            return node.getRightChild();
        }
        throw new RuntimeException("Invalid operation. There is no right child");
    }

    @Override
    public boolean hasLeft(Position<E> v) {
        BTNode<E> node = checkPosition(v);
        return node.hasLeftChild();
    }

    @Override
    public boolean hasRight(Position<E> v) {
        BTNode<E> node = checkPosition(v);
        return node.hasRightChild();
    }

    @Override
    public boolean isInternal(Position<E> v) {
        BTNode<E> node = checkPosition(v);
        return node.hasLeftChild() || node.hasRightChild();
    }

    @Override
    public boolean isLeaf(Position<E> p) {
        return !isInternal(p);
    }

    @Override
    public boolean isRoot(Position<E> p) {
        BTNode<E> node = checkPosition(p);
        return node == root;
    }

    @Override
    public Position<E> root() {
        if(isEmpty()) throw new RuntimeException("Invalid operation. Tree is empty.");
        return root;
    }

    @Override
    public E replace(Position<E> p, E e) {
        BTNode<E> node = checkPosition(p);
        E oldValue = node.getElement();
        node.setElement(e);
        return oldValue;
    }

    @Override
    public Position<E> sibling(Position<E> p) {
        BTNode<E> node = checkPosition(p);
        if(!isRoot(node)){
            BTNode<E> par = node.getParent();
            if(par.isLeftChild(node)){
                return par.getRightChild();
            }
            else{
                return par.getLeftChild();
            }
        }
        else throw new RuntimeException("Invalid operation. There is no sibling");
    }

    @Override
    public Position<E> addRoot(E e) {
        if(!isEmpty()) throw new RuntimeException("Invalid Operation. Tree has a root");
        root = new BTNode(e);
        return root;
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) {
        BTNode<E> node = checkPosition(p);
        if(this.hasLeft(p)) throw new RuntimeException("Invalid Operation. There is a left child");
        BTNode<E> newChild = new BTNode(e);
        newChild.setParent(node);
        node.setLeftChild(newChild);
        return newChild;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) {
        BTNode<E> node = checkPosition(p);
        if(this.hasRight(p)) throw new RuntimeException("Invalid Operation. There is a left child");
        BTNode<E> newChild = new BTNode(e);
        newChild.setParent(node);
        node.setRightChild(newChild);
        return newChild;
    }

    @Override
    public E remove(Position<E> p) {
        BTNode<E> node = checkPosition(p);       
        if(node.hasLeftChild() && node.hasRightChild()){
            throw new RuntimeException("Invalid Operation. Cannot remove node, there are 2 childs");
        }
        E oldValue = node.getElement();
        if(isRoot(node)){
            root = null;
        }
        else{
            BTNode<E> parent = node.getParent();
            if(parent.isLeftChild(node)){
                parent.setLeftChild(null);
            }
            else if (parent.isRightChild(node)){
                parent.setRightChild(null);
            }
            node.setParent(null);
        }
        return oldValue;
    }

    @Override
    public void swap(Position<E> p1, Position<E> p2) {
        BTNode<E> node1 = checkPosition(p1);
        BTNode<E> node2 = checkPosition(p2);
        E aux = node1.getElement();
        node1.setElement(node2.getElement());
        node2.setElement(aux);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        BTNode<E> node = checkPosition(v);
        if(isRoot(node)) throw new RuntimeException("Invalid Operation. Root has no parent");
        return node.getParent();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        BTNode<E> node = checkPosition(v);
        List<Position<E>> children = new LinkedList<>();
        if(node.hasLeftChild()) children.add(node.getLeftChild());
        if(node.hasRightChild()) children.add(node.getRightChild());
        return children;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new LinkedBinaryTreeIterator(this);
    }

    @Override
    public void attachLeft(Position<E> h, BinaryTree<E> t1) {
        if(t1 == null || !(t1 instanceof LinkedBinaryTree)){
            throw new RuntimeException("Tree is not valid");
        }
        BTNode<E> node = checkPosition(h);
        BTNode<E> newChild = checkPosition(t1.root());
        if(t1 == null) throw new RuntimeException("Invalid Operation. Tree is not valid.");
        if(hasLeft(node)) throw new RuntimeException("Invalid Operation. There is a left child");
        newChild.setParent(node);
        node.setLeftChild(newChild);
    }
    
    @Override
    public void attachRight(Position<E> h, BinaryTree<E> t1) {
        if(t1 == null || !(t1 instanceof LinkedBinaryTree)){
            throw new RuntimeException("Tree is not valid");
        }
        BTNode<E> node = checkPosition(h);
        BTNode<E> newChild = checkPosition(t1.root());
        if(t1 == null) throw new RuntimeException("Invalid Operation. Tree is not valid.");
        if(hasRight(node)) throw new RuntimeException("Invalid Operation. There is a left child");
        newChild.setParent(node);
        node.setRightChild(newChild);
    }

    @Override
    public LinkedBinaryTree<E> subTree(Position<E> h) {
        BTNode<E> node = checkPosition(h);
        if(isRoot(node)) return this;
        
        LinkedBinaryTree<E> subTree = new LinkedBinaryTree<>();
        BTNode<E> parent = (BTNode<E>)parent(node);
        if(parent.isLeftChild(node)) parent.setLeftChild(null);
        else parent.setRightChild(null);
        
        node.setParent(null);
        subTree.root = node;
        return subTree;
    }

   private BTNode<E> checkPosition(Position<E> pos){
       if(pos == null || !(pos instanceof BTNode)){
           throw new RuntimeException("Position is not valid.");
       }
       return (BTNode)pos;
   }
}
