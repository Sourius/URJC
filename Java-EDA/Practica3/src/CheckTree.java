
import java.util.Iterator;
import material.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.InorderBinaryTreeIterator;

/**
 *
 * @author mayte
 * @param <E>
 */
public class CheckTree<E extends Comparable> {

    /**
     * Receives a BinaryTree and returns true if the tree is a BinarySearchTree
     *
     * @param tree
     * @return
     */
    public boolean isBinarySearchTree(BinaryTree<E> tree) {
        InorderBinaryTreeIterator it = new InorderBinaryTreeIterator(tree);
        Position<? extends Comparable> current;
        Position<? extends Comparable> old = null;
        while(it.hasNext()){
           current = (Position<? extends Comparable>)it.next();
           if(old != null){
               if(old.getElement().compareTo(current.getElement()) > 0){
                   return false;
                }
           }
           old = current;
        }
        return true;
    }

}
