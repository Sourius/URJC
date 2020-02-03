package material.graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @author mayte
 * @param <V> Set of vertex
 * @param <E> Set of edges
 */
public class AMGraph <V,E> implements Graph <V,E> {
    private final ArrayList<ArrayList<AMEdge<E,V>>> matriz;
    private final HashSet<AMEdge<E,V>> edges;
    private final HashSet<AMVertex<V>> vertices;
    
    public AMGraph(){
        matriz = new ArrayList<>();
        edges = new HashSet<>();
        vertices = new HashSet<>();
    }
    
    @Override
    public Collection<? extends Vertex<V>> vertices() {
        return Collections.unmodifiableCollection(vertices);
    }

    @Override
    public Collection<? extends Edge<E>> edges() {
        return Collections.unmodifiableCollection(edges);
    }

    @Override
    public Collection<? extends Edge<E>> incidentEdges(Vertex<V> v) {
        if(!(v instanceof AMVertex) || v == null) throw new RuntimeException("Invalid vertex!");
        if(!vertices.contains(v)) throw new RuntimeException("Vertex not in Graph!!");
        
        HashSet<AMEdge<E,V>> incidentEdges = new HashSet<>();
        for(Edge aux: edges){
            AMEdge e = (AMEdge) aux;
            if(e.getStartVertex().equals(v) || e.getEndVertex().equals(v)) {
               incidentEdges.add(e);
            }
        }
        return Collections.unmodifiableCollection(incidentEdges);
    }

    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) {
        if(v == null || e == null) throw new RuntimeException("Invalid element!");
        AMVertex<V> other;
        AMEdge edge = (AMEdge) e;
        
        if(!edge.getStartVertex().equals(v) && !edge.getEndVertex().equals(v)) 
            throw new RuntimeException("Invalid vertex!");
        
        other = (AMVertex<V>)edge.getEndVertex();
        if(other.equals(v)) other = (AMVertex<V>)edge.getStartVertex();
        return other;
    }

    @Override
    public List<Vertex<V>> endVertices(Edge<E> edge) {
        if(edge == null) throw new RuntimeException("Invalid edge!");
        List<Vertex<V>> endVertices = new ArrayList<>();
        AMEdge<E,V> e = (AMEdge<E,V>)edge;
        endVertices.add(e.getStartVertex());
        endVertices.add(e.getEndVertex());
        return endVertices;
    }

    @Override
    public Edge<E> areAdjacent(Vertex<V> v1, Vertex<V> v2) {
        if((!vertices.contains(v1) || v1 == null)) throw new RuntimeException("Invalid vertex!");
        if((!vertices.contains(v2) || v2 == null))  throw new RuntimeException("Invalid vertex!");
        for(Edge<E> edge : incidentEdges(v1)){
           AMEdge aux = (AMEdge) edge;
           if(aux.getStartVertex().equals(v1) && aux.getEndVertex().equals(v2)) return aux;
           else if(aux.getEndVertex().equals(v2) && aux.getStartVertex().equals(v2)) return aux;
        }
        return null;
    }

    @Override
    public V replace(Vertex<V> vertex, V vertexValue) {
        AMVertex<V> v = (AMVertex) vertex;
        V oldValue = v.getElement();
        v.setElement(vertexValue);
        return oldValue;
    }

    @Override
    public E replace(Edge<E> edge, E edgeValue) {
        AMEdge e = (AMEdge)edge;
        E oldValue = (E) e.getElement();
        e.setElement(edgeValue);
        return oldValue;
    }

    @Override
    public Vertex<V> insertVertex(V value) {
        AMVertex<V> vertex = new AMVertex(value,this);
        vertices.add(vertex);
        return vertex;
    }

    @Override
    public Edge<E> insertEdge(Vertex<V> v1, Vertex<V> v2, E edgeValue) {
        if(vertices.contains(v1) && vertices.contains(v2)){
            AMEdge<E,V> e = new AMEdge(v1,v2,edgeValue, this); 
            if(!edges.contains(e)){
                edges.add(e);
                
                // añadir la nueva arista
                ArrayList<AMEdge<E,V>> aux = new ArrayList<>();
                aux.add(e);
                
                //actualizar matriz
                for(List<AMEdge<E,V>> list: matriz){
                    AMEdge edge= list.get(0);
                    if(edge.getStartVertex().equals(v1) || edge.getEndVertex().equals(v2)){
                        if(!edge.equals(e)){
                            aux.add(edge);
                            list.add(e);
                        }
                    }
                }
                // rellenar matriz
                matriz.add(aux);
                return e;
            } 
            return e;
        }
        else throw new RuntimeException("Unknown vertices!");
    }
    
    @Override
    public V removeVertex(Vertex<V> vertex) {
        if(vertex == null || !vertices.contains(vertex)) throw new RuntimeException("Invalid vertex!");
        V value = vertex.getElement();
        edges.removeAll(incidentEdges(vertex));
        vertices.remove((AMVertex)vertex);
        return value;
    }

    @Override
    public E removeEdge(Edge<E> edge) {
        if(edge == null || !edges.contains(edge)) throw new RuntimeException("Invalid edge!");
        E value = edge.getElement();
        
        List<AMEdge<E,V>> removeAll = new ArrayList<>();
        for(List<AMEdge<E,V>> edgeList: matriz){
            if(edgeList.get(0).equals(edge)){
                edgeList.clear();
            }else{
                for(AMEdge<E,V> e: edgeList){
                    if(e.equals(edge)) removeAll.add(e);
                }
                edgeList.removeAll(removeAll);
                removeAll.clear();
            }
        }
        edges.remove(edge);
        return value;
    } 
    
    private class AMEdge <E,V> implements Edge {
        private E edgeValue;
        private final Vertex<V> startVertex;
        private final Vertex<V> endVertex;
        private final Graph graph;
        
        public AMEdge(Vertex<V> start, Vertex<V> end, E value, Graph g){
            edgeValue = value;
            startVertex = start;
            endVertex = end;
            graph = g;
        }
        
        @Override
        public Object getElement() {
            return edgeValue;
        }
        
        public void setElement(E value){
            edgeValue = value;
        }

        public Vertex<V> getStartVertex() {
            return startVertex;
        }

        public Vertex<V> getEndVertex() {
            return endVertex;
        }
        
        public Graph getGraph(){
            return graph;
        }
        
        @Override
        public int hashCode() {
            int hash = 3;

            final int min = Math.min(Objects.hashCode(this.startVertex), Objects.hashCode(this.endVertex));
            final int max = Math.max(Objects.hashCode(this.startVertex), Objects.hashCode(this.endVertex));

            hash = 67 * hash + Objects.hashCode(this.getGraph());
            hash = 67 * hash + min;
            hash = 67 * hash + max;
            return hash;
        }
    
    
        @Override
        public boolean equals(Object o){
            if (o == null) {
                return false;
            }
            if (getClass() != o.getClass()) {
                return false;
            }
            final AMEdge<E, V> other = (AMEdge<E, V>) o;
            if (!Objects.equals(this.graph, other.graph)) {
                return false;
            }

            final int min1 = Math.min(Objects.hashCode(getStartVertex()), Objects.hashCode(getEndVertex()));
            final int max1 = Math.max(Objects.hashCode(getStartVertex()), Objects.hashCode(getEndVertex()));

            final int min2 = Math.min(Objects.hashCode(other.getStartVertex()), Objects.hashCode(getEndVertex()));
            final int max2 = Math.max(Objects.hashCode(other.getStartVertex()), Objects.hashCode(getEndVertex()));

            if (!Objects.equals(min1, min2)) {
                return false;
            }
            return Objects.equals(max1, max2);
        }
    }

    private class AMVertex <V> implements Vertex<V>{
        private V vertexValue;
        private final Graph graph;
        
        public AMVertex(V value, Graph g){
            vertexValue = value;
            graph = g;
        }
        
        @Override
        public V getElement() {
            return vertexValue;
        }

        public void setElement(V vertexValue) {
            this.vertexValue = vertexValue;
        }   
        
        @Override
        public boolean equals(Object o){
            AMVertex<V> v = (AMVertex<V>) o;
            return hashCode()==v.hashCode();
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 31 * hash + Objects.hashCode(graph);
            hash = 31 * hash + Objects.hashCode(this.vertexValue);
            return hash;
        }
        
        public Graph getGraph() {
            return graph;
        }
    }
}