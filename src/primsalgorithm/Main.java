package primsalgorithm;

import java.util.ArrayList;
import java.util.List;

public class Main {

        public static void main(String[] args) {

            int vertices = 5;
            List<List<Edge>> graph = new ArrayList<>();

            for (int i = 0; i < vertices; i++) {
                graph.add(new ArrayList<>());
            }

            addEdge(graph, 0, 1, 2);
            addEdge(graph, 0, 3, 6);
            addEdge(graph, 1, 2, 3);
            addEdge(graph, 1, 3, 8);
            addEdge(graph, 1, 4, 5);
            addEdge(graph, 2, 4, 7);
            addEdge(graph, 3, 4, 9);

            PrimAlgorithm prim = new PrimAlgorithm();
            prim.run(graph, vertices);
        }

        private static void addEdge(List<List<Edge>> graph, int u, int v, int weight) {
            graph.get(u).add(new Edge(v, weight));
            graph.get(v).add(new Edge(u, weight));
        }
    }

