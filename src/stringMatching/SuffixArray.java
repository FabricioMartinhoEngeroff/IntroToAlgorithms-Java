package stringMatching;

import java.util.Arrays;

public class SuffixArray {
    private final String text;
    private final int[] sa;   //  SA = índices dos sufixos ordenados
    private final int[] lcp;  //  LCP entre vizinhos no SA (Kasai)

    public SuffixArray(String text) {
        this.text = text;
        this.sa = buildSuffixArrayDoubling(text);
        this.lcp = buildLcpKasai(text, sa);
    }

    public int[] getSa() { return sa; }
    public int[] getLcp() { return lcp; }
    public String getText() { return text; }

    // ====== SA (doubling ranks) ======

    private int[] buildSuffixArrayDoubling(String s) {
        int n = s.length();
        int[] sa = new int[n];
        int[] rank = new int[n];
        int[] tmp = new int[n];

        // rank inicial baseado no char (ordem Unicode).
        for (int i = 0; i < n; i++) {
            sa[i] = i;
            rank[i] = s.charAt(i);
        }

        // dobra o tamanho comparado: 1, 2, 4, 8...
        for (int k = 1; k < n; k <<= 1) {
            final int kk = k;

            // ordena por par (rank[i], rank[i+k]) usando sort simples (Arrays.sort).
            Integer[] boxed = new Integer[n];
            for (int i = 0; i < n; i++) boxed[i] = sa[i];

            Arrays.sort(boxed, (a, b) -> {
                if (rank[a] != rank[b]) return Integer.compare(rank[a], rank[b]);
                int ra = (a + kk < n) ? rank[a + kk] : -1;
                int rb = (b + kk < n) ? rank[b + kk] : -1;
                return Integer.compare(ra, rb);
            });

            for (int i = 0; i < n; i++) sa[i] = boxed[i];

            // recalcula ranks compactos.
            tmp[sa[0]] = 0;
            for (int i = 1; i < n; i++) {
                int cur = sa[i], prev = sa[i - 1];

                boolean diffLeft = rank[cur] != rank[prev];
                int curRight = (cur + kk < n) ? rank[cur + kk] : -1;
                int prevRight = (prev + kk < n) ? rank[prev + kk] : -1;
                boolean diffRight = curRight != prevRight;

                tmp[cur] = tmp[prev] + ((diffLeft || diffRight) ? 1 : 0);
            }

            System.arraycopy(tmp, 0, rank, 0, n);

            // se ranks já são 0..n-1, acabou.
            if (rank[sa[n - 1]] == n - 1) break;
        }

        return sa;
    }

    // ====== LCP (Kasai) ======

    private int[] buildLcpKasai(String s, int[] sa) {
        int n = s.length();
        int[] rank = new int[n];
        int[] lcp = new int[n];

        // rank[pos] = posição de pos no SA
        for (int i = 0; i < n; i++) rank[sa[i]] = i;

        int h = 0;
        for (int i = 0; i < n; i++) {
            int r = rank[i];
            if (r == 0) { //primeiro não tem anterior
                lcp[r] = 0;
                continue;
            }
            int j = sa[r - 1];
            while (i + h < n && j + h < n && s.charAt(i + h) == s.charAt(j + h)) {
                h++;
            }
            lcp[r] = h;
            if (h > 0) h--;
        }
        return lcp;
    }
}
