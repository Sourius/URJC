/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import material.Position;

/**
 *
 */

public class News implements Comparable<News>{
    class Entity implements Comparable<Entity>, Position<String>{
        private int frec;
        private int maxFrec;
        private final String entity;

        Entity (String entity){
            this.entity = entity;
            frec = 1;
            maxFrec=1;
        }
        
        void setMaxFrec(){
            this.maxFrec ++;
        }
        
        public int getFrec() {
            return frec;
        }
        
        int maxFrec(){
            return maxFrec;
        }
        
        void increment(){
            this.frec++;
        }

        @Override
        public String getElement() {
            return entity;
        }
        
        @Override
        public String toString(){
            return entity;
        }

        public boolean equals(Entity e){
            return this.getElement().equals(e.getElement());
        }
        
        @Override
        public int compareTo(Entity e) {
            return this.getElement().compareTo(e.getElement());
        }
       
    }
    
    private final String title;
    private final String body;
    private final TreeSet<Entity> entities;
    private Entity maxEntity;
    private final String signs = ". , : ; ... ( ) [ ] ' \" { } - _ ! ¡ ¿ ? ";


    News(String t, String b){
        title = t;
        body = b;
        entities = new TreeSet <>();
        setEntities();
    }
    
    //devuelve el titulo
    public String getTitle() {
        return title;
    }

    //devuelve el mensaje
    public String getBody() {
        return body;
    }

    //obtiene las entidades que aparecen en esta noticia
    private void setEntities(){
        String[] names;
        names = body.split(" ");
       
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(new File("ES_stopList.txt")));
            String aux = reader.readLine();
            for(String s: names){
                int len = s.length();
                char c;
                
                c = s.charAt(len-1);
                if(signs.contains(""+c)){
                    s = s.substring(0,len-1);
                }
                if(this.containsEntity(s)){
                    this.increment(s);
                }
                else{
                    c = s.charAt(0);
                    
                    if(signs.contains(""+c)){
                        s = s.substring(1);
                    }
                    if(Character.isUpperCase(c)){
                        if(!aux.toLowerCase().contains(s.toLowerCase())){
                            Entity e = new Entity(s);
                            this.entities.add(e);
                        }
                    }
                }
            }
            reader.close();
            setMax();
        
        }catch (FileNotFoundException ex){
            Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex){
            Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // devuelve la lista de entidades
    TreeSet<Entity> getEntities(){
        return this.entities;
    }
    
    //incrementa la frecuencia de la entidad
    void increment(String entity){
        boolean found = false;
        
        for(Entity e: this.entities){
            if(e.getElement().equals(entity)){
                e.increment();
                if(maxEntity != null){
                    if(e.getFrec() > this.maxEntity.getFrec()) {
                        this.maxEntity = e;
                    }
                } 
                found = true;
            }
        }  
    }
    
    // devuelve la entidad con mas frecuencia dentro de la noticia
    public Position getMaxRepEntity(){
        return maxEntity;
    }
    
    // obtiene la entidad con mas frecuencia dentro de la noticia
    private void setMax (){
        Entity maxRep = null;
        int max = 0, frec;
        int size = entities.size();
        
        for(Entity e: this.entities){
            frec = e.getFrec();
            if(frec > max){
                max = frec;
                maxRep = e;
            }
        }
        maxEntity = maxRep;
    }
    
    // indica si la entidad aparece o no en esta noticia
    boolean containsEntity(String namedEntity) {
        for(Entity e: this.entities){
            if(e.getElement().equals(namedEntity)) return true;
        }
        return false;
    }
    
    // devueleve la frecuencia de aparicion de la entidad en esta noticia
    int getFrec(String ent){
        for(Entity e: this.entities){
            if(e.getElement().equals(ent)) return e.getFrec();
        }
        return 0;
    }
    
    @Override
    public int compareTo(News n) {
        return this.getTitle().compareTo(n.getTitle());
    }
    
    public boolean equals(News n){
        return this.title.equals(n.getTitle());
    }
    
    @Override
    public String toString(){
        return this.getTitle()+"\n"+this.getBody();
    }
}
