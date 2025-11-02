class CentroidDecompostion{
    public int[] p;
    private ArrayList<Integer>[] adj;
    private int[] sz;
    private boolean[] dead;
    public CentroidDecompostion(ArrayList<Integer>[] adj) {
        int n = adj.length;
        p = new int[n];
        sz = new int[n];
        dead = new boolean[n];
        this.adj = adj;
        build(0, -1);
        sz = null;
        dead = null;
    }
    public final void build(int u, int par) {
        dfs(u, par); 
        int c = centroid(u, par, sz[u] >> 1);
        // do something with tree rooted at c / paths passing by c here
        p[c] = par;
        dead[c] = true;
        for(int v: adj[u])
            if(!dead[v]) build(v, c);
    }
    private final void dfs(int u, int par) {
        sz[u] = 1;
        for(int v : adj[u])
            if(v != par) {
                dfs(v, u);
                sz[u] += sz[v];
            }
    }
    private final int centroid(int u, int par, int l) {
        for(int v: adj[u])
            if(v != par && sz[v] > l) return centroid(v, u, l);
        return u;
    }
}
