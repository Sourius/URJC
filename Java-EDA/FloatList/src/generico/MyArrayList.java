/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generico;

/**
 *
 * @author Sourius
 * @param <T>
 */
public class MyArrayList<T> implements MyList<T> {
    private int size;
    private final int max;
    private final T[] elements;
    
    public MyArrayList(int max){
        size = 0;
        this.max = max;
        elements = (T[])new Object[max];
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
        if(size == max){
            throw new RuntimeException("List is full");
        }
        elements[size] = value;
        size++;
    }

    @Override
    public void add(int index, T value) {
        if(index < 1 || index > size){
            throw new RuntimeException("Index is not valid");
        }
        
        if(size == max){
            throw new RuntimeException("List is full");
        }
        
        int newIndex = size-index-1;
        for(int j = size-1; j > newIndex; j--){
            elements[j+1] = elements[j];   
        }
        
        elements[newIndex+1] = value;
        size++;
    }

    @Override
    public T remove() {
        if(isempty()) throw new RuntimeException("List is empty");
        int head = size-1;
        T delElement = elements[head];
        elements[head] = null;
        size--;
        return delElement;
    }

    @Override
    public T remove(int index) {
        if(isempty()) throw new RuntimeException("List is empty");
        if(index < 1 || index > size){
            throw new RuntimeException("Index is not valid");
        }
        
        T delElement = elements[index-1];
        for(int j = index-1; j < size; j++){
            elements[j] = elements[j+1]; 
        }
        
        size--;
        return delElement;
    }

    @Override
    public T get() {
        int head = size - 1;
        return elements[head];
    }

    @Override
    public T get(int index) {
        if(index < 1 || index > size){
            throw new RuntimeException("Index is not valid");
        }
        int newIndex = size - index;
        return elements[newIndex];
    }

    @Override
    public int search(T value) {
        int index = - 1;
        for(int i = size; i > 0; i--){
            if(elements[i-1].equals(value)){
                return size-i+1;
            }
        }
        return index;
    }

    @Override
    public boolean contains(T value) {
        int index = search(value);
        return index != -1;
    }
}
