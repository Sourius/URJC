
import java.util.Iterator;
import material.Position;
import material.tree.binarysearchtree.BinarySearchTree;
import material.tree.binarysearchtree.LinkedBinarySearchTree;

/**
 *
 * @author mayte
 */
public class ClosestInteger {

    /**
     * Gets a BinarySearchTree and an integer i and returns the Position that
     * contains the closest integer to i
     *
     * @param tree
     * @param i
     * @return
     */
    public Position<Integer> closest(BinarySearchTree<Integer> tree, int i) {
        if (tree == null || tree.isEmpty()) {
            throw new RuntimeException("Invalid operation. Tree is either null or empty");
        }
        Position<Integer> closest = null;        
       
        Iterator<Position<Integer>> it = tree.iterator(); 
        int diff = 0;
        int minDiff = -1;
        
        while(it.hasNext()){
            Position<Integer> current = it.next();
            diff = current.getElement() - i;
            if(Math.abs(diff) < minDiff || minDiff == -1){
                minDiff = Math.abs(diff);
                closest = current;
            }
            // realiza un recorrido inOrden
            if(diff > 0){
                return closest;
            }
        }
        
        return closest;
    }
}
