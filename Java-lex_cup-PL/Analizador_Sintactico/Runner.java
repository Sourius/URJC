/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica_Obligatoria;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Runner {
    public static void main(String []args){
        PrintStream ps;
        String filename;
        Analizador_Lexico lexico = null;
        
        if (args.length == 0) {
            System.out.println("Inserta nombre de archivo\n"+"( Usage : java Analizador <inputfile> )");
        } else {
            for (int i = 0; i < args.length; i++) {
                filename = args[i];
                try {
                    lexico = new Analizador_Lexico( new java.io.FileReader(args[i]));
                    parser sintactico = new parser(lexico);
                    sintactico.parse();

                    //redireccionar la salida
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(new File(filename+".ps")));
                        try{
                            //escribir en el fichero de salida
                            ps= new PrintStream(new BufferedOutputStream( 
                                    new FileOutputStream(
                                            new File(filename+".c"),true)),true);
                            System.setOut(ps);
                            //redireccionar errores a la salida estandar
                            System.setErr(System.out);
                            //escribir la solucion en el fichero de salida
                            System.out.println(sintactico.resultado);
                        }catch (FileNotFoundException ex) {
                            System.out.println("Error al generar fichero "+filename+".c!");
                        }
                    }catch (FileNotFoundException ex) {
                        System.out.println("Error al abrir fichero "+filename+".ps!");
                    }catch (IOException e) {
                        System.out.println("Error durante la lectura del fichero "+filename+".ps!");
                        e.printStackTrace();
                    }
                }catch (FileNotFoundException e) {
                        System.out.println("Archivo "+filename+" no se ha encontrado!");
                }catch (IOException e) {
                        System.out.println("Error durante la lectura del archivo "+filename+"!");
                        e.printStackTrace();
                }catch (Exception e) {
                        System.out.println("EXCEPTION! :");
                        e.printStackTrace();
                }	
            } 
        }
    }
 }