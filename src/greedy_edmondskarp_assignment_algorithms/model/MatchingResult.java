package greedy_edmondskarp_assignment_algorithms.model;

import java.util.List;

public class MatchingResult {

    private String algorithmName;
    private List<Match> matches;
    private int totalValue;

    public MatchingResult(String algorithmName, List<Match> matches, int totalValue) {
        this.algorithmName = algorithmName;
        this.matches = matches;
        this.totalValue = totalValue;
    }

    public void print() {
        System.out.println("\n=== " + algorithmName + " ===");

        if (matches.isEmpty()) {
            System.out.println("No matches found.");
        } else {
            matches.forEach(m -> System.out.println(" - " + m));
        }
        System.out.println("Total Value = " + totalValue);
    }
}
