class CentroidDecompostion{
    private static TreeSet<Integer>[] adj;
    private static int[] p, sz;
    // returns parent array of centroid decomposition of tree (O(nlog(n)))
    public static final int[] buildTree(ArrayList<Integer>[] adj_) {
        int n = adj_.length;
        adj = new TreeSet[n];
        p = new int[n];
        sz = new int[n];
        for(int i = 0; i < n; ++i) adj[i] = new TreeSet<>(adj_[i]);
        build(0, -1);
        adj = null;
        sz = null;
        return p;
    }
    public static final void build(int u, int par) {
        dfs(u, par); 
        int c = centroid(u, par, sz[u] >> 1);
        // do something with tree rooted at c / paths passing by c here
        p[c] = par;
        ArrayList<Integer> temp = new ArrayList<>(adj[c]);
        for(int v: temp) {
            adj[c].remove(v);
            adj[v].remove(c);
            build(v, c);
        }
    }
    private static final void dfs(int u, int par) {
        sz[u] = 1;
        for(int v : adj[u])
            if(v != par) {
                dfs(v, u);
                sz[u] += sz[v];
            }
    }
    private static final int centroid(int u, int par, int l) {
        for(int v: adj[u])
            if(v != par && sz[v] > l)
                return centroid(v, u, l);
        return u;
    }
}
