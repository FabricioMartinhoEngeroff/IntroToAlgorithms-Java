package np_completeness_clrs;

import java.util.List;

/**
 * Resultado completo da verificação de um certificado para um problema NP.
 *
 * Demonstra o conceito central de NP:
 *   — encontrar a solução pode ser difícil (exponencial)
 *   — mas VERIFICAR se uma solução candidata é válida é rápido (polinomial)
 */
public class VerificationResult {

    private final String              certificateLabel;  // descrição do certificado testado
    private final List<VerificationStep> steps;          // passos de verificação
    private final boolean             valid;             // o certificado é uma solução válida?
    private final String              verdict;           // mensagem final

    // ── construtor ────────────────────────────────────────────────────────────
    public VerificationResult(String certificateLabel,
                              List<VerificationStep> steps,
                              boolean valid,
                              String verdict) {
        this.certificateLabel = certificateLabel;
        this.steps            = steps;
        this.valid            = valid;
        this.verdict          = verdict;
    }

    // ── getters ───────────────────────────────────────────────────────────────
    public String                   getCertificateLabel() { return certificateLabel; }
    public List<VerificationStep>   getSteps()            { return steps; }
    public boolean                  isValid()             { return valid; }
    public String                   getVerdict()          { return verdict; }
}
