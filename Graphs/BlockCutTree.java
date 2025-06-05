class BlockCutTree {
    final int[] comp;
    final boolean[] is_cutpoint;
    final ArrayList<Integer>[] adj;
    // (O(n + m))
    public BlockCutTree(ArrayList<Integer>[] adj_) {
        int n = adj_.length;
        BlockCutTree.adj_ = adj_;
        comp = new int[n];
        is_cutpoint = new boolean[n];
        curr = 0;
        comps = new ArrayList<>();
        disc = new int[n];
        low = new int[n];
        vis = new Stack(n);
        for(int u = 0; u < n; ++u, curr = 0)
            if(disc[u] == 0) dfs(u, -1);
        curr = 0;
        for(int u = 0; u < n; ++u) if(is_cutpoint[u]) comp[u] = curr++;
        adj = new ArrayList[curr + comps.size()];
        for(int u = 0; u < adj.length; ++u) adj[u] = new ArrayList<>();
        for(ArrayList<Integer> c: comps) {
            for(int u: c) {
                if(!is_cutpoint[u]) comp[u] = curr;
                else {
                    adj[comp[u]].add(curr);
                    adj[curr].add(comp[u]);
                }
            }
            curr++;
        }
    }   
    private static ArrayList<Integer>[] adj_;
    private static ArrayList<ArrayList<Integer>> comps;
    private static int[] disc, low;
    private static Stack vis;
    private static int curr;
    private final void dfs(int u, int p) {
        disc[u] = low[u] = ++curr;
        vis.add(u);
        for(int v: adj_[u]) {
            if(v == p) continue;
            if(disc[v] != 0) {
                low[u] = min(low[u], disc[v]);
                continue;
            }
            dfs(v, u);
            low[u] = min(low[u], low[v]);
            if(low[v] >= disc[u]) {
                is_cutpoint[u] = disc[u] > 1 || disc[v] > 2;
                ArrayList<Integer> comp = new ArrayList<>();
                comps.add(comp);
                comp.add(u);
                while(comp.get(comp.size() - 1) != v) comp.add(vis.pop());
            }
        }
    }
    private static final int min(int x, int y) { return x < y ? x : y; }
    private static final class Stack {
        private final int[] s;
        private int size = 0;
        public Stack(int n) { s = new int[n]; }
        public final void add(int x) { s[size++] = x; }
        public final int pop() { return s[--size]; }
    }
}
