/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablashash;

import java.util.HashMap;

/**
 *
 * @author Sourius
 */
public class MapaAlumno {
    public static void main(String[] args){
        HashMap<Alumno, Nota> mapa = new HashMap<>();
        Alumno alumno1 = new Alumno("Pepe Perez Garcia", 1234567);
        Alumno alumno2 = new Alumno("Pepe Perez Garcia", 1234568);
        Nota nota1 = new Nota(7,"N");
        Nota nota2 = new Nota(10, "E");
        mapa.put(alumno1, nota1);
        mapa.put(alumno2, nota2);
        Alumno alumno3 = new Alumno("Pepe Perez Garcia", 1234567);
        System.out.println(mapa.get(alumno3));
        System.out.println(alumno1.equals(alumno3));
    }
}
