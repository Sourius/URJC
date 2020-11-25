/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generico;

/**
 *
 * @param <T>
 */
public class MyLinkedList<T> implements MyList<T> {
    
    private class MyNode<T>{
        private T element;
        private MyNode<T> nextElement;

        public MyNode(T element, MyNode<T> nextElement){
            this.element = element;
            this.nextElement = nextElement;
        }
        
        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public MyNode<T> getNextElement() {
            return nextElement;
        }

        public void setNextElement(MyNode<T> nextElement) {
            this.nextElement = nextElement;
        }
    }
    
    private int size;
    private MyNode<T> head;
    private MyNode<T> tail;
    
    public MyLinkedList(){
        head = null;
        tail = null;
        size = 0;
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isempty() {
        return size == 0;
    }

    @Override
    public void add(T value) {
        MyNode<T> newElement = new MyNode<>(value, head);
        head = newElement;
        if(isempty()) tail = newElement;
        size++;
    }

    @Override
    public void add(int index, T value) {
        if(index > size || index < 1){
            throw new RuntimeException("The value of index is not valid.");
        }
        
        MyNode<T> newNode = null;
        MyNode<T> prev = null, next = null;
        prev = head;
        int currIndex = 1;
        
        while(prev != null && currIndex <= size){
            next = prev.getNextElement();
            if(currIndex == index){
                newNode = new MyNode<>(value,next);
                prev.setNextElement(newNode);
                size++;
                if(size == index) tail = newNode;
                break;
            }
            currIndex++;
            prev = prev.getNextElement();
        }
    }

    @Override
    public T remove() {
        if(isempty()) throw new RuntimeException("La lista esta vacia");
        MyNode<T> delElement = head;
        head = head.getNextElement();
        delElement.setNextElement(null);
        size--;
        return delElement.getElement();
    }

    @Override
    public T remove(int index) {
        if(index >= size || index < 1){
            throw new RuntimeException("Index value is not valid");
        }
        MyNode<T> prev = null, next = null;
        MyNode<T> delNode = null;
        
        prev = head;
        for(int i=1; i <= size; i++){
            next = prev.getNextElement();
            if(i == index-1){
                delNode = next;
                prev.setNextElement(next.getNextElement());
                delNode.setNextElement(null);
                size--;
                break;
            }
            prev = next;
        }
        
        return delNode.getElement();
    }

    @Override
    public T get() {
        return head.getElement();
    }

    @Override
    public T get(int index) {
        if(index < 1 || index >= size){
            throw new RuntimeException("Index is not valid");
        }
        MyNode<T> aux = head;
        for(int i = 1; i <= size ; i++){
            if(i == index){
                break;
            }
            aux = head.getNextElement();
        }
        return aux.getElement();
    }

    @Override
    public int search(T value) {
        MyNode<T> aux = head;
        for(int i = 1; i <= size; i++){
            if(aux.getElement().equals(value)){
                return i;
            }
            aux = aux.getNextElement();
        }
        return -1;
    }

    @Override
    public boolean contains(T value) {
        MyNode<T> aux = head;
        while(aux != null){
            if(aux.getElement().equals(value)) return true;
            aux = aux.getNextElement();
        }
        return false;
    }
}
