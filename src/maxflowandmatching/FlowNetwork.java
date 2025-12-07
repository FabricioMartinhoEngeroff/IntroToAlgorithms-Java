package maxflowandmatching;

import java.util.ArrayList;
import java.util.List;

public class FlowNetwork {

    private final int n;
    private final List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public FlowNetwork(int n) {
        this.n = n;
        this.adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    public int size(){
        return n;
    }

    public List<Edge> getEdges(int v){
        return adj[v];
    }

    public void addEdge(int from, int to, int capacity){
        Edge e1 = new Edge(from, to, capacity);
        Edge e2 = new Edge(to, from, 0);

        e1.reverse = e2;
        e2.reverse = e1;

        adj[from].add(e1);
        adj[from].add(e2);
    }
}
