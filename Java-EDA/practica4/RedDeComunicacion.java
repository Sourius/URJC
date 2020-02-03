package practica4;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import material.graphs.AMGraph;
import material.graphs.ELGraph;
import material.graphs.Edge;
import material.graphs.Vertex;

/**
 *
 * @author mayte
 */
public class RedDeComunicacion {
    private ELGraph red;

    public RedDeComunicacion(){
        red = new ELGraph();
    }
    
    /**
     Añade una nueva sede a la empresa
     */
    public void addSede(String sede){
        red.insertVertex(sede);
    }
    
    /**
     Añade un nuevo enlace de comunicacion a la empresa
     */
    public void addComunicacion(String sede1, String sede2, int distancia){
        Vertex v1 = null,v2 = null, aux;
        Edge e= null;
        
        for(Object o:red.vertices()){
            aux = (Vertex)o;
            
            if(aux.getElement().equals(sede1)) v1 = aux;
            if(aux.getElement().equals(sede2)) v2 = aux;
            
            if(v1 != null && v2 != null) {
                e = red.insertEdge(v1, v2, distancia);
                break;
            }
        }
        
    }
    
    public boolean tenemosPuntosDeArticulacion(){
        for(Object o: red.vertices()){
            Vertex v = (Vertex) o;
            if(red.incidentEdges(v).size() == 1) return true;
        }
        return false;
    }
    
    public boolean tenemosQueReforzarComunicacion(){
        return tenemosPuntosDeArticulacion();
    }
    
    public Collection<String> puntosDeArticulacion(){
        HashSet<String> pArt = new HashSet<>();
        for(Object v: red.vertices()){
            Vertex vertex = (Vertex) v;

            if(red.incidentEdges(vertex).size() == 1){
                for(Object e: red.incidentEdges(vertex)){
                    Edge edge =(Edge) e;
                    String s = (String)red.opposite(vertex, edge).getElement();
                    pArt.add(s);
                }
            } 
        }
        return Collections.unmodifiableCollection(pArt);
    }
}
