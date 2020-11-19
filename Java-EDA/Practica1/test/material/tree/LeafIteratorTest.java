package material.tree;

import material.Position;
import material.tree.binarytree.LinkedBinaryTree;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class LeafIteratorTest {

    public LeafIteratorTest() {
    }

    /**
     * Test of Iterator, of class LeafIterator.
     */
    @Test
    public void testIterator() {
        System.out.println("testIterator");
        LinkedBinaryTree<Integer> t = new LinkedBinaryTree<>();
        Position<Integer> a = t.addRoot(4);
        Position<Integer> b = t.insertLeft(a, 2);
        Position<Integer> c = t.insertRight(a, 8);
        Position<Integer> d = t.insertLeft(b, 1);
        Position<Integer> e = t.insertRight(b, 3);
        Position<Integer> f = t.insertLeft(c, 6);
        Position<Integer> g = t.insertRight(c, 9);
        Position<Integer> h = t.insertLeft(f, 5);
        Position<Integer> i = t.insertRight(f, 7);

        String salida = "";
        LeafIterator<Integer> it = new LeafIterator<>(t);
        while (it.hasNext()) {
            salida += it.next().getElement().toString();
        }
        assertEquals("13579", salida);
    }

}
