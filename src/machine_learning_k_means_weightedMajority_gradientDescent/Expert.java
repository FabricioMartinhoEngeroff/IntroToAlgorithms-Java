package machine_learning_k_means_weightedMajority_gradientDescent;

/**
 * Representa um "especialista" no algoritmo Weighted Majority.
 * Cada expert tem uma filosofia de treino diferente.
 */
public class Expert {

    public final String name;
    public final String philosophy;
    public double weight;

    public Expert(String name, String philosophy) {
        this.name       = name;
        this.philosophy = philosophy;
        this.weight     = 1.0;
    }

    /** Penaliza o expert quando ele errar. Fórmula do CLRS: w = w * (1 - gamma) */
    public void penalize(double gamma) {
        weight *= (1.0 - gamma);
    }

    @Override
    public String toString() {
        return String.format("%-22s peso=%.4f  [%s]", name, weight, philosophy);
    }
}

