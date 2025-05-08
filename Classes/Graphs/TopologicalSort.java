class TopologicalSort {
    // returns a valid topological sorting, null if there is a cycle (O(n + m))
    public final static int[] topoSort(ArrayList<Integer>[] adj) {
        final int n = adj.length;
        int[] indegree = new int[n];
        for (int u = 0; u < n; ++u)
            for (int v : adj[u])
                ++indegree[v];
        Queue<Integer> q = new LinkedList<>();
        for (int u = 0; u < n; ++u)
            if (indegree[u] == 0)
                q.add(u);
        int[] ans = new int[n];
        int idx = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            ans[idx++] = u;
            for (int v : adj[u])
                if (--indegree[v] == 0)
                    q.add(v);
        }
        if (idx != n) return null;
        else return ans;
    }
    // returns the lexicographically minimum topological sorting, null if there's a cycle (O(nlog(n)+m))
    public final static int[] minimumTopoSort(ArrayList<Integer>[] adj) {
        final int n = adj.length;
        int[] indegree = new int[n];
        for (int u = 0; u < n; ++u)
            for (int v : adj[u])
                ++indegree[v];
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int u = 0; u < n; ++u)
            if (indegree[u] == 0)
                q.add(u);
        int[] ans = new int[n];
        int idx = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            ans[idx++] = u;
            for (int v : adj[u])
                if (--indegree[v] == 0)
                    q.add(v);
        }
        if (idx != n) return null;
        else return ans;
    }
}
