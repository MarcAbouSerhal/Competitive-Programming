class Center {
    // returns <=2 centers of the tree (vertices that minimize distance from farthest leaf) (O(n))
    public static final ArrayList<Integer> getCenters(ArrayList<Integer>[] adj) {
        int n = adj.length;
        int[] deg = new int[n];
        ArrayList<Integer> nodes = new ArrayList<>();
        for(int u = 0; u < n; ++u) {
            deg[u] = adj[u].size();
            if(deg[u] == 1) {
                nodes.add(i);
                deg[u] = 0;
            }
        }
        int m = nodes.size();
        while(m < n) {
            ArrayList<Integer> newNodes = new ArrayList<>();
            for(int u: nodes) 
                for(int v: adj[u]) 
                    if(--deg[v] == 1) newNodes.add(v);
            m += newNodes.size();
            nodes = newNodes;
        }
        return nodes;
    }
}
