class SmallToLarge {
    static ArrayList<Integer>[] adj;
    static int[] size, bigChild;
    static ArrayList<Integer>[] subtree;
    public static void start(ArrayList<Integer>[] adj, int src) {
        SmallToLarge.adj = adj;
        int n = adj.length;
        size = new int[n];
        bigChild = new int[n];
        calc_size(src, -1);
        dfs(src, -1, true);
    }
    public static void calc_size(int u, int p) {
        size[u] = 1;
        bigChild[u] = -1;
        subtree[u] = new ArrayList<>();
        for(int v: adj[u]) 
            if(v != p) {
                calc_size(v, u);
                size[v] += size[u];
                if(bigChild[u] == -1 || size[bigChild[u]] < size[v]) bigChild[u] = v;
            }
    }
    public static void dfs(int u, int p, boolean keep) {
        for(int v: adj[u])
            if(v != p && v != bigChild[u])
                dfs(v, u, false);
        if(bigChild[u] != -1) {
            dfs(bigChild[u], u, true);
            subtree[u] = subtree[bigChild[u]];
        }
        subtree[u].add(u); 
        // add contribution of u
        for(int v: adj[u])
            if(v != p && v != bigChild[u])
                for(int w: subtree[v]) {
                    subtree[u].add(w);
                    // add contribution of w
                }
        // answer queries
        if(!keep) 
            for(int v: subtree[u])
                // remove contribution of v
    }
}
