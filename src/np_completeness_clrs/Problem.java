package np_completeness_clrs;

/**
 * Representa um problema computacional concreto com contexto do mundo real.
 *
 * Cada instância carrega:
 *  - tipo e classe de complexidade
 *  - descrição do cenário real
 *  - versão de decisão do problema (sim/não)
 *  - por que encontrar a solução é difícil
 *  - como verificar um certificado rapidamente (prova de NP-membership)
 *  - tamanho do espaço de busca bruta
 */
public class Problem {

    private final ProblemType type;
    private final String      realWorldScenario;
    private final String      decisionQuestion;
    private final String      whyHard;
    private final String      howToVerify;
    private final String      bruteForceSize;
    private final int         instanceSize;      // n — tamanho da instância concreta

    // ── construtor ────────────────────────────────────────────────────────────
    public Problem(ProblemType type,
                   String realWorldScenario,
                   String decisionQuestion,
                   String whyHard,
                   String howToVerify,
                   String bruteForceSize,
                   int    instanceSize) {
        this.type              = type;
        this.realWorldScenario = realWorldScenario;
        this.decisionQuestion  = decisionQuestion;
        this.whyHard           = whyHard;
        this.howToVerify       = howToVerify;
        this.bruteForceSize    = bruteForceSize;
        this.instanceSize      = instanceSize;
    }

    // ── getters ───────────────────────────────────────────────────────────────
    public ProblemType    getType()              { return type; }
    public ComplexityClass getComplexityClass()  { return type.getComplexityClass(); }
    public String         getRealWorldScenario() { return realWorldScenario; }
    public String         getDecisionQuestion()  { return decisionQuestion; }
    public String         getWhyHard()           { return whyHard; }
    public String         getHowToVerify()       { return howToVerify; }
    public String         getBruteForceSize()    { return bruteForceSize; }
    public int            getInstanceSize()      { return instanceSize; }
}
