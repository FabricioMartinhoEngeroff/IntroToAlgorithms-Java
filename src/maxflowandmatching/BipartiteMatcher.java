package maxflowandmatching;

import java.util.ArrayList;
import java.util.List;

public class BipartiteMatcher {

    private int nLeft;
    private int nRight;

    public BipartiteMatcher(int nLeft, int nRight) {
        this.nLeft = nLeft;
        this.nRight = nRight;
    }

    public List<int[]> maximumMatching(List<int[]> edgesLR) {

        int total = 2 + nLeft + nRight;
        int source = 0;
        int sink = total - 1;

        FlowNetwork net = new FlowNetwork(total);

        int offsetL = 1;
        int offsetR = 1 + nLeft;

        // s -> L
        for (int i = 0; i < nLeft; i++) {
            net.addEdge(source, offsetL + i, 1);
        }

        // L -> R
        for (int[] e : edgesLR) {
            int u = e[0];
            int v = e[1];
            net.addEdge(offsetL + u, offsetR + v, 1);
        }

        // R -> t
        for (int i = 0; i < nRight; i++) {
            net.addEdge(offsetR + i, sink, 1);
        }

        EdmondsKarp ek = new EdmondsKarp();
        ek.maxFlow(net, source, sink);

        List<int[]> match = new ArrayList<>();

        for (int u = 0; u < nLeft; u++) {
            for (Edge edge : net.getEdges(offsetL + u)) {
                if (edge.to >= offsetR && edge.to < offsetR + nRight && edge.flow == 1) {
                    match.add(new int[]{u, edge.to - offsetR});
                }
            }
        }

        return match;
    }
}

   
