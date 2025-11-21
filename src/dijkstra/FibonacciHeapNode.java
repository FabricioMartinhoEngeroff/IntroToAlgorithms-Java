package dijkstra;

public class FibonacciHeapNode {

    Node value;
    int key;

    FibonacciHeapNode(Node value) {
        this.value = value;
        this.key = value.getDistance();
    }
}
