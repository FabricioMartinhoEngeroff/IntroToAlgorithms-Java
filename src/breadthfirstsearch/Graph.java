package breadthfirstsearch;

import java.util.*;

public class Graph {

    private final Map<Vertex, List<Vertex>> adjList = new HashMap<>();

    public void addVertex(Vertex v){
        adjList.putIfAbsent(v, new ArrayList<>());
    }

    public void addEdge(Vertex u, Vertex v){
        adjList.get(u).add(v);
    }

    public List<Vertex> getNeighbors(Vertex v){
        return adjList.get(v);
    }

    public Set<Vertex> getVertices(){
        return  adjList.keySet();
    }

    public void printGraph(){
        System.out.println("\nRepresentação do Grafo (Lista de Adjacência):");
        for (Vertex v : adjList.keySet()) {
            System.out.print(v.getName() + " → ");
            for (Vertex neighbor : adjList.get(v)) {
                System.out.print(neighbor.getName() + " ");
            }
            System.out.println();
        }
    }
}
