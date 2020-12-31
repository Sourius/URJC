package material.maps;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class HashTableMapQPTest {
    
    public HashTableMapQPTest() {
    }

    /**
     * Test of offset method, of class HashTableMapQP.
     */
    @Test
    public void testOffset() {
        System.out.println("offset");
        Object key = null;
        int i = 0;
        HashTableMapQP instance = new HashTableMapQP();
        int expResult = 1;
        int result = instance.offset(key, i);
        assertNotEquals(expResult, result);
        assertNotEquals(expResult, result+i);
    }
    
    @Test
    public void calculateCollisions(){
        HashTableMapQP instance = new HashTableMapQP(15000);
        int collision = 0;
        for(int i = 0; i < 10000; i++){
            instance.put(i, i);
            if(instance.findEntry(i).index != instance.hashValue(i)){
                collision++;
            }
        }
        System.out.println("Collisions: "+collision);
        
        collision = 0;
        for(int i = 0; i < 1000000; i++){
            instance.put(i, i);
            if(instance.findEntry(i).index != instance.hashValue(i)){
                collision++;
            }
        }
        System.out.println("Collisions: "+collision);
    }
}
