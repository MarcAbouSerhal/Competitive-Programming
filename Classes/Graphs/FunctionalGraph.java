class FunctionalGraph{
    private static int cycles;
    private static int[] next;
    final int[] cycle_id;
    // -1  : part of a tree
    // >=0 : id of the cycle the vertex belongs to
    final ArrayList<Integer>[] cycle;
    // returns vertices of cycle with that id
    final int[] tree_id;
    // -1  : part of a cycle
    // >=0 : id of the tree the vertex belongs to 
    final int[] cycle_index;
    // given a vertex u that belongs to a cycle, returns its index in the cycle it belongs to (cycle[id[u]].index(u))
    final int[] leading_to;
    // given a vertex u that belongs to a tree, return  what vertex of a cycle that tree points to
    final int[] cycle_dist;
    // given a vertex u, returns distance from u to the cycle it leads to
    // cycle_dist[u] = 0 iff u is part of a cycle
    final int log;
    final int[][] nxt;
    public FunctionalGraph(int[] next){
        final int n = next.length;
        cycle_id = new int[n]; 
        cycle_index = new int[n];
        cycle = new ArrayList[n];
        cycle_dist = new int[n];
        tree_id = new int[n];
        leading_to = new int[n]; 
        for(int u = 0; u < n; ++u){ cycle_id[u] = -2; tree_id[u] = -1; }
        FunctionalGraph.next = next;
        cycles = 0;
        for(int u = 0; u < n; ++u){
            if(cycle_id[u] != -2) continue;
            // if next[u] = u, vertices incorrectly includes u twice, deal with this seperately
            if(next[u] == u){
                ArrayList<Integer> vertices = new ArrayList<>();
                vertices.add(u);
                cycle_id[u] = cycles;
                cycle[cycles++] = vertices;
                continue;
            }
            ArrayList<Integer> path = new ArrayList<>();
            path.add(u);
            cycle_id[u] = -1;
            int at = u;
            while(cycle_id[next[at]] == -2){
                cycle_id[at = next[at]] = -1; // so not to infinite loop
                path.add(at);
            }
            ArrayList<Integer> vertices = new ArrayList<>();
            int firstOfCycle = next[at];
            boolean in_cycle = false;
            for (int i : path) { 
                if(!in_cycle) in_cycle = i == firstOfCycle;
                if (in_cycle){
                    cycle_index[i] = vertices.size();
                    vertices.add(i);
                    cycle_id[i] = cycles;
                }
            }
            if(in_cycle) cycle[cycles++] = vertices;
        }
        cycles = 0; // here cycles counts number of trees, just trying to use less memory
        for(int u = 0; u < n; ++u)
            if(cycle_id[u] == -1 && tree_id[u] == -1)
                dfs(u);
        // this last part is for kth next (remove if not used)
        log = 32 - Integer.numberOfLeadingZeros(n);
        nxt = new int[n][log];
        for(int i = 0; i < n; ++i) nxt[i][0] = next[i];
        for(int j = 1; j < log; ++j)
            for(int i = 0; i < n; ++i)
                nxt[i][j] = nxt[nxt[i][j - 1]][j - 1];
    }
    // (O(log(n)) if u was and stays in a tree, otherwise O(1))
    public final int kthNext(int u, int k){
        if(cycle_dist[u] > k){
            for(int i = 0; i < log; ++i)
                if((k & (1 << i)) != 0)
                    u = nxt[u][i];
            return u;
        }
        if(cycle_dist[u] > 0){
            k -= cycle_dist[u];
            u = leading_to[u];
        }
        final ArrayList<Integer> cyc = cycle[cycle_id[u]];
        return cyc.get((cycle_index[u] + k) % cyc.size());
    }
    private final void dfs(int u){
        int to = next[u];
        if(cycle_id[to] != -1){
            leading_to[u] = to;
            cycle_dist[u] = 1;
            tree_id[u] = cycles++;
        }
        else{
            if(tree_id[to] == -1) dfs(to);
            leading_to[u] = leading_to[to];
            tree_id[u] = tree_id[to];
            cycle_dist[u] = cycle_dist[to] + 1;
        }
    }
}
