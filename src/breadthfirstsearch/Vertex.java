package breadthfirstsearch;

public class Vertex {

    private final String name;
    private  String color;
    private int distance;
    private Vertex parent;

    public Vertex(String name) {
        this.name = name;
        this.color = "WHITE";
        this.distance = Integer.MAX_VALUE;
        this.parent = null;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return name + " (dist=" + (distance == Integer.MAX_VALUE ? "∞" : distance) + ")";
    }

}
