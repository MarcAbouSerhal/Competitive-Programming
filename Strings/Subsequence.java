class Subsequence {
    // returns an array c[i], where LCS(s, t[l, r]) = #{l <= k <= r: c[k] <= l} (O(|s|.|t|))
    public final static int[] characteristic(char[] s, char[] t) {
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
}
