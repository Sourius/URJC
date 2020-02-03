package practica4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import material.graphs.BreadthSearch;
import material.graphs.Graph;
import material.graphs.Vertex;
/**
 *
 * @author mayte
 */
public class GraphUtils {    
    /*
        Dado un grafo, indica si es conexo
    */
    public boolean isConnected(Graph graph){
        // si no hay vertices, el grafo no es conexo
        if(graph == null) return false;
        ArrayList vertexList = new ArrayList(graph.vertices());
        
        // si no hay aristas, el grafo no es conexo
        if (graph.edges().isEmpty() || vertexList.isEmpty()) return false;
        
        // si todos los vertices no estan conectados, el grafo no es conexo
        BreadthSearch recorrido = new BreadthSearch();
        Vertex root = (Vertex)vertexList.get(0);
        return recorrido.traverse(graph, root).size() == vertexList.size();
    }
    
    public boolean isEulerian(Graph graph){
        // comprovar si es conexo
        if(graph == null) return false;
        if(!isConnected(graph)) return false;
        
        int n=0;
        for(Object o: graph.vertices()){
            // mirar si tiene camino euleriano
            Vertex v = (Vertex)o;
            if(graph.incidentEdges(v).size()>1) n++;
            if(n > 1) return false;
        }
        return true;
    }
    
    public boolean isFreeTree(Graph graph){
        int sizeVertexs;
        int sizeEdges;
        
        // comprovar que sea conexo
        if(graph == null) return false;
        if(!isConnected(graph)) return false;
        
        // comprovar que el numero de aristas sea vertices-1
        sizeVertexs = graph.vertices().size();
        sizeEdges = graph.edges().size();
        return (sizeVertexs-1) == sizeEdges;
    }
}