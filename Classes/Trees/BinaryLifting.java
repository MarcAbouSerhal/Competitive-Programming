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
        final int n = adj.length;
        log = 32 - Integer.numberOfLeadingZeros(n);
        up = new int[n][log];
        depth = new int[n];
        dfs(0, -1);
        for(int j = 1; j < log; ++j)
            for(int i = 0; i < n; ++i)
                up[i][j] = up[i][j - 1] == -1 ? -1 : up[up[i][j - 1]][j - 1];
        // if we're finding op(vertices): 
        // opUp[u][i] is op(vertices on u -> up[u][i] excluding up[u][i])
        // if we're finding op(edges):
        // opUp[u][i] is op(edges on u -> up[u][i])
    }
    private final void dfs(int u, int p) {
        up[u][0] = p; 
        for(int v: adj[u])
            if(v != p){
                depth[v] = depth[u] + 1;
                dfs(v, u);
            }     
    }
    // returns ith vertex on the simple path from u to v (0-indexed) (O(log(n)))
    public final int ithOnPath(int u, int v, int i) {
        int sz = 1 + depth[u] + depth[v] + (depth[lca(u, v)] << 1);
        if(depth[u] < depth[v]){
            u ^= v; v ^= u; u ^= v; i = sz - i - 1;
        }
        int w = kthAncestor(u, depth[u] - depth[v]);
        if(w == v) return kthAncestor(u, i); // lca(u, v) = v
        int x = w, y = v;
        for(int l = log - 1; l >= 0; --l)
            if(up[x][l] != up[y][l]){
                x = up[x][l];
                y = up[y][l];
            }
        x = up[x][0];
        int d1 = depth[u] - depth[x] + 1, d2 = sz - d1;
        return i < d1 ? kthAncestor(u, i) : kthAncestor(v, d2 - 1 - i + d1); 
    }
    // returns kth ancestor of u (O(log(n)))
    public final int kthAncestor(int u, int k) {
        for(int i = 0; i < log; ++i)
            if((k & (1 << i)) != 0)
                u = up[u][i];
        return u;
    }
    // returns lca(u, v) (O(log(n)))
    public final int lca(int a, int b) {
        if(depth[a] < depth[b]){ a ^= b; b ^= a; a ^= b; }
        a = kthAncestor(a, depth[a] - depth[b]);
        if(a == b) return a;
        for(int i = log - 1; i >= 0; --i)
            if(up[a][i] != up[b][i]){
                a = up[a][i];
                b = up[b][i];
            }
        return up[a][0];
    }
    // returns whether u is an ancestor of v (or u == v) O(log(n))
    public final boolean isAncestor(int u, int v) {
        return depth[u] <= depth[v] && kthAncestor(u, depth[v] - depth[u]) == v;
    }
    // returns whether w is on the path from u to v (O(log(n)))
     public final boolean isOnPath(int u, int v, int w) {
        return depth[w] >= depth[lca(u, v)] && (isAncestor(w, u) || isAncestor(w, v));
    }
    // (O(T(op).log(n)))
    // public final ... opOfPath(int u, int v) {
    //     if(depth[u] < depth[v]){ u ^= v; v ^= u; u ^= v; }
    //     int k = depth[u] - depth[v];
    //     res = identity;
    //     for(int i = 0; i < log; ++i)
    //         if((k & (1 << i)) != 0){
    //             res = op(res, opUp[u][i]);
    //             u = up[u][i];
    //         }
    //     if(u == v){
    //         // if we're finding op(vertices), include u
    //         return res;
    //     }
    //     for(int i = log - 1; i >= 0; --i)
    //         if(up[u][i]!=up[v][i]){
    //             res = op(res, op(opUp[u][i], opUp[v][i]));
    //             u = up[u][i];
    //             v = up[v][i];
    //         }
    //     // if we're finding op(vertices), include up[u][0]
    //     return res;
    // }
}
