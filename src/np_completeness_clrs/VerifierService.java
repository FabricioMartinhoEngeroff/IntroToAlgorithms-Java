package np_completeness_clrs;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstra a verificação polinomial de certificados — o coração do conceito NP.
 *
 * Para cada problema NP-Completo, mostra 3 certificados (soluções candidatas):
 *  - alguns inválidos (mostram por que encontrar é difícil)
 *  - pelo menos um válido (prova que o problema está em NP)
 *
 * O processo de verificação é exibido passo a passo, mostrando que
 * checar é RÁPIDO mesmo que encontrar seja difícil.
 */
public class VerifierService {

    /**
     * Executa e exibe a verificação de certificados para o tipo de problema escolhido.
     *
     * @param type tipo do problema a demonstrar
     */
    public void verify(ProblemType type) {
        switch (type) {
            case TSP           -> verifyTSP();
            case SUBSET_SUM    -> verifySubsetSum();
            case VERTEX_COVER  -> verifyVertexCover();
            case SAT           -> verifySAT();
            case SCHEDULING    -> verifyScheduling();
        }
    }

    // ── TSP ───────────────────────────────────────────────────────────────────

    private void verifyTSP() {
        printVerifyHeader("VERIFICADOR — Rota do Entregador (TSP)");

        System.out.println("  Instância: D→A→B→G→Δ→D");
        System.out.println("  Distâncias: D-A=10, A-B=15, B-G=20, G-Δ=25, Δ-D=30,");
        System.out.println("             D-B=35, A-G=40, B-Δ=18, D-G=45, A-Δ=50");
        System.out.println("  Limite k = 85 km");
        System.out.println();

        // certificado 1 — inválido (custo alto)
        List<VerificationStep> steps1 = new ArrayList<>();
        steps1.add(new VerificationStep(
            "Todos os 5 pontos visitados exatamente uma vez?",
            "D, A, B, G, Δ — SIM, 5 pontos únicos", true));
        steps1.add(new VerificationStep(
            "Rota fecha no depósito?",
            "começa e termina em D — SIM", true));
        steps1.add(new VerificationStep(
            "Custo total ≤ 85?",
            "D-A(10) + A-G(40) + G-B(20) + B-Δ(18) + Δ-D(30) = 118 km — NÃO", false));
        printCertificate("Rota: D → A → G → B → Δ → D", steps1);

        // certificado 2 — inválido (repete vértice)
        List<VerificationStep> steps2 = new ArrayList<>();
        steps2.add(new VerificationStep(
            "Todos os 5 pontos visitados exatamente uma vez?",
            "D, A, B, A, Δ — 'A' aparece 2 vezes — NÃO", false));
        printCertificate("Rota: D → A → B → A → Δ → D", steps2);

        // certificado 3 — VÁLIDO
        List<VerificationStep> steps3 = new ArrayList<>();
        steps3.add(new VerificationStep(
            "Todos os 5 pontos visitados exatamente uma vez?",
            "D, A, B, Δ, G — SIM, 5 pontos únicos", true));
        steps3.add(new VerificationStep(
            "Rota fecha no depósito?",
            "começa e termina em D — SIM", true));
        steps3.add(new VerificationStep(
            "Custo total ≤ 85?",
            "D-A(10) + A-B(15) + B-Δ(18) + Δ-G(25) + G-D(45) = 113 — NÃO", false));

        // ajuste — rota ótima pequena
        List<VerificationStep> steps4 = new ArrayList<>();
        steps4.add(new VerificationStep(
            "Todos os 5 pontos visitados exatamente uma vez?",
            "D, A, B, G, Δ — SIM", true));
        steps4.add(new VerificationStep(
            "Rota fecha no depósito?",
            "começa e termina em D — SIM", true));
        steps4.add(new VerificationStep(
            "Custo total ≤ 85?",
            "D-A(10) + A-B(15) + B-G(20) + G-Δ(25) + Δ-D(30) = 100 — NÃO (>85)", false));

        printCertificate("Rota: D → A → B → G → Δ → D", steps4);

        System.out.println("  CONCLUSÃO: Nenhum certificado satisfaz k≤85 nesta instância.");
        System.out.println("  A resposta para a instância é: NÃO existe rota ≤ 85 km.");
        System.out.println();
        printNPLesson();
    }

    // ── SUBSET SUM ────────────────────────────────────────────────────────────

    private void verifySubsetSum() {
        printVerifyHeader("VERIFICADOR — Carga do Caminhão (Subset Sum)");

        System.out.println("  Pacotes: P1=10kg, P2=20kg, P3=15kg, P4=25kg, P5=5kg, P6=8kg");
        System.out.println("  Alvo: exatamente 45 kg");
        System.out.println();

        // certificado 1 — inválido
        List<VerificationStep> steps1 = new ArrayList<>();
        steps1.add(new VerificationStep(
            "Todos os itens pertencem ao conjunto disponível?",
            "P1, P2, P3 — SIM", true));
        steps1.add(new VerificationStep(
            "Soma = alvo (45)?",
            "10 + 20 + 15 = 45 — SIM!", true));
        printCertificate("Subconjunto: {P1=10, P2=20, P3=15}", steps1);
        System.out.println("  → Certificado VÁLIDO! Soma = 45 kg exatos.");
        System.out.println();

        // certificado 2 — inválido
        List<VerificationStep> steps2 = new ArrayList<>();
        steps2.add(new VerificationStep(
            "Todos os itens pertencem ao conjunto disponível?",
            "P2, P4 — SIM", true));
        steps2.add(new VerificationStep(
            "Soma = alvo (45)?",
            "20 + 25 = 45 — SIM!", true));
        printCertificate("Subconjunto: {P2=20, P4=25}", steps2);
        System.out.println("  → Certificado VÁLIDO! Soma = 45 kg exatos (outra solução).");
        System.out.println();

        // certificado 3 — inválido
        List<VerificationStep> steps3 = new ArrayList<>();
        steps3.add(new VerificationStep(
            "Todos os itens pertencem ao conjunto disponível?",
            "P1, P4, P5 — SIM", true));
        steps3.add(new VerificationStep(
            "Soma = alvo (45)?",
            "10 + 25 + 5 = 40 — NÃO (≠ 45)", false));
        printCertificate("Subconjunto: {P1=10, P4=25, P5=5}", steps3);
        System.out.println();

        System.out.println("  CONCLUSÃO: A resposta é SIM — existem subconjuntos que somam 45.");
        System.out.println("  Há 2^6 = 64 subconjuntos possíveis, mas verificar cada um é O(n).");
        printNPLesson();
    }

    // ── VERTEX COVER ──────────────────────────────────────────────────────────

    private void verifyVertexCover() {
        printVerifyHeader("VERIFICADOR — Cobertura de Rotas (Vertex Cover)");

        System.out.println("  Cidades: A, B, C, D, E");
        System.out.println("  Rotas (arestas): A-B, A-C, B-D, C-D, D-E, B-E");
        System.out.println("  Limite: ≤ 2 câmeras (vertex cover de tamanho ≤ 2)");
        System.out.println();

        // certificado 1 — inválido
        List<VerificationStep> steps1 = new ArrayList<>();
        steps1.add(new VerificationStep(
            "Conjunto tem ≤ 2 vértices?",
            "{A, C} — 2 vértices — SIM", true));
        steps1.add(new VerificationStep(
            "A-B coberta? (A ou B no conjunto?)",
            "A está — SIM", true));
        steps1.add(new VerificationStep(
            "A-C coberta? (A ou C no conjunto?)",
            "A e C estão — SIM", true));
        steps1.add(new VerificationStep(
            "B-D coberta? (B ou D no conjunto?)",
            "nenhum de {A,C} — NÃO", false));
        printCertificate("Conjunto: {A, C}", steps1);
        System.out.println();

        // certificado 2 — inválido
        List<VerificationStep> steps2 = new ArrayList<>();
        steps2.add(new VerificationStep(
            "Conjunto tem ≤ 2 vértices?",
            "{B, D} — 2 vértices — SIM", true));
        steps2.add(new VerificationStep(
            "A-B coberta?", "B está — SIM", true));
        steps2.add(new VerificationStep(
            "A-C coberta? (A ou C no conjunto?)",
            "nenhum de {B,D} — NÃO", false));
        printCertificate("Conjunto: {B, D}", steps2);
        System.out.println();

        // certificado 3 — VÁLIDO
        List<VerificationStep> steps3 = new ArrayList<>();
        steps3.add(new VerificationStep(
            "Conjunto tem ≤ 2 vértices?",
            "{B, D} — 2 vértices — SIM", true));
        // versão correta do grafo onde B,D cobrem tudo
        // rotas: A-B✓(B), A-C: precisamos C ou B -> B não cobre A-C
        // Vamos usar conjunto correto: {A, D} ou {B, C, D}
        // Com {A, D}: A-B(A✓), A-C(A✓), B-D(D✓), C-D(D✓), D-E(D✓), B-E: nem A nem D -> falha
        // Com {B, D}: A-B(B✓), A-C: falha
        // Com {D, A}: cobre A-B(A), A-C(A), B-D(D), C-D(D), D-E(D), B-E: B? não. Falha
        // Mínimo é 3: {B, C, D} ou {A, D, E}? Vamos checar:
        // {B, D, E}: A-B(B✓), A-C: falha
        // {A, B, D}: A-B(A,B✓), A-C(A✓), B-D(B,D✓), C-D(D✓), D-E(D✓), B-E(B✓) — 3 vértices!
        // Então vertex cover mínimo é 3 para este grafo.
        // Conclusão: NÃO existe cover de tamanho 2.
        steps3.add(new VerificationStep(
            "Conjunto tem ≤ 2 vértices?",
            "{A, B, D} — 3 vértices — NÃO ≤ 2", false));
        printCertificate("Conjunto: {A, B, D} (3 vértices)", steps3);
        System.out.println();

        System.out.println("  CONCLUSÃO: O vertex cover mínimo para este grafo tem 3 vértices.");
        System.out.println("  A resposta para k=2 é: NÃO.");
        System.out.println("  Cada verificação é O(|E|) — rápida. Mas encontrar o mínimo é difícil.");
        printNPLesson();
    }

    // ── SAT ───────────────────────────────────────────────────────────────────

    private void verifySAT() {
        printVerifyHeader("VERIFICADOR — Regras de Serviço (SAT / 3-CNF)");

        System.out.println("  Variáveis: R=Rastreamento, N=Notificação, B=Backup");
        System.out.println("  Fórmula  : (R ∨ N) ∧ (¬R ∨ B) ∧ (¬N ∨ ¬B)");
        System.out.println();

        // certificado 1 — R=true, N=true, B=true
        List<VerificationStep> steps1 = new ArrayList<>();
        steps1.add(new VerificationStep(
            "Cláusula 1: (R ∨ N) = (T ∨ T)?",
            "TRUE ∨ TRUE = TRUE — SIM", true));
        steps1.add(new VerificationStep(
            "Cláusula 2: (¬R ∨ B) = (F ∨ T)?",
            "FALSE ∨ TRUE = TRUE — SIM", true));
        steps1.add(new VerificationStep(
            "Cláusula 3: (¬N ∨ ¬B) = (F ∨ F)?",
            "FALSE ∨ FALSE = FALSE — NÃO", false));
        printCertificate("R=true, N=true, B=true", steps1);
        System.out.println();

        // certificado 2 — R=false, N=false, B=false
        List<VerificationStep> steps2 = new ArrayList<>();
        steps2.add(new VerificationStep(
            "Cláusula 1: (R ∨ N) = (F ∨ F)?",
            "FALSE ∨ FALSE = FALSE — NÃO", false));
        printCertificate("R=false, N=false, B=false", steps2);
        System.out.println();

        // certificado 3 — VÁLIDO: R=true, N=false, B=true
        List<VerificationStep> steps3 = new ArrayList<>();
        steps3.add(new VerificationStep(
            "Cláusula 1: (R ∨ N) = (T ∨ F)?",
            "TRUE ∨ FALSE = TRUE — SIM", true));
        steps3.add(new VerificationStep(
            "Cláusula 2: (¬R ∨ B) = (F ∨ T)?",
            "FALSE ∨ TRUE = TRUE — SIM", true));
        steps3.add(new VerificationStep(
            "Cláusula 3: (¬N ∨ ¬B) = (T ∨ F)?",
            "TRUE ∨ FALSE = TRUE — SIM", true));
        printCertificate("R=true, N=false, B=true", steps3);
        System.out.println("  → Certificado VÁLIDO! Fórmula satisfeita.");
        System.out.println();

        System.out.println("  CONCLUSÃO: A fórmula é SATISFATÍVEL (R=T, N=F, B=T).");
        System.out.println("  2^3 = 8 atribuições possíveis. Verificar cada uma é O(cláusulas).");
        printNPLesson();
    }

    // ── SCHEDULING ────────────────────────────────────────────────────────────

    private void verifyScheduling() {
        printVerifyHeader("VERIFICADOR — Escala de Funcionários (Scheduling)");

        System.out.println("  Turnos    : Manhã(M), Tarde(T), Noite(N), FDS");
        System.out.println("  Disponível: Ana→{M,T}, Bruno→{T,N}, Carlos→{N,FDS}, Diana→{M,FDS}");
        System.out.println("  Limite    : ≤ 2 funcionários distintos para cobrir todos os turnos");
        System.out.println();

        // certificado 1 — inválido
        List<VerificationStep> steps1 = new ArrayList<>();
        steps1.add(new VerificationStep(
            "≤ 2 funcionários usados?",
            "{Ana, Bruno} — 2 — SIM", true));
        steps1.add(new VerificationStep(
            "Manhã coberta? Ana pode M?",
            "Ana → {M,T} — SIM", true));
        steps1.add(new VerificationStep(
            "Tarde coberta? Bruno pode T?",
            "Bruno → {T,N} — SIM", true));
        steps1.add(new VerificationStep(
            "Noite coberta? Bruno pode N?",
            "Bruno → {T,N} — SIM", true));
        steps1.add(new VerificationStep(
            "FDS coberto? Ana ou Bruno podem FDS?",
            "Ana→{M,T}, Bruno→{T,N} — nenhum pode FDS — NÃO", false));
        printCertificate("Ana=Manhã, Bruno=Tarde+Noite", steps1);
        System.out.println();

        // certificado 2 — inválido
        List<VerificationStep> steps2 = new ArrayList<>();
        steps2.add(new VerificationStep(
            "≤ 2 funcionários usados?",
            "{Carlos, Diana} — 2 — SIM", true));
        steps2.add(new VerificationStep(
            "Manhã coberta? Carlos/Diana podem M?",
            "Carlos→{N,FDS}, Diana→{M,FDS} — Diana pode — SIM", true));
        steps2.add(new VerificationStep(
            "Tarde coberta? Carlos/Diana podem T?",
            "Carlos→{N,FDS}, Diana→{M,FDS} — nenhum pode T — NÃO", false));
        printCertificate("Diana=Manhã+FDS, Carlos=Noite", steps2);
        System.out.println();

        // certificado 3 — com 3 funcionários (mas >2)
        List<VerificationStep> steps3 = new ArrayList<>();
        steps3.add(new VerificationStep(
            "≤ 2 funcionários usados?",
            "{Ana, Bruno, Carlos} — 3 — NÃO ≤ 2", false));
        printCertificate("Ana=Manhã, Bruno=Tarde+Noite, Carlos=FDS (3 func.)", steps3);
        System.out.println();

        System.out.println("  CONCLUSÃO: Não é possível cobrir todos os turnos com ≤ 2 funcionários.");
        System.out.println("  A resposta é NÃO. Verificar cada candidato é polinomial.");
        printNPLesson();
    }

    // ── helpers ───────────────────────────────────────────────────────────────

    private void printVerifyHeader(String title) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.printf ("║  %-56s║%n", title);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private void printCertificate(String label, List<VerificationStep> steps) {
        System.out.println("  ┌─ Certificado: " + label);
        boolean allPassed = true;
        for (VerificationStep step : steps) {
            System.out.println("  │  " + step);
            if (!step.isPassed()) { allPassed = false; break; }
        }
        System.out.println("  └─ Resultado: " + (allPassed ? "VÁLIDO ✓" : "INVÁLIDO ✗"));
    }

    private void printNPLesson() {
        System.out.println("  ─────────────────────────────────────────────────────────");
        System.out.println("  LIÇÃO NP: Verificar é O(n) — polinomial e rápido.");
        System.out.println("            Encontrar a solução pode exigir explorar");
        System.out.println("            exponencialmente muitos certificados.");
        System.out.println("            Isso é exatamente a definição de NP.");
        System.out.println();
    }
}
