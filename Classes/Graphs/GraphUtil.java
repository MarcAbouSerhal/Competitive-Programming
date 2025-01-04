class GraphUtil{
    private final static class Stack{
        private final int[] s;
        private int size = 0;
        public Stack(int size){ s = new int[size]; }
        public final void add(int u){ s[size++] = u; }
        public final int pop(){ return s[--size]; }
    }
    private static ArrayList<Integer>[] adj;
    private static int tick;
    private static int[] t;
    private static final int min(int a, int b){ return a<b ? a : b; }
    
    static ArrayList<Edge> bridges;
    // returns list of bridges in undirected graph
    public static final ArrayList<Edge> getBridges(ArrayList<Integer>[] adj){
        GraphUtil.adj = adj; tick = 0;
        int n = adj.length;
        t = new int[n];
        bridges = new ArrayList<>();
        for(int i = 0; i < n; ++i)
            if(t[i] == 0)
                dfs(i, -1);
        return bridges;
    }
    private static final void dfs(int u, int p){
        int currentTime = t[u] = ++tick;
        for(int v: adj[u]){
            if(v == p) continue;
            if(t[v] == 0) dfs(v, u);
            t[u] = min(t[u], t[v]);
            if(currentTime < t[v]) bridges.add(new Edge(u, v));
        }
    }

    private static int[] id;
    private static Stack s;
    private static int group_id;
    // returns SCC id for each vertex in directed graph
    // id[u] < id[v] -> surely u can't reach v 
    public static final int[] getSCCs(ArrayList<Integer>[] adj){
        tick = group_id = 0;
        GraphUtil.adj = adj;
        int n = adj.length;
        id = new int[n]; for(int i=0; i<n; ++i) id[i] = -1;
        t = new int[n];
        s = new Stack(n);
        for(int i=0; i < n; ++i)
            if(t[i] == 0)
                dfs(i);
        return id;
    }
    private static final int dfs(int u){
        int low = t[u] = ++tick;
        s.add(u);
        for(int v: adj[u])
            if(id[v] == -1)
                low = min(low, t[v] != 0 ? t[v] : dfs(v));
        if(low == t[u]){
            int v = -1;
            while(v != u) id[v = s.pop()] = group_id;
            ++group_id;
        }
        return low;
    }

    private static ArrayList<Integer> cutpoints;
    private static int[] tin;
    // returns list of cutpoints in undirected graph
    public static final ArrayList<Integer> getCutpoints(ArrayList<Integer>[] adj){
        GraphUtil.adj = adj;
        int n = adj.length;
        tick = 0;
        t = new int[n]; tin = new int[n];
        cutpoints = new ArrayList<>();
        for(int i = 0; i < n; ++i)
            if(t[i] == 0)
                dfs2(i, -1);
        return cutpoints;
    }
    private static final void dfs2(int u, int p){
        tin[u] = t[u] = ++tick;
        int children = 0;
        for(int v: adj[u]){
            if(v == p) continue;
            if(t[v] == 0){
                dfs2(v, u);
                t[u] = min(t[u], t[v]);
                if(t[v] >= tin[u] && p != -1)
                    cutpoints.add(u);
                ++children;
            }
            else t[u] = min(t[u], tin[v]);
        }
        if(p == -1 && children > 1) cutpoints.add(u);
    }
}
class Edge{
    final int u, v;
    public Edge(int u, int v){ this.u=u; this.v=v; }
}
