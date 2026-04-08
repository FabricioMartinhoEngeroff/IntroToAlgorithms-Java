package approximation_algorithms;

/**
 * Enum dos algoritmos de aproximação implementados neste projeto (CLRS Cap. 35).
 *
 * Cada entrada representa um problema NP-Difícil com sua estratégia de aproximação
 * e garantia de qualidade (razão de aproximação).
 *
 * Tema: RouteOptix — empresa de otimização de rotas e logística.
 */
public enum AlgorithmType {

    VERTEX_COVER    (1, "Vertex Cover (2-aprox)",                    "2",       "constante"),
    TSP_APPROX      (2, "TSP com Triangle Inequality (2-aprox)",     "2",       "constante"),
    SET_COVER       (3, "Set Cover Guloso (O(log n)-aprox)",         "O(ln n)", "logaritmica"),
    MAX_3CNF        (4, "MAX-3-CNF Aleatorizado (7/8-aprox)",        "8/7",     "aleatorio"),
    WEIGHTED_VERTEX (5, "Weighted Vertex Cover via LP (2-aprox)",    "2",       "LP-rounding"),
    SUBSET_SUM_EXACT(6, "Subset Sum Exato",                          "1",       "exato"),
    SUBSET_SUM_FPTAS(7, "Subset Sum Aproximado — FPTAS",             "1+eps",   "FPTAS");

    private final int    menuOption;
    private final String displayName;
    private final String ratio;     // razao de aproximacao
    private final String type;      // classificacao do esquema

    AlgorithmType(int menuOption, String displayName, String ratio, String type) {
        this.menuOption  = menuOption;
        this.displayName = displayName;
        this.ratio       = ratio;
        this.type        = type;
    }

    public int    getMenuOption()  { return menuOption; }
    public String getDisplayName() { return displayName; }
    public String getRatio()       { return ratio; }
    public String getType()        { return type; }

    /** Retorna o AlgorithmType correspondente à opção do menu, ou null se inválido. */
    public static AlgorithmType fromOption(int option) {
        for (AlgorithmType t : values()) {
            if (t.menuOption == option) return t;
        }
        return null;
    }
}