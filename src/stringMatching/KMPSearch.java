package stringMatching;

public class KMPSearch implements StringSearchAlgorithm {

    @Override
    public SearchResult search(String text, String pattern) {
        SearchResult result = new SearchResult();
        if (pattern == null || pattern.isEmpty()) return result;

        long start = System.nanoTime();
        long comparisons = 0;

        int[] pi = computePrefixFunction(pattern);

        int q = 0;

        for (int i = 0; i < text.length(); i++) {
            while (q > 0) {
                comparisons++;
                if (pattern.charAt(q) == text.charAt(i)) break;
                q = pi[q - 1];
            }

            comparisons++;
            if (pattern.charAt(q) == text.charAt(i)) q++;

            if (q == pattern.length()) {
                result.addPosition(i - pattern.length() + 1);
                q = pi[q - 1];
            }
        }

        result.setComparisons(comparisons);
        result.setElapsedNanos(System.nanoTime() - start);
        return result;
    }

    @Override
    public String name() {
        return "KMP Search (Prefix Function)";
    }

    private int[] computePrefixFunction(String pattern) {
        int m = pattern.length();
        int[] pi = new int[m];
        int k = 0;

        for (int q = 1; q < m; q++) {
            while (k > 0 && pattern.charAt(k) != pattern.charAt(q)) {
                k = pi[k - 1];
            }
            if (pattern.charAt(k) == pattern.charAt(q)) {
                k++;
            }
            pi[q] = k;
        }
        return pi;
    }
}