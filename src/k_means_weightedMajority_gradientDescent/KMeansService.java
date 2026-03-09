package k_means_weightedMajority_gradientDescent;


import java.util.Arrays;
import java.util.List;

/**
 * ALGORITMO 3: K-MEANS
 * ─────────────────────────────────────────────────────────────────
 * Agrupa as sessões de treino em clusters por perfil de estímulo.
 * Identifica em qual cluster a sessão atual se encaixa e gera
 * a conclusão baseada no perfil do cluster.
 *
 * Lógica central (CLRS):
 *   1. Inicializa k centroides
 *   2. Atribui cada ponto ao centroide mais próximo
 *   3. Recalcula centroide como média dos membros
 *   4. Repete até convergir
 * ─────────────────────────────────────────────────────────────────
 */
public class KMeansService {

    private static final int K              = 3;   // 3 perfis: baixo/médio/alto estímulo
    private static final int MAX_ITERATIONS = 100;

    private double[][] centroids;

    public TrainingConclusion analyze(List<TrainingSession> history, TrainingSession current) {
        System.out.println("\n[K-Means] Agrupando sessões históricas em " + K + " clusters...");

        double[][] data = toMatrix(history);
        int[]      assignment = new int[data.length];

        // Inicializa centroides com os 3 primeiros pontos
        centroids = new double[K][];
        for (int i = 0; i < K; i++) {
            centroids[i] = Arrays.copyOf(data[i], data[i].length);
        }

        // Loop principal do K-Means
        for (int iter = 0; iter < MAX_ITERATIONS; iter++) {
            boolean changed = false;

            // Passo 2: atribui cada ponto ao centroide mais próximo
            for (int i = 0; i < data.length; i++) {
                int best = nearestCentroid(data[i]);
                if (best != assignment[i]) {
                    assignment[i] = best;
                    changed = true;
                }
            }

            if (!changed) {
                System.out.println("[K-Means] Convergiu na iteração " + (iter + 1));
                break;
            }

            // Passo 3: recalcula centroides
            recalculate(data, assignment);
        }

        printClusters(history, assignment);
        return conclude(current);
    }

    // ── ATRIBUI ao centroide mais próximo ───────────────────────────
    private int nearestCentroid(double[] point) {
        int    best = 0;
        double bestDist = Double.MAX_VALUE;
        for (int k = 0; k < K; k++) {
            double dist = squaredDist(point, centroids[k]);
            if (dist < bestDist) {
                bestDist = dist;
                best = k;
            }
        }
        return best;
    }

    // ── RECALCULA centroides como média dos membros ─────────────────
    private void recalculate(double[][] data, int[] assignment) {
        int features = data[0].length;
        double[][] sum   = new double[K][features];
        int[]      count = new int[K];

        for (int i = 0; i < data.length; i++) {
            int k = assignment[i];
            count[k]++;
            for (int f = 0; f < features; f++) {
                sum[k][f] += data[i][f];
            }
        }

        for (int k = 0; k < K; k++) {
            if (count[k] > 0) {
                for (int f = 0; f < features; f++) {
                    centroids[k][f] = sum[k][f] / count[k];
                }
            }
        }
    }

    // ── CONCLUSÃO baseada no cluster da sessão atual ─────────────────
    private TrainingConclusion conclude(TrainingSession s) {
        TrainingConclusion c = new TrainingConclusion();

        int cluster = nearestCentroid(s.features());

        c.isJunkVolume        = s.rpe < 7.0;
        c.techniqueFailed     = s.techniqueScore < 6.0;
        c.adaptationScore     = (s.rpe / 10.0) * s.workingSets * (1 - s.fatigueIndex / 10.0);
        c.isHomeostasisBroken = c.adaptationScore >= 5.0;
        c.systemicFatigue     = s.fatigueIndex + (s.rpe / 10.0) * s.workingSets * 0.4;

        // Perfil do cluster
        System.out.println("\nSessão atual pertence ao Cluster " + (cluster + 1));

        switch (cluster) {
            case 0 -> { // Cluster de baixo estímulo
                c.suggestedLoadNext = s.load + 5.0;
                c.recommendation    = "PERFIL BAIXO: Aumente intensidade/carga.     ║";
            }
            case 1 -> { // Cluster de estímulo moderado
                c.suggestedLoadNext = s.load + 2.5;
                c.recommendation    = "PERFIL MODERADO: Progresso constante +2.5kg. ║";
            }
            default -> { // Cluster de alto estímulo
                c.suggestedLoadNext = s.load * 0.90;
                c.recommendation    = "PERFIL ALTO: Recuperação necessária.         ║";
            }
        }

        if (c.techniqueFailed) {
            c.suggestedLoadNext = s.load * 0.90;
            c.recommendation    = "FALHA TÉCNICA: Reduza carga imediatamente.   ║";
        }

        return c;
    }

    // ── AUXILIARES ──────────────────────────────────────────────────
    private double squaredDist(double[] a, double[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            double d = a[i] - b[i];
            sum += d * d;
        }
        return sum;
    }

    private double[][] toMatrix(List<TrainingSession> sessions) {
        double[][] m = new double[sessions.size()][];
        for (int i = 0; i < sessions.size(); i++) {
            m[i] = sessions.get(i).features();
        }
        return m;
    }

    private void printClusters(List<TrainingSession> sessions, int[] assignment) {
        System.out.println("\nDistribuição dos clusters:");
        for (int k = 0; k < K; k++) {
            System.out.print("  Cluster " + (k + 1) + ": ");
            int count = 0;
            for (int i = 0; i < assignment.length; i++) {
                if (assignment[i] == k) count++;
            }
            System.out.println(count + " sessões");
        }
    }
}
