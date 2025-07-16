class SuffixArray {
    private final static int alphabet = 128;
    int n;
    final char[] s;
    final int[] sa, lcp; // sa[i] is position of ith smallest suffix of s
    final int[][] g;
    // O(nlog(n))
    public SuffixArray(char[] s_) {
        n = s_.length + 1;
        int log = 31 - Integer.numberOfLeadingZeros(n), k = 0, cls = 1;
        s = new char[n];
        for(int i = 0; i + 1 < n; ++i) s[i] = s_[i];
        s[n - 1] = '$';
        sa = new int[n];
        lcp = new int[n - 2];
        g = new int[log + 2][]; g[0] = new int[n];
        int[] cnt = new int[Math.max(n, alphabet)], np = new int[n], rank = new int[n - 1], ng;
        for(char c: s) ++cnt[c];
        for(int i = 1; i < alphabet; ++i) cnt[i] += cnt[i - 1];
        for(int i = 0; i < n; ++i) sa[--cnt[s[i]]] = i;
        for(int i = 1; i < n; ++i) {
            if(s[sa[i]] != s[sa[i - 1]]) ++cls;
            g[0][sa[i]] = cls - 1;
        }
        for(int h = 0, po = 1; h <= log; ++h, po <<= 1) {
            ng = new int[n];
            for(int i = 0; i < n; ++i) {
                np[i] = sa[i] - po;
                if(np[i] < 0) np[i] += n;
            }
            for(int i = 0; i < cls; i++) cnt[i] = 0;
            for(int x: np) ++cnt[g[h][x]];
            for(int i = 1; i < cls; ++i) cnt[i] += cnt[i - 1];
            for(int i = n - 1; i >= 0; --i) sa[--cnt[g[h][np[i]]]] = np[i];
            ng[sa[0]] = 0;
            cls = 1;
            for(int i = 1; i < n; ++i) {
                int x = sa[i], y = sa[i - 1];
                if(g[h][x] != g[h][y] || g[h][(x + po) % n] != g[h][(y + po) % n]) ++cls;
                ng[x] = cls - 1;
            }
            g[h + 1] = ng;
        }
        --n;
        for(int i = 0; i < n; ++i) rank[sa[i] = sa[i + 1]] = i;
        for(int i = 0; i < n; ++i) {
            if(rank[i] == n - 1) {
                k = 0;
                continue;
            }
            int j = sa[rank[i] + 1];
            while (i + k < n && j + k < n && s[i + k] == s[j + k]) ++k;
            lcp[rank[i]] = k;
            if (k != 0) --k;
        }
    }
    // compares s[i...i + l - 1] and s[j...j + l - 1] (O(1)) -1 if <, 1 if >, 0 if =
    public final int compare(int i, int j, int l) {
        int k = 31 - Integer.numberOfLeadingZeros(l), nextI = i + l - (1 << k), nextJ = j + l - (1 << k);
        return g[k][i] < g[k][j] ? -1 : g[k][i] > g[k][j] ? 1 : g[k][nextI] < g[k][nextJ] ? -1 : g[k][nextI] > g[k][nextJ] ? 1 : 0;
    }
}