/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablashash;

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
public class AlumnoTest {
    
    public AlumnoTest() {
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
     * Test of hashCode method, of class Alumno.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Alumno alumno1 = new Alumno("Pepe Perez Garcia", 1234567);
        Alumno alumno2 = new Alumno("Pepe Perez Garcia", 1234568);
        System.out.print(alumno1);
        System.out.println(" "+alumno1.hashCode());
        System.out.print(alumno2);
        System.out.println(" "+alumno2.hashCode());
        assertNotEquals(alumno1, alumno2);
    }
}
