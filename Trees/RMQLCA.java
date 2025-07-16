class Tree{
    private int tick = -1;
    private final int[][] d;
    private final int[] depth, in, out;
    private final ArrayList<Integer>[] adj;
    private final int op(int l, int r){ return depth[l] < depth[r] ? l : r; }
    // (O(nlog(n)))
    public Tree(ArrayList<Integer>[] adj){
        this.adj = adj;
        int n = adj.length, size = (n << 1) - 1, log = 32 - Integer.numberOfLeadingZeros(size);
        depth = new int[n];
        in = new int[n];
        out = new int[n];
        d = new int[log][size];
        dfs(0, -1);
        for(int j = 1; j < log; ++j)
            for(int i = 0; i + (1 << j) <= size; ++i)
                d[j][i] = op(d[j - 1][i], d[j - 1][i + (1 << (j - 1))]);
    }
    public final void dfs(int u, int p){
        d[0][in[u] = ++tick] = u;
        for(int v: adj[u])
            if(v != p){
                depth[v] = depth[u] + 1;
                dfs(v, u);
                d[0][++tick] = u;
            }
        out[u] = tick;
    }
    // (O(1))
    public final int lca(int a, int b){
        a = in[a]; 
        b = in[b];
        if(a > b){ a = a ^ b; b = a ^ b; a = a ^ b; }
        int x = 31 - Integer.numberOfLeadingZeros(b - a + 1);
        return op(d[x][a], d[x][b + 1 - (1 << x)]);
    }
    // returns whether u is an ancestor of v (O(1))
    public final boolean isAncestor(int u, int v) { return in[u] <= in[v] && out[u] >= out[v]; }
    // returns whether w is on the path from u to v (O(1))
    public final boolean isOnPath(int u, int v, int w) { return depth[w] >= depth[lca(u, v)] && (isAncestor(w, u) || isAncestor(w, v)); }
}