/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generico;

/**
 * @param <E>
 */
public interface MyList<E> {
    /**
     * Returns the number of elements in this list
     * 
     * @return 
     */
    public int size();
    
    /**
     * Returns true if this list contains no elements
     * 
     * @return 
     */
    public boolean isempty();
   
    /**
     * Appends the specified element to the begining of this list 
     * 
     * @param value 
     */
    public void add(E value);
    
    /**
     * Inserts the specified element at the specified position in this list (optional operation)
     * 
     * @param index
     * @param value 
     */
    public void add(int index, E value);
    
    /**
     * Returns and returns the element at the beginning of this list
     * 
     * @return 
     */
    public E remove();
    
    /**
     * Removes and returns the element at the specified position in this list 
     * 
     * @param index
     * @return 
     */
    public E remove(int index);
    
    /**
     * Returns the element at the beginning of this list
     * 
     * @return 
     */
    public E get();
    
    /**
     * Returns the element at the specified position in this list
     * 
     * @param index
     * @return 
     */
    public E get(int index);
    
    /**
     * Returns the element's position if this list contains the specified element
     * otherwise returns 0
     * 
     * @param value
     * @return 
     */
    public int search(E value);
    
    /**
     * Returns true if this list contains the specified element.
     * 
     * @param value
     * @return 
     */
    public boolean contains (E value);
}
