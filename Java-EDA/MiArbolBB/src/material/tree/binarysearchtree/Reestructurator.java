package material.tree.binarysearchtree;

import material.Position;
import material.tree.binarytree.LinkedBinaryTree;

/**
 * LinkedBinarySearchTree that implements the tri-node restructuration
 * @author jvelez
 */
class Reestructurator {
   /**
     * Performs a tri-node restructuring. Assumes the nodes are in one of
     * following configurations:
     *
     * <pre>
     *          z=c       z=c        z=a         z=a
     *         /  \      /  \       /  \        /  \
     *       y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *      /  \      /  \           /  \         /  \
     *    x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *   /  \          /  \       /  \             /  \
     *  t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     *
     * @return the new root of the restructured subtree
     */ 
     public Position restructure(Position posNode, LinkedBinaryTree binTree){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
     }
}
