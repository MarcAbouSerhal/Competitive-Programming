class TSP{
    private final static int inf = Integer.MAX_VALUE;
    private final static int min(int x, int y){ return x < y ? x : y; }
    // assumes start is 0
    public static final int tsp(int[][] d){
        int n = d.length;
        int[][] g = new int[1 << (n - 1)][n - 1];
        for(int k = 0; k < n - 1; ++k)
            g[1 << k][k] = d[0][k + 1];
        for(int mask = 3; mask < 1 << (n - 1); ++mask){
            if((mask & (mask - 1)) == 0) continue;
            for(int k = 0; k < n - 1; ++k)
                if((mask & (1 << k)) != 0){
                    g[mask][k] = inf;
                    int m;
                    for(m = 0; m < k; ++m)
                        if((mask & (1 << m))!= 0)
                            g[mask][k] = min(g[mask][k], g[mask ^ (1 << k)][m] + d[m + 1][k + 1]);
                    for(m = k + 1; m < n - 1; ++m)
                        if((mask & (1 << m))!= 0)
                            g[mask][k] = min(g[mask][k], g[mask ^ (1 << k)][m] + d[m + 1][k + 1]);
                } 
        }
        int opt = inf;
        for(int i = 0; i < n - 1; ++i)
            opt = min(opt, g[(1 << (n - 1)) - 1][i] + d[i + 1][0]);
        return opt;
    }
}
