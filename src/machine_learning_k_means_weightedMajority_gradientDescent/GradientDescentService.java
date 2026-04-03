package machine_learning_k_means_weightedMajority_gradientDescent;


import java.util.List;

/**
 * ALGORITMO 1: GRADIENT DESCENT
 * ─────────────────────────────────────────────────────────────────
 * Aprende pesos para prever o performanceScore a partir das features.
 * Depois usa esses pesos para gerar a conclusão do treino.
 *
 * Lógica central (CLRS):
 *   prediction = w0 + w1*x1 + w2*x2 + ...
 *   error      = prediction - y
 *   w          = w - learningRate * gradiente
 * ─────────────────────────────────────────────────────────────────
 */
public class GradientDescentService {

    // Constantes do algoritmo
    private static final double LEARNING_RATE       = 0.000001;
    private static final int    ITERATIONS          = 20000;
    private static final double JUNK_VOLUME_RPE     = 7.0;   // abaixo disso = volume morto
    private static final double TECHNIQUE_THRESHOLD = 6.0;   // abaixo disso = falha técnica
    private static final double HOMEOSTASIS_SCORE   = 5.0;   // acima disso = homeostase quebrada
    private static final double LOAD_INCREMENT      = 2.5;   // kg a adicionar quando performance boa
    private static final double OVERLOAD_PENALTY    = 0.90;  // reduz carga se houver falha técnica

    private double[] weights; // pesos aprendidos

    // ── PASSO 1: TREINAR o modelo nos dados históricos ──────────────
    public void train(List<TrainingSession> sessions) {
        int features = sessions.get(0).features().length;

        weights = new double[features + 1]; // +1 para o bias (w0)

        System.out.println("\n[Gradient Descent] Treinando modelo...");

        for (int iter = 0; iter < ITERATIONS; iter++) {
            double[] grad = new double[weights.length];

            for (TrainingSession s : sessions) {
                double[] x     = s.features();
                double   y     = s.performanceScore;
                double   pred  = predict(x);
                double   error = pred - y;

                grad[0] += error; // gradiente do bias
                for (int j = 0; j < x.length; j++) {
                    grad[j + 1] += error * x[j];
                }
            }

            // Atualização dos pesos: w = w - η * gradiente
            for (int i = 0; i < weights.length; i++) {
                weights[i] -= LEARNING_RATE * grad[i] / sessions.size();
            }

            if ((iter + 1) % 5000 == 0) {
                System.out.printf("  Iteração %5d | loss = %.4f%n",
                        iter + 1, computeLoss(sessions));
            }
        }
        System.out.println("[Gradient Descent] Treinamento concluído.\n");
    }

    // ── PASSO 2: ANALISAR a sessão atual e gerar conclusão ──────────
    public TrainingConclusion analyze(TrainingSession session) {
        if (weights == null) {
            throw new IllegalStateException("Modelo não treinado. Chame train() antes.");
        }

        TrainingConclusion c = new TrainingConclusion();

        // Predição do score esperado
        double predicted = predict(session.features());

        // --- Regras de conclusão ---

        // Volume morto: RPE baixo = esforço insuficiente
        c.isJunkVolume = session.rpe < JUNK_VOLUME_RPE;

        // Falha técnica: forma ruim = risco de lesão, bloqueia progressão
        c.techniqueFailed = session.techniqueScore < TECHNIQUE_THRESHOLD;

        // Score de adaptação: quanto o treino foi capaz de gerar estímulo
        // Fórmula: RPE normalizado × sets × (1 - fadiga/10)
        c.adaptationScore = (session.rpe / 10.0)
                * session.workingSets
                * (1.0 - session.fatigueIndex / 10.0);
        c.adaptationScore = Math.min(c.adaptationScore, 10.0);

        // Homeostase quebrada se adaptationScore passou do limiar
        c.isHomeostasisBroken = c.adaptationScore >= HOMEOSTASIS_SCORE;

        // Fadiga sistêmica acumulada
        c.systemicFatigue = session.fatigueIndex
                + (session.rpe / 10.0) * session.workingSets * 0.5;
        c.systemicFatigue = Math.min(c.systemicFatigue, 10.0);

        // Carga sugerida para próxima sessão
        if (c.techniqueFailed) {
            c.suggestedLoadNext = session.load * OVERLOAD_PENALTY; // reduz
        } else if (c.isHomeostasisBroken && !c.isJunkVolume) {
            c.suggestedLoadNext = session.load + LOAD_INCREMENT;   // progride
        } else {
            c.suggestedLoadNext = session.load;                    // mantém
        }

        // Texto de recomendação
        c.recommendation = buildRecommendation(c, session, predicted);

        return c;
    }

    // ── AUXILIARES ─────────────────────────────────────────────────

    private double predict(double[] x) {
        double result = weights[0];
        for (int i = 0; i < x.length; i++) {
            result += weights[i + 1] * x[i];
        }
        return result;
    }

    private double computeLoss(List<TrainingSession> sessions) {
        double sum = 0;
        for (TrainingSession s : sessions) {
            double err = predict(s.features()) - s.performanceScore;
            sum += err * err;
        }
        return sum / sessions.size();
    }

    private String buildRecommendation(TrainingConclusion c,
                                       TrainingSession s,
                                       double predicted) {
        if (c.techniqueFailed) {
            return "REDUZIR CARGA. Falha técnica detectada.      ║";
        }
        if (c.isJunkVolume) {
            return "AUMENTAR RPE. Intensidade insuficiente.      ║";
        }
        if (c.systemicFatigue > 8.0) {
            return "DELOAD necessário. Fadiga crítica.           ║";
        }
        if (c.isHomeostasisBroken) {
            return "PROGREDIR CARGA em " + LOAD_INCREMENT + "kg. Ótimo estímulo!      ║";
        }
        return "MANTER protocolo atual.                      ║";
    }
}
