package k_means_weightedMajority_gradientDescent;

import java.util.Arrays;
import java.util.List;

/**
 * Gera histórico fictício de sessões de treino para alimentar os algoritmos.
 * Dados representam diferentes perfis: baixo estímulo, ideal e overtraining.
 */
public class DataService {

    public List<TrainingSession> history() {
        // TrainingSession(sets, rpe, load, fadiga, tecnica, performance)
        return Arrays.asList(
                // Perfil 1: baixo estímulo (junk volume)
                new TrainingSession(5, 6.0, 80,  2.0, 9.0, 55),
                new TrainingSession(4, 5.5, 75,  1.5, 9.5, 50),
                new TrainingSession(6, 6.5, 70,  2.5, 8.5, 58),

                // Perfil 2: treino ideal (Heavy Duty / progressão)
                new TrainingSession(2, 9.5, 120, 4.0, 8.5, 88),
                new TrainingSession(3, 9.0, 115, 3.5, 9.0, 85),
                new TrainingSession(2, 10.0,125, 4.5, 8.0, 91),

                // Perfil 3: overtraining (fadiga alta, técnica caindo)
                new TrainingSession(6, 8.0, 100, 8.5, 5.5, 62),
                new TrainingSession(5, 7.5, 95,  9.0, 5.0, 58),
                new TrainingSession(4, 7.0, 90,  9.5, 4.5, 54)
        );
    }
}