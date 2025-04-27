// solves knapsack problems in O(n.max(c))
// returns null if there is no solution
class Knapsack{
    public final static boolean[] subsetSum(int[] c, int t) {
        int n = c.length;
        int cmax = 0;
        for(int x: c) cmax = max(x, cmax);
        int b = 0, sumb = 0;
        while(b < n && sumb + c[b] <= t) sumb += c[b++];
        if(b == n && sumb != t) return null;
        int[] dp = new int[cmax << 1]; 
        for(int i = 0; i < cmax << 1; ++i) dp[i] = -1;
        int[][] prev = new int[n][cmax << 1];
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < cmax << 1; ++j) prev[i][j] = -1;
        dp[sumb - t + cmax - 1] = b;
        for(int i = b; i < n; ++i) {
            int[] newdp = new int[cmax << 1], prev_cur = prev[i];
            for(int j = 0; j < cmax << 1; ++j) newdp[j] = dp[j];
            for(int j = cmax - 1; j >= 0; --j)
                if(newdp[j + c[i]] < newdp[j]) {
                    prev_cur[j + c[i]] = -2;
                    newdp[j + c[i]] = newdp[j];
                }
            for(int j = (cmax << 1) - 1; j >= cmax; --j) 
                for(int k = newdp[j] - 1; k >= max(dp[j], 0); --k) 
                    if(newdp[j - c[k]] < k) {
                        prev_cur[j - c[k]] = k;
                        newdp[j - c[k]] = k;
                    }
            dp = newdp;
        }
        if(dp[cmax - 1] == -1) return null;
        boolean[] ans = new boolean[n];
        int i = n - 1, j = cmax - 1;
        while(i >= b) {
            int p = prev[i][j];
            if(p == -2) {
                ans[i] = !ans[i];
                j -= c[i--];
            }
            else if(p == -1) --i;
            else {
                ans[p] = !ans[p];
                j += c[p];
            }
        }
        while(i >= 0) ans[i] = !ans[i--];
        return ans;
    }
    public final static int max(int a, int b) { return a > b ? a : b; }
}