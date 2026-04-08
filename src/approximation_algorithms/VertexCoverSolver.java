package approximation_algorithms;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Algoritmo APPROX-VERTEX-COVER — 2-aproximação (CLRS seção 35.1).
 *
 * Estratégia:
 *   Enquanto existirem arestas não cobertas:
 *     1. Escolhe qualquer aresta (u, v)
 *     2. Adiciona AMBOS os vértices à cobertura
 *     3. Remove todas as arestas incidentes a u ou v
 *
 * Garantia: |C| <= 2 * |C*|  (nunca mais que o dobro do ótimo)
 *
 * Intuição da prova: cada aresta escolhida exige pelo menos 1 vértice na solução
 * ótima, mas o algoritmo sempre pega 2 → fator 2 no pior caso.
 *
 * Contexto RouteOptix: quais depósitos (vértices) cobrem todas as rotas (arestas)?
 */
public class VertexCoverSolver {

    /**
     * Executa APPROX-VERTEX-COVER e imprime cada passo.
     *
     * @param graph grafo de entrada
     */
    public void solve(Graph graph) {
        System.out.println("\n  ── APPROX-VERTEX-COVER (CLRS 35.1) ──");
        System.out.println("  Contexto: RouteOptix quer cobrir todas as rotas com o menor número de depósitos.");
        System.out.println("  Problema: cada rota (aresta) precisa ter pelo menos um depósito (vértice) em suas pontas.\n");

        // copia das arestas para não modificar o grafo original
        List<int[]> remaining = new ArrayList<>(graph.getEdges());
        Set<Integer> cover    = new LinkedHashSet<>();
        boolean[] inCover     = new boolean[graph.getN()];

        System.out.println("  Grafo com " + graph.getN() + " depósitos e " + remaining.size() + " rotas.\n");

        int step = 1;
        while (!remaining.isEmpty()) {
            // pega a primeira aresta disponível (qualquer aresta serve)
            int[] edge = remaining.get(0);
            int u = edge[0], v = edge[1];

            // adiciona ambos os vértices à cobertura
            inCover[u] = inCover[v] = true;
            cover.add(u);
            cover.add(v);

            // remove todas as arestas incidentes a u ou v
            int before = remaining.size();
            remaining.removeIf(e -> e[0] == u || e[1] == u || e[0] == v || e[1] == v);
            int removed = before - remaining.size();

            System.out.printf("  Passo %d: rota (%s — %s) → inclui ambos na cobertura%n",
                    step++, graph.getLabel(u), graph.getLabel(v));
            System.out.printf("            cobriu %d rota(s) adicional(is), restam %d%n",
                    removed, remaining.size());
        }

        // calcula custo total (soma dos pesos dos vértices escolhidos)
        int totalCost = 0;
        for (int v : cover) totalCost += graph.getWeight(v);

        System.out.println("\n  ── RESULTADO ──");
        System.out.print("  Depósitos na cobertura: { ");
        for (int v : cover) System.out.print(graph.getLabel(v) + " ");
        System.out.println("}");
        System.out.println("  Total de depósitos: " + cover.size());
        System.out.println("  Garantia: tamanho <= 2 x ótimo  (2-aproximação)");
    }
}
