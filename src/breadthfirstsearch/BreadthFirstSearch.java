package breadthfirstsearch;

import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch  {

    public void bfs(Graph G, Vertex source) {
    // Inicializa todos os vértices
    for (Vertex u : G.getVertices()) {
        u.setColor("WHITE");
        u.setDistance(Integer.MAX_VALUE);
        u.setParent(null);
    }

    // Define o vértice fonte
    source.setColor("GRAY");
    source.setDistance(0);
    source.setParent(null);

    // Fila para controlar a fronteira da busca
    Queue<Vertex> Q = new LinkedList<>();
    Q.add(source);

    System.out.println("\nIniciando BFS a partir de: " + source.getName());

    while (!Q.isEmpty()) {
        Vertex u = Q.remove();
        System.out.println("\nExplorando " + u.getName() + ":");

        for (Vertex v : G.getNeighbors(u)) {
            if (v.getColor().equals("WHITE")) {
                v.setColor("GRAY");
                v.setDistance(u.getDistance() + 1);
                v.setParent(u);
                Q.add(v);
                System.out.println("  Descoberto " + v.getName() + " (dist=" + v.getDistance() + ")");
            }
        }
        u.setColor("BLACK");
    }
}

// Exibe o caminho mais curto entre dois vértices
public void printPath(Vertex source, Vertex target) {
    if (target == source) {
        System.out.print(source.getName());
    } else if (target.getParent() == null) {
        System.out.println("Não há caminho de " + source.getName() + " até " + target.getName());
    } else {
        printPath(source, target.getParent());
        System.out.print(" -> " + target.getName());
    }
}
}
