/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package material.tree.binarytree;

import material.Position;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class InorderBinaryTreeIteratorTest {

    public InorderBinaryTreeIteratorTest() {
    }

    /**
     * Test of hasNext method, of class InorderBinaryTreeIterator.
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

        /*  t.insertLeft(e, -1);
        Position<Integer> aux = t.insertRight(e, -2);
        t.insertLeft(aux, -3);
        t.insertRight(aux, -4);
         */
        Position<Integer> f = t.insertLeft(c, 6);
        Position<Integer> g = t.insertRight(c, 9);
        Position<Integer> h = t.insertLeft(f, 5);
        Position<Integer> i = t.insertRight(f, 7);

        String salida = "";
        InorderBinaryTreeIterator<Integer> it = new InorderBinaryTreeIterator<>(t);
        while (it.hasNext()) {
            salida += it.next().getElement().toString();
        }
        assertEquals("123456789", salida);
    }
}
