package stringMatching;

public class NaiveSearch implements StringSearchAlgorithm {

    @Override
    public SearchResult search(String text, String pattern) {
        SearchResult result = new SearchResult();
        if (pattern == null || pattern.isEmpty()) return result;

        long start = System.nanoTime();
        long comparisons = 0;

        for (int s = 0; s <= text.length() - pattern.length(); s++) {
            int j = 0;
            while (j < pattern.length()) {
                comparisons++;
                if (text.charAt(s + j) != pattern.charAt(j)) break;
                j++;
            }
            if (j == pattern.length()) result.addPosition(s);
        }

        result.setComparisons(comparisons);
        result.setElapsedNanos(System.nanoTime() - start);
        return result;
    }

    @Override
    public String name() {
        return "Naive Search";
    }
}
