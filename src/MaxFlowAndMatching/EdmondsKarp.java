package MaxFlowAndMatching;

import java.util.ArrayDeque;
import java.util.Queue;

public class EdmondsKarp {

    public int maxFlow (FlowNetwork net, int source, int sink){
        int flow = 0;

        while (true){
            Edge[] parente = new Edge[net.size()];
            boolean found = bfs(net, parente, source, sink);

            if (!found) break;

            int bottlenec = Integer.MAX_VALUE;

            for (int v = sink; v != source; v = parente[v].from){
                bottlenec = Math.min(bottlenec, parente[v].residualCapacity());
            }

            for (int v = sink; v != source; v = parente[v].from){
                parente[v].addFlow(bottlenec);
            }

            flow += bottlenec;
        }

        return flow;
    }

    private boolean bfs(FlowNetwork net, Edge[] parent, int source, int sink) {
        boolean[] visited = new boolean[net.size()];
        Queue<Integer> q = new ArrayDeque<>();
        q.add(source);
        visited[source] = true;

        while (!q.isEmpty()) {
            int u = q.poll();

            for (Edge e : net.getEdges(u)) {
                int v = e.to;

                if (!visited[v] && e.residualCapacity() > 0) {
                    visited[v] = true;
                    parent[v] = e;
                    q.add(v);

                    if (v == sink) return true;
                }
            }
        }
        return false;
    }

}
