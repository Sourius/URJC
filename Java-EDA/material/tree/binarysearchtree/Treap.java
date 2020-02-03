package material.tree.binarysearchtree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import material.Position;
import material.tree.binarytree.LinkedBinaryTree;

/**
 * @author mayte
 * @param <E>
 */
public class Treap<E> implements BinarySearchTree<E> {
    private LinkedBinarySearchTree<TreapNode<E>> lbst;
    private final TreeSet<Integer> pList;
    
    public Treap(){
        lbst = new LinkedBinarySearchTree<>();
        pList = new TreeSet<>();
    }
    
    @Override
    public Position<E> find(E value) {
        TreapNode<E> t = new TreapNode<>(value);
        Position<TreapNode<E>> p;
        try{
            p = lbst.find(t);
        }catch(NullPointerException npe){
            System.out.println(npe.getMessage());
            p = null;
        }
        if(p == null) return null;
        return p.getElement();
    }

    @Override
    public Iterable<? extends Position<E>> findAll(E value) {
        TreapNode<E> t = new TreapNode <>(value);
        ArrayList<Position<E>> list = new ArrayList<>();
        for(Position<TreapNode<E>> aux: lbst.findAll(t)){
            list.add(aux.getElement());
        }
        return list;   
    }

    //restructurción binodo
    /*
              yPar/null             yPar/null
              |                       |
              y                       x
            /   \                   /   \
           x     t3                t1     y
          / \                           /   \
         t1  t2                        t2    t3
     */ 
     /*
              y                       x
            /   \                   /   \
           t1    x                y     t3
                / \             /   \
               t2  t3          t1    t2
     */ 
    @Override
    public Position<E> insert(E value) {
        // añadir
        TreapNode<E> t = new TreapNode(value);
        Position<TreapNode<E>> p = lbst.insert(t);
        t.setPos(p);
  
        // reoordenar el arbol
        while(!isBalanced(p)){
            Position<TreapNode<E>> par = lbst.binTree.parent(p);
            Position<TreapNode<E>> gp, aux;
            boolean toLeft = false;
            
            //si ptioridad x > prioridad y
            LinkedBinaryTree x,y,t1=null,t2=null,t3 = null;
            
            // si x > y
            if(p.getElement().compareTo(par.getElement())>0){
                toLeft = true;
                // obtener t1
                if(lbst.binTree.hasLeft(par)){
                    aux = lbst.binTree.left(par);
                    t1 = lbst.binTree.subTree(aux);
                }
                // obtener t2 y t3
                if(lbst.binTree.hasLeft(p)){
                    aux = lbst.binTree.left(p);
                    t2 = lbst.binTree.subTree(aux);
                }
                if(lbst.binTree.hasRight(p)){
                    aux = lbst.binTree.right(p);
                    t3 = lbst.binTree.subTree(aux);
                }
            
            //si x < y    
            }else{
                // obtener t1 y t2
                if(lbst.binTree.hasLeft(p)){
                    aux = lbst.binTree.left(p);
                    t1 = lbst.binTree.subTree(aux);
                }
                if(lbst.binTree.hasRight(p)){
                    aux = lbst.binTree.right(p);
                    t2 = lbst.binTree.subTree(aux);
                }
                //t3
                if(lbst.binTree.hasRight(par)){
                    aux = lbst.binTree.right(par);
                    t3 = lbst.binTree.subTree(aux);
                }
            }
             
            x = lbst.binTree.subTree(p); // nodo nuevo
            
            // si el padre es la raiz
            if(lbst.binTree.isRoot(par)){ 
                y = lbst.binTree.subTree(par); // nodo padre
                // el arbol esta vacio
                lbst.binTree.attach(x);
            }
            else{
                gp = lbst.binTree.parent(par); // abuelo
                y = lbst.binTree.subTree(par); // nodo padre
                // hay que añadir x a la izquierda
                if(gp.getElement().compareTo(par.getElement())<0){
                    lbst.binTree.attachRight(gp,x);
                }
            }
           
            if(toLeft){// x > y
                lbst.binTree.attachLeft(p, y);
                if(t1 != null) lbst.binTree.attachLeft(par,t1);
                if(t2 != null) lbst.binTree.attachRight(par,t2);
                if(t3!=null) lbst.binTree.attachRight(p, t3);
            }
            else{ //x <= y
                lbst.binTree.attachRight(p, y);
                if(t1 != null) lbst.binTree.attachLeft(p,t1);
                if(t2 != null) lbst.binTree.attachLeft(par,t2);
                if(t3!=null) lbst.binTree.attachRight(par, t3);
            }
            p = par;
        }
        return t;
    }

    @Override
    public boolean isEmpty() {
       return lbst.isEmpty();
    }

    //restructurción binodo
    /*
              yPar                   yPar
              |                       |
              y                       x
            /   \                   /   \
           x     t3                t1     y
          / \                           /   \
         t1  t2                        t2    t3
     */ 
     /*
              y                       x
            /   \                   /   \
           t1    x                y     t3
                / \             /   \
               t2  t3          t1    t2
     */ 
    // t1 < x < t2 < y < t3  --> codigo
    //  x > y, y > t2, y > t1 o t1 --> prioridad
    //https://www.geeksforgeeks.org/treap-a-randomized-binary-search-tree/
    //https://yourbasic.org/algorithms/treap/
    
    @Override
    public void remove(Position<E> pos) {
        TreapNode<E> t = (TreapNode<E>) pos;
        Position<TreapNode<E>> p = t.getPos();
        Position<TreapNode<E>> auxNode;
        Position<TreapNode<E>> par;
        
        t.setPriority(-1);
        
        //si no es hoja, cambiar su prioridad e reordenar el arbol
        while(!lbst.binTree.isLeaf(p)){   
            LinkedBinaryTree auxTree;
            // si tiene un hijo
            if((!lbst.binTree.hasLeft(p))||(!lbst.binTree.hasRight(p))){
                //obtener hijo
                if(lbst.binTree.hasLeft(p)){
                    auxNode = lbst.binTree.left(p);
                    auxTree = lbst.binTree.subTree(auxNode);
                }else{
                    auxNode = lbst.binTree.right(p);
                    auxTree = lbst.binTree.subTree(auxNode);
                }
                // si es raiz
                if(lbst.binTree.isRoot(p)){
                    lbst.binTree.remove(p);
                    // el hijo se convierte en la nueva raiz
                    lbst.binTree.attach(auxTree);
                }
                else{ 
                    //si no es raiz
                    par = lbst.binTree.parent(p); // nodo padre
                    lbst.binTree.remove(p);
                    
                    // añadir hijo a la posicion del padre (el nodo que se quiere borrar)
                    // si el nodo padre tiene algun hijo
                    if(lbst.binTree.hasLeft(par)) lbst.binTree.attachRight(par, auxTree);
                    else if(lbst.binTree.hasRight(par)) lbst.binTree.attachLeft(par, auxTree);
                    else{
                        // si el nodo eliminado era su unico hijo
                        if(par.getElement().compareTo(auxNode.getElement()) < 0)lbst.binTree.attachLeft(par, auxTree);
                        else lbst.binTree.attachRight(par, auxTree);
                    }
                }
            }
            // si tiene dos hijos
            else{
                LinkedBinaryTree t1 = null,t2 = null, high, low;
                Position <TreapNode<E>> left, right, aux;
                left = lbst.binTree.left(p);
                right = lbst.binTree.right(p);
                
                // si el izquierdo es el mas prioritario
                if(left.getElement().getPriority() > right.getElement().getPriority()){
                    aux = left;
                    if(lbst.binTree.hasLeft(left)){
                        auxNode = lbst.binTree.left(left);
                        t1 = lbst.binTree.subTree(auxNode);
                    }
                    if(lbst.binTree.hasRight(left)){
                        auxNode = lbst.binTree.right(left);
                        t2 = lbst.binTree.subTree(auxNode);
                    }
                    
                    high = lbst.binTree.subTree(left);
                    low = lbst.binTree.subTree(right);
                }
                // si el derecho es el mas prioritario
                else{
                    aux = right;
                    if(lbst.binTree.hasLeft(right)){
                        auxNode = lbst.binTree.left(right);
                        t1 = lbst.binTree.subTree(auxNode);
                    }
                    if(lbst.binTree.hasRight(right)){
                        auxNode = lbst.binTree.right(right);
                        t2 = lbst.binTree.subTree(auxNode);
                    }
                    
                    high = lbst.binTree.subTree(right);
                    low = lbst.binTree.subTree(left);
                }
                
                // si es la raiz
                if(lbst.binTree.isRoot(p)){
                    auxTree = lbst.binTree.subTree(p); // el nodo a eliminar
                    lbst.binTree.attach(high); // el hijo mas prioritario
                }
                // si no es la raiz
                else{
                    par = lbst.binTree.parent(p);
                    auxTree = lbst.binTree.subTree(p); // el nodo a eliminar
                    if(par.getElement().compareTo(t)< 0) lbst.binTree.attachRight(par, high);
                    else lbst.binTree.attachLeft(par, high);
                }
                if(aux.getElement().compareTo(t) < 0){
                    if(t1 != null)lbst.binTree.attachLeft(aux, t1);
                    lbst.binTree.attachRight(aux, auxTree);
                    if(t2 != null)lbst.binTree.attachLeft(p, t2);
                    lbst.binTree.attachRight(p, low);
                }
                else{
                    if(t1 != null)lbst.binTree.attachLeft(aux, t1);
                    lbst.binTree.attachRight(aux, auxTree);
                    if(t2 != null)lbst.binTree.attachLeft(p, t2);
                    lbst.binTree.attachRight(p, low);
                }
            }
        }
        // si es hoja eliminar
        lbst.binTree.remove(p);
    }

    @Override
    public int size() {
        return lbst.size();
    }

    @Override
    public Iterator<Position<E>> iterator() {
        Iterator<Position<TreapNode<E>>> it =  this.lbst.iterator();
        TreapIterator treapIT = new TreapIterator(it);
        return treapIT;
    }

    @Override
    public Position<E> minimum(Position<E> pos) {
        TreapNode<E> t = (TreapNode<E>)pos;  
        return lbst.minimum(t.getPos()).getElement();
    }
    
    @Override
    public Position<E> sucessor(Position<E> pos) {
        TreapNode<E> t = (TreapNode<E>)pos;
        Iterator<Position<TreapNode<E>>> it = lbst.iterator();
        TreapNode<E> sucessor = null;
        
        boolean found = false;
        Position<TreapNode<E>> aux;
        while(it.hasNext()){
            aux = it.next();
            if(found){
                sucessor = aux.getElement();
                break;
            }
            if(aux.getElement().equals(t)) found = true;
        }
        return sucessor;
    }

    @Override
    public Position<E> first() {
        return lbst.first().getElement();
    }

    @Override
    public Position<E> last() {
        return lbst.last().getElement();
    }

    private boolean isBalanced(Position<TreapNode<E>> p){
        int pr = p.getElement().getPriority();
        TreapNode<E> left;
        TreapNode<E> right;
        
        if(!lbst.binTree.isRoot(p)){
            TreapNode<E> par = lbst.binTree.parent(p).getElement();
            if(pr > par.getPriority()) return false;
        }
        if(lbst.binTree.hasLeft(p)){
            left = lbst.binTree.left(p).getElement();
            if(left.getPriority() > pr) return false;
        }
        if(lbst.binTree.hasRight(p)){
            right = lbst.binTree.right(p).getElement();
            if(right.getPriority()>pr) return false;
        }
        return true;
    }
    
    private class TreapNode<E> implements Comparable<TreapNode<E>>, Position<E> {
        private final E code;
        private Position<TreapNode<E>> pos;
        private int priority;
        
        public TreapNode(E code){
            this.code = code;
            Random r = new Random();
            while(pList.add(r.nextInt())==false){
                //no hacer nada
            }
            priority = pList.last();
        }

        public Position<TreapNode<E>> getPos() {
            return pos;
        }

        public void setPos(Position<TreapNode<E>> pos) {
            if(pos != null) this.pos = (Position<TreapNode<E>>)pos;
            else throw new RuntimeException("Elemento no valido!");
        }
       
        private int getPriority(){
            return priority;
        }
        
        private void setPriority(int n){
            priority = n;
        }
        
        @Override
        public E getElement() {
            return this.code;
        }        

        @Override
        public int compareTo(TreapNode<E> t) {
            if (code instanceof Comparable && t.getElement() instanceof Comparable) {
                return ((Comparable)code).compareTo(t.getElement());
            } else {
                throw new ClassCastException("Element is not comparable");
            }
        }
        
        @Override
        public boolean equals(Object o){
            TreapNode t = (TreapNode) o;
            return this.compareTo(t) == 0;
        }
    }
    
    private class TreapIterator implements Iterator<Position<E>>{
        private final Iterator<Position<TreapNode<E>>> it;
        
        public TreapIterator(Iterator<Position<TreapNode<E>>> it){
            this.it = it;
        }
        
        @Override
        public boolean hasNext() {
            return this.it.hasNext();
        }

        @Override
        public Position<E> next() {
            return this.it.next().getElement();
        }
        
        @Override
        public void remove(){
            it.remove();
        }
    }
}