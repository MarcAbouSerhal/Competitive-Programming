class HeavyLightDecomposition{
    private final int[] p, d, in, out, root;
    private final DS ds;
    private final int edgeProp;
    private static ArrayList<Integer>[] adj;
    private static X[] a, b;
    private static int[] heavyChild;
    private static int tick = 0;
    // set edgeProp to true if values are on edges: a[u] = value on (u, parent[u]) and a[src] is ignored
    public HeavyLightDecomposition(ArrayList<Integer>[] adj, X[] a, int src, boolean edgeProp) {
        int n = adj.length;
        p = new int[n];
        d = new int[n];
        in = new int[n];
        out = new int[n];
        root = new int[n];
        this.edgeProp = edgeProp ? 1 : 0;
        HeavyLightDecomposition.adj = adj;
        HeavyLightDecomposition.a = a;
        HeavyLightDecomposition.b = new X[n];
        HeavyLightDecomposition.heavyChild = new int[n];
        HeavyLightDecomposition.tick = 0;
        dfs1(src, -1);
        dfs2(src, src);
        ds = new DS(b);
        HeavyLightDecomposition.b = null;
        HeavyLightDecomposition.heavyChild = null;
    }
    // sets value[u] or value[u, p(u)] to x (O(T(DS)))
    public final void set(int u, X x) { ds.set(in[u], x); }
    // returns property of the path from u to v (O(log(n)T(DS)))
    public final X getPathProperty(int u, int v) {
        X ans = id;
        while(root[u] != root[v]) {
            if(d[root[u]] < d[root[v]]) { u ^= v; v ^= u; u ^= v; }
            ans = op(ans, ds.get(in[root[u]], in[u]));
            u = p[root[u]];
        }
        if(u == v && edgeProp == 1) return ans;
        return op(ans, ds.get(Math.min(in[u], in[v]) + edgeProp, Math.max(in[u], in[v])));
    }
    // returns property of subtree of u (O(T(DS)))
    public final X getSubtreeProperty(int u) { return ds.get(in[u] + edgeProp, out[u]); }
    // CHANGE THESE
    private static final X id;
    private static final X op(X a, X b){ /* define associative operation here (f(f(a,b),c)=f(a,f(b,c))) */ }
    private final int dfs1(int u, int par) {
        p[u] = par;
        int size = 1, heavySize = 0;
        for(int v: adj[u]) {
            if(v == par) continue;
            d[v] = d[u] + 1;
            int childSize = dfs1(v, u);
            size += childSize;
            if(childSize > heavySize) {
                heavySize = childSize;
                heavyChild[u] = v;
            }
        }
        return size;
    }
    private final void dfs2(int u, int rt) {
        b[in[u] = tick++] = a[u];
        if(adj[u].size() != 0 && (adj[u].size() != 1 || p[u] == -1)) {
            dfs2(heavyChild[u], rt);
            for(int v: adj[u])
                if(v != p[u] && v != heavyChild[u]) dfs2(v, v);
        }
        out[u] = tick - 1;
    }
}
