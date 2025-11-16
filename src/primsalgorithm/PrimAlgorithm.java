package primsalgorithm;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrimAlgorithm {

        public void run(List<List<Edge>> graph, int vertices) {

            boolean[] visited = new boolean[vertices];
            PriorityQueue<Edge> pq =
                    new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

            int start = 0;
            visited[start] = true;
            pq.addAll(graph.get(start));

            int totalWeight = 0;

            System.out.println("Edges in MST:");

            while (!pq.isEmpty()) {

                Edge e = pq.poll();

                if (visited[e.target]) continue;

                visited[e.target] = true;
                totalWeight += e.weight;

                System.out.println("  to " + e.target + " (weight " + e.weight + ")");

                for (Edge next : graph.get(e.target)) {
                    if (!visited[next.target]) {
                        pq.add(next);
                    }
                }
            }

            System.out.println("Total cost = " + totalWeight);
        }
}
