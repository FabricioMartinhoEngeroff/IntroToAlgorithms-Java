package np_completeness_clrs;

/**
 * Analisa e explica a classificação de complexidade de um problema.
 *
 * Para cada problema, exibe:
 *  1. Classe de complexidade (P / NP / NP-Hard / NP-Complete)
 *  2. Por que não é em P (por que encontrar é difícil)
 *  3. Por que está em NP (certificado e verificação polinomial)
 *  4. Por que é NP-Hard (cadeia de redução)
 *  5. Impacto prático (o que fazer na vida real)
 */
public class ComplexityAnalyzer {

    // ── método principal ──────────────────────────────────────────────────────

    /**
     * Imprime a análise completa de complexidade para o problema dado.
     *
     * @param problem instância do problema a analisar
     */
    public void analyze(Problem problem) {
        ProblemType    type  = problem.getType();
        ComplexityClass cls  = problem.getComplexityClass();

        printSectionHeader("ANÁLISE DE COMPLEXIDADE — " + type.getDisplayName());

        // Classe
        System.out.println("  Classe de Complexidade : " + cls.getLabel());
        System.out.println("  Definição              : " + cls.getDefinition());
        System.out.println("  Nota Prática           : " + cls.getPracticalNote());
        System.out.println();

        // Domínio
        System.out.println("  Domínio do Problema    : " + type.getDomain());
        System.out.println();

        // Cenário real
        printSubHeader("CENÁRIO REAL");
        printWrapped(problem.getRealWorldScenario());
        System.out.println();

        // Versão de decisão
        printSubHeader("VERSÃO DE DECISÃO (sim / não)");
        printWrapped(problem.getDecisionQuestion());
        System.out.println();

        // Por que é difícil
        printSubHeader("POR QUE RESOLVER É DIFÍCIL");
        printWrapped(problem.getWhyHard());
        System.out.println("  Tamanho do espaço bruto: " + problem.getBruteForceSize());
        System.out.println();

        // NP membership
        printSubHeader("POR QUE ESTÁ EM NP (verificação polinomial)");
        printWrapped(problem.getHowToVerify());
        System.out.println();

        // NP-Hard — cadeia de redução
        printSubHeader("POR QUE É NP-HARD (redução)");
        System.out.println("  Reduz de    : " + type.getReducesFrom());
        System.out.println("  Notação     : " + type.getReductionNotation());
        System.out.println("  Interpretação: se " + type.getReducesFrom()
                + " puder ser resolvido");
        System.out.println("  através de " + type.getDisplayName().split(" ")[0]
                + ", então " + type.getReducesFrom() + " também seria eficiente.");
        System.out.println("  Como " + type.getReducesFrom()
                + " já é NP-Completo, isso prova que " + type.getDisplayName()
                + " é NP-Hard.");
        System.out.println();

        // Consequência prática
        printSubHeader("O QUE FAZER NA PRÁTICA");
        System.out.println("  Não existe algoritmo polinomial exato conhecido.");
        System.out.println("  Abordagens recomendadas:");
        System.out.println("    → Heurísticas (greedy, local search)");
        System.out.println("    → Algoritmos de aproximação com garantia de erro");
        System.out.println("    → Branch and bound para instâncias pequenas");
        System.out.println("    → Programação dinâmica para casos especiais");
        System.out.println();
    }

    // ── helpers de formatação ─────────────────────────────────────────────────

    private void printSectionHeader(String title) {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.printf ("║  %-56s║%n", title);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private void printSubHeader(String title) {
        System.out.println("  ── " + title + " " + "─".repeat(Math.max(0, 50 - title.length())));
    }

    private void printWrapped(String text) {
        // quebra linhas longas em múltiplas linhas de 70 chars com indentação
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder("  ");
        for (String word : words) {
            if (line.length() + word.length() > 72) {
                System.out.println(line);
                line = new StringBuilder("  ");
            }
            line.append(word).append(" ");
        }
        if (line.length() > 2) System.out.println(line);
    }
}
