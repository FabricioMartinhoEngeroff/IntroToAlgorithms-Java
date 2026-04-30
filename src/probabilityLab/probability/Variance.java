package probabilityLab.probability;

/**
 * Variance — CLRS Appendix C.3
 *
 * Var(X) = E[(X - E[X])²] = E[X²] - (E[X])²
 *
 * Standard deviation: σ = √Var(X)
 *
 * Dev translation:
 *   How spread out are the values around the mean?
 *   High variance → unpredictable. Low variance → consistent.
 */
public class Variance {

    /**
     * Computes Var(X) = E[X²] - (E[X])².
     *
     * @param values        possible values of X
     * @param probabilities P(X = values[i])
     */
    public static double compute(double[] values, double[] probabilities) {
        if (values.length != probabilities.length) {
            throw new IllegalArgumentException("Arrays must have the same length.");
        }
        double mean    = ExpectedValue.compute(values, probabilities);
        double meanSq  = ExpectedValue.compute(squaredValues(values), probabilities);
        return meanSq - mean * mean;
    }

    private static double[] squaredValues(double[] values) {
        double[] sq = new double[values.length];
        for (int i = 0; i < values.length; i++) sq[i] = values[i] * values[i];
        return sq;
    }

    public static void demo() {
        System.out.println("=== Variance  Var(X) = E[X²] - (E[X])² ===");
        System.out.println();

        // Case 1: fair coin (0/1)
        System.out.println("-- Fair coin: X ∈ {0, 1} with p=0.5 each --");
        double[] coinValues = {0, 1};
        double[] coinProbs  = {0.5, 0.5};
        double mean1 = ExpectedValue.compute(coinValues, coinProbs);
        double var1  = compute(coinValues, coinProbs);
        System.out.printf("  E[X]   = %.4f%n", mean1);
        System.out.printf("  Var(X) = %.4f   σ = %.4f%n", var1, Math.sqrt(var1));
        System.out.println();

        // Case 2: high-variance vs low-variance examples
        System.out.println("-- Low variance: values clustered around the mean --");
        double[] lowValues = {4, 5, 6};
        double[] lowProbs  = {1.0/3, 1.0/3, 1.0/3};
        double var2 = compute(lowValues, lowProbs);
        System.out.printf("  Values = [4,5,6]  Var = %.4f%n", var2);

        System.out.println("-- High variance: values spread far from the mean --");
        double[] highValues = {0, 5, 10};
        double[] highProbs  = {1.0/3, 1.0/3, 1.0/3};
        double var3 = compute(highValues, highProbs);
        System.out.printf("  Values = [0,5,10] Var = %.4f%n", var3);
        System.out.println();

        System.out.println("Rule: Var(X + Y) = Var(X) + Var(Y)  if X and Y are independent.");
        System.out.println("Dev insight: variance predicts reliability —");
        System.out.println("  e.g. worst-case vs average-case gap in randomized algorithms.");
    }
}
