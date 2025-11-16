package kruskalmst;

import java.util.*;

public class KruskalMSTDemo {

    public static void main(String[] args) {

        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge("Porto Alegre", "Caxias", 4));
        edges.add(new Edge("Porto Alegre", "Pelotas", 8));
        edges.add(new Edge("Caxias", "Santa Maria", 6));
        edges.add(new Edge("Caxias", "Uruguaiana", 5));
        edges.add(new Edge("Santa Maria", "Pelotas", 3));
        edges.add(new Edge("Santa Maria", "Uruguaiana", 7));
        edges.add(new Edge("Uruguaiana", "Bagé", 2));


        Set<String> vertices = new HashSet<>();
        for (Edge e : edges) {
            vertices.add(e.source);
            vertices.add(e.target);
        }

        kruskalMST(vertices, edges);
    }

    public static void kruskalMST(Set<String> vertices, List<Edge> edges) {
        List<Edge> result = new ArrayList<>();  // chosen edges for MST
        DisjointSet ds = new DisjointSet();

        ds.makeSet(vertices);          // each city starts isolated
        Collections.sort(edges);       // sort edges by weight

        System.out.println("Edges sorted by weight:");
        for (Edge e : edges) System.out.println("  " + e);
        System.out.println();

        System.out.println("Step-by-step MST construction:");
        for (Edge e : edges) {
            String rootU = ds.find(e.source);
            String rootV = ds.find(e.target);

            // If vertices belong to different sets, add the edge
            if (!rootU.equals(rootV)) {
                result.add(e);
                ds.union(rootU, rootV);
                System.out.println("✅ Added: " + e + " (no cycle)");
            } else {
                System.out.println("❌ Ignored: " + e + " (would form a cycle)");
            }
        }

        System.out.println("\n Minimum Spanning Tree (MST):");
        int totalCost = 0;
        for (Edge e : result) {
            System.out.println("  " + e);
            totalCost += e.weight;
        }

        System.out.println(" Total minimum cost: " + totalCost);
    }

}
