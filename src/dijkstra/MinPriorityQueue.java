package dijkstra;

public interface MinPriorityQueue <T> {

    void insert(T value);

    T extractMin();

    boolean isEmpty();

}
