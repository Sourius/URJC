package practica3;

import material.tree.binarysearchtree.AVLTree;
import material.tree.binarysearchtree.RBTree;

/**
 *
 * @author jvelez
 */
public class RotationCounter {
    private final AVLTree avl = new AVLTree();
    private final RBTree rb = new RBTree();
    
    public void count() {
        count(0,1000000);
    }
    
    public void count(int n) {
        count(0,n);
    }
    
    public void count(int m, int n) {
        for(int i = m; i < n; i++){
            avl.insert(i);
            rb.insert(i);
        }
        System.out.println("AVLTree: "+avl.getRestructureNumber());
        System.out.println("RBTree: "+rb.getRestructureNumber());
    }
}