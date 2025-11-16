package kruskalmst;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class DisjointSet {
    private final Map<String, String> parent = new HashMap<>();

    // Each vertex starts as its own parent
    public void makeSet(Collection<String> vertices) {
        for (String v : vertices) {
            parent.put(v, v);
        }
    }

    // Finds the representative (root) of the set
    public String find(String v) {
        if (!parent.get(v).equals(v)) {
            parent.put(v, find(parent.get(v))); // path compression
        }
        return parent.get(v);
    }

    // Unites two different sets
    public void union(String u, String v) {
        String rootU = find(u);
        String rootV = find(v);
        if (!rootU.equals(rootV)) {
            parent.put(rootU, rootV);
        }
    }
}

