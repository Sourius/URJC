/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class FrecuenciasTest {
    
    public FrecuenciasTest() {
    }

    /**
     * Test of kMaximunFrecuencies method, of class Frecuencias.
     */
    @Test
    public void testKMaximunFrecuencies() {
        System.out.println("kMaximunFrecuencies");
        List<Integer> l = new ArrayList<>(Arrays.asList(7,3,5,12,4,7,2,1,3,9));
        int k = 2;
        Frecuencias instance = new Frecuencias();
        List<Integer> expResult = Arrays.asList(7,3);
        List<Integer> result = instance.kMaximunFrecuencies(l, k);
        assertEquals(expResult.toString(), result.toString());
        
        l = new ArrayList<>(Arrays.asList(12,1,5,9,8,7,1,2,4));
        k = 4;
        expResult = Arrays.asList(1,12,9,8);
        result = instance.kMaximunFrecuencies(l, k);
        assertEquals(expResult.toString(), result.toString());
    }
    
}
