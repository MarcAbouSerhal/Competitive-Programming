// O(min(F, nm)m)
class EdmondsKarpMaxFlow {
    private final int n;
    public final ArrayList<Edge>[] adj; // for(MinCostMaxFlow.Edge e: g.adj[u])
    public final boolean[] cut;
    private final Edge[] par;
    public EdmondsKarpMaxFlow(int n) {
        this.n = n;
        adj = new ArrayList[n];
        for(int i = 0; i < n; ++i) adj[i] = new ArrayList<>();
        cut = new boolean[n];
        par = new Edge[n];
    }
    public final void addEdge(int from , int to, long cap) {
        adj[from].add(new Edge(from, to, adj[to].size(), cap));
        adj[to].add(new Edge(to, from, adj[from].size() - 1, 0));
    }
    // min cut is stored in .cut
    public final long getMaxFlow(int s, int t) {
        for(int u = 0; u < n; ++u)
            for(Edge e: adj[u])
                e.flow = 0; // reset all flows to 0
        long flow = 0;
        while(true) {
            cut[s] = true;
            Queue<Integer> q = new LinkedList<>();
            q.add(s);
            while(!q.isEmpty()){
                s = q.poll();
                for(Edge e: adj[s])
                    if(e.cap > e.flow && !cut[e.to]) {
                        par[e.to] = e;
                        q.add(e.to);
                        cut[e.to] = true;
                    }
            }
            if(!cut[t]) break;
            for(int u = 0; u < n; ++u) cut[u] = false;
            long fl = Long.MAX_VALUE;
            for(Edge x = par[t]; x != null; x = par[x.from]) fl = Math.min(fl, x.cap - x.flow);
            flow += fl;
            for(Edge x = par[t]; x != null; x = par[x.from]) {
                x.flow += fl;
                adj[x.to].get(x.rev).flow -= fl;
            }
        }
        return flow;
    }
    public static final class Edge {
        final int from, to, rev;
        final long cap;  
        long flow = 0;
        public Edge(int from, int to, int rev, long cap) {
            this.from = from;
            this.to = to;
            this.rev = rev;
            this.cap = cap;
        }
    }
}
