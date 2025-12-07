package maxflowandmatching;

public class FordFulkerson {

    private boolean dfs(FlowNetwork net, int u, int sink, boolean[] visited, Edge[] parent) {
        visited[u] = true;

        if (u == sink) return true;

        for (Edge e : net.getEdges(u)) {
            if (!visited[e.to] && e.residualCapacity() > 0) {
                parent[e.to] = e;
                if (dfs(net, e.to, sink, visited, parent))
                    return true;
            }
        }
        return false;
    }

    public int maxFlow(FlowNetwork net, int source, int sink) {
        int flow = 0;

        while (true) {
            boolean[] visited = new boolean[net.size()];
            Edge[] parent = new Edge[net.size()];

            boolean found = dfs(net, source, sink, visited, parent);

            if (!found) break;

            int bottleneck = Integer.MAX_VALUE;

            for (int v = sink; v != source; v = parent[v].from) {
                bottleneck = Math.min(bottleneck, parent[v].residualCapacity());
            }

            for (int v = sink; v != source; v = parent[v].from) {
                parent[v].addFlow(bottleneck);
            }

            flow += bottleneck;
        }
        return flow;
    }
}

