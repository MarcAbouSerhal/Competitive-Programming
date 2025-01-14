class HamiltonianPath{
    // returns some hamiltonian path starting from start, null if there isn't one
    // works for both directed and undirected (O(n!))
    public final static int[] getPath(ArrayList<Integer>[] adj, int start, boolean directed){
        final int n = adj.length;
        Hamiltonian.adj = adj;
        if(directed){
            revAdj = new ArrayList[n];
            for(int i = 0; i < n; ++i) revAdj[i] = new ArrayList<>();
            for(int u = 0; u < n; ++u)
                for(int v: adj[u])
                    revAdj[v].add(u);
        }
        else revAdj = adj;
        remaining = new int[n]; for(int u = 0; u < n; ++u) remaining[u] = adj[u].size();
        visited = new boolean[n];
        path = new int[n];
        tick = 0;
        found = false;
        dfs(start);
        return tick == n ? path : null;
    }
    private static ArrayList<Integer>[] adj, revAdj;
    private static int[] remaining;
    private static boolean[] visited;
    private static int[] path;
    private static int tick;
    private static boolean found;
    private final static void dfs(int u){
        if(tick == adj.length - 1){
            path[tick++] = u;
            found = true;
            return;
        }
        visited[u] = true;
        for(int v: revAdj[u]) --remaining[v];
        // sort possible next vertices by how many remaining neighbors they have
        Collections.sort(adj[u], (x, y) -> remaining[x] - remaining[y]);
        for(int v: adj[u]){
            if(visited[v]) continue;
            path[tick++] = u;
            dfs(v);
            if(found) return;
            --tick;
        }
        visited[u] = false;
        for(int v: revAdj[u]) ++remaining[v];
    }
}
