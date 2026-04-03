package machine_learning_k_means_weightedMajority_gradientDescent;

/**
 * Representa uma sessão de treino intenso.
 * Todos os parâmetros que o algoritmo vai processar.
 */
public class TrainingSession {

    // --- PARÂMETROS DE ENTRADA ---

    /** Séries de trabalho reais (que geram adaptação, não volume morto) */
    public int workingSets;

    /** Intensidade percebida (RPE). 10 = falha total. Abaixo de 7 = volume morto. */
    public double rpe;

    /** Carga absoluta utilizada no exercício (kg) */
    public double load;

    /** Fadiga residual acumulada (0 = descansado, 10 = exausto) */
    public double fatigueIndex;

    /** Técnica/forma de execução (0 a 10). Abaixo de 6 = falha técnica. */
    public double techniqueScore;

    /** Score de performance real medido após o treino */
    public double performanceScore;

    public TrainingSession(int workingSets, double rpe, double load,
                           double fatigueIndex, double techniqueScore,
                           double performanceScore) {
        this.workingSets    = workingSets;
        this.techniqueScore = techniqueScore;
        this.rpe            = rpe;
        this.load           = load;
        this.fatigueIndex   = fatigueIndex;
        this.performanceScore = performanceScore;
    }

    /** Retorna vetor de features para os algoritmos */
    public double[] features() {
        return new double[]{ workingSets, rpe, load, fatigueIndex, techniqueScore };
    }

    @Override
    public String toString() {
        return String.format(
                "Sets=%-2d RPE=%-4.1f Load=%-6.1f Fadiga=%-4.1f Técnica=%-4.1f Perf=%.1f",
                workingSets, rpe, load, fatigueIndex, techniqueScore, performanceScore
        );
    }
}
