package np_completeness_clrs;

/**
 * Representa um passo individual de verificação de um certificado.
 *
 * No capítulo de NP-Completeness, "verificar" significa:
 * dada uma solução candidata, checar cada condição necessária em tempo polinomial.
 * Cada VerificationStep corresponde a uma condição checada.
 */
public class VerificationStep {

    private final String  condition;   // o que está sendo verificado
    private final String  detail;      // valor / cálculo realizado
    private final boolean passed;      // a condição foi satisfeita?

    // ── construtor ────────────────────────────────────────────────────────────
    public VerificationStep(String condition, String detail, boolean passed) {
        this.condition = condition;
        this.detail    = detail;
        this.passed    = passed;
    }

    // ── getters ───────────────────────────────────────────────────────────────
    public String  getCondition() { return condition; }
    public String  getDetail()    { return detail; }
    public boolean isPassed()     { return passed; }

    @Override
    public String toString() {
        String icon = passed ? "  [OK]  " : " [FAIL] ";
        return icon + condition + "\n         " + detail;
    }
}
