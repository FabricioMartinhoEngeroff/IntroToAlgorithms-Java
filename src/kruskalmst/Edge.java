package kruskalmst;

class Edge implements Comparable<Edge> {
    String source, target;
    int weight;

    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }


    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }

    @Override
    public String toString() {
        return source + " - " + target + " (" + weight + ")";
    }
}
