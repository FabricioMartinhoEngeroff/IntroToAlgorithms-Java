package approximation_algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Algoritmo APPROX-TSP-TOUR — 2-aproximação para TSP com Triangle Inequality (CLRS 35.2).
 *
 * Pré-condição: o grafo satisfaz a desigualdade triangular:
 *   dist(u, w) <= dist(u, v) + dist(v, w) para quaisquer u, v, w
 *
 * Estratégia:
 *   1. Calcula a MST a partir do vértice 0 (usando Prim)
 *   2. Percorre a MST em pré-ordem (DFS)
 *   3. Os vértices nessa ordem formam o tour (ciclo hamiltoniano)
 *
 * Garantia: custo_tour <= 2 * custo_ótimo
 *
 * Intuição: MST <= ótimo; percurso completo da MST = 2*MST; atalhos (triangle ineq.)
 * nunca aumentam o custo → tour final <= 2 * MST <= 2 * ótimo.
 *
 * Contexto RouteOptix: caminhão visita todas as cidades exatamente uma vez e volta à base.
 */
public class TSPSolver {

    /**
     * Executa APPROX-TSP-TOUR e imprime cada etapa.
     *
     * @param dist   matriz de distâncias completa (satisfaz triangle inequality)
     * @param labels nomes das cidades
     */
    public void solve(int[][] dist, String[] labels) {
        int n = labels.length;

        System.out.println("\n  ── APPROX-TSP-TOUR (CLRS 35.2) ──");
        System.out.println("  Contexto: RouteOptix quer a menor rota de entrega passando por todas as cidades.");
        System.out.println("  Requisito: triangle inequality — ir direto nunca custa mais que passar por um intermediário.\n");

        // ── Passo 1: MST com algoritmo de Prim partindo do vértice 0 ─────────
        System.out.println("  [1] Calculando MST (Prim a partir de '" + labels[0] + "')...");
        int[] parent = prim(dist, n);

        // constrói lista de adjacência da MST para o DFS
        List<Integer>[] mstAdj = buildMstAdj(parent, n);

        int mstCost = 0;
        System.out.println("  Arestas da MST:");
        for (int v = 1; v < n; v++) {
            int p = parent[v];
            System.out.printf("    %-12s — %-12s  dist=%d%n", labels[p], labels[v], dist[p][v]);
            mstCost += dist[p][v];
        }
        System.out.println("  Custo total da MST: " + mstCost);

        // ── Passo 2: percurso em pré-ordem na MST ──────────────────────────
        System.out.println("\n  [2] Percurso em pré-ordem (DFS) na MST:");
        List<Integer> preorder = new ArrayList<>();
        boolean[] visited = new boolean[n];
        dfsPreorder(0, mstAdj, visited, preorder);
        System.out.print("  Ordem de visita: ");
        for (int v : preorder) System.out.print(labels[v] + " ");
        System.out.println();

        // ── Passo 3: forma o tour e calcula custo ──────────────────────────
        System.out.println("\n  [3] Tour hamiltoniano (retorna à cidade inicial):");
        int tourCost = 0;
        for (int i = 0; i < preorder.size(); i++) {
            int from = preorder.get(i);
            int to   = preorder.get((i + 1) % preorder.size());
            System.out.printf("    %-12s → %-12s  dist=%d%n", labels[from], labels[to], dist[from][to]);
            tourCost += dist[from][to];
        }

        System.out.println("\n  ── RESULTADO ──");
        System.out.println("  Custo do tour encontrado : " + tourCost);
        System.out.println("  Custo da MST             : " + mstCost);
        System.out.printf ("  Razão tour/MST           : %.2f (esperado <= 2.0)%n", (double) tourCost / mstCost);
        System.out.println("  Garantia: custo_tour <= 2 x ótimo  (2-aproximação)");
    }

    // ── Prim — retorna array parent[] onde parent[v] = pai de v na MST ──────

    private int[] prim(int[][] dist, int n) {
        int[] key    = new int[n];
        int[] parent = new int[n];
        boolean[] inMST = new boolean[n];

        Arrays.fill(key, Integer.MAX_VALUE);
        key[0]    = 0;
        parent[0] = -1;

        for (int iter = 0; iter < n - 1; iter++) {
            // encontra o vértice com menor chave ainda fora da MST
            int u = -1;
            for (int v = 0; v < n; v++) {
                if (!inMST[v] && (u == -1 || key[v] < key[u])) u = v;
            }
            inMST[u] = true;

            // relaxa os vizinhos de u
            for (int v = 0; v < n; v++) {
                if (!inMST[v] && dist[u][v] < key[v]) {
                    key[v]    = dist[u][v];
                    parent[v] = u;
                }
            }
        }
        return parent;
    }

    // ── Constrói lista de adjacência da MST a partir do array parent ─────────

    @SuppressWarnings("unchecked")
    private List<Integer>[] buildMstAdj(int[] parent, int n) {
        List<Integer>[] adj = new List[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int v = 1; v < n; v++) {
            int p = parent[v];
            adj[p].add(v);
            adj[v].add(p);
        }
        return adj;
    }

    // ── DFS em pré-ordem — visita o vértice antes de explorar os filhos ──────

    private void dfsPreorder(int u, List<Integer>[] adj, boolean[] visited, List<Integer> order) {
        visited[u] = true;
        order.add(u);
        for (int neighbor : adj[u]) {
            if (!visited[neighbor]) dfsPreorder(neighbor, adj, visited, order);
        }
    }
}
