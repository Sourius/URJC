package practica2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.TreeSet;
import practica2.News.Entity;

/**
 * @author jvelez
 */
public class NewsAnalyzer{
    private TreeSet<News> newsList;
    private TreeSet<Entity> entityList;
    
    /**
     * Builds an analysis from meneame web site.
     */
    public NewsAnalyzer() {
        newsList = new TreeSet <>();
        entityList = new TreeSet<>();
        
        StringBuilder content = new StringBuilder();

        try{
            URL meneame = new URL("https://www.meneame.net/");
            URLConnection connection = meneame.openConnection();
            InputStreamReader input = new InputStreamReader(connection.getInputStream(),"UTF-8");
            BufferedReader reader = new BufferedReader(input);
            String html;
            Scanner sc;
            
            html = reader.readLine();
            
            sc = new Scanner(html);
            //saltar la cabecera
            sc.findInLine("<body>");
            
            //obtener noticias
            News news = getNews(sc);
            while(news!=null){
                newsList.add(news);
                //obtener noticia
                try{
                    news = getNews(sc);
                }catch(NoSuchElementException nse){
                    break;
                }
            }
            sc.close();
            reader.close();
            this.setEntities();
        }
        catch(MalformedURLException m){
            System.out.println(m.getMessage()+"\n"+m.getStackTrace());
        }
        catch(IOException io){
            System.out.println(io.getMessage()+"\n"+io.getStackTrace());
        }       
    }
    
    /**
     * @param namedEntity 
     * @return 
     */
    public List <News> getNewsWith(String namedEntity) {
        List<News> aux = new ArrayList <>();
        for(News news: newsList){
            if(news.containsEntity(namedEntity)){
                aux.add(news);
            }
        }
        return aux;                        
    }

    /**
     * 
     * @return the more frecuent named entity.
     */
    public String moreFrecuentNamedEntity() {
        String entity = null;
        int max = 0;
        
        for(Entity e: this.entityList){
            int frec = e.maxFrec();
            if( frec > max) {
                entity = e.getElement();
                max = frec;
            }
        }
        return entity;
    }
    
    //obtiene el titulo de la noticia
    private String getTitle(Scanner sc){
        StringBuilder content = new StringBuilder();
        sc.findInLine("<h2>*.<a.{10,200} >");
        String value = sc.next();       
        while(!value.equals("</a>")){
            content.append(value+" ");
            value = sc.next();
        }
        return content.toString();
    }
    
    //obtiene cuerpo o el mensaje de la noticia
    private String getBody(Scanner sc){
       StringBuilder content = new StringBuilder();

       sc.findInLine("<div class=\"news-content\">");
       String value = sc.next();       
       while(!value.equals("</div>")){
           content.append(value+" ");
           value = sc.next();
       }
       int n = content.length()-7;
       return content.toString().substring(0, n);
    }
     
    // obtiene la noticia
    private News getNews(Scanner sc){
       //obtener la noticia
       sc.findInLine("<div class=\"news-summary\">");
       sc.findInLine("<div class=\"news-body\">");
       sc.findInLine("<div class=\"center-content\">");

       //obtener el titulo
       String title = getTitle(sc);
       if(title.contains("<div class=\"news-details>\""));
       title = getTitle(sc);
       
       //obtener el contenido
       String body = getBody(sc);
       // devolver noticia
       return new News(title,body);
    }
     
    // obtiene la lista de entidades que aparecen en todas las noticias
    private void setEntities() {
        //recorrer noticias
        for (News news: this.newsList) {
           
            //recorrer entidades de cada noticia
            for (Entity e: news.getEntities()) {
                boolean found = false;
                // recorrer la lista de entidades global
                for(Entity e2: this.entityList){
                    // si la entidad esta en la lista incrementar frecuencia
                    if(e.equals(e2)){
                        found = true;
                        int max = e2.maxFrec();
                        e2.setMaxFrec();
                        break;
                    }
                }
                
                // si no esta en la lista insertar
                if(!found){
                    this.entityList.add(e);
                }
            }
        }
    }
}