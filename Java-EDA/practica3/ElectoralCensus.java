package practica3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import material.tree.binarysearchtree.AVLTree;
import material.tree.binarysearchtree.BinarySearchTree;
import material.utils.Pair;

/**
 *
 * @author mayte
 */
public class ElectoralCensus{

    private BinarySearchTree<Pair<String,Integer>> list;
    
    ElectoralCensus(String listadotxt) {
        list = new AVLTree<>();

        File f = new File(listadotxt);
        Scanner sc;
        try {
            sc = new Scanner(new FileInputStream(f),"UTF8");
            while(sc.hasNextLine()){
                    
                String s = sc.nextLine();
                String aux[] = s.split("\\s");
                String name, surname, surname2;

                name = aux[0];
                surname = aux[1];
                surname2 = aux[2];

                int dni = Integer.parseInt(aux[3]);
                String fullname = surname+" "+surname2+", "+name;

                Pair<String, Integer> p = new Pair<>(fullname, dni);
                list.insert(p);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ElectoralCensus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public BinarySearchTree<Pair<String,Integer>> getList(){
        return list;
    }
}