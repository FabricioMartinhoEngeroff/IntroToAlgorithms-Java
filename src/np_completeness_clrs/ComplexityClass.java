package np_completeness_clrs;

/**
 * Enum que representa as classes de complexidade estudadas no Capítulo 34 do CLRS.
 *
 * Hierarquia:
 *   P ⊆ NP ⊆ NP-Hard
 *   NP-Complete = NP ∩ NP-Hard
 *
 * A grande pergunta em aberto: P = NP ?
 */
public enum ComplexityClass {

    P(
        "P — Polinomial",
        "O problema pode ser RESOLVIDO em tempo polinomial.",
        "Eficiente — qualquer computador resolve instâncias grandes rapidamente."
    ),

    NP(
        "NP — Verificável em Tempo Polinomial",
        "Dada uma solução candidata (certificado), é possível VERIFICAR se ela é válida em tempo polinomial.",
        "Encontrar a solução pode ser difícil, mas checar uma resposta é rápido."
    ),

    NP_HARD(
        "NP-Hard",
        "Pelo menos tão difícil quanto qualquer problema em NP. Todo problema NP reduz para ele.",
        "Não precisa estar em NP — pode nem ter certificado verificável facilmente."
    ),

    NP_COMPLETE(
        "NP-Completo",
        "Está em NP E é NP-Hard. Se um NP-completo for resolvido em tempo polinomial, então P = NP.",
        "Provavelmente não existe solução eficiente. Use heurísticas ou aproximações."
    );

    // ── campos ────────────────────────────────────────────────────────────────
    private final String label;
    private final String definition;
    private final String practicalNote;

    // ── construtor ────────────────────────────────────────────────────────────
    ComplexityClass(String label, String definition, String practicalNote) {
        this.label        = label;
        this.definition   = definition;
        this.practicalNote = practicalNote;
    }

    // ── getters ───────────────────────────────────────────────────────────────
    public String getLabel()        { return label; }
    public String getDefinition()   { return definition; }
    public String getPracticalNote(){ return practicalNote; }
}
