package material.maps;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author mayte
 */
public class HashTableMapDHTest {
    
    public HashTableMapDHTest() {
    }

    /**
     * Test of offset method, of class HashTableMapDH.
     */
    @Test
    public void testOffset() {
        System.out.println("offset");
        Object key = null;
        int i = 0;
        HashTableMapDH instance = new HashTableMapDH();
        int expResult = 1;
        int result = instance.offset(key, i);
        assertNotEquals(expResult, result);
        assertNotEquals(expResult, result+i);
    }
    
    @Test
    public void calculateCollisions(){
        HashTableMapDH instance = new HashTableMapDH(15000);
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
