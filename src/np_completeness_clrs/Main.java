package np_completeness_clrs;

import java.util.Scanner;

/**
 * Ponto de entrada do projeto NP-Completeness — CLRS Capítulo 34.
 *
 * Este projeto demonstra os conceitos do capítulo de forma prática:
 *  - Classificação de complexidade (P / NP / NP-Hard / NP-Complete)
 *  - Verificação polinomial de certificados (prova de NP membership)
 *  - Cadeias de reduções entre problemas
 *
 * Tema: Empresa de Logística "FastLog"
 * Problemas reais mapeados para problemas NP-Completos clássicos:
 *   1. Rota do Entregador    → TSP
 *   2. Carga do Caminhão     → Subset Sum
 *   3. Cobertura de Rotas    → Vertex Cover
 *   4. Regras de Serviço     → SAT / 3-CNF-SAT
 *   5. Escala de Funcionários → Scheduling
 */
public class Main {

    // scanner compartilhado por todos os métodos de leitura
    private static final Scanner sc = new Scanner(System.in);

    // ── serviços ──────────────────────────────────────────────────────────────
    private static final ProblemCatalog     catalog   = new ProblemCatalog();
    private static final ComplexityAnalyzer analyzer  = new ComplexityAnalyzer();
    private static final VerifierService    verifier  = new VerifierService();
    private static final ReductionExplainer explainer = new ReductionExplainer();

    // ── main ──────────────────────────────────────────────────────────────────

    public static void main(String[] args) {

        printWelcome();

        while (true) {
            printProblemMenu();
            int problemOption = readInt("Escolha o problema: ");

            if (problemOption == 0) {
                System.out.println("\n  Encerrando FastLog NP-Complexity Lab. Até logo!\n");
                break;
            }

            ProblemType type = ProblemType.fromOption(problemOption);
            if (type == null) {
                System.out.println("  Opção inválida.\n");
                continue;
            }

            // carrega a instância concreta do catálogo
            Problem problem = loadProblem(type);

            // menu de ações para o problema escolhido
            boolean backToProblems = false;
            while (!backToProblems) {
                printActionMenu(type);
                int action = readInt("Escolha a ação: ");

                switch (action) {
                    case 1 -> analyzer.analyze(problem);
                    case 2 -> verifier.verify(type);
                    case 3 -> explainer.explain(type);
                    case 0 -> backToProblems = true;
                    default -> System.out.println("  Opção inválida.");
                }

                if (!backToProblems) {
                    System.out.println("\n  Pressione ENTER para continuar...");
                    sc.nextLine();
                }
            }
        }
    }

    // ── carregamento de problema ───────────────────────────────────────────────

    private static Problem loadProblem(ProblemType type) {
        return switch (type) {
            case TSP          -> catalog.buildTSP();
            case SUBSET_SUM   -> catalog.buildSubsetSum();
            case VERTEX_COVER -> catalog.buildVertexCover();
            case SAT          -> catalog.buildSAT();
            case SCHEDULING   -> catalog.buildScheduling();
        };
    }

    // ── menus ─────────────────────────────────────────────────────────────────

    private static void printWelcome() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║      NP-COMPLETENESS LAB — CLRS Chapter 34              ║");
        System.out.println("║      Tema: FastLog — Empresa de Logística               ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║  Explore problemas NP-Completos do mundo real:          ║");
        System.out.println("║  • Veja a classificação de complexidade                 ║");
        System.out.println("║  • Verifique certificados passo a passo                 ║");
        System.out.println("║  • Entenda as reduções entre problemas                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printProblemMenu() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  ESCOLHA O PROBLEMA                                     ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        for (ProblemType type : ProblemType.values()) {
            System.out.printf("║  %d. %-53s║%n",
                type.getMenuOption(), type.getDisplayName());
        }
        System.out.println("║  0. Sair                                                ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    private static void printActionMenu(ProblemType type) {
        System.out.println();
        System.out.println("  ── " + type.getDisplayName() + " ──");
        System.out.println("  1. Analisar complexidade (P/NP/NP-Complete)");
        System.out.println("  2. Verificar certificados (demonstração de NP)");
        System.out.println("  3. Explicar redução (NP-Hardness)");
        System.out.println("  0. Voltar ao menu de problemas");
    }

    // ── utilitários de leitura ─────────────────────────────────────────────────

    private static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Digite um número inteiro.");
            }
        }
    }
}
