package dijkstraAlgorithm;

import java.util.*;

public class Node implements Comparable<Node> {

    private String name;
    private Integer distance = Integer.MAX_VALUE;
    private List<Node> ShortesPath = new ArrayList<>();
    private Map<Node, Integer> neighbors = new HashMap<>();

    public void addNeighbor(Node node, Integer weight){
        neighbors.put(node, weight);
    }

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getDistance() {
        return distance;
    }

    public List<Node> getShortesPath() {
        return ShortesPath;
    }

    public Map<Node, Integer> getNeighbors() {
        return neighbors;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public void setShortesPath(List<Node> shortesPath) {
        ShortesPath = shortesPath;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(name, node.name);
    }
    @Override public int hashCode() { return Objects.hash(name); }

    @Override
    public int compareTo(Node o) {
        return this.distance.compareTo(o.distance);
    }
}
