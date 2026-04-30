package probabilityLab.probability;

/**
 * Expected Value — CLRS Appendix C.3
 *
 * E[X] = Σ x · P(X = x)
 *
 * Golden rule (linearity of expectation):
 *   E[X + Y] = E[X] + E[Y]   — holds even if X and Y are NOT independent.
 *
 * Dev translation:
 *   The long-run average. What you'd expect "on average" after many trials.
 *   Linearity lets you analyze complex algorithms piece by piece.
 */
public class ExpectedValue {

    /**
     * Computes E[X] = Σ values[i] * probabilities[i].
     *
     * @param values        possible values that X can take
     * @param probabilities corresponding probabilities (must sum to 1)
     */
    public static double compute(double[] values, double[] probabilities) {
        if (values.length != probabilities.length) {
            throw new IllegalArgumentException("values and probabilities must have the same length.");
        }
        double sum = 0.0;
        double probSum = 0.0;
        for (int i = 0; i < values.length; i++) {
            if (probabilities[i] < 0) {
                throw new IllegalArgumentException("Probabilities cannot be negative.");
            }
            sum     += values[i] * probabilities[i];
            probSum += probabilities[i];
        }
        if (Math.abs(probSum - 1.0) > 1e-6) {
            throw new IllegalArgumentException("Probabilities must sum to 1 (got " + probSum + ").");
        }
        return sum;
    }

    public static void demo() {
        System.out.println("=== Expected Value  E[X] = Σ x · P(X = x) ===");
        System.out.println();

        // Example 1: fair dice
        System.out.println("-- Fair 6-sided dice --");
        double[] diceValues = {1, 2, 3, 4, 5, 6};
        double[] diceProbs  = {1.0/6, 1.0/6, 1.0/6, 1.0/6, 1.0/6, 1.0/6};
        double evDice = compute(diceValues, diceProbs);
        System.out.printf("  E[dice] = %.4f  (should be 3.5)%n", evDice);
        System.out.println();

        // Example 2: a game — win +3 or lose -2
        System.out.println("-- Gambling game: win +3 (p=0.4) or lose -2 (p=0.6) --");
        double[] gameValues = {3, -2};
        double[] gameProbs  = {0.4, 0.6};
        double evGame = compute(gameValues, gameProbs);
        System.out.printf("  E[game] = %.4f%n", evGame);
        System.out.println(evGame >= 0
                ? "  Positive EV → play this game in the long run."
                : "  Negative EV → avoid this game in the long run.");
        System.out.println();

        // Example 3: linearity of expectation
        System.out.println("-- Linearity of expectation --");
        System.out.println("  E[X + Y] = E[X] + E[Y]   even if X and Y are dependent.");
        System.out.println();
        System.out.println("  Algorithm example: QuickSort with random pivot.");
        System.out.println("  Total comparisons = X_1 + X_2 + ... + X_{n(n-1)/2}");
        System.out.println("  where X_ij = 1 if elements i and j are ever compared.");
        System.out.println("  E[total] = Σ E[X_ij]  ← analyze each pair independently!");
        System.out.println("  Result: E[comparisons] = O(n log n)");
        System.out.println();
        System.out.println("Dev insight: linearity lets you break complex algorithms");
        System.out.println("  into simple independent pieces and sum their expectations.");
    }
}
