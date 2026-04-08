package approximation_algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Grafo não-direcionado com suporte a pesos nas arestas e nos vértices.
 *
 * Usado pelos algoritmos de Vertex Cover e Weighted Vertex Cover.
 * Cada vértice tem um rótulo (nome) e um peso opcional (custo).
 * Cada aresta tem um peso opcional (distância / custo).
 */
public class Graph {

    private final int          n;        // quantidade de vértices
    private final String[]     labels;   // nomes dos vértices
    private final int[]        weights;  // pesos dos vértices (usado no Weighted VC)
    private final List<int[]>[] adj;     // adjacência: cada entrada = {vizinho, peso_aresta}

    @SuppressWarnings("unchecked")
    public Graph(String[] labels, int[] vertexWeights) {
        this.n       = labels.length;
        this.labels  = labels;
        this.weights = (vertexWeights != null) ? vertexWeights : new int[n];
        this.adj     = new List[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
    }

    /** Construtor sem pesos nos vértices (todos valem 1). */
    public Graph(String[] labels) {
        this(labels, null);
    }

    /** Adiciona aresta não-direcionada com peso. */
    public void addEdge(int u, int v, int weight) {
        adj[u].add(new int[]{v, weight});
        adj[v].add(new int[]{u, weight});
    }

    /** Adiciona aresta não-direcionada sem peso (peso = 1). */
    public void addEdge(int u, int v) {
        addEdge(u, v, 1);
    }

    /**
     * Retorna todas as arestas do grafo, sem duplicatas.
     * Cada entrada é int[]{u, v, peso}, onde u < v.
     */
    public List<int[]> getEdges() {
        List<int[]> edges = new ArrayList<>();
        for (int u = 0; u < n; u++) {
            for (int[] e : adj[u]) {
                int v = e[0];
                if (u < v) edges.add(new int[]{u, v, e[1]});
            }
        }
        return edges;
    }

    public int         getN()              { return n; }
    public String      getLabel(int i)     { return labels[i]; }
    public int         getWeight(int i)    { return weights[i]; }
    public List<int[]> getAdj(int u)       { return adj[u]; }
    public String[]    getLabels()         { return labels; }
}