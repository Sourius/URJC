/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Practica_Obligatoria;

public class ErrorClass {
    private String lexema, type, desc;
    private int line,column;
    
    public ErrorClass(String lex,int l,int c, String t, String d){
        this.lexema = lex;
        this.line = l;
        this.column = c;
        this.type = t;
        this.desc = d;
    }
    
    public String getLexema (){
        return this.lexema;
    }
    public String getType (){
        return this.type;
    }
    public String getDescription (){
        return this.desc;
    }
    public int getLine (){
        return this.line;
    }
    public int getColumn (){
        return this.column;
    }
}
