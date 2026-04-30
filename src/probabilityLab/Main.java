package probabilityLab;

import probabilityLab.probability.*;
import java.util.Scanner;

/**
 * ProbabilityLabCLRS — Main entry point.
 *
 * Interactive menu covering every concept from CLRS Appendix C.2–C.4:
 *   - Conditional probability
 *   - Independence
 *   - Bayes' theorem
 *   - Random variable & indicator variables
 *   - Expected value + linearity
 *   - Variance
 *   - Geometric distribution
 *   - Binomial distribution
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            printMenu();
            option = readInt(scanner);
            System.out.println();

            switch (option) {
                case 1  -> ConditionalProbability.demo();
                case 2  -> Independence.demo();
                case 3  -> BayesTheorem.demo();
                case 4  -> RandomVariable.demo();
                case 5  -> ExpectedValue.demo();
                case 6  -> Variance.demo();
                case 7  -> GeometricDistribution.demo();
                case 8  -> BinomialDistribution.demo();
                case 0  -> System.out.println("Exiting ProbabilityLab. Bye!");
                default -> System.out.println("Invalid option. Try again.");
            }

        } while (option != 0);

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("======== ProbabilityLab CLRS ========");
        System.out.println(" 1 - Conditional Probability");
        System.out.println(" 2 - Independence");
        System.out.println(" 3 - Bayes' Theorem");
        System.out.println(" 4 - Random Variable & Indicator");
        System.out.println(" 5 - Expected Value + Linearity");
        System.out.println(" 6 - Variance");
        System.out.println(" 7 - Geometric Distribution");
        System.out.println(" 8 - Binomial Distribution");
        System.out.println(" 0 - Exit");
        System.out.println("=====================================");
        System.out.print("Choose an option: ");
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Please enter a valid number: ");
        }
        return scanner.nextInt();
    }
}

