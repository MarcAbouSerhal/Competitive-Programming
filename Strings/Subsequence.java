interface Subsequence {
    // returns an array c, where LCS(s, t[l, r]) = #{l <= k <= r: c[k] <= l} (O(|s|.|t|))
    public static int[] characteristic(char[] s, char[] t) {
        int n = s.length, m = t.length;
        int[][] ih = new int[n + 1][m + 1], iv = new int[n + 1][m + 1];
        for(int j = 0; j <= m; ++j) ih[0][j] = j;
        for(int i = 1; i <= n; ++i)
            for(int j = 1; j <= m; ++j)
                if(s[i - 1] == t[j - 1]) {
                    ih[i][j] = iv[i][j - 1];
                    iv[i][j] = ih[i - 1][j];
                }
                else {
                    ih[i][j] = Math.max(ih[i - 1][j], iv[i][j - 1]);
                    iv[i][j] = Math.min(ih[i - 1][j], iv[i][j - 1]); 
                }
        return Arrays.copyOfRange(ih[n], 1, m + 1);
    }
    // returns an array c, where c[i] = min{j: t[, i] is subsequence of s[, j]} (O(|s| + |t|))
    public static int[] smallestPrefix(char[] t, char[] s) {
        int n = s.length, m = t.length;
        int[] c = new int[m];
        int p1 = 0, p2 = 0;
        while(p1 < n && p2 < m) {
            if(s[p1] == t[p2]) c[p2++] = p1;
            ++p1;
        }
        for(; p2 < m; ++p2) c[p2] = -1;
        return c;
    }
    // returns an array c, where c[i] = max{j: t[i, ] is subsequence of s[j, ]} (O(|s| + |t|))
    public static int[] smallestSuffix(char[] t, char[] s) {
        int n = s.length, m = t.length;
        int[] c = new int[m];
        int p1 = n - 1, p2 = m - 1;
        while(p1 >= 0 && p2 >= 0) {
            if(s[p1] == t[p2]) c[p2--] = p1;
            --p1;
        }
        for(; p2 >= 0; --p2) c[p2] = -1;
        return c;
    }
    public static boolean isSubsequence(char[] t, char[] s) {
        int n = s.length, m = t.length;
        int p1 = 0, p2 = 0;
        while(p1 < n && p2 < m) {
            if(s[p1] == t[p2]) ++p2;
            ++p1;
        }
        return p2 == m;
    }
}
