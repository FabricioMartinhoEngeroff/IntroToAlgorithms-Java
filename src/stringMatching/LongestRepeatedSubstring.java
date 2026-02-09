package stringMatching;

public class LongestRepeatedSubstring {

    // encontra a maior substring repetida usando SA + LCP.
    public String find(String text) {
        if (text == null || text.isEmpty()) return "";

        SuffixArray saObj = new SuffixArray(text);
        int[] sa = saObj.getSa();
        int[] lcp = saObj.getLcp();

        int bestLen = 0;
        int bestPos = 0;

        for (int i = 1; i < lcp.length; i++) {
            if (lcp[i] > bestLen) {
                bestLen = lcp[i];
                bestPos = sa[i];
            }
        }

        if (bestLen == 0) return "";
        return text.substring(bestPos, bestPos + bestLen);
    }
}