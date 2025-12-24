// dp[i] = min(0 < j <= i) { a[j - 1] + c(j, i) } (dp[0] = 0)
// where opt(i) <= opt(i + 1) (opt(i) is best j for [i])
// or for all a <= b <= c <= d: c(a, c) + c(b, d) <= c(a, d) + c(b, c)
class DivideAndConquer {
    static final long INF = Long.MAX_VALUE >> 1;
    // (O(nlog(n)))
    static long[] solve(long[] a, CalcFunction c) {
        int n = a.length;
        DivideAndConquer.a = a;
        DivideAndConquer.dp = new long[n];
        DivideAndConquer.c = c;
        compute(1, n - 1, 1, n - 1);
        return dp;
    }
    static CalcFunction c;
    static long[] a, dp;
    static void compute(int l, int r, int pl, int pr) {
        if(l > r) return;
        int mid = (l + r) >> 1, opt = 1;
        dp[mid] = INF;
        for(int i = pl; i <= Math.min(pr, mid); ++i) {
            long val = a[i - 1] + c.cost(i, mid);
            if(val < dp[mid]) {
                dp[mid] = val;
                opt = i;
            }
        }
        compute(l, mid - 1, pl, opt);
        compute(mid + 1, r, opt, pr);
    }
    @FunctionalInterface
    public static interface CalcFunction { long cost(int a, int b); }
}
