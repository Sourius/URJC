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
public class ComplexNumberTest {
    
    public ComplexNumberTest() {
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
     * Test of realPart method, of class ComplexNumber.
     */
    @Test
    public void testRealPart() {
        System.out.println("realPart");
        ComplexNumber instance = new ComplexNumber(2.3,5);
        double expResult = 2.3;
        double result = instance.realPart();
        assertEquals(expResult, result, 0.01);
    }

    /**
     * Test of imaginaryPart method, of class ComplexNumber.
     */
    @Test
    public void testImaginaryPart() {
        System.out.println("imaginaryPart");
        ComplexNumber instance = new ComplexNumber(4.2, 9.1);
        double expResult = 9.1;
        double result = instance.imaginaryPart();
        assertEquals(expResult, result, 0.01);
    }

    /**
     * Test of add method, of class ComplexNumber.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        ComplexNumber c = new ComplexNumber(2,7);
        ComplexNumber instance = new ComplexNumber(3,-4);
        ComplexNumber expResult = new ComplexNumber(5,3);
        ComplexNumber result = instance.add(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of subtract method, of class ComplexNumber.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        ComplexNumber c = new ComplexNumber(4,7);;
        ComplexNumber instance = new ComplexNumber(9,5);;
        ComplexNumber expResult = new ComplexNumber(5,-2);;
        ComplexNumber result = instance.subtract(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of multiply method, of class ComplexNumber.
     */
    @Test
    public void testMultiply() {
        System.out.println("multiply");
        ComplexNumber c = new ComplexNumber(3,2);
        ComplexNumber instance = new ComplexNumber(5,6);
        ComplexNumber expResult = new ComplexNumber(3,28);
        ComplexNumber result = instance.multiply(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of division method, of class ComplexNumber.
     */
    @Test
    public void testDivision() {
        System.out.println("division");
        ComplexNumber c = new ComplexNumber(1,-3);
        ComplexNumber instance = new ComplexNumber(13,1);
        ComplexNumber expResult = new ComplexNumber(1,4);
        ComplexNumber result = instance.division(c);
        assertEquals(expResult, result);
    }

    /**
     * Test of conjugate method, of class ComplexNumber.
     */
    @Test
    public void testConjugate() {
        System.out.println("conjugate");
        ComplexNumber instance = new ComplexNumber(8,-2);
        ComplexNumber expResult = new ComplexNumber(8,2);
        ComplexNumber result = instance.conjugate();
        assertEquals(expResult, result);
    }

    /**
     * Test of module method, of class ComplexNumber.
     */
    @Test
    public void testModule() {
        System.out.println("module");
        ComplexNumber instance = new ComplexNumber(4,-3);
        double expResult = 5.0;
        double result = instance.module();
        assertEquals(expResult, result, 0.01);
    }
    
}
