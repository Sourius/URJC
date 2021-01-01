
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import material.ordereddictionary.AVLOrderedDict;
import material.ordereddictionary.Entry;
import material.ordereddictionary.OrderedDictionary;

/**
 *
 * @author mayte
 */
public class ReferenciasCruzadas {
    
    private OrderedDictionary<String, Integer> dictionary = new AVLOrderedDict<>();
    
    /**
     * Builds an ordered dictionary from a file
     *
     * @param fichero
     * @throws java.io.IOException
     */
    public ReferenciasCruzadas(FileReader fichero) throws IOException {
        Scanner sc = new Scanner(fichero).useDelimiter("[\\s\\.,;:\\?¿\\(\\)\\[\\]\"\\n]+");
        int numWords = 1;
        while(sc.hasNext()){
            //System.out.println(sc.next());
            this.dictionary.insert(sc.next(), numWords);
            numWords++;
        }
    }

    /**
     * Returns the list of indexes that the word occupies in the text with which
     * the dictionary has been built. If the word does not belong to the file
     * returns null
     *
     * @param word
     * @return
     */
    public List<Integer> apariciones(String word) {
        List<Integer> positions = new LinkedList<>();
        for (Entry<String, Integer> e: dictionary.findAll(word)){
            positions.add(e.getValue());
        }
        if(!positions.isEmpty()){
            Collections.sort(positions);
            return positions;
        }
        return null;
    }

}
