
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



/**
 *
 * @author mayte
 */
public class Frecuencias {
    private Map<Integer, Frequency> map = new HashMap<>();
    /**
     *
     * @param l
     * @param k
     * @return
     */
    public List<Integer> kMaximunFrecuencies(List<Integer> l, int k){
        map = new HashMap<>();
        // add values to the map
        for(int i = 0; i < l.size(); i++){
            Frequency freq = map.get(l.get(i));
            if(freq == null){
                freq = new Frequency(l.get(i), 0);   
                map.put(freq.getValue(), freq);
            }
            freq.setFrequency(freq.getFrequency()+1);
        }
        
        // order frequencies
        List<Frequency> freqList = new ArrayList<>(map.values());
        Collections.sort(freqList, new FreqComparator());
        
        System.out.println(Arrays.toString(freqList.toArray()));
        
        // return the most repeated k elements
        List<Integer> mostRepeated = new ArrayList<>();
        for(int i = 0; i < k && i < freqList.size(); i++){
            mostRepeated.add(freqList.get(i).getValue());
        }
        return mostRepeated;
    }
    
    private class Frequency{
        private int value;
        private int frequency;
        
        private Frequency(int value, int freq){
            this.value = value;
            this.frequency = freq;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }
        
        @Override
        public boolean equals(Object o){
            Frequency f = (Frequency) o;
            return value == f.value && frequency == f.frequency;
        }
        
        public String toString() {
            return ""+value;
        }
    }
    
    private class FreqComparator implements Comparator<Frequency>{

        @Override
        public int compare(Frequency t, Frequency t1) {
            int cmp = t1.getFrequency() - t.getFrequency();
            if(cmp == 0){
                cmp = t1.getValue() - t.getValue();
            }
            return cmp;
        }
    }
    
}
