/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mayte
 */
public class FloatLinkedList implements FloatList{
    
    private class FloatNode {
        private float element;
        private FloatNode nextElement;

        public FloatNode(float element, FloatNode nextElement){
            this.element = element;
            this.nextElement = nextElement;
        }
        
        public float getElement() {
            return element;
        }

        public void setElement(float element) {
            this.element = element;
        }

        public FloatNode getNextElement() {
            return nextElement;
        }

        public void setNextElement(FloatNode nextElement) {
            this.nextElement = nextElement;
        }
    }
    
    private int size;
    private FloatNode head;
    private FloatNode tail;
    
    public FloatLinkedList(){
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
    public void add(Float value) {
        FloatNode newElement = new FloatNode(value, head);
        head = newElement;
        size++;
    }

    @Override
    public void add(int index, Float value) {
        if(index > size || index < 1){
            throw new RuntimeException("The value of index is not valid.");
        }
        
        FloatNode newNode = null;
        FloatNode prev = null, next = null;
        prev = head;
        int currIndex = 1;
        
        while(prev != null && currIndex <= size){
            next = prev.getNextElement();
            if(currIndex == index){
                newNode = new FloatNode(value,next);
                prev.setNextElement(newNode);
                size++;
                break;
            }
            currIndex++;
            prev = prev.getNextElement();
        }
    }

    @Override
    public Float remove() {
        if(isempty()) throw new RuntimeException("La lista esta vacia");
        FloatNode delElement = head;
        head = head.getNextElement();
        delElement.setNextElement(null);
        size--;
        return delElement.getElement();
    }

    @Override
    public Float remove(int index) {
        if(index >= size || index < 1){
            throw new RuntimeException("Index value is not valid");
        }
        FloatNode prev = null, next = null;
        FloatNode delNode = null;
        
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
    public Float get() {
        return head.getElement();
    }

    @Override
    public Float get(int index) {
        if(index < 1 || index >= size){
            throw new RuntimeException("Index is not valid");
        }
        FloatNode aux = head;
        for(int i = 1; i <= size ; i++){
            if(i == index){
                break;
            }
            aux = head.getNextElement();
        }
        return aux.getElement();
    }

    @Override
    public int search(Float value) {
        FloatNode aux = head;
        for(int i = 1; i <= size; i++){
            if(aux.getElement() == value){
                return i;
            }
            aux = aux.getNextElement();
        }
        return -1;
    }

    @Override
    public boolean contains(Float value) {
        FloatNode aux = head;
        while(aux != null){
            if(aux.getElement() == value) return true;
            aux = aux.getNextElement();
        }
        return false;
    }
    
}
