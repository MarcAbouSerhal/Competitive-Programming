class FloydWarshall{
    // only uses d[i][j] as long as i <= j
    // d(u,v) = d[min(u,v)][max(u,v)]
    // (O(n^3 / 2))
    public final static void compute_undirected(long[][] d){
        final int n = d.length;
        int i, j;
        for(int k = 0; k < n; ++k){
            i = 0;
            for(; i < k; ++i){
                j = 0;
                for(; j < i; ++j)
                    if(d[j][i] > d[i][k] + d[j][k])
                        d[j][i] = d[i][k] + d[j][k];
            }
            for(; i < n; ++i){
                j = 0;
                for(; j < k; ++j)
                    if(d[j][i] > d[k][i] + d[j][k])
                        d[j][i] = d[k][i] + d[j][k];
                for(; j < i; ++j)
                    if(d[j][i] > d[k][i] + d[k][j])
                        d[j][i] = d[k][i] + d[k][j];
            }
        }
    }
    // normal floyd warshall (O(n^3))
    public final static void compute(long[][] d){
        final int n = d.length;
        int i, j;
        for(int k = 0; k < n; ++k){
            for(i = 0; i < n; ++i){
                for(j = 0; j < n; ++j)
                    if(d[i][j] > d[i][k] + d[k][j])
                        d[i][j] = d[i][j] + d[k][j];
            }
        }
    }
}
