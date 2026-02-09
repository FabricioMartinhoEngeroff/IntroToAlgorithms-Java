package stringMatching;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<StringSearchAlgorithm> algorithms = List.of(
                new NaiveSearch(),
                new KMPSearch(),
                new SuffixArraySearch()
        );

        while (true) {
            System.out.println("\n=== String Algorithms Playground ===");
            System.out.println("1) Naive Search");
            System.out.println("2) KMP Search");
            System.out.println("3) Suffix Array Search");
            System.out.println("4) Longest Repeated Substring (SA + LCP)");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            String choice = sc.nextLine().trim();
            if (choice.equals("0")) break;

            // input do usuário com um default bom pra testar.
            System.out.print("Text   (default: 'ratatat'): ");
            String text = sc.nextLine();
            if (text.isBlank()) text = "ratatat";

            if (choice.equals("4")) {
                LongestRepeatedSubstring lrs = new LongestRepeatedSubstring();
                String ans = lrs.find(text);
                System.out.println("Longest Repeated Substring: '" + ans + "'");
                continue;
            }

            System.out.print("Pattern(default: 'at'): ");
            String pattern = sc.nextLine();
            if (pattern.isBlank()) pattern = "at";

            StringSearchAlgorithm algo = switch (choice) {
                case "1" -> algorithms.get(0);
                case "2" -> algorithms.get(1);
                case "3" -> algorithms.get(2);
                default -> null;
            };

            if (algo == null) {
                System.out.println("Invalid option.");
                continue;
            }

            SearchResult res = algo.search(text, pattern);

            System.out.println("\nAlgorithm: " + algo.name());
            System.out.println("Text:     " + text);
            System.out.println("Pattern:  " + pattern);
            System.out.println("Result:   " + res);

            // Mostra o SA e LCP quando o usuário usar suffix array (pra visualizar).
            if (algo instanceof SuffixArraySearch) {
                SuffixArray saObj = new SuffixArray(text);
                System.out.println("SA:   " + Arrays.toString(saObj.getSa()));
                System.out.println("LCP:  " + Arrays.toString(saObj.getLcp()));
            }
        }

        System.out.println("Bye!");
        sc.close();
    }
}
