class TopologicalSort {
    // returns a topological sorting of G, null if there is a cycle (O(n + m))
    public final static ArrayList<Integer> topoSort(ArrayList<Integer>[] adj){
        final int n = adj.length;
        int[] indegree = new int[n];
        for(int u = 0; u < n; ++u)
            for(int v: adj[u])
                ++indegree[v];
        Queue<Integer> q = new LinkedList<>();
        for(int u = 0; u < n; ++u)
            if(indegree[u] == 0)
                q.add(u);
        ArrayList<Integer> ans = new ArrayList<>();
        while(!q.isEmpty()){
            final int u = q.poll();
            ans.add(u);
            for(int v: adj[u])
                if(--indegree[v] == 0)
                    q.add(v);
        }
        if(ans.size() != n) return null;
        else return ans;
    }

    // returns the lexicographically minimum topological sorting of G, null if there's a cycle (O(nlog(n)+m))
    public final static ArrayList<Integer> minimumTopoSort(ArrayList<Integer>[] adj){
        final int n = adj.length;
        int[] indegree = new int[n];
        for(int u = 0; u < n; ++u)
            for(int v: adj[u])
                ++indegree[v];
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for(int u = 0; u < n; ++u)
            if(indegree[u] == 0)
                q.add(u);
        ArrayList<Integer> ans = new ArrayList<>();
        while(!q.isEmpty()){
            final int u = q.poll();
            ans.add(u);
            for(int v: adj[u])
                if(--indegree[v] == 0)
                    q.add(v);
        }
        if(ans.size() != n) return null;
        else return ans;
    }
}
