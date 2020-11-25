package practica1;

import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class TrieTest {

    public TrieTest() {
    }

    /**
     * Test of insert method, of class Trie.
     */
    @Test
    public void testInsert_Search() {
        System.out.println("insert");
        Trie instance = new Trie();
        instance.insert("any");
        instance.insert("answer");
        instance.insert("there");
        instance.insert("their");
        instance.insert("bye");
        Iterable<String> search = instance.search("an");
        HashSet<String> c = new HashSet<>();
        c.add("any");
        c.add("answer");
        for (String s : search) {
            assertTrue(c.contains(s));
        }
    }

    /**
     * Test of erase method, of class Trie.
     */
    @Test
    public void testDelete() {
        System.out.println("delete");
        Trie instance = new Trie();
        instance.insert("any");
        instance.insert("answer");
        instance.insert("there");
        instance.insert("their");
        instance.insert("bye");
        instance.delete("an");
        assertEquals(instance.search("an"), null);
    }

}
