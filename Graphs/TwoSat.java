class TwoSat{
    private final ArrayList<Integer>[] adj;
    private final int n;
    public TwoSat(int n_){
        n = n_;
        n_ <<= 1;
        adj = new ArrayList[n_];
        for(int i = 0; i < n_; ++i) adj[i] = new ArrayList<>();
    }
    // adds the clause (u has u_value OR v has v_value) (O(1))
    public final void addClause(int u, boolean u_value, int v, boolean v_value){
        adj[(u << 1) + (u_value ? 0 : 1)].add((v << 1) + (v_value ? 1 : 0));
        adj[(v << 1) + (v_value ? 0 : 1)].add((u << 1) + (u_value ? 1 : 0));
    }
    // returns valid configuration if there exists one, null if there isn't (O(n + m))
    public final boolean[] answer(){
        final int[] id = getSCCs();
        final boolean[] ans = new boolean[n];
        for(int i = 0; i < n; ++i){
            if(id[i << 1] == id[(i << 1) + 1]) return null;
            ans[i] = id[i << 1] > id[(i << 1) + 1];
        }
        return ans;
    }
    private static int tick;
    private static int[] t;
    private static int[] id;
    private static Stack s;
    private static int group_id;
    public final int[] getSCCs(){
        tick = group_id = 0;
        int n = adj.length;
        id = new int[n]; for(int i=0; i<n; ++i) id[i] = -1;
        t = new int[n];
        s = new Stack(n);
        for(int i=0; i < n; ++i)
            if(t[i] == 0)
                dfs(i);
        return id;
    }
    private final int dfs(int u){
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
    private final static class Stack{
        private final int[] s;
        private int size = 0;
        public Stack(int size){ s = new int[size]; }
        public final void add(int u){ s[size++] = u; }
        public final int pop(){ return s[--size]; }
    }
    private static final int min(int a, int b){ return a<b ? a : b; }
}
