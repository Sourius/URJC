package Tree.BinaryTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import material.Position;

/**
 *
 * @author mayte
 */
public class ArrayBinaryTree<E> implements BinaryTree<E> {
    
    private class BTPos<E> implements Position<E>{
        private E element;
        private int rank;
        
        public BTPos(E value, int pos){
            element = value;
            rank = pos;
        }
        
        @Override
        public E getElement() {
            return element;
        }
        
        public void setElement(E value){
            element = value;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
    
    private class ArrayBinaryTreeIterator<T> implements Iterator<Position<T>>{
        private Queue<Position<T>> elements;
        private ArrayBinaryTree<T> tree;
        
        public ArrayBinaryTreeIterator(ArrayBinaryTree<T> tree){
            this(tree, null);
        }
        
        public ArrayBinaryTreeIterator(ArrayBinaryTree<T> tree, BTPos<T> pos){
            this.tree = tree;
            elements = new LinkedList<>();
            if(pos == null){
                elements.add(tree.root());
            }else{
                elements.add(pos);
            }
        }
        
        @Override
        public boolean hasNext() {
            return !elements.isEmpty();
        }

        @Override
        public Position<T> next() {
            BTPos<T> current = (BTPos<T>) elements.poll();
            if(tree.hasRight(current)){
                elements.add(tree.right(current));
            }
            elements.add(current);
            if(tree.hasLeft(current)){
               elements.add(tree.left(current));
            }
            return elements.remove();
        }
    }
    
    private BTPos<E>[] tree;
    private int maxSize;
    
    public ArrayBinaryTree(){
        this(20);
    }
    
    public ArrayBinaryTree(int size){
        tree = new BTPos[size];
        for(int i = 0; i < 20; i++){
            tree[i] = null;
        }
        maxSize = size;
    }

    @Override
    public Position<E> left(Position<E> p) {
        BTPos<E> parentPos = checkPosition(p);
        if(!hasLeft(p)) throw new RuntimeException("Invalid operation. There is no left child");
        int leftChildRank = calculateRankLeftChild(parentPos);
        return tree[leftChildRank];
    }

    @Override
    public Position<E> right(Position<E> p) {
        BTPos<E> parentPos = checkPosition(p);
        if(!hasRight(p)) throw new RuntimeException("Invalid operation. There is no right child");
        int rightChildRank = calculateRankRightChild(parentPos);
        return tree[rightChildRank];
    }

    @Override
    public boolean hasLeft(Position<E> p) {
        BTPos<E> parentPos = checkPosition(p);
        int leftChildRank = calculateRankLeftChild(parentPos);
        return tree[leftChildRank] != null;
    }

    @Override
    public boolean hasRight(Position<E> p) {
        BTPos<E> parentPos = checkPosition(p);
        int rightChildRank = calculateRankRightChild(parentPos);
        return tree[rightChildRank] != null;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        return hasLeft(v) || hasRight(v);
    }

    @Override
    public boolean isLeaf(Position<E> p) {
        return !isInternal(p);
    }

    @Override
    public boolean isRoot(Position<E> p) {
        BTPos<E> pos = checkPosition(p);
        return tree[0] == pos;
    }

    @Override
    public Position<E> root() {
        if(isEmpty()) throw new RuntimeException("Invalid operation. Tree is empty.");
        return tree[0];
    }

    @Override
    public E replace(Position<E> p, E e) {
        BTPos<E> pos = checkPosition(p);
        E oldValue = pos.getElement();
        pos.setElement(e);
        return oldValue;
    }

    @Override
    public Position<E> sibling(Position<E> p) {
        BTPos<E> pos = checkPosition(p);
        if(isRoot(pos)){
            throw new RuntimeException("Invalid operation. There is no sibling");
        }
        BTPos<E> par = (BTPos<E>)parent(p);
        if(isLeft(par,pos)){
            return left(par);
        }
        else{
            return right(par);
        }
    }

    @Override
    public Position<E> addRoot(E e) {
        if(!isEmpty()) throw new RuntimeException("Invalid Operation. Tree has a root");
        BTPos<E> root = new BTPos(e,0);
        tree[0] = root;
        return root;
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) {
        BTPos<E> pos = checkPosition(p);
        if(hasLeft(p)) throw new RuntimeException("Invalid Operation. There is a left child");
        int rankChild = calculateRankLeftChild(pos);
        if(rankChild >= maxSize) resizeTree(rankChild+10);
        BTPos<E> newChild = new BTPos(e,rankChild);
        tree[rankChild] = newChild;
        return newChild;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) {
        BTPos<E> pos = checkPosition(p);
        if(hasRight(p)) throw new RuntimeException("Invalid Operation. There is a left child");
        int rankChild = calculateRankRightChild(pos);
        if(rankChild >= maxSize) resizeTree(rankChild+10);
        BTPos<E> newChild = new BTPos(e,rankChild);
        tree[rankChild] = newChild;
        return newChild;
    }

    @Override
    public E remove(Position<E> p) {
        BTPos<E> pos = checkPosition(p);
        if(hasLeft(pos) && hasRight(pos)){
            throw new RuntimeException("Invalid Operation. Cannot remove node, there are 2 childs");
        }
        E oldValue = pos.getElement();
        if(isRoot(pos)){
            resetTree();
        }
        else{
            // remove children
            for(Position<E> c: children(p)){
                BTPos<E> child = (BTPos<E>)c;
                remove(child);
            }
            // remove parent
            tree[pos.getRank()] = null;
        }
        return oldValue;
    }

    @Override
    public void swap(Position<E> p1, Position<E> p2) {
        BTPos<E> pos1 = checkPosition(p1);
        BTPos<E> pos2 = checkPosition(p2);
        E aux = pos1.getElement();
        pos1.setElement(pos2.getElement());
        pos2.setElement(aux);
    }

    @Override
    public boolean isEmpty() {
        return root() == null;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        BTPos<E> pos = checkPosition(v);
        if(isRoot(v)) throw new RuntimeException("Invalid Operation. Root has no parent");
        BTPos<E> parent = null;
        //search for parent
        for(BTPos<E> aux : tree){
            int rankL = calculateRankLeftChild(aux);
            int rankR = calculateRankRightChild(aux);
            if(rankL == pos.getRank() || rankR == pos.getRank()){
                parent = aux;
                break;
            }
        }
        //return parent
        return parent;
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        BTPos<E> pos = checkPosition(v);
        List<Position<E>> children = new LinkedList<>();
        if(hasLeft(pos)) children.add(left(pos));
        if(hasRight(pos)) children.add(right(pos));
        return children;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new ArrayBinaryTreeIterator(this);
    }

    @Override
    public void attachLeft(Position<E> h, BinaryTree<E> t1) {
        if(t1 == null || !(t1 instanceof ArrayBinaryTree)){
            throw new RuntimeException("Tree is not valid");
        }
        BTPos<E> pos = checkPosition(h);
        BTPos<E> newChild = checkPosition(t1.root());
        if(t1 == null) throw new RuntimeException("Invalid Operation. Tree is not valid.");
        if(hasLeft(pos)) throw new RuntimeException("Invalid Operation. There is a left child");
        int leftChildRank = calculateRankLeftChild(pos);
        tree[leftChildRank] = newChild;
    }

    @Override
    public void attachRight(Position<E> h, BinaryTree<E> t1) {
        if(t1 == null || !(t1 instanceof ArrayBinaryTree)){
            throw new RuntimeException("Tree is not valid");
        }
        BTPos<E> pos = checkPosition(h);
        BTPos<E> newChild = checkPosition(t1.root());
        if(t1 == null) throw new RuntimeException("Invalid Operation. Tree is not valid.");
        if(hasRight(pos)) throw new RuntimeException("Invalid Operation. There is a left child.");
        int rightChildRank = calculateRankRightChild(pos);
        tree[rightChildRank] = newChild;
    }

    @Override
    public BinaryTree<E> subTree(Position<E> h) {
        BTPos<E> pos = checkPosition(h);
        if(isRoot(pos)) return this;
        //create new tree
        ArrayBinaryTree<E> subTree = new ArrayBinaryTree<>();
        // add pos as root
        subTree.tree[0] = pos;
        // refresh rank
        subTree.tree[0].setRank(0);
        // add descendents to new tree
            // perform a breadth search
        
            // add each pos
            // refresh rank of each pos
        
        
        // remove descendents from old tree
        
        // remove pos
        tree[pos.getRank()] = null;
        // return new tree
        return subTree;
    }
        
    private int calculateRankLeftChild(BTPos<E> par){
        return 2 * par.getRank() + 1;
    }
    
    private int calculateRankRightChild(BTPos<E> par){
       return 2 * par.getRank() + 2;
    }
    
    private BTPos<E> checkPosition(Position<E> p){
        if(p == null || !(p instanceof BTPos)){
            throw new RuntimeException("Position is not valid.");
        }
        return (BTPos<E>)p;
    }
    
    private boolean isLeft(Position<E> parent, Position<E> p){
        BTPos<E> parentPos = checkPosition(parent);
        BTPos<E> child = checkPosition(p);
        int rankChild = child.getRank();
        return (rankChild == calculateRankLeftChild(parentPos));
    }
    
    private boolean isRight(Position<E> parent, Position<E> p){
        BTPos<E> parentPos = checkPosition(parent);
        BTPos<E> child = checkPosition(p);
        int rankChild = child.getRank();
        return (rankChild == calculateRankRightChild(parentPos));
    }
    
    private void resetTree(){
        for(int i = 0; i < maxSize; i++){
            tree[i] = null;
        }
    }
    
    private BTPos<E>[] resizeTree(int newSize){
        BTPos<E>[] newTree = new BTPos[newSize];
        for(int i = 0; i < newSize; i++){
            if(i < maxSize) newTree[i] = tree[i];
            else newTree[i] = null;
        }
        maxSize = newSize;
        return newTree;
    }
}
