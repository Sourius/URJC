/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bingo;

import utils.NumberGetter;

/**
 *
 * @author jose.velez
 */
class Player {

    private Card [] cards;
    public Player() {
        NumberGetter n = new NumberGetter();
        int numCards = n.getNumber("Number of cards:");
        cards = new Card[numCards];
        for (int i = 0; i < numCards; i++) {
            cards[i] = new Card();
            
        }
    }

    // recibe un numero y elimina de todos los cartones ese numero
    void removeNumber(int number) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // canta linea, comprueba si hay algun carton con linea completa
    boolean hasLine() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // compreueba si hay algún carton con todas las lineas
    boolean hasBingo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
