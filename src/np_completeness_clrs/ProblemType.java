package np_completeness_clrs;

/**
 * Enum com os problemas NP-Completos clássicos do CLRS, mapeados para cenários reais
 * de uma empresa de logística e entregas.
 *
 * Cadeia de reduções do livro:
 *   CIRCUIT-SAT → SAT → 3-CNF-SAT → CLIQUE → VERTEX-COVER → HAM-CYCLE → TSP
 *                                          ↘ SUBSET-SUM
 */
public enum ProblemType {

    TSP(
        1,
        "Rota do Entregador (TSP)",
        "Logística — Otimização de Rotas",
        ComplexityClass.NP_COMPLETE,
        "HAM-CYCLE",
        "HAM-CYCLE ≤p TSP"
    ),

    SUBSET_SUM(
        2,
        "Carga do Caminhão (Subset Sum)",
        "Logística — Distribuição de Carga",
        ComplexityClass.NP_COMPLETE,
        "3-CNF-SAT",
        "3-CNF-SAT ≤p SUBSET-SUM"
    ),

    VERTEX_COVER(
        3,
        "Cobertura de Rotas (Vertex Cover)",
        "Logística — Hubs de Distribuição",
        ComplexityClass.NP_COMPLETE,
        "CLIQUE",
        "CLIQUE ≤p VERTEX-COVER"
    ),

    SAT(
        4,
        "Regras de Serviço (SAT)",
        "TI — Configuração de Sistemas",
        ComplexityClass.NP_COMPLETE,
        "CIRCUIT-SAT",
        "CIRCUIT-SAT ≤p SAT"
    ),

    SCHEDULING(
        5,
        "Escala de Funcionários (Scheduling)",
        "RH — Alocação de Turnos",
        ComplexityClass.NP_COMPLETE,
        "3-CNF-SAT",
        "3-CNF-SAT ≤p SCHEDULING"
    );

    // ── campos ────────────────────────────────────────────────────────────────
    private final int               menuOption;
    private final String            displayName;
    private final String            domain;
    private final ComplexityClass   complexityClass;
    private final String            reducesFrom;
    private final String            reductionNotation;

    // ── construtor ────────────────────────────────────────────────────────────
    ProblemType(int menuOption, String displayName, String domain,
                ComplexityClass complexityClass, String reducesFrom, String reductionNotation) {
        this.menuOption        = menuOption;
        this.displayName       = displayName;
        this.domain            = domain;
        this.complexityClass   = complexityClass;
        this.reducesFrom       = reducesFrom;
        this.reductionNotation = reductionNotation;
    }

    // ── getters ───────────────────────────────────────────────────────────────
    public int             getMenuOption()       { return menuOption; }
    public String          getDisplayName()      { return displayName; }
    public String          getDomain()           { return domain; }
    public ComplexityClass getComplexityClass()  { return complexityClass; }
    public String          getReducesFrom()      { return reducesFrom; }
    public String          getReductionNotation(){ return reductionNotation; }

    /** Retorna o ProblemType correspondente à opção do menu, ou null se inválido. */
    public static ProblemType fromOption(int option) {
        for (ProblemType p : values()) {
            if (p.menuOption == option) return p;
        }
        return null;
    }
}
