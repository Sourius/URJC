/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree.binarysearchtree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import material.Position;
import material.tree.binarytree.InorderBinaryTreeIterator;
import material.tree.binarytree.LinkedBinaryTree;

/**
 *
 * @author mayte
 * @param <E>
 */
public class LinkedBinarySearchTree<E> implements BinarySearchTree<E> {
    protected LinkedBinaryTree<E> tree;
    private DefaultComparator  comparator;
    private int size;
    
    public LinkedBinarySearchTree(){
        tree = new LinkedBinaryTree<>();
        comparator = new DefaultComparator();
        size = 0;
    }
    
    public LinkedBinarySearchTree(E value){
        this();
        checkComparable(value);
        tree.addRoot(value);
        size++;
    }
    
    @Override
    public Position<E> find(E value) {
        checkComparable(value);
        Position<E> element = null;
        Position<E> current = tree.root();
        
        while(current != null){
            int cmp = comparator.compare(value, current.getElement());
            if(cmp == 0){
                element = current;
                current = null;
            }
            else if(cmp < 0 && tree.hasLeft(current)){
                current = tree.left(current);
            }
            else if(cmp > 0 && tree.hasRight(current)){
                current = tree.right(current);
            }
            else {
                current = null;
            }
        }
        return current;
    }

    @Override
    public Iterable<? extends Position<E>> findAll(E value) {
        if(tree.isEmpty()){
            throw new RuntimeException("Tree is empty");
        }
        
        List<Position<E>> elements = new LinkedList<>();
        Position<E> current = tree.root();
        
        while(current != null){
            int cmp = comparator.compare(value, current.getElement());
            if(cmp == 0){
                elements.add(current);
            }
            if(cmp < 0 && tree.hasLeft(current)){
                current = tree.left(current);
            }
            else if(cmp > 0 && tree.hasRight(current)){
                current = tree.right(current);
            }
            else{
                current = null;
            }
        }
        return elements;
    }

    @Override
    public Position<E> insert(E value) {
        checkComparable(value);
        Position<E> parent = null;
        Position<E> newElement = null;
        
        if (tree.isEmpty()){
            newElement = tree.addRoot(value);
        }
        else{
            Position<E> current = tree.root();
            while(parent == null){
                int cmp = comparator.compare(value, current.getElement());
                if(cmp < 0 && tree.hasLeft(current)){
                    current = tree.left(current);
                }
                else if(cmp >= 0 && tree.hasRight(current)){
                    current = tree.right(current);
                }
                else{
                    parent = current;
                }
            }
            
            int cmp = comparator.compare(value, parent.getElement());
            if(cmp < 0){
                newElement = tree.insertLeft(parent, value);
            }
            else{
                newElement = tree.insertRight(parent, value);
            }
        }
        size++;
        return newElement;
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void remove(Position<E> pos) {
        checkPosition(pos);
        if(tree.isLeaf(pos)){
            tree.remove(pos);
        }
        else{
            tree.swap(pos, successor(pos));
            tree.remove(pos);
        }
        size--;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterable<? extends Position<E>> rangeIterator(E m, E n) {
        checkComparable(m);
        checkComparable(n);
        
        if(tree.isEmpty()){
            throw new RuntimeException("Tree is empty");
        }
        
        List<Position<E>> elements = new LinkedList<>();
        InorderBinaryTreeIterator it = new InorderBinaryTreeIterator(tree);
        Position<E> current = null;
        boolean done = false;
        
        int cmp;
        while(it.hasNext() && !done){
            current = it.next();
            cmp = comparator.compare(m, current.getElement());
            
            if(cmp >= 0){
                cmp = comparator.compare(n, current.getElement());
                if(cmp <= 0){
                    elements.add(current);
                }else{
                    // recorrido inorden, si el valor actual es mayor que n --> no hay mas valores posibles
                    done = true;
                }
            }
        }
        
        return elements;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return tree.iterator();
    }

    private void checkPosition(Position<E> pos) {
        if(pos == null) {
            throw new RuntimeException("Position is not valid");
        }
        checkComparable(pos.getElement());
    }
    
    private void checkComparable(E value){
        if(!(value instanceof Comparable)) {
            throw new RuntimeException("Element is no comparable");
        }
    }

    public Position<E> minimum(Position<E> pos){
        checkPosition(pos);
        Position<E> current = pos;
        while(current != null){
            if(tree.hasLeft(current)){
                current = tree.left(current);
            }
            else{
                return current;
            }
        }
        return null;
    }
    
    public Position<E> successor(Position<E> pos){
        checkPosition(pos);
        
        if(tree.hasRight(pos)){
            return minimum(pos);
        } 
        
        Position<E> parent = null;
        Position<E> current = pos;
        
        while(!tree.isRoot(current)){
            parent = tree.parent(pos);
            int cmp = comparator.compare(parent.getElement(), current.getElement());
            
            if(cmp < 0){// parent < current
                current = parent;
            }
            else { // parent >= 0
                return parent;
            }
        }
        return null;
    }
}
