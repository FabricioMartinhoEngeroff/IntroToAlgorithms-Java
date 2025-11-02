package disjointset;

public class Node {

    int value;
    Node parent;
    int rank;

    public Node(int value) {
        this.value = value;
        this.parent = this;
        this.rank = 0;
    }
}
