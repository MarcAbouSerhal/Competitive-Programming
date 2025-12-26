// dp[i, j] = min(i <= k < j) { dp[i, k] + dp[k + 1, j] + c(i, j) } 
// where opt(i, j - 1) <= opt(i, j) <= opt(i + 1, j) (opt(i, j) is best k for [i, j])
// or for all a <= b <= c <= d:
// - c(b, c) <= c(a, d)
// - c(a, c) + c(b, d) <= c(a, d) + c(b, c)
class Knuth {
    // returns dp2 where dp2[i, j] = dp[i, i + j] (O(n^2))
    public final static long[][] minimumConcatenationOrder(int n, CalcFunction a) {
        long[][] dp = new long[n][];
        int[][] opt = new int[n][];
        for (int i = 0; i < n; ++i) {
            opt[i] = new int[n - i];
            dp[i] = new long[n - i];
            dp[i][0] = a.cost(i, i);
        }
        for (int i = n - 2; i >= 0; --i)
            for (int j = 1; i + j < n; ++j) {
                long mn = Long.MAX_VALUE, c = a.cost(i, i + j);
                for (int k = opt[i][j - 1]; k <= Math.min(j - 1, 1 + opt[i + 1][j - 1]); ++k) {
                    long ck = dp[i][k] + dp[i + k + 1][j - k - 1] + c;
                    if (mn >= ck) {
                        opt[i][j] = k; 
                        mn = ck;
                    }  
                }          
                dp[i][j] = mn;
            }
        return dp;
    }
    @FunctionalInterface
    public static interface CalcFunction { long cost(int a, int b); }
}
