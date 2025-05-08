class HeavyLightDecomposition{
    private final int[] p, d, idx, root;
    private final DS[] ds;
    private final int edgeProp;

    private static ArrayList<Integer>[] adj;
    private static X[] a, chain;
    private static int[] heavyChild;
    private static int chainSize = 0;
    // set edgeProp to true if values are on edges: a[u] = value on (u, parent[u]) and a[src] is ignored
    public HeavyLightDecomposition(ArrayList<Integer>[] adj, X[] a) { this(adj, a, 0, false); }
    public HeavyLightDecomposition(ArrayList<Integer>[] adj, X[] a, boolean edgeProp) { this(adj, a, 0, edgeProp); }
    public HeavyLightDecomposition(ArrayList<Integer>[] adj, X[] a, int src) { this(adj, a, src, false); }
    public HeavyLightDecomposition(ArrayList<Integer>[] adj, X[] a, int src, boolean edgeProp) {
        int n = adj.length;
        p = new int[n];
        d = new int[n];
        idx = new int[n];
        root = new int[n];
        ds = new DS[n];
        this.edgeProp = edgeProp ? 1 : 0;
        HeavyLightDecomposition.adj = adj;
        HeavyLightDecomposition.a = a;
        chain = new X[n];
        heavyChild = new int[n];

        dfs1(src, -1);
        dfs2(src, src);
        chain = null;
        heavyChild = null; // trash unused arrays
    }
    // sets a[u] to x (O(T(DS)))
    private final void set(int u, X x) { ds[root[u]].set(idx[u], x); }
    // returns property f the path from u to v (O(log(n)T(DS)))
    private final X get(int u, int v) {
        X ans = null;
        while(root[u] != root[v]) {
            if(d[root[u]] < d[root[v]]) { u ^= v; v ^= u; u ^= v; }
            ans = op(ans, ds[root[u]].get(0, idx[u]));
            u = p[root[u]];
        }
        if(u == v && edgeProp == 1) return ans;
        return op(ans, ds[root[u]].get(min(idx[u], idx[v]) + edgeProp, max(idx[u], idx[v])));
    }
    // CHANGE THESE FUNCTIONS
    private static final X op(X a, X b){
        if(a == null) return b;
        if(b == null) return a;
        // define associative operation here (f(f(a,b),c)=f(a,f(b,c)))
    }
    private final int dfs1(int u, int par) {
        p[u] = par;
        int size = 1, heavySize = 0;
        for(int v: adj[u]) 
            if(v != par) {
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
        idx[u] = chainSize;
        root[u] = rt;
        chain[chainSize++] = a[u];
        if(adj[u].isEmpty() || (adj[u].size() == 1 && p[u] != -1)) {
            X[] resizedChain = new X[chainSize];
            for(int i = 0; i < chainSize; ++i) resizedChain[i] = chain[i];
            ds[rt] = new DS(resizedChain);
            chainSize = 0;
        }
        else {
            dfs2(heavyChild[u], rt);
            for(int v: adj[u])
                if(v != p[u] && v != heavyChild[u])
                    dfs2(v, v);
        }
    }
    private static final int min(int x, int y) { return x < y ? x : y; }
    private static final int max(int x, int y) { return x > y ? x : y; }
}
