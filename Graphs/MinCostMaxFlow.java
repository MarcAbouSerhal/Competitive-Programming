class MinCostMaxFlow {
    private final static long inf = Long.MAX_VALUE >> 1;
    private final int n;
    public final ArrayList<Edge>[] adj; // for(MinCostMaxFlow.Edge e: g.adj[u])
    private final long[] dist, pi;
    private final Edge[] par;
    public MinCostMaxFlow(int n) {
        this.n = n;
        adj = new ArrayList[n];
        for(int i = 0; i < n; ++i) adj[i] = new ArrayList<>();
        dist = new long[n];
        pi = new long[n];
        par = new Edge[n];
    }
    public final void addEdge(int from , int to, long cap, long cost) {
        adj[from].add(new Edge(from, to, adj[to].size(), cap, cost));
        adj[to].add(new Edge(to, from, adj[from].size() - 1, 0, -cost));
    }
    // returns {max flow, min cost} (O(Fmlog(n)))
    public final long[] getMinCostMaxFlow(int s, int t) {
        for(int u = 0; u < n; ++u)
            for(Edge e: adj[u])
                e.flow = 0; // reset all flows to 0
        long flow = 0, cost = 0;
        while(true) {
            path(s);
            if(dist[t] == inf) break;
            long fl = inf;
            for(Edge x = par[t]; x != null; x = par[x.from])
                fl = Math.min(fl, x.cap - x.flow);
            flow += fl;
            for(Edge x = par[t]; x != null; x = par[x.from]) {
                x.flow += fl;
                adj[x.to].get(x.rev).flow -= fl;
            }
        }
        for(int u = 0; u < n; ++u)
            for(Edge e: adj[u]) cost += e.cost * e.flow;
        return new long[] {flow, cost >> 1};
    }
    public final long[] getMinCostMaxFlow(int s, int t, boolean negCostEdges) {
        if(negCostEdges) setpi(s);
        return getMinCostMaxFlow(s, t);
    }
    private final void path(int s) {
        for(int u = 0; u < n; ++u) dist[u] = inf;
        dist[s] = 0;
        PQ pq = new PQ(s, dist);
        while(pq.size() != 0){
            s = pq.poll();
            if(dist[s] == inf) break;
            for(Edge e: adj[s])
                if(e.cap > e.flow) {
                    long val = dist[s] + pi[s] - pi[e.to] + e.cost;
                    if(dist[e.to] > val){
                        par[e.to] = e;
                        pq.decrease_key(e.to, val);
                    } 
                }          
        }
        for(s = 0; s < n; ++s) pi[s] = Math.min(pi[s] + dist[s], inf);
    }
    private final void setpi(int s) {
        for(int u = 0; u < n; ++u) pi[u] = inf;
        pi[s] = 0;
        int it = n;
        boolean ch = true;
        long d;
        while(it-- != 0 && ch) {
            ch = false;
            for(s = 0; s < n; ++s)
                if(pi[s] != inf)
                    for(Edge e: adj[s]) 
                        if(e.cap != 0 && (d = pi[s] + e.cost) < pi[e.to]) {
                            pi[e.to] = d; ch = true;
                        }
        }
    }
    public static final class Edge {
        final int from, to, rev;
        final long cap, cost;  
        long flow = 0;
        public Edge(int from, int to, int rev, long cap, long cost) {
            this.from = from;
            this.to = to;
            this.rev = rev;
            this.cap = cap;
            this.cost = cost;
        }
    }
}
