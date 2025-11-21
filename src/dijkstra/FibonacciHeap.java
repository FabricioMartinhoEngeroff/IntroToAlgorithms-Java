package dijkstra;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FibonacciHeap implements MinPriorityQueue<Node>{

    private List<FibonacciHeapNode> list = new ArrayList<>();

    @Override
    public void insert(Node value) {
        list.add(new FibonacciHeapNode(value));

    }

    @Override
    public Node extractMin() {
        FibonacciHeapNode min = list.stream()
                .min(Comparator.comparingInt(n -> n.key))
                .orElse(null);

        if (min == null) return null;

        list.remove(min);
        return min.value;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
