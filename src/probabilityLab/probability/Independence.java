package probabilityLab.probability;

/**
 * Independence — CLRS Appendix C.2
 *
 * A and B are independent when:
 *   P(A ∩ B) == P(A) * P(B)
 *
 * Dev translation:
 *   Knowing A happened tells you nothing new about B.
 */
public class Independence {

    private static final double EPS = 1e-9;

    /**
     * Returns true if A and B are independent: P(A ∩ B) ≈ P(A) * P(B).
     */
    public static boolean areIndependent(double probA, double probB, double intersectionAB) {
        return Math.abs(intersectionAB - probA * probB) < EPS;
    }

    public static void demo() {
        System.out.println("=== Independence  P(A ∩ B) = P(A) · P(B) ===");
        System.out.println();

        // Case 1: two fair coins — independent
        System.out.println("-- Case 1: flip two fair coins independently --");
        double pA1 = 0.5;  // first coin = heads
        double pB1 = 0.5;  // second coin = heads
        double pAB1 = 0.25;
        System.out.printf("  P(A) = %.2f, P(B) = %.2f, P(A ∩ B) = %.2f%n", pA1, pB1, pAB1);
        System.out.printf("  P(A) * P(B) = %.2f%n", pA1 * pB1);
        System.out.println("  Independent? " + areIndependent(pA1, pB1, pAB1));
        System.out.println();

        // Case 2: drawing cards without replacement — NOT independent
        System.out.println("-- Case 2: draw 2 cards without replacement (aces) --");
        double pA2  = 4.0 / 52.0;           // first card is an ace
        double pAB2 = (4.0 / 52.0) * (3.0 / 51.0); // both aces
        double pB2  = 4.0 / 52.0;           // second card is an ace (marginal)
        System.out.printf("  P(1st ace) = %.4f%n", pA2);
        System.out.printf("  P(both aces) = %.4f%n", pAB2);
        System.out.printf("  P(A) * P(B) = %.4f%n", pA2 * pB2);
        System.out.println("  Independent? " + areIndependent(pA2, pB2, pAB2));
        System.out.println();
        System.out.println("Dev rule: if knowing A changes P(B) → they are NOT independent.");
    }
}
