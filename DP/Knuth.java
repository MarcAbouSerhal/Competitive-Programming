// Use: long[][] dp = Knuth.compute(n, (l, r) -> cost(l, r));
// for all a <= b <= c <= d:
// - cost(b, c) <= cost(a, d)
// - cost(a, c) + cost(b, d) <= cost(a, d) + cost(b, c)
// or opt(i, j - 1) <= opt(i, j) <= opt(i + 1, j) where op(i, j) is best k for [i...j]
class Knuth {
    // dp[i][j] = answer for [i...i + j] (O(n^2))
    public final static long[][] compute(int n, CalcFunction a) {
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
                for (int k = opt[i][j - 1]; k <= min(j - 1, 1 + opt[i + 1][j - 1]); ++k) {
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
    private final static int min(int a, int b) { return a < b ? a : b; }
}
