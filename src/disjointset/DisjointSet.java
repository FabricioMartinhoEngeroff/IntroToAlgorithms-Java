package disjointset;

public class DisjointSet {

    public Node makeSet(int value){
        return new Node(value);
    }

    public Node findSet(Node node){
        if(node.parent != node){
            node.parent = findSet(node.parent);
        }
        return node.parent;
    }

    public void union(Node x, Node y){
        Node raizX = findSet(x);
        Node raizY = findSet(y);

        if(raizX == raizY)
            return;

        if (raizX.rank < raizY.rank) {
            raizX.parent = raizY;
        }
        else if (raizX.rank > raizY.rank) {
            raizY.parent = raizX;
        } else {
                    raizY.parent = raizX;
                    raizX.rank += 1;
        }
    }
}
