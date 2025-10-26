package dijkstra;

import java.util.Arrays;


public class Main {
    public static void main(String[] args) {

        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");
        Node F = new Node("F");

        A.addNeighbor(B, 2);
        A.addNeighbor(C, 4);
        B.addNeighbor(C, 3);
        B.addNeighbor(D, 2);
        C.addNeighbor(E, 2);
        D.addNeighbor(E, 2);
        E.addNeighbor(F, 2);

        Service service = new Service();
        service.calculatePath(A);
        service.printPath(Arrays.asList(A, B, C, D, E, F));

    }
}