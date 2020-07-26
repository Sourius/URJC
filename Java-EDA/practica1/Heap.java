package practica1;

import java.util.ArrayList;
import java.util.Comparator;
import material.Position;

/**
 *
 * @author jvelez
 * @param <T>
 */
class ArrayTree <T extends Comparable>{
    class ArrayNode < T extends Comparable>{
        T element;
        Position <T> par;
        Position <T> leftChild;
        Position <T> rightChild;

        public Position<T> getPar() {
            return par;
        }

        public void setPar(Position<T> par) {
            this.par = par;
        }

        public Position<T> getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(Position<T> leftChild) {
            this.leftChild = leftChild;
        }

        public Position<T> getRightChild() {
            return rightChild;
        }

        public void setRightChild(Position<T> rightChild) {
            this.rightChild = rightChild;
        }
    }
    
    private Position<T> root;
    
    Position getRoot(){
        return root;
    }
}

public class Heap <T extends Comparable> {
    /**
     * Adds an element to the heap.
     * @param element 
     */
    private ArrayList <T> heaptree; // max heap
    
    public Heap(){
        this.heaptree = new ArrayList<>();
    }
    
    public void add(T element) {
        this.heaptree.add(element);
        this.heaptree.sort(new Comp());

    }
            
    
    /**
     * Returns the top element of the heap.
     * @return 
     */
    public T top() {
        return this.heaptree.get(0);
    }

    /**
     * Removes the top element of the heap and returns it.
     * @return 
     */
    public T remove() {
        
        T e=this.heaptree.remove(0);
        this.heaptree.sort(new Comp());
        return e;
    }

    /**
    * 
    * @return True if the heap is empty.
    */
    boolean isEmpty() {
        return this.heaptree.isEmpty();
    }
    
    class Comp <T extends Comparable> implements Comparator{
        @Override
        public int compare(Object t, Object t1) {
            return ((T)t1).compareTo(t);
        }
    }
}