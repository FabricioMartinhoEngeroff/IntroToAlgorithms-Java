package probabilityLab.probability;

/**
 * Geometric Distribution — CLRS Appendix C.4
 *
 * Models: how many trials until the first success?
 *
 * P(X = k) = (1 - p)^(k-1) * p       k = 1, 2, 3, ...
 * E[X]     = 1 / p
 *
 * Dev translation:
 *   "How many times do I retry before it finally works?"
 *   Used in: hash probing, retry logic, randomized algorithms.
 */
public class GeometricDistribution {

    /**
     * P(X = k): probability that the first success happens on exactly trial k.
     *
     * @param p success probability per trial (0 < p <= 1)
     * @param k trial number of the first success (k >= 1)
     */
    public static double pmf(double p, int k) {
        if (p <= 0 || p > 1) throw new IllegalArgumentException("p must be in (0, 1].");
        if (k < 1)           throw new IllegalArgumentException("k must be >= 1.");
        return Math.pow(1 - p, k - 1) * p;
    }

    /** E[X] = 1/p */
    public static double expectedTrials(double p) {
        if (p <= 0 || p > 1) throw new IllegalArgumentException("p must be in (0, 1].");
        return 1.0 / p;
    }

    /** P(X <= k): cumulative probability — success within the first k trials. */
    public static double cdf(double p, int k) {
        return 1.0 - Math.pow(1 - p, k);
    }

    public static void demo() {
        System.out.println("=== Geometric Distribution  P(X=k) = (1-p)^(k-1) · p ===");
        System.out.println("Concept: how many trials until the first success?");
        System.out.println();

        double p = 0.5;
        System.out.println("-- Fair coin: p = 0.5 (success = heads) --");
        System.out.printf("  E[trials] = 1/p = %.1f%n", expectedTrials(p));
        System.out.println();
        System.out.println("  P(X = k)  for first 5 trials:");
        for (int k = 1; k <= 5; k++) {
            System.out.printf("    k=%d  P=%.4f  (cumulative P(X<=%d)=%.4f)%n",
                    k, pmf(p, k), k, cdf(p, k));
        }
        System.out.println();

        // Real example: hash table with load factor 0.25 → probing success
        double pHash = 0.75;
        System.out.println("-- Hash probing: p(slot free) = 0.75 --");
        System.out.printf("  E[probes] = %.2f%n", expectedTrials(pHash));
        System.out.printf("  P(success within 3 probes) = %.4f%n", cdf(pHash, 3));
        System.out.println();
        System.out.println("Dev insight: geometric distribution models any 'keep retrying'");
        System.out.println("  loop. E[trials] = 1/p tells you the expected cost directly.");
    }
}
