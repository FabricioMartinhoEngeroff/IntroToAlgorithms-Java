package probabilityLab.probability;

/**
 * Bayes' Theorem — CLRS Appendix C.2
 *
 * P(A | B) = P(B | A) * P(A) / P(B)
 *
 * Where P(B) = P(B|A)*P(A) + P(B|¬A)*P(¬A)   (total probability)
 *
 * Dev translation:
 *   Start with an initial guess (prior).
 *   Update it with new evidence.
 *   Get a better estimate (posterior).
 *
 * Real uses: spam filters, fraud detection, medical diagnosis, recommendation engines.
 */
public class BayesTheorem {

    /**
     * Computes P(A | B) using Bayes' Theorem.
     *
     * @param priorA     P(A)     — initial belief before seeing evidence
     * @param likelihoodBA  P(B | A)  — how likely evidence B is if A is true
     * @param likelihoodBnotA  P(B | ¬A) — how likely evidence B is if A is false
     * @return P(A | B) — updated belief after seeing evidence B
     */
    public static double compute(double priorA, double likelihoodBA, double likelihoodBnotA) {
        double priorNotA = 1.0 - priorA;
        double totalProbB = likelihoodBA * priorA + likelihoodBnotA * priorNotA;
        if (totalProbB <= 0) {
            throw new IllegalArgumentException("P(B) must be > 0.");
        }
        return (likelihoodBA * priorA) / totalProbB;
    }

    public static void demo() {
        System.out.println("=== Bayes' Theorem  P(A|B) = P(B|A)·P(A) / P(B) ===");
        System.out.println();
        System.out.println("Scenario: medical test for a rare disease.");
        System.out.println("  - Disease affects 1% of the population  → P(disease) = 0.01");
        System.out.println("  - Test sensitivity: 95%                 → P(+|disease) = 0.95");
        System.out.println("  - False positive rate: 5%               → P(+|healthy) = 0.05");
        System.out.println();

        double prior           = 0.01;   // P(disease)
        double sensitivity     = 0.95;   // P(positive | disease)
        double falsePositive   = 0.05;   // P(positive | healthy)

        double posterior = compute(prior, sensitivity, falsePositive);

        double priorNotA = 1 - prior;
        double totalProbB = sensitivity * prior + falsePositive * priorNotA;

        System.out.printf("  P(B)     = P(+|disease)·P(disease) + P(+|healthy)·P(healthy)%n");
        System.out.printf("           = %.2f·%.2f + %.2f·%.2f = %.4f%n",
                sensitivity, prior, falsePositive, priorNotA, totalProbB);
        System.out.printf("  P(disease | +) = %.4f  (≈ %.1f%%)%n", posterior, posterior * 100);
        System.out.println();
        System.out.println("Counter-intuitive insight:");
        System.out.println("  Test positive → only ~16% chance of actually having the disease.");
        System.out.println("  Because disease is RARE, most positives are false positives.");
        System.out.println();
        System.out.println("Dev pattern: prior → evidence → posterior. Used in every ML classifier.");
    }
}
