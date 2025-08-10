class Tree{
    // X[][] opUp
    // add any necessary members needed for prop
    private final int[][] up;
    private final int[] depth;
    private final ArrayList<Integer>[] adj;
    private final int log;
    // (O(nlog(n)))
    public Tree(ArrayList<Integer>[] adj) {
        this.adj = adj;
        int n = adj.length;
        log = 32 - Integer.numberOfLeadingZeros(n);
        up = new int[log][n];
        depth = new int[n];
        dfs(0, -1);
        for(int j = 1; j < log; ++j)
            for(int i = 0; i < n; ++i)
                up[j][i] = up[j - 1][i] == -1 ? -1 : up[j - 1][up[i][j - 1]];
        // if we're finding op(vertices): 
        // opUp[u][i] is op(vertices on u -> up[u][i] excluding up[u][i])
        // if we're finding op(edges):
        // opUp[u][i] is op(edges on u -> up[u][i])
    }
    // returns kth ancestor of u or -1 if there's no such node (O(log(n)))
    public final int kthAncestor(int u, int k) {
        if(depth[u] < k) return -1;
        for(int i = 0; i < log; ++i)
            if((k & (1 << i)) != 0) u = up[i][u];
        return u;
    }
    // returns lca(u, v) (O(log(n)))
    public final int lca(int a, int b) {
        if(depth[a] < depth[b]){ a ^= b; b ^= a; a ^= b; }
        a = kthAncestor(a, depth[a] - depth[b]);
        if(a == b) return a;
        for(int i = log - 1; i >= 0; --i)
            if(up[i][a] != up[i][b]){
                a = up[i][a];
                b = up[i][b];
            }
        return up[0][a];
    }
    // returns whether u is an ancestor of v (or u == v) O(log(n))
    public final boolean isAncestor(int u, int v) { return depth[u] <= depth[v] && kthAncestor(v, depth[v] - depth[u]) == u; }
    // returns whether w is on the path from u to v (O(log(n)))
    public final boolean isOnPath(int u, int v, int w) { return depth[w] >= depth[lca(u, v)] && (isAncestor(w, u) || isAncestor(w, v)); }
    // returns ith vertex on the simple path from u to v (0-indexed) (O(log(n)))
    public final int ithOnPath(int u, int v, int i) {
        int d = depth[lca(u, v)];
        return depth[u] >= d + i ? kthAncestor(u, i) : kthAncestor(v, depth[u] + depth[v] - (d << 1) - i);
    }
    // returns distance between u and v (O(log(n)))
    public final int distance(int u, int v) {
        if(u == 0) return depth[v];
        if(v == 0) return depth[u];
        return depth[u] + depth[v] - (depth[lca(u, v)] << 1);
    }
    // (O(T(op).log(n)))
    // public final ... opOfPath(int u, int v) {
    //     if(depth[u] < depth[v]){ u ^= v; v ^= u; u ^= v; }
    //     int k = depth[u] - depth[v];
    //     res = identity;
    //     for(int i = 0; i < log; ++i)
    //         if((k & (1 << i)) != 0){
    //             res = op(res, opUp[i][u]);
    //             u = up[i][u];
    //         }
    //     if(u == v){
    //         // if we're finding op(vertices), include u
    //         return res;
    //     }
    //     for(int i = log - 1; i >= 0; --i)
    //         if(up[i][u]!=up[i][v]){
    //             res = op(res, op(opUp[i][u], opUp[i][v]));
    //             u = up[i][u];
    //             v = up[i][v];
    //         }
    //     // if we're finding op(vertices), include up[0][u]
    //     return res;
    // }
    // private methods
    private final void dfs(int u, int p) {
        up[0][u] = p; 
        for(int v: adj[u])
            if(v != p){
                depth[v] = depth[u] + 1;
                dfs(v, u);
            }     
    }
}
