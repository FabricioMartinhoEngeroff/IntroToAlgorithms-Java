package breadthfirstsearch;

public class BreadthFirstSearchDemo {

    public static void main(String[] args) {

        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");

        Graph graph = new Graph();
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);

        graph.addEdge(a, b);
        graph.addEdge(a, c);
        graph.addEdge(b, d);
        graph.addEdge(c, d);
        graph.addEdge(d, e);

        graph.printGraph();

        BreadthFirstSearch bfs = new BreadthFirstSearch();
        bfs.bfs(graph, a);

        System.out.print("\nCaminho de A até E: ");
        bfs.printPath(a, e);
        System.out.println();
    }

}
