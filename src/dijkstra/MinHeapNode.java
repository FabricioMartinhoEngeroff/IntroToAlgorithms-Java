package dijkstra;

public class MinHeapNode implements MinPriorityQueue<Node>{

    private Node[] heap;
    private int size;

    public MinHeapNode(int capacity) {
        heap = new Node[capacity];
        size = 0;
    }

    @Override
    public void insert(Node value){
        heap[size] = value;
        siftUp(size);
        size ++;
    }

    @Override
    public Node extractMin() {
        if (size == 0) {
            return null;
        }

        Node min = heap[0];

        if (size == 1) {
            heap[0] = null;
            size--;
            return min;
        }

        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        siftDown(0);

        return min;
    }

    private void siftUp(int i){
        while (i > 0){
            int parent = (i -1) / 2;

            if (heap[parent].getDistance() <= heap[i].getDistance())
                break;

            swap(i, parent);
            i = parent;
        }
    }

    private void siftDown(int i){
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < size && heap[left].getDistance() < heap[smallest].getDistance())
                smallest = left;

            if (right < size && heap[right].getDistance() < heap[smallest].getDistance())
               smallest = right;

            if (smallest == i) break;

            swap(i, smallest);
            i = smallest;

            }
    }

    private void swap(int a, int b){
        Node tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
