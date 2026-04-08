package approximation_algorithms;

import java.util.Random;

/**
 * Algoritmo aleatorizado para MAX-3-CNF — 7/8-aproximação esperada (CLRS seção 35.4).
 *
 * Estratégia:
 *   Atribui 1 (true) ou 0 (false) a cada variável com probabilidade 1/2.
 *   Cada cláusula com 3 literais distintos é satisfeita com Pr = 7/8.
 *
 * Análise probabilística:
 *   Uma cláusula de 3 literais só NÃO é satisfeita se todos os 3 literais
 *   são falsos → Pr[insatisfeita] = (1/2)^3 = 1/8.
 *   Logo, Pr[satisfeita] = 1 - 1/8 = 7/8.
 *   Por linearidade da esperança: E[cláusulas satisfeitas] = 7/8 * total.
 *
 * Garantia: E[satisfeitas] >= 7/8 * OPT  (algoritmo 8/7-aproximação esperada)
 *
 * Formato dos literais:
 *   Literal positivo  j (j > 0) = variável j-1 deve ser true
 *   Literal negativo -j (j > 0) = variável j-1 deve ser false (negada)
 *
 * Contexto RouteOptix: verificar se restrições de entrega podem ser satisfeitas.
 */
public class Max3CnfSolver {

    private final Random rng;

    /** @param seed semente para reprodutibilidade dos resultados */
    public Max3CnfSolver(long seed) {
        this.rng = new Random(seed);
    }

    /**
     * Executa o algoritmo aleatorizado para MAX-3-CNF e imprime o resultado.
     *
     * @param variables nomes das variáveis booleanas
     * @param clauses   cláusulas — cada uma é int[3] com literais 1-based (negativo = negado)
     */
    public void solve(String[] variables, int[][] clauses) {
        System.out.println("\n  ── MAX-3-CNF ALEATORIZADO (CLRS 35.4) ──");
        System.out.println("  Contexto: RouteOptix verifica se as restrições de entrega são satisfatíveis.");
        System.out.println("  Estratégia: atribuição aleatória — cada variável recebe true/false com prob. 1/2.\n");

        // ── Passo 1: atribuição aleatória das variáveis ──────────────────────
        boolean[] assignment = new boolean[variables.length];
        System.out.println("  [1] Atribuição aleatória:");
        for (int i = 0; i < variables.length; i++) {
            assignment[i] = rng.nextBoolean();
            System.out.printf("    %-12s = %s%n", variables[i], assignment[i] ? "true" : "false");
        }

        // ── Passo 2: avaliação de cada cláusula ──────────────────────────────
        System.out.println("\n  [2] Avaliando cláusulas (OR de 3 literais):");
        int satisfied = 0;

        for (int c = 0; c < clauses.length; c++) {
            int[] clause = clauses[c];
            boolean clauseResult = false;
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("    Cláusula %d: (", c + 1));

            for (int j = 0; j < 3; j++) {
                int     lit    = clause[j];
                boolean litVal = (lit > 0) ? assignment[lit - 1] : !assignment[-lit - 1];
                String  name   = (lit > 0) ? variables[lit - 1]  : "!" + variables[-lit - 1];
                sb.append(name);
                if (j < 2) sb.append(" v ");
                clauseResult = clauseResult || litVal;
            }
            sb.append(") → ").append(clauseResult ? "SATISFEITA  [✓]" : "nao satisfeita [✗]");
            System.out.println(sb);
            if (clauseResult) satisfied++;
        }

        double pct = 100.0 * satisfied / clauses.length;

        // ── Resultado ──────────────────────────────────────────────────────────
        System.out.println("\n  ── RESULTADO ──");
        System.out.printf("  Satisfeitas nesta execucao: %d de %d  (%.1f%%)%n",
                satisfied, clauses.length, pct);
        System.out.println("  Esperado pela teoria      : 7/8 = 87.5% (media sobre muitas execucoes)");
        System.out.println("  Garantia: E[satisfeitas] >= 7/8 * total  (7/8-aproximacao esperada)");
        System.out.println("  Nota: uma unica execucao pode variar; a garantia vale em ESPERANCA.");
    }
}
