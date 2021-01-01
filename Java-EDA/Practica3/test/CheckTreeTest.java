
import material.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.LinkedBinaryTree;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class CheckTreeTest {

    public CheckTreeTest() {
    }

    /**
     * Test of isBinarySearchTree method, of class CheckTree.
     */
    @Test
    public void testIsBinarySearchTree() {
        System.out.println("isBinarySearchTree");
        BinaryTree<Integer> tree = new LinkedBinaryTree<>();
        Position<Integer> r = tree.addRoot(24);
        Position<Integer> p1 = tree.insertLeft(r, 15);
        Position<Integer> p2 = tree.insertRight(r, 31);
        Position<Integer> p3 = tree.insertLeft(p1, 12);
        Position<Integer> p4 = tree.insertRight(p1, 19);
        tree.insertLeft(p2, 29);
        tree.insertRight(p2, 44);
        tree.insertLeft(p3, 4);
        tree.insertLeft(p4, 17);
        tree.insertRight(p4, 21);
        CheckTree instance = new CheckTree();
        assertTrue(instance.isBinarySearchTree(tree));
        BinaryTree<Integer> tree2 = new LinkedBinaryTree<>();
        r = tree2.addRoot(24);
        p1 = tree2.insertLeft(r, 15);
        p2 = tree2.insertRight(r, 31);
        p3 = tree2.insertRight(p1, 12);
        p4 = tree2.insertLeft(p1, 19);
        tree2.insertLeft(p2, 29);
        tree2.insertRight(p2, 44);
        tree2.insertLeft(p3, 4);
        tree2.insertLeft(p4, 17);
        tree2.insertRight(p4, 21);
        assertFalse(instance.isBinarySearchTree(tree2));
    }

}
