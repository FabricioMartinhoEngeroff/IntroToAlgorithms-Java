package machine_learning_k_means_weightedMajority_gradientDescent;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DataService data = new DataService();
        List<TrainingSession> history = data.history();

        while (true) {
            printMenu();
            int option = readInt("Opção: ");

            if (option == 0) {
                System.out.println("Encerrando...");
                break;
            }

            if (option < 1 || option > 3) {
                System.out.println("Opção inválida.\n");
                continue;
            }

            // Coleta a sessão de treino do usuário
            TrainingSession session = readSession();

            TrainingConclusion conclusion = switch (option) {
                case 1 -> runGradientDescent(history, session);
                case 2 -> runWeightedMajority(history, session);
                case 3 -> runKMeans(history, session);
                default -> null;
            };

            if (conclusion != null) {
                System.out.println(conclusion);
            }

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();
        }
    }

    // ── ALGORITMO 1 ─────────────────────────────────────────────────
    private static TrainingConclusion runGradientDescent(List<TrainingSession> history,
                                                         TrainingSession session) {
        GradientDescentService gd = new GradientDescentService();
        gd.train(history);
        return gd.analyze(session);
    }

    // ── ALGORITMO 2 ─────────────────────────────────────────────────
    private static TrainingConclusion runWeightedMajority(List<TrainingSession> history,
                                                          TrainingSession session) {
        WeightedMajorityService wm = new WeightedMajorityService();
        return wm.analyze(history, session);
    }

    // ── ALGORITMO 3 ─────────────────────────────────────────────────
    private static TrainingConclusion runKMeans(List<TrainingSession> history,
                                                TrainingSession session) {
        KMeansService km = new KMeansService();
        return km.analyze(history, session);
    }

    // ── LEITURA DOS DADOS DO TREINO ──────────────────────────────────
    private static TrainingSession readSession() {
        System.out.println("\n──────────────────────────────────────────────");
        System.out.println("  INFORME OS DADOS DA SESSÃO DE TREINO");
        System.out.println("──────────────────────────────────────────────");

        int    sets      = readInt   ("Séries de trabalho (working sets) [ex: 2]: ");
        double rpe       = readDouble("RPE / Intensidade (1-10, 10=falha) [ex: 9.5]: ");
        double load      = readDouble("Carga absoluta (kg) [ex: 120]: ");
        double fatigue   = readDouble("Fadiga residual (0-10) [ex: 4]: ");
        double technique = readDouble("Qualidade técnica (0-10) [ex: 8]: ");
        double perf      = readDouble("Performance percebida (0-100) [ex: 85]: ");

        return new TrainingSession(sets, rpe, load, fatigue, technique, perf);
    }

    // ── MENU ─────────────────────────────────────────────────────────
    private static void printMenu() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║    TRAINING ML — CLRS Algorithms         ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.println("║ 1. Gradient Descent  (aprende pesos)     ║");
        System.out.println("║ 2. Weighted Majority (experts competem)  ║");
        System.out.println("║ 3. K-Means           (perfil do treino)  ║");
        System.out.println("║ 0. Sair                                  ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    // ── UTILS DE LEITURA ─────────────────────────────────────────────
    private static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                int v = Integer.parseInt(sc.nextLine().trim());
                return v;
            } catch (NumberFormatException e) {
                System.out.println("  Digite um número inteiro.");
            }
        }
    }

    private static double readDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("  Digite um número decimal.");
            }
        }
    }
}
