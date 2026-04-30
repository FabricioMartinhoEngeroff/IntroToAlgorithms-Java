package probabilityLab.probability;

/**
 * Random Variable — CLRS Appendix C.3
 *
 * A random variable X is a function that maps outcomes → numbers.
 *
 * Dev translation:
 *   Takes the messy random world and turns it into numbers you can compute with.
 */
public class RandomVariable {

    /** Maps a coin flip outcome to a numeric value: H → 1, T → 0. */
    public static int coinFlipToInt(String outcome) {
        return switch (outcome.toUpperCase()) {
            case "H", "HEADS" -> 1;
            case "T", "TAILS" -> 0;
            default -> throw new IllegalArgumentException("Unknown outcome: " + outcome);
        };
    }

    /** Maps a dice roll (1-6) to a numeric value (identity, but validates range). */
    public static int diceValue(int roll) {
        if (roll < 1 || roll > 6) {
            throw new IllegalArgumentException("Dice value must be between 1 and 6.");
        }
        return roll;
    }

    public static void demo() {
        System.out.println("=== Random Variable ===");
        System.out.println("Concept: transform random outcomes into numbers to do math on them.");
        System.out.println();

        System.out.println("-- Coin flip: H=1, T=0 --");
        String[] coinOutcomes = {"H", "T", "H", "H", "T"};
        System.out.print("  Outcomes: ");
        for (String o : coinOutcomes) System.out.print(o + " ");
        System.out.println();
        System.out.print("  Values:   ");
        for (String o : coinOutcomes) System.out.print(coinFlipToInt(o) + " ");
        System.out.println();
        System.out.println();

        System.out.println("-- Dice: value maps to itself --");
        int[] diceRolls = {1, 3, 6, 2, 5};
        System.out.print("  Rolls:  ");
        for (int r : diceRolls) System.out.print(r + " ");
        System.out.println();
        System.out.println();

        System.out.println("-- Indicator variable: useful in algorithm analysis --");
        System.out.println("  Define X_i = 1 if event i happens, 0 otherwise.");
        System.out.println("  Then: total successes = X_1 + X_2 + ... + X_n");
        System.out.println("  Expected successes    = E[X_1] + ... + E[X_n]  (linearity!)");
        System.out.println();
        System.out.println("Dev insight: indicator variables are the bridge between");
        System.out.println("  event counting and expected-value analysis in algorithms.");
    }
}
