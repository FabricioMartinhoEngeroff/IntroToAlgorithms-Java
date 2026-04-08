package approximation_algorithms;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Ponto de entrada do projeto Approximation Algorithms — CLRS Capítulo 35.
 *
 * Tema: RouteOptix — empresa de otimização de rotas e logística.
 * Cada opção do menu executa um algoritmo de aproximação diferente,
 * mostrando o passo a passo e a garantia de qualidade.
 *
 * Algoritmos implementados (todos para problemas NP-Difíceis):
 *   1. Vertex Cover           → 2-aproximação
 *   2. TSP (triangle ineq.)   → 2-aproximação
 *   3. Set Cover Greedy       → O(log n)-aproximação
 *   4. MAX-3-CNF Aleatorizado → 7/8-aproximação esperada
 *   5. Weighted Vertex Cover  → 2-aproximação via LP Rounding
 *   6. Subset Sum Exato       → solução ótima
 *   7. Subset Sum FPTAS       → (1 - eps)-aproximação, erro controlável
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    // ── instâncias dos solvers e da fábrica de cenários ───────────────────────
    private static final VertexCoverSolver         vcSolver  = new VertexCoverSolver();
    private static final TSPSolver                 tspSolver = new TSPSolver();
    private static final SetCoverSolver            scSolver  = new SetCoverSolver();
    private static final Max3CnfSolver             cnfSolver = new Max3CnfSolver(42L);
    private static final WeightedVertexCoverSolver wvcSolver = new WeightedVertexCoverSolver();
    private static final SubsetSumSolver           ssSolver  = new SubsetSumSolver();
    private static final ScenarioFactory           factory   = new ScenarioFactory();

    // ── main ──────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        printWelcome();

        while (true) {
            printMenu();
            int option = readInt("  Escolha o algoritmo: ");

            if (option == 0) {
                System.out.println("\n  Encerrando RouteOptix Approximation Lab. Até logo!\n");
                break;
            }

            AlgorithmType type = AlgorithmType.fromOption(option);
            if (type == null) {
                System.out.println("  Opcao invalida. Tente novamente.\n");
                continue;
            }

            runAlgorithm(type);

            System.out.println("\n  Pressione ENTER para continuar...");
            sc.nextLine();
        }
    }

    // ── execução de cada algoritmo ────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    private static void runAlgorithm(AlgorithmType type) {
        switch (type) {

            case VERTEX_COVER -> {
                Graph g = factory.buildVertexCoverGraph();
                vcSolver.solve(g);
            }

            case TSP_APPROX -> {
                Object[] s = factory.buildTSPScenario();
                tspSolver.solve((int[][]) s[0], (String[]) s[1]);
            }

            case SET_COVER -> {
                Object[] s = factory.buildSetCoverScenario();
                scSolver.solve((String[]) s[0], (List<Set<String>>) s[1], (String[]) s[2]);
            }

            case MAX_3CNF -> {
                Object[] s = factory.buildMax3CnfScenario();
                cnfSolver.solve((String[]) s[0], (int[][]) s[1]);
            }

            case WEIGHTED_VERTEX -> {
                Graph g = factory.buildWeightedVertexCoverGraph();
                wvcSolver.solve(g);
            }

            case SUBSET_SUM_EXACT -> {
                Object[] s = factory.buildSubsetSumScenario();
                ssSolver.solveExact((int[]) s[0], (String[]) s[1], (int) s[2]);
            }

            case SUBSET_SUM_FPTAS -> {
                Object[] s   = factory.buildSubsetSumScenario();
                double   eps = readEpsilon();
                ssSolver.solveApprox((int[]) s[0], (String[]) s[1], (int) s[2], eps);
            }
        }
    }

    // ── menus ─────────────────────────────────────────────────────────────────

    private static void printWelcome() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║   APPROXIMATION ALGORITHMS — CLRS Capitulo 35           ║");
        System.out.println("║   Tema: RouteOptix — Otimizacao de Rotas e Logistica    ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.println("║  Problemas NP-Dificeis com garantia de qualidade:       ║");
        System.out.println("║  • Vertex Cover    • TSP    • Set Cover                 ║");
        System.out.println("║  • MAX-3-CNF       • Weighted VC   • Subset Sum         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printMenu() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║  ALGORITMOS DISPONÍVEIS                 razao    tipo   ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        for (AlgorithmType t : AlgorithmType.values()) {
            System.out.printf("║  %d. %-33s %-8s %-6s║%n",
                    t.getMenuOption(), t.getDisplayName(), t.getRatio(), t.getType());
        }
        System.out.println("║  0. Sair                                                ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    // ── utilitários de leitura ────────────────────────────────────────────────

    private static double readEpsilon() {
        while (true) {
            System.out.print("  Informe o valor de epsilon (ex: 0.1 para 10% de erro): ");
            // aceita virgula como separador decimal (ex: 0,1)
            String input = sc.nextLine().trim().replace(',', '.');
            if (input.isEmpty()) continue;
            try {
                double eps = Double.parseDouble(input);
                if (eps > 0 && eps < 1) return eps;
                System.out.println("  Use um valor entre 0 e 1 exclusivo (ex: 0.1, 0.2, 0.5).");
            } catch (NumberFormatException e) {
                System.out.println("  Valor invalido. Digite um numero como 0.1 ou 0,1.");
            }
        }
    }

    private static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Digite um numero inteiro.");
            }
        }
    }
}
