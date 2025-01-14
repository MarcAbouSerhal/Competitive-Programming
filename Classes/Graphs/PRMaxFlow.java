class PRMaxFlow{
    // highest-label push-relabel max flow with gap relabeling heuristic (fancy works that idk the meaning of)
    private final int n;
    private final ArrayList<Edge>[] adj;
    private long[] excess;
    private int[] dist, count;
    private boolean[] active;
    private Stack[] B;
    private int b;
    int[] cut;
    public PRMaxFlow(int n){
        adj = new ArrayList[this.n = n];
        for(int u = 0; u < n; ++u) adj[u] = new ArrayList<>();
    }
    public final void addEdge(int from, int to, long cap){
        adj[from].add(new Edge(from, to, cap, 0, adj[to].size() + (from == to ? 1 : 0)));
        adj[to].add(new Edge(to, from, 0, 0, adj[from].size() - 1));
    }
    // returns max-flow (O(n^2.sqrt(m)))
    public final long getMaxFlow(int s, int t){
        dist = new int[n];
        excess = new long[n];
        count = new int[n + 1];
        active = new boolean[n];
        B = new Stack[n]; for(int i = 0; i < n; ++i) B[i] = new Stack();
        b = 0;

        for(int u = 0; u < n; ++u)
            for(Edge e: adj[u])
                e.flow = 0; //reset all flows to 0

        for(Edge e: adj[s]) excess[s] += e.cap;

        count[0] = n;
        enqueue(s);
        active[t] = true;

        while(b >= 0){
            if(!B[b].isEmpty()){
                int u = B[b].pop();
                active[u] = false;
                discharge(u);
            }
            else --b;
        }

        return excess[t];
    }
    // returns max-flow and stores minimal cut in .cut where:
    // cut[u] = 0 if u in S and cut[u] = 1 if u in T
    public final long getMinCut(int s, int t){
        long ret = getMaxFlow(s, t);
        preflowToFlow(s, t);
        cut = new int[n]; for(int u = 0; u < n; ++u) cut[u] = -1;
        final Queue<Integer> q = new LinkedList<>();

        q.add(s);
        cut[s] = 0;
        while(!q.isEmpty()){
            int u = q.poll();
            for(Edge e: adj[u])
                if(cut[e.to] == -1 && e.cap - e.flow > 0){
                    q.add(e.to);
                    cut[e.to] = 0;
                }
        }

        q.add(t);
        cut[t] = 1;
        while(!q.isEmpty()){
            int u = q.poll();
            for(Edge e: adj[u])
                if(cut[e.to] == -1 && adj[e.to].get(e.index).cap - adj[e.to].get(e.index).flow > 0){
                    q.add(e.to);
                    cut[e.to] = 1;
                }
        }
        return ret;
    }
    private final static int WHITE = 0, GREY = 1, BLACK = 2;
    private final void preflowToFlow(int s, int t){
        final int[] color = new int[n], prev = new int[n], current = new int[n];
        final Stack st = new Stack();
        for(int u = 0; u < n; ++u) prev[u] = -1;
        for(int u = 0; u < n; ++u){
            if(color[u] == WHITE && excess[u] > 0 && u != s && u != t){
                int r = u;
                color[r] = GREY;
                do{
                    while(current[u] != adj[u].size()){
                        Edge e = adj[u].get(current[u]);
                        if(e.cap == 0 && e.flow < 0 && e.to != s && e.to != t){
                            int v = e.to;
                            if(color[v] == WHITE){
                                color[v] = GREY;
                                prev[v] = u;
                                u = v;
                                break;
                            }
                            else if(color[v] == GREY){
                                long amt = e.cap - e.flow;
                                do{
                                    Edge e_ = adj[v].get(current[v]);
                                    amt = min(amt, e_.cap - e_.flow);
                                    if(u != v) v = e_.to;
                                } while (u != v);

                                do{
                                    e = adj[v].get(current[v]);
                                    e.flow += amt;
                                    adj[e.to].get(e.index).flow -= amt;
                                    v = e.to;
                                } while(u != v);

                                int restart = u;
                                for(v = adj[u].get(current[u]).to; u != v; v = e.to){
                                    e = adj[v].get(current[v]);
                                    if(color[v] == WHITE || e.cap == e.flow){
                                        color[e.to] = WHITE;
                                        if(color[v] != WHITE) restart = v;
                                    }
                                }
                                if(restart != u){
                                    ++current[u = restart];
                                    break;
                                }
                            }
                        }
                        ++current[u];
                    }
                    if(current[u] == adj[u].size()){
                        color[u] = BLACK;
                        if(u != s) st.add(u);

                        if(u != r) ++current[u = prev[u]];
                        else break;
                    }
                } while(true);
            }
        }
        while(!st.isEmpty()){
            int u = st.pop();
            Edge e = adj[u].get(0);
            int i = 1;
            while(excess[u] > 0){
                if(e.cap == 0 && e.flow < 0){
                    long amt = min(excess[u], e.cap - e.flow);
                    e.flow += amt;
                    adj[e.to].get(e.index).flow -= amt;
                    excess[u] -= amt;
                    excess[e.to] += amt;
                }
                if(i == adj[u].size()) break;
                e = adj[u].get(i++);
            }
        }
    }
    private final void enqueue(int u){
        if(!active[u] && excess[u] > 0 && dist[u] < n){
            active[u] = true;
            B[dist[u]].add(u);
            b = max(b, dist[u]);
        }
    }
    private final void push(Edge e){
        final int to = e.to, from = e.from;;
        final long amt = min(excess[from], e.cap - e.flow);
        if(dist[from] == dist[to] + 1 && amt > 0){
            e.flow += amt;
            adj[to].get(e.index).flow -= amt;
            excess[to] += amt;
            excess[from] -= amt;
            enqueue(to);
        }
    }
    private final void gap(int k){
        for(int u = 0; u < n; ++u)
            if(dist[u] >= k){
                --count[dist[u]];
                dist[u] = max(dist[u], n);
                ++count[dist[u]];
                enqueue(u);
            }
    }
    private final void relabel(int u){
        --count[dist[u]];
        dist[u] = n;
        for(Edge e: adj[u])
            if(e.cap > e.flow)
                dist[u] = min(dist[u], dist[e.to] + 1);
        ++count[dist[u]];
        enqueue(u);
    }
    private final void discharge(int u){
        for(Edge e: adj[u])
            if(excess[u] > 0) push(e);
            else break;
        if(excess[u] > 0){
            if(count[dist[u]] == 1) gap(dist[u]);
            else relabel(u);
        }
    }
    private static final class Edge{
        int from, to, index;
        long cap, flow;
        public Edge(int from, int to, long cap, long flow, int index) {
            this.from = from;
            this.to = to;
            this.index = index;
            this.cap = cap;
            this.flow = flow;
        }
    }
    private static final int min(int x, int y){ return x < y ? x : y; }
    private static final int max(int x, int y){ return x > y ? x : y; }
    private static final long min(long x, long y){ return x < y ? x : y; }
    private static final class Node{
        final int x;
        final Node next;
        public Node(int x, Node next){ this.x = x; this.next = next; }
    }
    private static final class Stack{
        Node head;
        public Stack(){}
        public final void add(int x){ head = new Node(x, head); }
        public final int pop(){ int x = head.x; head = head.next; return x; }
        public final boolean isEmpty(){ return head == null; }
    }
}
