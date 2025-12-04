package MaxFlowAndMatching;

public class Edge {
    public int from;
    public int to;
    public int capacity;
    public int flow;
    public Edge reverse;

    public Edge(int from, int to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }

    public int residualCapacity() {
        return capacity - flow;
    }

    public void addFlow(int value) {
        this.flow += value;
        this.reverse.flow -= value;
    }

    @Override
    public String toString() {
        return String.format("%d -> %d | flow=%d cap=%d",
                from, to, flow, capacity);
    }
}
