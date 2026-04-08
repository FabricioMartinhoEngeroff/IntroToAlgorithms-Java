package approximation_algorithms;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Algoritmo para Weighted Vertex Cover via LP Rounding — 2-aproximação (CLRS seção 35.4).
 *
 * Formulação do LP (relaxação inteira):
 *   Minimizar:   sum( w[v] * x[v] )
 *   Sujeito a:   x[u] + x[v] >= 1  para toda aresta (u, v)
 *                0 <= x[v] <= 1    para todo vértice v
 *
 * Estratégia (LP Rounding):
 *   1. Resolve a versão LP relaxada (x[v] pode ser fracionário)
 *   2. Simula o LP via matching maximal: vértices matched recebem x = 0.5
 *   3. Arredonda: inclui vértice v se x[v] >= 0.5
 *
 * Garantia: custo(C) <= 2 * custo(C*)
 *
 * Intuição: o custo do LP ótimo é <= custo do IP ótimo (C*); ao arredondar
 * por 0.5, o custo só pode dobrar → 2 * LP_ótimo <= 2 * C*.
 *
 * Contexto RouteOptix: escolher depósitos (com custo) que cubram todas as rotas,
 * minimizando o custo total de operação.
 */
public class WeightedVertexCoverSolver {

    /**
     * Executa Weighted Vertex Cover via LP Rounding e imprime cada etapa.
     *
     * @param graph grafo com pesos nos vértices (custo de cada depósito)
     */
    public void solve(Graph graph) {
        int n = graph.getN();

        System.out.println("\n  ── WEIGHTED VERTEX COVER — LP ROUNDING (CLRS 35.4) ──");
        System.out.println("  Contexto: RouteOptix quer o menor custo de depósitos que cubra todas as rotas.");
        System.out.println("  Abordagem: relaxa o problema para LP contínuo, depois arredonda a solução.\n");

        // ── Passo 1: mostra os pesos (custos) dos vértices ───────────────────
        System.out.println("  [1] Custo de operação de cada depósito:");
        for (int i = 0; i < n; i++) {
            System.out.printf("    %-8s custo=%d%n", graph.getLabel(i), graph.getWeight(i));
        }

        // ── Passo 2: LP relaxado — simula via matching maximal ───────────────
        // Para cada aresta (u,v) não coberta: define x[u] = x[v] = 0.5
        // (equivale a "dividir o peso igualmente entre os dois vértices")
        System.out.println("\n  [2] Simulando LP relaxado (matching maximal):");
        System.out.println("      Para aresta (u,v) não coberta: x[u] = x[v] = 0.5");

        double[] x       = new double[n];
        boolean[] matched = new boolean[n];
        List<int[]> edges = graph.getEdges();

        for (int[] e : edges) {
            int u = e[0], v = e[1];
            if (!matched[u] && !matched[v]) {
                // ambos os vértices entram no matching
                x[u]       = 0.5;
                x[v]       = 0.5;
                matched[u] = true;
                matched[v] = true;
                System.out.printf("    Aresta (%s, %s) → x[%s]=0.5, x[%s]=0.5%n",
                        graph.getLabel(u), graph.getLabel(v),
                        graph.getLabel(u), graph.getLabel(v));
            }
        }

        // ── Passo 3: arredondamento — inclui vértice se x[v] >= 0.5 ─────────
        System.out.println("\n  [3] Arredondamento: inclui depósito se x[v] >= 0.5");
        Set<Integer> cover = new LinkedHashSet<>();
        int coverCost = 0;

        for (int i = 0; i < n; i++) {
            if (x[i] >= 0.5) {
                cover.add(i);
                coverCost += graph.getWeight(i);
                System.out.printf("    %-8s x=%.1f  custo=%d  → INCLUÍDO%n",
                        graph.getLabel(i), x[i], graph.getWeight(i));
            } else {
                System.out.printf("    %-8s x=%.1f          → ignorado%n",
                        graph.getLabel(i), x[i]);
            }
        }

        // ── Resultado ──────────────────────────────────────────────────────────
        System.out.println("\n  ── RESULTADO ──");
        System.out.print("  Depósitos na cobertura: { ");
        for (int v : cover) System.out.print(graph.getLabel(v) + " ");
        System.out.println("}");
        System.out.println("  Custo total da cobertura: " + coverCost);
        System.out.println("  Garantia: custo <= 2 x ótimo  (2-aproximação via LP Rounding)");
    }
}
