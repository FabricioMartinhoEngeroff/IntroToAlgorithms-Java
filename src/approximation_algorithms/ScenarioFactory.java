package approximation_algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Fábrica de instâncias de demonstração para os algoritmos de aproximação.
 *
 * Todos os cenários usam o contexto da empresa RouteOptix, mapeando problemas
 * clássicos do CLRS para situações reais de logística e operações:
 *
 *   Vertex Cover          → depósitos que cobrem rotas de entrega
 *   TSP                   → menor rota de entrega passando por todas as cidades
 *   Set Cover             → equipes cobrindo todos os tipos de entrega
 *   MAX-3-CNF             → restrições de entrega satisfatíveis?
 *   Weighted Vertex Cover → depósitos com custo mínimo cobrindo todas as rotas
 *   Subset Sum            → carregar carga próxima do limite de peso do caminhão
 */
public class ScenarioFactory {

    // ── Vertex Cover (sem pesos) ───────────────────────────────────────────────

    /** Grafo de 6 depósitos com rotas entre eles. */
    public Graph buildVertexCoverGraph() {
        String[] labels = {"DepA", "DepB", "DepC", "DepD", "DepE", "DepF"};
        Graph g = new Graph(labels);
        g.addEdge(0, 1); // DepA — DepB
        g.addEdge(0, 2); // DepA — DepC
        g.addEdge(1, 3); // DepB — DepD
        g.addEdge(2, 3); // DepC — DepD
        g.addEdge(3, 4); // DepD — DepE
        g.addEdge(4, 5); // DepE — DepF
        return g;
    }

    // ── TSP ───────────────────────────────────────────────────────────────────

    /**
     * Grafo completo de 5 cidades com distâncias que satisfazem triangle inequality.
     * Retorna Object[]{int[][] dist, String[] labels}.
     */
    public Object[] buildTSPScenario() {
        String[] cities = {"Base", "SaoPaulo", "Rio", "Campinas", "Santos"};
        int[][] dist = {
            //     Base  SP   Rio  Camp  Santos
            /*Base*/   {  0,  10,  15,   8,   12 },
            /*SP*/     { 10,   0,  20,   6,   14 },
            /*Rio*/    { 15,  20,   0,  18,   10 },
            /*Campinas*/{  8,   6,  18,   0,   16 },
            /*Santos*/ { 12,  14,  10,  16,    0 },
        };
        return new Object[]{dist, cities};
    }

    // ── Set Cover ─────────────────────────────────────────────────────────────

    /**
     * Universo de 6 tipos de entrega e 4 equipes especializadas.
     * Retorna Object[]{String[] universe, List<Set<String>> subsets, String[] names}.
     */
    public Object[] buildSetCoverScenario() {
        String[] universe = {"Express", "Refrigerado", "Pesado", "Fragil", "Urgente", "Rural"};
        String[] names    = {"Equipe-Alpha", "Equipe-Beta", "Equipe-Gamma", "Equipe-Delta"};

        List<Set<String>> subsets = new ArrayList<>();
        subsets.add(new HashSet<>(Arrays.asList("Express", "Urgente", "Fragil")));
        subsets.add(new HashSet<>(Arrays.asList("Refrigerado", "Pesado", "Express")));
        subsets.add(new HashSet<>(Arrays.asList("Rural", "Pesado", "Urgente")));
        subsets.add(new HashSet<>(Arrays.asList("Fragil", "Rural", "Refrigerado")));

        return new Object[]{universe, subsets, names};
    }

    // ── MAX-3-CNF ─────────────────────────────────────────────────────────────

    /**
     * Instância com 4 variáveis de entrega e 8 cláusulas de restrição.
     * Literais: positivo j = variável j-1 é true; negativo -j = variável j-1 é false.
     * Retorna Object[]{String[] variables, int[][] clauses}.
     */
    public Object[] buildMax3CnfScenario() {
        // variáveis: representam condições para uma entrega ser realizada
        String[] variables = {"Motorista", "Veiculo", "Carga", "Rota"};

        // restrições: cada cláusula = 3 condições em OR
        // ex: [1, -2, 3] = Motorista OR (NOT Veiculo) OR Carga
        int[][] clauses = {
            { 1, -2,  3},  // Motorista v !Veiculo v Carga
            {-1,  2,  4},  // !Motorista v Veiculo v Rota
            { 1,  2, -3},  // Motorista v Veiculo v !Carga
            {-1, -2,  4},  // !Motorista v !Veiculo v Rota
            { 2,  3, -4},  // Veiculo v Carga v !Rota
            {-1,  3,  4},  // !Motorista v Carga v Rota
            { 1, -3, -4},  // Motorista v !Carga v !Rota
            {-2, -3,  4},  // !Veiculo v !Carga v Rota
        };

        return new Object[]{variables, clauses};
    }

    // ── Weighted Vertex Cover ─────────────────────────────────────────────────

    /** Grafo de 5 depósitos com custos de operação diferentes. */
    public Graph buildWeightedVertexCoverGraph() {
        String[] labels  = {"DepA", "DepB", "DepC", "DepD", "DepE"};
        int[]    weights = {3, 5, 2, 7, 4};  // custo de operar cada depósito
        Graph g = new Graph(labels, weights);
        g.addEdge(0, 1); // DepA — DepB
        g.addEdge(0, 2); // DepA — DepC
        g.addEdge(1, 3); // DepB — DepD
        g.addEdge(2, 3); // DepC — DepD
        g.addEdge(3, 4); // DepD — DepE
        return g;
    }

    // ── Subset Sum ────────────────────────────────────────────────────────────

    /**
     * Conjunto de caixas de carga com peso alvo do caminhão.
     * Retorna Object[]{int[] items, String[] names, int target}.
     */
    public Object[] buildSubsetSumScenario() {
        String[] names  = {"Caixa-A", "Caixa-B", "Caixa-C", "Caixa-D", "Caixa-E"};
        int[]    items  = {10, 22, 14, 7, 18};
        int      target = 40;
        return new Object[]{items, names, target};
    }
}
