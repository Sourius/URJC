/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class FloatArrayListTest {
    
    public FloatArrayListTest() {
    }
    
    public FloatArrayList inicializa(){
        FloatArrayList instance = new FloatArrayList(5);
        instance.add(new Float(3));
        instance.add(new Float(8));
        instance.add(new Float(12));
        return instance; //[12, 8, 3]
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
     * Test of size method, of class FloatArrayList.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        FloatArrayList instance = inicializa();
        int expResult = 3;
        int result = instance.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of isempty method, of class FloatArrayList.
     */
    @Test
    public void testIsempty1() {
        System.out.println("isempty");
        FloatArrayList instance = inicializa();
        boolean expResult = false;
        boolean result = instance.isempty();
        assertEquals(expResult, result);
    }

    /**
     * Test of isempty method, of class FloatArrayList.
     */
    @Test
    public void testIsempty2() {
        System.out.println("isempty");
        FloatArrayList instance = new FloatArrayList(4);
        boolean expResult = true;
        boolean result = instance.isempty();
        assertEquals(expResult, result);
    }

    /**
     * Test of add method, of class FloatArrayList.
     */
    @Test
    public void testAdd_Float() {
        System.out.println("add");
        FloatArrayList instance = new FloatArrayList(3);
        assertTrue(instance.isempty());
        instance.add(new Float(2));
        assertFalse(instance.isempty());
        assertEquals(instance.size(),1);
    }

    /**
     * Test of add method, of class FloatArrayList.
     */
    @Test
    public void testAdd_int_Remove() {
        System.out.println("add");
        int index = 2;
        Float value = new Float(5);
        FloatArrayList instance = inicializa(); // [12, 8, 3]
        instance.add(index, value); // [12, 8, 5, 3]
        assertEquals(instance.size(),4);
        Float remove = instance.remove();
        assertEquals(12.0,remove,0.01);
        remove = instance.remove();
        assertEquals(8.0,remove,0.01);
        remove = instance.remove();
        assertEquals(5.0,remove,0.01);
        remove = instance.remove();
        assertEquals(3.0,remove,0.01);
        assertTrue(instance.isempty());
    }

    /**
     * Test of remove method, of class FloatArrayList.
     */
    @Test
    public void testRemove_int() {
        System.out.println("remove");
        int index = 2;
        FloatArrayList instance = inicializa(); // [12, 8, 3]
        Float result = instance.remove(index);// 8
        assertEquals(8.0, result,0.01);
    }

    /**
     * Test of get method, of class FloatArrayList.
     */
    @Test
    public void testGet_0args() {
        System.out.println("get");
        FloatArrayList instance = inicializa(); // [12, 8, 3]
        Float result = instance.get();//12
        assertEquals(12.0, result,0.01);
    }

    /**
     * Test of get method, of class FloatArrayList.
     */
    @Test
    public void testGet_int() {
        System.out.println("get");
        int index = 2;
        FloatArrayList instance = inicializa(); // [12, 8, 3]
        Float result = instance.get(index);// 8
        assertEquals(8.0, result,0.01);
        
        index = 1;
        result = instance.get(index);// 12
        assertEquals(12.0, result,0.01);
        
        index = 3;
        result = instance.get(index);// 3
        assertEquals(3.0, result,0.01);
    }

    /**
     * Test of search method, of class FloatArrayList.
     */
    @Test
    public void testSearch() {
        System.out.println("search");
        Float value = null;
        FloatArrayList instance = inicializa();
        int expResult = -1;
        int result = instance.search(value);
        assertEquals(expResult, result);
        
        value = new Float(3);
        expResult = 3;
        result = instance.search(value);
        assertEquals(expResult, result);
        
        value = new Float(8);
        expResult = 2;
        result = instance.search(value);
        assertEquals(expResult, result);
        
        value = new Float(12);
        expResult = 1;
        result = instance.search(value);
        assertEquals(expResult, result);
    }

    /**
     * Test of contains method, of class FloatArrayList.
     */
    @Test
    public void testContains() {
        System.out.println("contains");
        Float value = new Float(5);
        FloatArrayList instance = inicializa();// [12, 8, 3]
        boolean result = instance.contains(value);
        assertFalse(result);
        value = new Float(12);
        result = instance.contains(value);
        assertTrue(result);
    }
    
}
