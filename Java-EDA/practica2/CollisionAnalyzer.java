package practica2;

import material.maps.AbstractHashTableMap;
import material.maps.HashTableMapDH;
import material.maps.HashTableMapLP;
import material.maps.HashTableMapQP;

/**
 *
 * @author jvelez
 */
public class CollisionAnalyzer {
    
    /**
     * @param N size of bucket
     * @return number of collisions
     */
    public int analyzeLinearHash(int N) {
        AbstractHashTableMap bucket = new HashTableMapLP(N);
        String s;
        
        for(int i = 0; i < 100000; i++){
            s = this.generateCombination();
            bucket.put(i, s);
        }
        return bucket.getCol();
    }

    /**
     * @param N size of bucket
     * @return number of collisions
     */
    public int analyzeQuadraticHash(int N) {
        HashTableMapQP bucket = new HashTableMapQP (N);
        String s;
        
        for(int i = 0; i < 1000000; i++){
            s = this.generateCombination();
            bucket.put(i, s);
        }
       return bucket.getCol();
    }

    /**
     * @param N size of bucket
     * @return number of collisions
     */
    public int analyzeDoubleHash(int N) {
        HashTableMapDH bucket = new HashTableMapDH (N);
        String s;
        
        for(int i = 0; i < 20000; i++){ 
        // da error por alguna razon --> no se puede dividir por 0 
        //segun lo que tengo entendido no deberia salir ese error
        //hay veces que la capacidad vale 0 a la hora de calcular el offset
            s = this.generateCombination();
            bucket.put(i, s);
        }
        return bucket.getCol();  
    }
    
    /*
    * Returns a random combination of not repeated letters a-z
    */
    private String generateCombination(){
        String s="";
        char aux;
        boolean repeated;
        int n[] = new int[4];
        int num;
        
        int i = 0;
        while(i<4){
            repeated = false;
            // generar numero aleatorio
            num = (int)Math.round(Math.random()*26);
            
            for(int j = 0; j < i && !repeated; j++){
                if(n[j] == num){
                    repeated = true;
                }
            }
            
            if(repeated == false){
                n[i] = num;
                aux = (char)('a'+(num));  
                i++;
                s += aux;   
            } 
        }
        return s;
    }
}