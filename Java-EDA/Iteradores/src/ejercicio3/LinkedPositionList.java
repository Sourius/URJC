/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio3;

import java.util.Iterator;
import material.Position;

/**
 *
 * @author Sourius
 * @param <E>
 */
public class LinkedPositionList <E> implements MyListBetter<E>{
    private class DLinkedNode<D> implements Position<D>{
        private D element;
        private DLinkedNode prev;
        private DLinkedNode next;

        public DLinkedNode(){}
        
        public DLinkedNode(DLinkedNode prev, DLinkedNode next, D element) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
        
        @Override
        public D getElement() {
            return element;
        }

        public void setElement(D element){
            this.element = element;
        }
        
        public DLinkedNode getPrev() {
            return prev;
        }

        public void setPrev(DLinkedNode prev) {
            this.prev = prev;
        }

        public DLinkedNode getNext() {
            return next;
        }

        public void setNext(DLinkedNode next) {
            this.next = next;
        }
    }
    
    private class DLinkedIterator<I> implements Iterator<Position<I>>{
        Position<I> current;
        
        public DLinkedIterator(Position<I> node){
            current = node;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Position<I> next() {
            Position<I> next = current;
            DLinkedNode<I> node = (DLinkedNode) current;
            current = node.getNext();
            return next;
        }
    }
    
    private DLinkedNode<E> head;
    private DLinkedNode<E> tail;
    private int size;
    
    public LinkedPositionList(){
        size = 0;
    }
    
    private DLinkedNode<E> checkPosition(Position<E> pos){
        DLinkedNode<E> node;
        if(pos == null || !(pos instanceof DLinkedNode)){
            throw new RuntimeException("Position is not valid");
        }
        node = (DLinkedNode) pos;
        return node;
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isempty() {
        return size() == 0;
    }

    private DLinkedNode<E> createNewNode(E value){
        DLinkedNode<E> newNode = new DLinkedNode();
        newNode.setElement(value);
        return newNode;
    }
    
     private DLinkedNode<E> createNewNode(DLinkedNode prev, DLinkedNode next, E value){
        DLinkedNode<E> newNode = new DLinkedNode();
        newNode.setElement(value);
        newNode.setNext(next);
        newNode.setPrev(prev);
        return newNode;
    }
    
    @Override
    public Position<E> add(E value) {
        DLinkedNode<E> newNode = createNewNode(value);
        if(isempty()) {
            tail = newNode;
            newNode.setNext(null);
        }
        else{
            head.setPrev(newNode);
            newNode.setNext(head);
        } 
        newNode.setPrev(null);
        head = newNode;
        size++;
        return newNode;
    }

    @Override
    public Position<E> addAfter(Position<E> pos, E value) {
        DLinkedNode<E> node = checkPosition(pos);
        DLinkedNode<E> newNode;
        
        if(isempty()) throw new RuntimeException("List is empty");
        newNode = createNewNode(node, null, value);
        if(node.equals(tail)){
            tail = newNode;
        }
        else{
            newNode.setNext(node.getNext());
            node.getNext().setPrev(newNode);
        }
        node.setNext(newNode);
        size++;
        return newNode;
    }

    @Override
    public Position<E> addBefore(Position<E> pos, E value) {
        DLinkedNode<E> node;
        DLinkedNode<E> newNode;
        
        if(isempty()) throw new RuntimeException("List is empty");
        node = checkPosition(pos);
        newNode = createNewNode(null, node, value);
        if(node.equals(head)){
            head = newNode;
        }
        else{
            newNode.setPrev(node.getPrev());
            node.getPrev().setNext(newNode);
        }
        node.setPrev(newNode);
        size++;
        return newNode;
    }

    @Override
    public E remove(Position<E> pos) {
        DLinkedNode<E> node = null;
        DLinkedNode<E> prev = null;
        DLinkedNode<E> next = null;
        boolean isHead = false;
        boolean isTail = false;
        
        if(isempty()) throw new RuntimeException("List is empty.");
        node = checkPosition(pos);
        isHead = node.equals(head);
        isTail = node.equals(tail);
        
        prev = node.getPrev();
        next = node.getNext();
        
        if(!isHead) {
            prev.setNext(next);
        }
        else{
            if(next != null) next.setPrev(null);
            head = next;
        }
        
        if(!isTail){
            next.setPrev(prev);
        }
        else{
            if(prev != null) prev.setNext(null);
            tail = prev;
        }
        
        size--;
        return node.getElement();
    }

    @Override
    public Position<E> get() {
        if(isempty()) throw new RuntimeException("List is empty");
        return head;
    }

    @Override
    public Position<E> set(Position<E> pos, E value) {
        if(isempty()) throw new RuntimeException("List is empty");
        DLinkedNode<E> node = checkPosition(pos);
        node.setElement(value);
        return node;
    }

    @Override
    public Position<E> search(E value) {
        if(isempty()) throw new RuntimeException("List is empty");
        Iterator<Position<E>> it = iterator();
        Position<E> pos;
        Position<E> aux = null;
        
        while(it.hasNext()){
            pos = it.next();
            if(pos.getElement().equals(value)){
                aux = pos;
            }
        }
        return aux;
    }

    @Override
    public boolean contains(E value) {
        return search(value) != null;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new DLinkedIterator(head);
    }
}
