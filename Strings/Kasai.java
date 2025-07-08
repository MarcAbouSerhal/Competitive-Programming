class Kasai {
    // returns lcp where lcp[i] = lcp(s[p[i]...n - 1], s[p[i + 1]...n - 1]) where p is suffix array (O(n))
    public final static int[] lcp(char[] s, int[] p) {
        int n = s.length, k = 0;
        int[] rank = new int[n];
        for(int i = 0; i < n; ++i) rank[p[i]] = i;
        int[] lcp = new int[n - 1];
        for (int i = 0; i < n; ++i) {
            if (rank[i] == n - 1) {
                k = 0;
                continue;
            }
            int j = p[rank[i] + 1];
            while (i + k < n && j + k < n && s[i + k] == s[j + k]) ++k;
            lcp[rank[i]] = k;
            if (k != 0) --k;
        }
        return lcp;
    }
}
