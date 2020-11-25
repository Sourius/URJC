/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author jose.velez
 */
class Card {
    private Set<Integer> []line = new TreeSet[3]; 
    
    public Card(){
        generateLines();
        fillLines(getNumbers());
    }

    private void generateLines() {
        for(int i = 0; i < 3; i++){
            line[i] = new TreeSet<>();
        }
    }
    
    private void fillLines(TreeSet<Integer> numbers){
        int counter = 0;
        for(Integer i: numbers){
            line[counter].add(i);
            counter++;
            counter = counter % 3;
        }
    }
    
    private TreeSet<Integer> getNumbers(){
        Random rd = new Random();
        int num;
        
        TreeSet<Integer> out = new TreeSet<>();
        while(out.size() < 15){
            out.add(rd.nextInt(90)+1);
        }
        return out;
    }
}
