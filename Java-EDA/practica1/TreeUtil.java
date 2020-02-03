package practica1;

import java.util.Iterator;
import java.util.LinkedList;
import material.Position;
import material.tree.narytree.LinkedTree;
import material.tree.narytree.NAryTree;

/**
 *
 * @author jvelez
 */
public class TreeUtil {
    /**
     * Clone source in dest. Dest must be a empty tree.
     * @param <T>
     * @param source
     * @param dest 
     */
    
    public static <T> void  clone(NAryTree<T> source, NAryTree<T> dest) {
        LinkedList <Position<T>> cola = new LinkedList<>();
        Iterator<Position<T>> it = source.iterator();
        Position node;
        
        while(it.hasNext()){
            Position p = (Position) it.next();
            
            if(dest.isEmpty()) {
                dest.addRoot(source.root().getElement());
                cola.add(dest.root());
            }
            if(source.isInternal(p)){  
                node = cola.pop();
                for(Object q: source.children(p)){
                    Position q2 = (Position) q;                 
                    if(source.isInternal(q2)){
                        cola.add(dest.add((T)q2.getElement(), node));
                    }
                    else dest.add((T)q2.getElement(), node);
                }
            }
            else; // para saltar nodos hojas que ya estan incluidas
        }
    }
    
    /**
    * Modifies tree to make pos the root maintaining all the distances between nodes.
    * @param <T>
    * @param tree
    * @param pos 
    */
    public static <T> void rearranger(NAryTree<T> tree, Position<T> pos) {
        LinkedTree aux = new LinkedTree();
        
        Position parRoot = tree.parent(pos); 
        Position col = parRoot;      
        Position root = tree.root();
        
        while(!tree.parent(col).equals(root)){
            col = tree.parent(col);
        }
        
        //cambiar la raiz
        aux.attach(null,tree.subTree(pos));  
        Position rootAux = aux.root();

        // añadir raiz a la posicion original de pos
        aux.attach(rootAux, tree.subTree(col));
        aux.replace(pos,root.getElement());
        
        // mover los hijos todos menos los que estan en la misma columna
        aux.attach(parRoot, tree);
        tree.attach(null, aux);
    }
}
