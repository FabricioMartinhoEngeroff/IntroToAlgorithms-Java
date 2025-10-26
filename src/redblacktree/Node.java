package redblacktree;

public class Node <T extends Comparable<T>>{
    T data;
    Node<T> left, right;
    boolean color;

    public Node(T data, boolean color){
        this.data = data;
        this.color = color; // true = RED, false = BLACK
    }
}
