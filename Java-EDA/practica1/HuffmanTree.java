package practica1;

import java.util.Comparator;
import java.util.ArrayList;
import material.Position;

/**
 * @author jvelez
 */
public class HuffmanTree <T extends Comparable>{
    
    class HuffmanNode <T extends Comparable> implements Position<T>{
        private T element;
        private String code;
        private Position<T> parent;

        public HuffmanNode(T t){
            this.element = t;
            this.parent = null;
        }
        
        public HuffmanNode(T t, Position<T> p){
            this(t);
            this.parent = p;
            this.setCode();
        }
        
        @Override
        public T getElement() {
            return element;
        }
        
        public String getCode(){
            return this.code;
        }
        
        public void setCode(){
            this.code = ((FrequencyNode)this.parent).getCode().concat("0");
        }
        
        public void setCode(String code){
            this.code = code;
        }
        
        public void addParent(Position p){
            if(this.parent == null && p instanceof FrequencyNode)
                this.parent = p;
            else throw new RuntimeException("Invalid position!");
        }
    }
    
    class FrequencyNode <T extends Comparable> implements Position<T>{
        private Integer value;
        private Position<T> child;
        private String code;
        
        public FrequencyNode (int v, HuffmanNode hf){
            value = v;
            this.child = hf;
        }
        
        @Override
        public T getElement() {
            return (T)value;
        }

        public void add(){
            this.value++;
        }
        
        public Position<T> getChild() {
            return child;
        }
        
        public void setChild(Position p){
            if(this.child == null && p instanceof HuffmanNode)
                this.child = p;
        }
        
        public void setCode(String s){
            this.code = s;
        }
        
        public String getCode(){
            return this.code;
        }
    }
    
    private ArrayList huffmanTree;
    /**
     * Creates the Huffman tree.
     * @param text 
     */
    
    public HuffmanTree(String text) {
        char c;
        
        HuffmanNode hf = new HuffmanNode(" ");
        FrequencyNode fn = new FrequencyNode(0,hf);
        hf.addParent(fn); 
        hf.setCode("00000000");
        this.huffmanTree.add(fn);
        
        Position <T> p;
        
        for(int i = 0; i < text.length(); i++){
            c = text.charAt(i);
            if(text.indexOf(c)!=i){ // caracter repetido
                if((p = search(c)) == null){
                    throw new RuntimeException("Invalid element!");
                }
                hf = (HuffmanNode)p;
                fn = (FrequencyNode) hf.parent;
                fn.add();
            }
            else{
                //caracter no repetido
                hf = new HuffmanNode(c);
                fn = new FrequencyNode(1,hf);
                hf.addParent(fn);
                this.huffmanTree.add(fn);
            }
        }
        
        // ordenar por maxima frecuencia
        huffmanTree.sort(new HuffmanComp());
        setCode();
    }
    
    /**
     * Encodes a text into a binary array using a Huffman tree.
     * @param text
     * @return 
     */
    byte [] encoding(String text) {
        new HuffmanTree(text);
        byte code[] = new byte[text.length()/8];
        
        // convertir codigo en un array de bytes de 2 dimensiones
        
        
        return null;
    }
    
    /**
     * Decodes a binary array into a text using a Huffman tree.
     * @param code
     * @return 
     */
    String decoding(byte [] code) {
        String res = null, aux;
        
        // decodificar los bytes
        return null;
    }
    
    private Position search(char c){
        Position p = null;
        FrequencyNode f;
        HuffmanNode h;
        
        for(Object o: this.huffmanTree){
            f = (FrequencyNode) o;
            p = f.getChild();
            if((char)p.getElement() == c) return p;
        }
        return null;
    }
    
    private void setCode(){
        String s = "";
        int size = this.huffmanTree.size();
        
        for(int i = 1; i < size ; i++){
            String code="";
            FrequencyNode fn = (FrequencyNode) this.huffmanTree.get(i);
            s = s.concat("1");
            
            /*for(int j = s.length()-1; j < 8; j++){
               code = code.concat("0");
            }*/
            
            code = code.concat(s);
            fn.setCode(code); 
            
            HuffmanNode hf = (HuffmanNode)fn.getChild();
            if(i != size-1){
                hf.setCode(code.substring(1).concat("0"));
            }
            else{
                hf.setCode(code.substring(1).concat("1"));
            }
        }
    }
    
    // Comparator
    class HuffmanComp<T extends Comparable> implements Comparator{
        @Override
        public int compare(Object o1, Object o2) {
            FrequencyNode f1, f2;
            int i1,i2;
            
            f1 = (FrequencyNode) o1;
            f2 = (FrequencyNode) o2;
            
            //ignorar espacios en blanco
            if(f1.getElement().equals("")) return -1;
            else if(f2.getElement().equals("")) return +1;
            
            i1 = (int)f1.getElement();
            i2 = (int)f2.getElement();
            return i2-i1;
        }
    }
}
