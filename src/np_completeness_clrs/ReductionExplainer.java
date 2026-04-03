package np_completeness_clrs;

/**
 * Explica as reduções entre problemas NP-Completos, seguindo a cadeia do CLRS.
 *
 * Cadeia de reduções do Capítulo 34:
 *
 *   CIRCUIT-SAT → SAT → 3-CNF-SAT → CLIQUE → VERTEX-COVER → HAM-CYCLE → TSP
 *                                          ↘ SUBSET-SUM
 *
 * Cada redução L1 ≤p L2 significa:
 *   "qualquer instância de L1 pode ser transformada em instância de L2
 *    em tempo polinomial, de forma que a resposta se preserve"
 */
public class ReductionExplainer {

    /**
     * Exibe a explicação da redução que prova o NP-Completeness do problema dado.
     *
     * @param type tipo do problema cujo NP-Completeness será explicado
     */
    public void explain(ProblemType type) {
        printHeader("CADEIA DE REDUÇÕES — " + type.getDisplayName());
        printChainOverview();

        switch (type) {
            case TSP          -> explainTSP();
            case SUBSET_SUM   -> explainSubsetSum();
            case VERTEX_COVER -> explainVertexCover();
            case SAT          -> explainSAT();
            case SCHEDULING   -> explainScheduling();
        }

        printReductionConclusion(type);
    }

    // ── visão geral da cadeia ────────────────────────────────────────────────

    private void printChainOverview() {
        System.out.println("  Cadeia completa do CLRS:");
        System.out.println();
        System.out.println("  CIRCUIT-SAT");
        System.out.println("      │  ≤p");
        System.out.println("      ▼");
        System.out.println("     SAT");
        System.out.println("      │  ≤p");
        System.out.println("      ▼");
        System.out.println("   3-CNF-SAT ──────────────────────────── ≤p ──▶ SUBSET-SUM");
        System.out.println("      │  ≤p");
        System.out.println("      ▼");
        System.out.println("    CLIQUE");
        System.out.println("      │  ≤p");
        System.out.println("      ▼");
        System.out.println("  VERTEX-COVER");
        System.out.println("      │  ≤p");
        System.out.println("      ▼");
        System.out.println("  HAM-CYCLE");
        System.out.println("      │  ≤p");
        System.out.println("      ▼");
        System.out.println("     TSP");
        System.out.println();
    }

    // ── TSP ──────────────────────────────────────────────────────────────────

    private void explainTSP() {
        printSubHeader("Redução: HAM-CYCLE ≤p TSP");

        System.out.println("  IDEIA CENTRAL:");
        System.out.println("  HAM-CYCLE pergunta se existe um ciclo passando por todos os");
        System.out.println("  vértices exatamente uma vez. TSP pergunta se existe um ciclo");
        System.out.println("  de custo ≤ k. A redução é elegante e usa PESOS como ferramenta.");
        System.out.println();

        System.out.println("  CONSTRUÇÃO (estratégia: recompensas e penalidades):");
        System.out.println("  Dado grafo G = (V, E) para HAM-CYCLE:");
        System.out.println("    1. Criar grafo completo G' com os mesmos vértices");
        System.out.println("    2. Aresta (u,v) em G  → custo 0 em G'   (aresta real)");
        System.out.println("    3. Aresta (u,v) fora G → custo 1 em G'   (aresta falsa)");
        System.out.println("    4. Definir k = 0");
        System.out.println();

        System.out.println("  EQUIVALÊNCIA:");
        System.out.println("  G tem ciclo hamiltoniano");
        System.out.println("    ⟺  G' tem tour de custo 0");
        System.out.println("    ⟺  TSP(G', k=0) = SIM");
        System.out.println();

        System.out.println("  POR QUE FUNCIONA:");
        System.out.println("  Um tour de custo 0 só pode usar arestas com custo 0.");
        System.out.println("  Essas arestas são exatamente as arestas de G.");
        System.out.println("  Logo, o tour visita todos os vértices usando só arestas reais");
        System.out.println("  — ou seja, é um ciclo hamiltoniano em G.");
        System.out.println();

        System.out.println("  EXEMPLO CONCRETO (FastLog):");
        System.out.println("  G: D—A, A—B, B—G, G—Δ, Δ—D (grafo anel)");
        System.out.println("  G' completo com custo 0 nas 5 arestas do anel,");
        System.out.println("         custo 1 nas demais (D-B, D-G, A-G, A-Δ, B-Δ)");
        System.out.println("  TSP(k=0): D→A→B→G→Δ→D  custo = 0+0+0+0+0 = 0  ✓");
        System.out.println("  → HAM-CYCLE tem solução em G");
    }

    // ── SUBSET SUM ────────────────────────────────────────────────────────────

    private void explainSubsetSum() {
        printSubHeader("Redução: 3-CNF-SAT ≤p SUBSET-SUM");

        System.out.println("  IDEIA CENTRAL:");
        System.out.println("  Codificar variáveis booleanas e cláusulas como NÚMEROS.");
        System.out.println("  Escolher um subconjunto que soma ao alvo = escolher valores V/F.");
        System.out.println("  A chave: construir os números sem 'carry' (vai-um).");
        System.out.println();

        System.out.println("  CONSTRUÇÃO:");
        System.out.println("  Para cada variável xi: criar dois números vi e vi'");
        System.out.println("    vi  → representa xi = true");
        System.out.println("    vi' → representa xi = false");
        System.out.println("  Para cada cláusula Cj: criar dois números de ajuste sj e sj'");
        System.out.println("  Cada número tem múltiplos dígitos:");
        System.out.println("    [colunas das variáveis | colunas das cláusulas]");
        System.out.println();

        System.out.println("  RESTRIÇÃO CRÍTICA (sem carry):");
        System.out.println("  Os dígitos são projetados para que a soma nunca ultrapasse 4");
        System.out.println("  em nenhuma coluna — evitando propagação de carry.");
        System.out.println("  Isso garante que cada coluna funcione de forma independente.");
        System.out.println();

        System.out.println("  EQUIVALÊNCIA:");
        System.out.println("  Fórmula 3-CNF é SATISFATÍVEL");
        System.out.println("    ⟺  Existe subconjunto de números que soma ao alvo t");
        System.out.println();

        System.out.println("  EXEMPLO (FastLog — regras simplificadas):");
        System.out.println("  Fórmula: (x1 ∨ x2) ∧ (¬x1 ∨ x2) codificada como:");
        System.out.println("  Escolher x1=T → selecionar v1");
        System.out.println("  Escolher x2=T → selecionar v2");
        System.out.println("  Somar: v1 + v2 + ajustes = alvo t  → SIM → SAT satisfatível");
    }

    // ── VERTEX COVER ─────────────────────────────────────────────────────────

    private void explainVertexCover() {
        printSubHeader("Redução: CLIQUE ≤p VERTEX-COVER");

        System.out.println("  IDEIA CENTRAL:");
        System.out.println("  Usar o GRAFO COMPLEMENTAR.");
        System.out.println("  Clique em G  ↔  Vertex Cover no complementar de G.");
        System.out.println();

        System.out.println("  CONSTRUÇÃO:");
        System.out.println("  Dado grafo G = (V, E) e parâmetro k:");
        System.out.println("    1. Criar G' = complementar de G");
        System.out.println("       (arestas de G' = arestas que NÃO estão em G)");
        System.out.println("    2. Parâmetro k' = |V| - k");
        System.out.println();

        System.out.println("  EQUIVALÊNCIA:");
        System.out.println("  G tem CLIQUE de tamanho k");
        System.out.println("    ⟺  G' (complementar) tem VERTEX COVER de tamanho |V| - k");
        System.out.println();

        System.out.println("  POR QUE FUNCIONA:");
        System.out.println("  Se S é uma clique em G (k vértices, todos conectados),");
        System.out.println("  então V - S (os outros |V|-k vértices) cobre todas as");
        System.out.println("  arestas de G' (porque as arestas de G' conectam vértices");
        System.out.println("  fora da clique).");
        System.out.println();

        System.out.println("  EXEMPLO CONCRETO (FastLog):");
        System.out.println("  G: cidades A,B,C,D,E com arestas A-B, B-C, C-D, D-E, A-E");
        System.out.println("  Clique {A,B} no G  →  Vertex Cover {C,D,E} em G'");
        System.out.println("  |V|-k = 5-2 = 3  →  VC de tamanho 3 em G'");
    }

    // ── SAT ───────────────────────────────────────────────────────────────────

    private void explainSAT() {
        printSubHeader("Redução: CIRCUIT-SAT ≤p SAT");

        System.out.println("  IDEIA CENTRAL:");
        System.out.println("  Transformar um CIRCUITO BOOLEANO em uma FÓRMULA BOOLEANA.");
        System.out.println("  Cada fio do circuito vira uma variável.");
        System.out.println("  Cada porta lógica vira uma cláusula.");
        System.out.println();

        System.out.println("  CONSTRUÇÃO:");
        System.out.println("  Para cada fio wi no circuito: criar variável wi na fórmula");
        System.out.println("  Para cada porta AND(wi, wj) → wk: adicionar cláusula");
        System.out.println("    wk ↔ (wi ∧ wj)  →  equivale a 3 cláusulas em CNF");
        System.out.println("  Para cada porta OR(wi, wj) → wk: adicionar cláusula");
        System.out.println("    wk ↔ (wi ∨ wj)");
        System.out.println("  Para cada porta NOT(wi) → wk: adicionar wk ↔ ¬wi");
        System.out.println("  Adicionar cláusula que força saída = 1");
        System.out.println();

        System.out.println("  TRUQUE IMPORTANTE (sem explosão):");
        System.out.println("  Criar UMA variável por fio (não copiar sub-expressões).");
        System.out.println("  Isso mantém o tamanho da fórmula polinomial no tamanho do circuito.");
        System.out.println();

        System.out.println("  EQUIVALÊNCIA:");
        System.out.println("  Circuito é satisfatível (saída = 1 para alguma entrada)");
        System.out.println("    ⟺  A fórmula construída é satisfatível");
        System.out.println();

        System.out.println("  EXEMPLO (FastLog — sistema de alarme):");
        System.out.println("  Circuito: AND(sensor1, sensor2) OR NOT(manutencao) → alarme=1");
        System.out.println("  Variáveis: x1=sensor1, x2=sensor2, x3=manutencao");
        System.out.println("  Fórmula: (x1 ∧ x2) ∨ ¬x3 = 1");
        System.out.println("  SAT: existe x1,x2,x3 que satisfaz? SIM (ex: x3=false)");
    }

    // ── SCHEDULING ────────────────────────────────────────────────────────────

    private void explainScheduling() {
        printSubHeader("Redução: 3-CNF-SAT ≤p SCHEDULING");

        System.out.println("  IDEIA CENTRAL:");
        System.out.println("  Mapear variáveis booleanas → funcionários.");
        System.out.println("  Mapear cláusulas → grupos de turnos.");
        System.out.println("  Satisfazer uma cláusula = cobrir o turno correspondente.");
        System.out.println();

        System.out.println("  CONSTRUÇÃO:");
        System.out.println("  Para cada variável xi: criar 2 funcionários");
        System.out.println("    fi_true  → representa xi = true");
        System.out.println("    fi_false → representa xi = false");
        System.out.println("  Para cada cláusula Cj = (la ∨ lb ∨ lc): criar 1 turno tj");
        System.out.println("    Funcionários que podem trabalhar em tj:");
        System.out.println("    → fi_true  se la = xi (literal positivo)");
        System.out.println("    → fi_false se la = ¬xi (literal negado)");
        System.out.println("  k = número de variáveis (escolhemos true OU false p/ cada)");
        System.out.println();

        System.out.println("  EQUIVALÊNCIA:");
        System.out.println("  Fórmula 3-CNF é SATISFATÍVEL");
        System.out.println("    ⟺  Existe escala com ≤ k funcionários que cobre todos os turnos");
        System.out.println();

        System.out.println("  EXEMPLO (FastLog):");
        System.out.println("  Fórmula: (R ∨ N) ∧ (¬R ∨ B) ∧ (¬N ∨ ¬B)");
        System.out.println("  Variáveis: R, N, B → 3 pares de funcionários → k=3");
        System.out.println("  Turnos: T1=(R∨N), T2=(¬R∨B), T3=(¬N∨¬B)");
        System.out.println("  Solução: R=T, N=F, B=T →");
        System.out.println("    T1 coberto por fR_true (R=T),");
        System.out.println("    T2 coberto por fB_true (B=T),");
        System.out.println("    T3 coberto por fN_false (N=F)  → escala válida");
    }

    // ── conclusão ─────────────────────────────────────────────────────────────

    private void printReductionConclusion(ProblemType type) {
        System.out.println();
        System.out.println("  ─────────────────────────────────────────────────────────");
        System.out.println("  CONCLUSÃO DE NP-COMPLETENESS para " + type.getDisplayName());
        System.out.println();
        System.out.println("  Passo 1 — NP membership:");
        System.out.println("    Dado certificado, verificação é polinomial  ✓");
        System.out.println("  Passo 2 — NP-Hard:");
        System.out.println("    " + type.getReductionNotation() + "  ✓");
        System.out.println("    A redução é polinomial  ✓");
        System.out.println("    Equivalência (sim⟺sim) demonstrada  ✓");
        System.out.println();
        System.out.println("  Portanto: " + type.getDisplayName() + " é NP-COMPLETO.");
        System.out.println("  Se existisse algoritmo polinomial para ele,");
        System.out.println("  então " + type.getReducesFrom()
                + " também seria polinomial,");
        System.out.println("  implicando P = NP.");
        System.out.println();
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private void printHeader(String title) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.printf ("║  %-56s║%n", title);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private void printSubHeader(String title) {
        System.out.println("  ── " + title + " " + "─".repeat(Math.max(0, 48 - title.length())));
        System.out.println();
    }
}
