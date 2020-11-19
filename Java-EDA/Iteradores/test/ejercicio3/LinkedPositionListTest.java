/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicio3;

import java.util.Iterator;
import material.Position;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Sourius
 */
public class LinkedPositionListTest {
    
    public LinkedPositionListTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of size method, of class LinkedPositionList.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        LinkedPositionList instance = new LinkedPositionList();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        
        Position first = instance.add(1);
        Position second = instance.add(2);
        expResult = 2;
        result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of isempty method, of class LinkedPositionList.
     */
    @Test
    public void testIsempty() {
        System.out.println("isempty");
        LinkedPositionList instance = new LinkedPositionList();
        boolean expResult = true;
        boolean result = instance.isempty();
        
        assertEquals(expResult, result);
        Position first = instance.add(1);
        Position second = instance.add(2);
        Position third = instance.add(3);
        
        expResult = false;
        result = instance.isempty();
        assertEquals(expResult, result);
        
        instance.remove(first);
        instance.remove(second);
        instance.remove(third);
        expResult = true;
        result = instance.isempty();
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class LinkedPositionList.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        LinkedPositionList instance = new LinkedPositionList();
        Object value = 1;
        int expResult = 1;
        Position result = instance.add(value);
        assertEquals(expResult, result.getElement());
        
        value = 2;
        expResult = 2;
        result = instance.add(value);
        assertEquals(expResult, result.getElement());
        
        expResult = 1;
        instance.remove(result);
        result = instance.get();
        assertEquals(expResult, result.getElement());
    }

    /**
     * Test of addAfter method, of class LinkedPositionList.
     */
    @Test
    public void testAddAfter() {
        System.out.println("addAfter");
        LinkedPositionList instance = new LinkedPositionList();
        
        Position first = instance.add(1);
        Position second = instance.addAfter(first,2);
        assertEquals(first, instance.get());
        assertEquals(2, instance.size());
    }

    /**
     * Test of addBefore method, of class LinkedPositionList.
     */
    @Test
    public void testAddBefore() {
        System.out.println("addBefore");
        LinkedPositionList instance = new LinkedPositionList();
        
        Position first = instance.add(1);
        Position second = instance.addBefore(first,2);
        Position third = instance.addBefore(first, 3);
        
        assertEquals(second, instance.get());
        assertEquals(3, instance.size());
        instance.remove(second);
        assertEquals(third, instance.get());
    }

    /**
     * Test of remove method, of class LinkedPositionList.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        LinkedPositionList instance = new LinkedPositionList();
        
        Position first = instance.add(1);
        assertEquals(1, instance.remove(first));
        
        try{
            instance.remove(first);
            // it doesn't work
            fail();
        } catch(Exception e){
           // it works
        }
    }

    /**
     * Test of get method, of class LinkedPositionList.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        LinkedPositionList instance = new LinkedPositionList();
        try{
            instance.get();
            //it doesn't work
            fail();
        }
        catch(Exception e){
            // it works
        }
        
        Position first = instance.add(1);
        assertEquals(first, instance.get());
    }

    /**
     * Test of set method, of class LinkedPositionList.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        LinkedPositionList instance = new LinkedPositionList();
        Position first = instance.add(1);
        Position result = instance.set(first, 2);
        assertEquals(first,result);
        assertEquals(first.getElement(),2);
    }

    /**
     * Test of search method, of class LinkedPositionList.
     */
    @Test
    public void testSearch() {
        System.out.println("search");
        LinkedPositionList instance = new LinkedPositionList();
        
        try{
            instance.search(1);
            //doesn't work
            fail();
        }catch(Exception e){
            //works
        }
        
        Position first = instance.add(1);
        Position second = instance.add(2);
        Position third = instance.add(3);
        Position fourth = instance.addBefore(second,4);
        Position fifth = instance.addAfter(second,5);
        Position sixth = instance.addBefore(third,6);
        
        assertNull(instance.search(9));
        assertEquals(fifth, instance.search(5));
    }

    /**
     * Test of contains method, of class LinkedPositionList.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        Object value = null;
        LinkedPositionList instance = new LinkedPositionList();
        
        try{
            instance.contains(1);
            //doesn't work
            fail();
        } catch(Exception e){
            //works
        }
        
        Position first = instance.add(1);
        Position second = instance.add(2);
        Position third = instance.add(3);
        Position fourth = instance.addBefore(second,4);
        Position fifth = instance.addAfter(second,5);
        Position sixth = instance.addBefore(third,6);
        
        assertTrue(instance.contains(1));
        assertFalse(instance.contains(10));
        
    }

    /**
     * Test of iterator method, of class LinkedPositionList.
     */
    @Test
    public void testIterator() {
        System.out.println("iterator");
        LinkedPositionList instance = new LinkedPositionList();
        Position first = instance.add(1);
        Position second = instance.add(2);
        Position third = instance.add(3);
        Position fourth = instance.addBefore(second,4);
        Position fifth = instance.addAfter(second,5);
        Position sixth = instance.addBefore(third,6);
        
        Iterator<Position> it = instance.iterator();
        String result = "";
        String expResult = "634251";
        while(it.hasNext()){
            result += it.next().getElement();
        }
        assertEquals(expResult, result);
    }
}
