package probabilityLab.probability;

/**
 * Conditional Probability — CLRS Appendix C.2
 *
 * P(A | B) = P(A ∩ B) / P(B)
 *
 * Dev translation:
 *   Filter only the cases where B happened.
 *   Among those, count how many are also A.
 *   Divide.
 */
public class ConditionalProbability {

    /**
     * Computes P(A | B) given the probability of the intersection and of B.
     *
     * @param intersectionAB  P(A ∩ B) — probability that both A and B occur
     * @param probB           P(B)     — probability that B occurs (must be > 0)
     * @return P(A | B)
     */
    public static double compute(double intersectionAB, double probB) {
        if (probB <= 0) {
            throw new IllegalArgumentException("P(B) must be > 0 (conditioning on an impossible event).");
        }
        if (intersectionAB < 0 || intersectionAB > probB) {
            throw new IllegalArgumentException("P(A ∩ B) must be in [0, P(B)].");
        }
        return intersectionAB / probB;
    }

    public static void demo() {
        System.out.println("=== Conditional Probability  P(A | B) = P(A ∩ B) / P(B) ===");
        System.out.println("Scenario: flip a fair coin twice.");
        System.out.println("  S = { HH, HT, TH, TT }  →  each outcome has probability 1/4");
        System.out.println();

        // A = "two heads" = { HH }
        // B = "at least one head" = { HH, HT, TH }
        double probB           = 3.0 / 4.0;   // P(B) = 3/4
        double intersectionAB  = 1.0 / 4.0;   // P(A ∩ B) = P(HH) = 1/4

        double result = compute(intersectionAB, probB);

        System.out.printf("  A = \"two heads\"         P(A) = 1/4%n");
        System.out.printf("  B = \"at least one head\" P(B) = 3/4%n");
        System.out.printf("  P(A ∩ B) = 1/4%n");
        System.out.printf("  P(A | B) = %.4f  (≈ 1/3)%n", result);
        System.out.println();
        System.out.println("Dev insight: knowing B happened shrinks the sample space.");
        System.out.println("  Only { HH, HT, TH } remain. HH is 1 of those 3 → 1/3.");
    }
}
