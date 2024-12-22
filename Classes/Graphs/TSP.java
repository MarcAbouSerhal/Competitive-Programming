class TSP{
    private final static long inf = Long.MAX_VALUE;
    private final static long min(long x, long y){ return x < y ? x : y; }
    private final static int bitCount(int x){
        int ans = 0;
        for(int i = 0; i < 21; ++i)
            if((x & (1 << i)) != 0) ++ans;
        return ans;
    }
    // assumes start is 0
    public final static long tsp(long[][] d){
        int n = d.length;
        long[][] g = new long[(1 << (n - 1)) - 1][n - 1];
        for(int k = 0; k < n - 1; ++k)
            g[(1 << k) - 1][k] = d[0][k + 1];
        int[][] masks = new int[(1 << (n - 1))][2];
        for(int i = 0; i < masks.length; ++i){
            masks[i][0] = i;
            masks[i][1] = bitCount(i);
        }
        Arrays.sort(masks, (x,y) -> x[1] - y[1]);
        for(int[] maskp: masks){
            int mask = maskp[0];
            if((mask & (mask - 1)) == 0) continue;
            for(int k = 0; k < n - 1; ++k)
                if((mask & (1 << k)) != 0){
                    g[mask - 1][k] = inf;
                    for(int m = 0; m < n - 1; ++m)
                        if(k != m && (mask & (1 << m))!= 0)
                            g[mask - 1][k] = min(g[mask - 1][k], g[(mask ^ (1 << k)) - 1][m] + d[m + 1][k + 1]);
                } 
        }
        long opt = inf;
        for(int i = 0; i < n - 1; ++i)
            opt = min(opt, g[(1 << (n - 1)) - 2][i] + d[i + 1][0]);
        return opt;
    }
}
