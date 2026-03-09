package k_means_weightedMajority_gradientDescent;

import java.util.Arrays;
import java.util.List;

/**
 * ALGORITMO 2: WEIGHTED MAJORITY
 * ─────────────────────────────────────────────────────────────────
 * Três filosofias de treino competem entre si.
 * A que mais acerta sobre "a sessão gerou adaptação?" ganha mais peso.
 * No final, o expert dominante dita a conclusão.
 *
 * Lógica central (CLRS):
 *   - cada expert vota 0 ou 1
 *   - somam-se os pesos de cada lado
 *   - lado vencedor = decisão do algoritmo
 *   - quem errou tem peso reduzido: w = w * (1 - gamma)
 * ─────────────────────────────────────────────────────────────────
 */
public class WeightedMajorityService {

    private static final double GAMMA = 0.2; // penalidade por erro

    // Os 3 experts / filosofias de treino
    private final List<Expert> experts = Arrays.asList(
            new Expert("Heavy Duty (Low Vol)", "Alta carga, 1-2 séries até a falha total"),
            new Expert("Volume Moderado",       "3-5 séries, RPE 8-9, progressão linear"),
            new Expert("Deload/Recuperação",    "Reduz fadiga antes de progredir")
    );

    public TrainingConclusion analyze(List<TrainingSession> history, TrainingSession current) {

        System.out.println("\n[Weighted Majority] Simulando rodadas com histórico...");

        // Treina os pesos rodando o histórico
        for (TrainingSession session : history) {
            runRound(session, false);
        }

        System.out.println("\n[Weighted Majority] Analisando sessão atual...\n");
        printExperts();

        // Analisa a sessão atual
        return conclude(current);
    }

    // ── UMA RODADA do algoritmo ─────────────────────────────────────
    private void runRound(TrainingSession session, boolean verbose) {
        // Cada expert vota: 1 = "houve adaptação", 0 = "não houve"
        int[] votes = {
                vote_HeavyDuty(session),
                vote_ModerateVolume(session),
                vote_Deload(session)
        };

        // Resultado real: adaptação ocorreu se RPE alto + técnica boa + não junk
        int outcome = (session.rpe >= 8.0
                && session.techniqueScore >= 6.0
                && session.workingSets >= 2) ? 1 : 0;

        // Penaliza quem errou
        for (int i = 0; i < experts.size(); i++) {
            if (votes[i] != outcome) {
                experts.get(i).penalize(GAMMA);
            }
        }
    }

    // ── VOTOS de cada expert ────────────────────────────────────────

    /** Heavy Duty: só vota SIM se RPE for ≥ 9 e sets forem poucos */
    private int vote_HeavyDuty(TrainingSession s) {
        return (s.rpe >= 9.0 && s.workingSets <= 3) ? 1 : 0;
    }

    /** Volume Moderado: vota SIM se sets entre 3-6 e RPE razoável */
    private int vote_ModerateVolume(TrainingSession s) {
        return (s.workingSets >= 3 && s.rpe >= 7.5 && s.rpe < 9.5) ? 1 : 0;
    }

    /** Deload: vota SIM se fadiga for alta (treino leve era o certo) */
    private int vote_Deload(TrainingSession s) {
        return (s.fatigueIndex >= 7.0) ? 1 : 0;
    }

    // ── CONCLUSÃO baseada no expert dominante ───────────────────────
    private TrainingConclusion conclude(TrainingSession s) {
        TrainingConclusion c = new TrainingConclusion();

        Expert dominant = experts.stream()
                .max((a, b) -> Double.compare(a.weight, b.weight))
                .orElse(experts.get(0));

        System.out.println("Expert dominante: " + dominant.name);

        // Flags de conclusão
        c.isJunkVolume        = s.rpe < 7.0;
        c.techniqueFailed     = s.techniqueScore < 6.0;
        c.systemicFatigue     = s.fatigueIndex + (s.rpe / 10.0) * s.workingSets * 0.4;
        c.adaptationScore     = (s.rpe / 10.0) * s.workingSets * (1 - s.fatigueIndex / 10.0);
        c.isHomeostasisBroken = c.adaptationScore >= 5.0;

        // Carga e recomendação guiadas pelo expert dominante
        if (dominant == experts.get(0)) { // Heavy Duty
            c.suggestedLoadNext = s.load + (s.rpe >= 9.5 ? 5.0 : 2.5);
            c.recommendation    = "HEAVY DUTY: Reduza sets, aumente carga.       ║";
        } else if (dominant == experts.get(1)) { // Moderado
            c.suggestedLoadNext = s.load + 2.5;
            c.recommendation    = "VOLUME MODERADO: Progrida +2.5kg sets 3-5.   ║";
        } else { // Deload
            c.suggestedLoadNext = s.load * 0.85;
            c.recommendation    = "DELOAD: Reduza carga 15%. Recuperação!        ║";
        }

        if (c.techniqueFailed) {
            c.suggestedLoadNext = s.load * 0.90;
            c.recommendation    = "FALHA TÉCNICA: Reduza carga imediatamente.   ║";
        }

        return c;
    }

    private void printExperts() {
        System.out.println("Pesos dos experts após aprendizado:");
        for (Expert e : experts) {
            System.out.println("  " + e);
        }
    }
}
