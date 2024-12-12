class GraphUtil{
    static class Stack{
        int[] s;
        int size = 0;
        public Stack(int size){ s = new int[size]; }
        public void add(int u){ s[size++] = u; }
        public int pop(){ return s[--size]; }
    }
    static ArrayList<Integer>[] ad;
    static int tick;
    static int[] t;
    private static int min(int a, int b){ return a<b ? a : b; }
    
    static ArrayList<Edge> bridges;
    public static ArrayList<Edge> getBridges(ArrayList<Integer>[] adj){
        ad = adj; tick = 0;
        t = new int[adj.length];
        bridges = new ArrayList<>();
        for(int i=0; i<adj.length; ++i)
            if(t[i] == 0)
                dfs(i,-1);
        return bridges;
    }
    private static void dfs(int u, int p){
        int currentTime = t[u] = ++tick;
        for(int v: ad[u]){
            if(v==p) continue;
            if(t[v] == 0) dfs(v,u);
            t[u] = min(t[u], t[v]);
            if(currentTime < t[v]) bridges.add(new Edge(u, v));
        }
    }

    static int[] id;
    static Stack s;
    static int group_id;
    // id[u] < id[v] -> surely u can't reach v 
    public static int[] getSCCs(ArrayList<Integer>[] adj){
        tick = group_id = 0;
        ad = adj;
        int n = adj.length;
        id = new int[n]; for(int i=0; i<n; ++i) id[i] = -1;
        t = new int[n];
        s = new Stack(n);
        for(int i=0; i<n; ++i)
            if(t[i]==0)
                dfs(i);
        return id;
    }
    private static int dfs(int u){
        int low = t[u] = ++tick;
        s.add(u);
        for(int v: ad[u])
            if(id[v] == -1)
                low = min(low, t[v] != 0 ? t[v] : dfs(v));
        if(low == t[u]){
            int v = -1;
            while(v != u) id[v = s.pop()] = group_id;
            ++group_id;
        }
        return low;
    }

    static ArrayList<Integer> cutpoints;
    static int[] tin;
    public static ArrayList<Integer> getCutpoints(ArrayList<Integer>[] adj){
        ad = adj;
        int n = adj.length;
        tick = 0;
        t = new int[n]; tin = new int[n];
        cutpoints = new ArrayList<>();
        for(int i=0; i<n; ++i)
            if(t[i] == 0)
                dfs2(i,-1);
        return cutpoints;
    }
    private static void dfs2(int u, int p){
        tin[u] = t[u] = ++tick;
        int children = 0;
        for(int v: ad[u]){
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
    int u, v;
    public Edge(int u, int v){ this.u=u; this.v=v; }
}
