package maps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Separate chaining table implementation of hash tables. Note that all
 * "matching" is based on the equals method.
 *
 * @author A. Duarte, J. Vélez, J. Sánchez-Oro
 * @param <K> The key
 * @param <V> The stored value
 */
public class HashTableMapSC<K, V> implements Map<K, V> {
    
    private class HashEntry<T, U> implements Entry<T, U> {
        private T key;
        private U value;
        
        public HashEntry(T k, U v) {
            key = k;
            value = v;
        }

        @Override
        public U getValue() {
            return value;
        }

        @Override
        public T getKey() {
            return key;
        }

        public U setValue(U val) {
            U oldValue = val;
            value = val;
            return oldValue;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }
        
        @Override
        public boolean equals(Object o) {
            if(o == null) return false;
            if(o.getClass() != this.getClass()) return false;
            HashEntry<T,U> entry = (HashEntry<T,U>) o;
            return key.equals(entry.getKey()) && value.equals(entry.getValue());
        }
/*
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final HashEntry<?, ?> other = (HashEntry<?, ?>) obj;
            if (!Objects.equals(this.key, other.key)) {
                return false;
            }
            if (!Objects.equals(this.value, other.value)) {
                return false;
            }
            return true;
        }
 */       
        /**
         * Entry visualization.
         */
        @Override
        public String toString() {
            return "("+getKey().toString()+", "+getValue().toString()+")";
        }
    }

    private class HashTableMapIterator<T, U> implements Iterator<Entry<T, U>> {
        private List<HashEntry<T, U>>[] map;
        private int numElems;
        private int contador = 0;
        private int pos = -1;
        private List<HashEntry<T, U>> actual;
        private Iterator<HashEntry<T, U>> it;

        //Ejercicio 2.2
        public HashTableMapIterator(List<HashEntry<T, U>>[] map, int numElems) {
            this.map = map;
            this.numElems = numElems;
            this.actual = goToNextElement();
            if (this.actual != null) {
                it = this.actual.iterator();
            }
        }

        private List<HashEntry<T, U>> goToNextElement() {
            pos++;
            while((pos < map.length) && (map[pos] == null)) {
                pos++;
            }
            if (pos < map.length) {
                return map[pos];
            } else {
                return null;
            }
        }

        @Override
        public boolean hasNext() {
            return numElems > contador;
        }

        @Override
        public Entry<T, U> next() {
            if (!it.hasNext()) {
                this.actual = goToNextElement();
                /*while((this.actual == null) || (this.actual.isEmpty())) {
                    this.actual = goToNextElement();
                }
                */
                it = this.actual.iterator();
            }
            contador++;
            return it.next();
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }
    
    private class HashTableMapKeyIterator<T, U> implements Iterator<T> {
        private HashTableMapIterator<T,U> it;
        public HashTableMapKeyIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }

        @Override
        public T next() {
            return it.next().getKey();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            // NO HAY QUE IMPLEMENTARLO
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private class HashTableMapValueIterator<T, U> implements Iterator<U> {
        private HashTableMapIterator<T,U> it;

        public HashTableMapValueIterator(HashTableMapIterator<T, U> it) {
            this.it = it;
        }
        
        @Override
        public U next() {
            return it.next().getValue();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not implemented.");
        }
    }

    private List<HashEntry<K,V>>[] bucket;
    private int capacity;
    private int prime;
    private int n;
    private int p;
    private int a;
    private int b;
    
    
    /**
     * Creates a hash table
     */
    public HashTableMapSC() {
        this(109345121, 100);
    }

    /**
     * Creates a hash table.
     *
     * @param cap initial capacity
     */
    public HashTableMapSC(int cap) {
        this(109345121, cap);
    }

    /**
     * Creates a hash table with the given prime factor and capacity.
     *
     * @param p prime number
     * @param cap initial capacity
     */
    public HashTableMapSC(int p, int cap) {
        prime = p;
        capacity = cap;
        bucket = new ArrayList[cap];
        
        Random r = new Random();
        this.p = generatePrimeNumber(r.nextInt(capacity));
        a = 13;
        b = 2020;
    }
    
    private int generatePrimeNumber(int nextInt){
        int aux = nextInt;
        while(!isPrime(aux)){
            aux++;
        }
        return aux;
    }

    private boolean isPrime(int n){
        int aux = (int)Math.sqrt(n);
        for(int i = 2; i < aux; i++) {
            if(n % i == 0) return false;
        }
        return true;
    }
    
    /**
     * Hash function applying MAD method to default hash code.
     *
     * @param key Key
     * @return
     */
    protected int hashValue(K key) {
        return ((Math.abs(a * key.hashCode()) + b) % p) % capacity;
    }

    /**
     * Returns the number of entries in the hash table.
     *
     * @return the size
     */
    @Override
    public int size() {
        return n;
    }

    /**
     * Returns whether or not the table is empty.
     *
     * @return true if the size is 0
     */
    @Override
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the value associated with a key.
     *
     * @param key
     * @return value
     */
    @Override
    public V get(K key) throws IllegalStateException {
        checkKey(key);
        HashEntry<K,V> entry = findEntry(key);
        if(entry != null) return entry.getValue();
        return null;
    }

    /**
     * Put a key-value pair in the map, replacing previous one if it exists.
     *
     * @param key
     * @param value
     * @return value
     */
    @Override
    public V put(K key, V value) throws IllegalStateException {
        checkKey(key);
        int pos = hashValue(key);
        
        V oldValue = null;
        
        HashEntry<K,V> entry = findEntry(key);
        if(entry == null){
            if(bucket[pos] == null){
                bucket[pos] = new ArrayList<>();
            }
            bucket[pos].add(new HashEntry(key,value));
            n++;
        }
        else{
            oldValue = entry.getValue();
            entry.setValue(value);
        }
        return oldValue;
    }

    private HashEntry<K,V> findEntry(K key){
        HashEntry<K,V> entry = null;
        int pos = hashValue(key);
        List<HashEntry<K,V>> elements = bucket[pos];
        if(elements == null || elements.isEmpty()) return null;
        
        Iterator it = elements.iterator();
        while(it.hasNext()){
            HashEntry<K,V> e = (HashEntry<K,V>) it.next();
            if(e.getKey().equals(key)){
                entry = e;
                break;
            }
        }
        
        return entry;
    }
    
    /**
     * Removes the key-value pair with a specified key.
     *
     * @param key
     * @return
     */
    @Override
    public V remove(K key) throws IllegalStateException {
        checkKey(key);
        int pos = hashValue(key);
        
        HashEntry<K,V> entry = findEntry(key);
        if(entry != null) {
            bucket[pos].remove(entry);
            n--;
            return entry.getValue();
        }
        return null;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableMapIterator<>(bucket, n);
    }

    /**
     * Returns an iterable object containing all of the keys.
     *
     * @return
     */
    @Override
    public Iterable<K> keys() {
        List<K> keys = new LinkedList<>();
        /*for(List<HashEntry<K,V>> elements: bucket){
            if(elements != null){
                for(HashEntry<K,V> entry: elements){
                    if(entry != null){
                        keys.add(entry.getKey());
                    }
                }
            }
        }*/
        HashTableMapKeyIterator<K,V> it = new HashTableMapKeyIterator<>((HashTableMapIterator)iterator());
        while(it.hasNext()){
            K key = it.next();
            keys.add(key);
        }
        return keys;
    }

    /**
     * Returns an iterable object containing all of the values.
     *
     * @return
     */
    @Override
    public Iterable<V> values() {
        List<V> values = new LinkedList<>();
        /*for(List<HashEntry<K,V>> elements: bucket){
            if(elements != null){
                for(HashEntry<K,V> entry: elements){
                    if(entry != null){
                        values.add(entry.getValue());
                    }
                }
            }
        }*/
        HashTableMapValueIterator<K,V> it = new HashTableMapValueIterator<>((HashTableMapIterator)iterator());
        while(it.hasNext()){
            values.add(it.next());
        }
        return values;
    }

    /**
     * Returns an iterable object containing all of the entries.
     *
     * @return
     */
    @Override
    public Iterable<Entry<K, V>> entries() {
        List<Entry<K,V>> entries = new LinkedList<>();
        /*for(List<HashEntry<K,V>> elements: bucket){
            if(elements != null){
                for(HashEntry<K,V> entry: elements){
                    if(entry != null){
                        entries.add(entry);
                    }
                }
            }
        }*/
        
        Iterator<Entry<K,V>> it = iterator();
        while(it.hasNext()){
            entries.add(it.next());
        }
        return entries;
    }

    /**
     * Determines whether a key is valid.
     *
     * @param k Key
     */
    protected void checkKey(K k) {
        if(k == null) throw new IllegalStateException("Invalid key.");
    }

    /**
     * Increase/reduce the size of the hash table and rehashes all the entries.
     * @param newCap
     */
    protected void rehash(int newCap) {
        List<HashEntry<K,V>>[] newBucket = new List[newCap];
        Iterable<Entry<K,V>> entries = entries();
        bucket = newBucket;
        capacity = newCap;
        n = 0;
        
        for(Entry<K,V> entry : entries){
            /*if(entry != null){
                HashEntry<K,V> e = (HashEntry<K,V>) entry;
                int pos = hashValue(e.getKey());
                if(newBucket[pos] == null){
                    newBucket[pos] = new ArrayList<>();
                }
                newBucket[pos].add(e);
            }*/
            put(entry.getKey(),entry.getValue());
        }
    }
}
