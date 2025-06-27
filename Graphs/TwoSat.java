class TwoSat{
    private final ArrayList<Integer>[] adj;
    private final int n, vars;
    private int aux;
    public TwoSat(int n_) {
        n = vars = n_;
        n_ <<= 1;
        adj = new ArrayList[n_];
        for(int i = 0; i < n_; ++i) adj[i] = new ArrayList<>();
    }
    // aux_ is number of auxiliary vertices needed 
    // (each AMO of k >= 3 variables uses k auxiliary variables)
    public TwoSat(int n_, int aux_) {
        aux = vars = n_;
        n = n_ + aux_;
        n_ = n << 1;
        adj = new ArrayList[n_];
        for(int i = 0; i < n_; ++i) adj[i] = new ArrayList<>();
    }
    public final void IMPLIES(int u, int v) { adj[u].add(v); adj[NOT(v)].add(NOT(u)); }
    public final void OR(int u, int v){ adj[NOT(u)].add(v); adj[NOT(v)].add(u); }
    public final void XOR(int u, int v) { adj[NOT(u)].add(v); adj[NOT(v)].add(u); adj[u].add(NOT(v)); adj[v].add(NOT(u)); }
    public final void IFF(int u, int v) { adj[u].add(v); adj[v].add(u); adj[NOT(u)].add(NOT(v)); adj[NOT(v)].add(NOT(u)); }
    public final void NAND(int u, int v) { adj[u].add(NOT(v)); adj[v].add(NOT(u)); }
    public final void NOR(int u, int v) { force(NOT(u)); force(NOT(v)); }
    public final void force(int u) { adj[NOT(u)].add(u); }
    // removes the clause (u) (O(1)) (assumes last thing added to adj[NOT(u)] was u)
    public final void remove(int u) { adj[NOT(u)].remove(adj[NOT(u)].size() - 1); }
    // adds the clause (at most one of u[0], u[1], ..., u[k - 1] is true) (O(k))
    public final void atMostOneTrue(ArrayList<Integer> u) {
        if(u.size() <= 1) return;
        if(u.size() == 2) NAND(u.get(0), u.get(1));
        else {
            int k = u.size();
            OR(NOT(u.get(0)), aux);
            for(int i = 1; i < k; ++i) {
                OR(NOT(u.get(i)), aux + 1);
                OR(NOT(u.get(i)), NOT(aux));
                OR(NOT(aux), ++aux);
            }
            ++aux;
        }
    }
    // adds the clause (at most one of u[0], u[1], ..., u[k - 1] is false) (O(k))
    public final void atMostOneFalse(ArrayList<Integer> u) {
        if(u.size() <= 1) return;
        if(u.size() == 2) OR(u.get(0), u.get(1));
        else {
            int k = u.size();
            OR(u.get(0), NOT(aux));
            for(int i = 1; i < k; ++i) {
                OR(u.get(i), NOT(aux + 1));
                OR(u.get(i), aux);
                OR(aux, NOT(++aux));
            }
            ++aux;
        }  
    }
    // returns vertex corresponding to complement of u (O(1))
    public final int NOT(int u) { return u < n ? u + n : u - n; }
    // returns valid configuration if there exists one, null if there isn't (O(n + m))
    public final boolean[] answer(){
        tick = group_id = 0;
        id = new int[n << 1]; for(int i = 0; i < n << 1; ++i) id[i] = -1;
        t = new int[n << 1];
        s = new Stack(n << 1);
        for(int i = 0; i < n << 1; ++i)
            if(t[i] == 0) dfs(i);
        boolean[] ans = new boolean[vars];
        for(int u = 0; u < vars; ++u){
            if(id[u] == id[u + n]) return null;
            ans[u] = id[u] < id[u + n];
        }
        return ans;
    }
    private static int tick;
    private static int[] t;
    private static int[] id;
    private static Stack s;
    private static int group_id;
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
    private static final int min(int a, int b){ return a < b ? a : b; }
}
