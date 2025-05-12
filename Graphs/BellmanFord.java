class BellmanFord{
    // if you want to calculate max distance:
    // use inf = Long.MIN_VALUE / 2, can change every < to >
    static final long inf = Long.MAX_VALUE / 2;
    final Long[] d; 
    // if d[u] = BellmanFord.inf, then can get a better path to u
    // if d[u] = null, then u is not reachable from s
    final int[] p;
    // (O(n.m))
    public BellmanFord(int s, ArrayList<Edge>[] adj){
        final int n = adj.length;
        d = new Long[n];
        p = new int[n];
        d[s] = 0L; p[0] = -1; 
        for(int i = 0; i < s; ++i) d[i] = inf;
        for(int i = s + 1; i < n; ++i) d[i] = inf;
        for(int i = 1; i < n; ++i)
            for(int u = 0; u < n; ++u)
                if(d[u] != inf)
                    for(Edge e: adj[u])
                        if(d[u] + e.w < d[e.v]){
                            p[e.v] = u;
                            d[e.v] = d[u] + e.w;
                        }
        final boolean[] reachable = new boolean[n], bad = new boolean[n];
        for(int u = 0; u < n; ++u)
            for(Edge e: adj[u])
                if(d[u] + e.w < d[e.v]){
                    d[e.v] = d[u] + e.w;
                    bad[e.v] = true;
                }
        reachable[s] = true;
        dfs(s, adj, reachable);
        for(int u = 0; u < n; ++u)
            if(reachable[u] && bad[u])  
                dfs(u, adj, bad);
        for(int u = 0; u < n; ++u)
            if(!reachable[u]) d[u] = null;
            else if(bad[u]) d[u] = inf;
    }
    private static final void dfs(int u, ArrayList<Edge>[] adj, final boolean[] reachable){
        for(Edge e: adj[u])
            if(!reachable[e.v]){
                reachable[e.v] = true;
                dfs(e.v, adj, reachable);
            }   
    }
    // returns neg cycle if it exists, null if there is none
    // stolen from Wxssim <3
    public static final int[] getNegCycle(ArrayList<Edge>[] adj){
        final int n = adj.length;
        long[] d  = new long[n];
        int[] p = new int[n];
        int x = -1;
        for(int i = 0; i < n; ++i){
            x = -1;
            for(int u = 0; u < n; ++u)
                for(Edge e: adj[u])
                    if(d[u] + e.w < d[e.v]){
                        p[x = e.v] = u;
                        d[x] = d[u] + e.w;
                    }
        }
        if(x == -1) return null;
        for(int i = 0; i < n; ++i) x = p[x];
        ArrayList<Integer> cycle = new ArrayList<>(); cycle.add(x);
        for(int u = p[x]; u != x; u = p[u])
            cycle.add(u);
        cycle.add(x);
        int cycleSize = cycle.size();
        int[] reversed = new int[cycleSize];
        for(int i = 0; i < cycleSize; ++i)
            reversed[i] = cycle.get(cycleSize - 1 - i);
        return reversed;
    }
}
class Edge{
    final int v;
    final long w;
    public Edge(int v, long w){ this.v = v; this.w = w; }
}
