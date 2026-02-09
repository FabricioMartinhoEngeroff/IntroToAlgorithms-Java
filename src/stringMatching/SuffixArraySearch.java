package stringMatching;

public class SuffixArraySearch implements StringSearchAlgorithm {

    @Override
    public SearchResult search(String text, String pattern) {
        SearchResult result = new SearchResult();
        if (pattern == null || pattern.isEmpty()) return result;

        long start = System.nanoTime();
        long comparisons = 0;

        SuffixArray saObj = new SuffixArray(text);
        int[] sa = saObj.getSa();

        // busca binária para achar o intervalo de sufixos que começam com pattern.
        int left = lowerBound(text, sa, pattern);
        int right = upperBound(text, sa, pattern);

        for (int i = left; i < right; i++) {
            result.addPosition(sa[i]);
        }

        //  aqui não contamos comparações com precisão (depende do compare),
        // mas deixo o campo pra manter o padrão.
        result.setComparisons(comparisons);
        result.setElapsedNanos(System.nanoTime() - start);
        return result;
    }

    @Override
    public String name() {
        return "Suffix Array Search (Binary Search)";
    }

    private int lowerBound(String text, int[] sa, String pattern) {
        int lo = 0, hi = sa.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            int cmp = compareSuffixWithPattern(text, sa[mid], pattern);
            if (cmp < 0) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private int upperBound(String text, int[] sa, String pattern) {
        int lo = 0, hi = sa.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            int cmp = compareSuffixWithPattern(text, sa[mid], pattern);
            //  <= 0 empurra pra direita pra achar fim do intervalo
            if (cmp <= 0) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private int compareSuffixWithPattern(String text, int startIndex, String pattern) {
        // compara text[startIndex..] com pattern, só até pattern acabar.
        int n = text.length();
        int m = pattern.length();
        int i = 0;

        while (i < m && startIndex + i < n) {
            char a = text.charAt(startIndex + i);
            char b = pattern.charAt(i);
            if (a != b) return Character.compare(a, b);
            i++;
        }

        // Se pattern acabou -> significa que pattern é prefixo do sufixo -> "igual" (0).
        if (i == m) return 0;

        //se texto acabou antes do pattern, sufixo é menor.
        return -1;
    }
}