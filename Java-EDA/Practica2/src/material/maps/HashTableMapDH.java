package material.maps;

import java.util.Random;

/**
 * @param <K> The hey
 * @param <V> The stored value
 */
public class HashTableMapDH<K, V> extends AbstractHashTableMap<K, V> {

    private int q = 7;
    private int oldcap = -1;
    
    public HashTableMapDH(int size) {
        super(size);
    }

    public HashTableMapDH() {
        super();
    }

    public HashTableMapDH(int p, int cap) {
        super(p, cap);
    }

    @Override
    protected int offset(K key, int i) {
        if(oldcap == -1) oldcap = capacity;
/*        
        if(q < 2 || oldcap != capacity){

            // recalcular q si la capacidad cambia
            Random r = new Random();
            do{
                q = r.nextInt(capacity);
            } while(q < 2 && !isPrime(q));
        }
*/
        // devolver offset
        return q - (i % q); 
    }
    
    private boolean isPrime(int num) {
        if(num < 2) return false; // el 2 es el primer numero primo
        int aux = 2;
        while (aux <= num/2) {
            if(num % aux == 0) {
                return false;
            }
            aux++;
        }
        return true;
    }
}
