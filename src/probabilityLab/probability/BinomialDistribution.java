package probabilityLab.probability;

/**
 * Binomial Distribution — CLRS Appendix C.4
 *
 * Models: how many successes in n independent trials?
 *
 * P(X = k) = C(n,k) * p^k * (1-p)^(n-k)
 * E[X]     = n * p
 * Var(X)   = n * p * (1 - p)
 *
 * Dev translation:
 *   Run n independent trials, each with success probability p.
 *   X counts the total number of successes.
 */
public class BinomialDistribution {

    /**
     * P(X = k) for a Binomial(n, p).
     *
     * @param n number of trials
     * @param k desired number of successes (0 <= k <= n)
     * @param p success probability per trial
     */
    public static double pmf(int n, int k, double p) {
        if (n < 0)           throw new IllegalArgumentException("n must be >= 0.");
        if (k < 0 || k > n)  throw new IllegalArgumentException("k must be in [0, n].");
        if (p < 0 || p > 1)  throw new IllegalArgumentException("p must be in [0, 1].");
        return binomialCoefficient(n, k) * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }

    /** E[X] = n * p */
    public static double expectedSuccesses(int n, double p) {
        return n * p;
    }

    /** Var(X) = n * p * (1 - p) */
    public static double variance(int n, double p) {
        return n * p * (1 - p);
    }

    /** C(n, k) = n! / (k! * (n-k)!) — computed without overflow risk via multiplicative form. */
    public static long binomialCoefficient(int n, int k) {
        if (k > n - k) k = n - k; // use smaller k for efficiency
        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }
        return result;
    }

    public static void demo() {
        System.out.println("=== Binomial Distribution  P(X=k) = C(n,k) · p^k · (1-p)^(n-k) ===");
        System.out.println("Concept: count successes across n independent trials.");
        System.out.println();

        int n = 10;
        double p = 0.5;
        System.out.printf("-- Flip a fair coin %d times --  (p=%.1f)%n", n, p);
        System.out.printf("  E[heads] = %d * %.1f = %.1f%n", n, p, expectedSuccesses(n, p));
        System.out.printf("  Var      = %.2f   σ = %.4f%n", variance(n, p), Math.sqrt(variance(n, p)));
        System.out.println();
        System.out.println("  Full distribution P(X = k):");
        for (int k = 0; k <= n; k++) {
            double prob = pmf(n, k, p);
            String bar = "#".repeat((int) Math.round(prob * 60));
            System.out.printf("    k=%2d  P=%.4f  %s%n", k, prob, bar);
        }
        System.out.println();

        // Algorithm example: birthday paradox / collision probability in hashing
        System.out.println("-- Hash collision: n=365 keys, m=365 slots, p(collision per key) ≈ k/m --");
        System.out.println("  As k (keys inserted) grows, expected collisions = n * (k/m).");
        System.out.println("  This is exactly the Binomial expectation: E[X] = n*p.");
        System.out.println();
        System.out.println("Dev insight: Binomial answers 'how many successes in a batch?'");
        System.out.println("  Used in: A/B testing, error rates, hashing analysis.");
    }
}
