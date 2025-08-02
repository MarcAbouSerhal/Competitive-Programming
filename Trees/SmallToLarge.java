class SmallToLarge {
    static ArrayList<Integer>[] adj;
    static int[] size, bigChild, in, out, order;
    static int tick;
    public static void start(ArrayList<Integer>[] adj, int src) {
        SmallToLarge.adj = adj;
        int n = adj.length;
        size = new int[n];
        bigChild = new int[n];
        in = new int[n];
        out = new int[n];
        order = new int[n];
        tick = 0;
        calc_size(src, -1);
        dfs(src, -1, true);
    }
    public static void calc_size(int u, int p) {
        size[u] = 1;
        bigChild[u] = -1;
        order[in[u] = tick++] = u;
        for(int v: adj[u]) 
            if(v != p) {
                calc_size(v, u);
                size[v] += size[u];
                if(bigChild[u] == -1 || size[bigChild[u]] < size[v]) bigChild[u] = v;
            }
        out[u] = tick - 1;
    }
    public static void dfs(int u, int p, boolean keep) {
        for(int v: adj[u])
            if(v != p && v != bigChild[u])
                dfs(v, u, false);
        if(bigChild[u] != -1) dfs(bigChild[u], u, true);
        // add contribution of u
        for(int v: adj[u])
            if(v != p && v != bigChild[u])
                for(int i = in[v]; i <= out[v]; ++i) {
                    int w = order[i];
                    // add contribution of w
                }
        // answer queries
        if(!keep) 
            for(int i = in[u]; i <= out[u]; ++i) {
                int v = order[i];
                // remove contribution of v
            }
    }
}
