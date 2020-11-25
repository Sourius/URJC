/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author jose.velez
 */
public class NumberGetter {
    public int getNumber(String message){
        int numPlayers = 0;
        try {
            System.out.print(message);
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

            boolean exit = false;
            while (!exit) {
                String n = bf.readLine();
                try {
                    numPlayers = Integer.parseInt(n);
                    exit = true;
                } catch (RuntimeException ex) {
                }
            }

        } catch (IOException ex) {
            throw new RuntimeException("System problem");
        }
        return numPlayers;
    }
    
}
