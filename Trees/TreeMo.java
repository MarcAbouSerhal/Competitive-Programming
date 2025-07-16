class TreeMo{
    private static X[] a;
    private static ArrayList<Integer>[] adj;
    private static int[] in, cnt, id;
    private static int tick = 0;
    // (O(n.sqrt(q).(T(add or remove or finding answer)))
    public final static int[] solve(ArrayList<Integer>[] adj, Query[] queries, X[] a) {
        TreeMo.a = a;
        int n = adj.length;
        Tree tree = new Tree(adj);
        TreeMo.adj = adj;
        in = new int[n];
        cnt = new int[n];
        id = new int[n << 1];
        tick = 0;
        dfs(0, -1);
        for(Query q: queries) {
            q.extra = in[tree.lca(q.l, q.r)];
            if(in[q.l] > in[q.r]){
                int temp = in[q.l] + 1;
                q.l = in[q.r] + 1;
                q.r = temp;
            }
            else{
                q.l = in[q.l] + 1;
                q.r = in[q.r] + 1;
            }
            q.index = hilbert_order(q.l, q.r);
        }
        Arrays.sort(queries, (x, y) -> Long.compare(x.index, y.index));
        int[] res = new int[queries.length];
        int curr_l = 0, curr_r = 0;
        for(Query q: queries) {
            while(curr_r < q.r) handle(curr_r++, true);
            while(curr_l > q.l) handle(--curr_l, true);
            while(curr_r > q.r) handle(--curr_r, false);
            while(curr_l < q.l) handle(curr_l++, false);
            handle(q.extra, true);
            res[q.i] = ; // get answer of this range
            // Note: id[q.extra] = lca(u, v)
            handle(q.extra, false);
        }
        return res;
    }
    public final static void add(int u) { /* adds vertex u */ }
    public final static void remove(int u) { /* removes vertex u */ }
    public final static void handle(int u, boolean insert) {
        u = id[u];
        if(insert) {
            if(++cnt[u] == 2) remove(u);
            else add(u);
        }
        else {
            if(--cnt[u] == 1) add(u);
            else remove(u);
        }
    }
    private final static void dfs(int u, int p) {
        in[id[tick] = u] = tick++;
        for(int v: adj[u]) if(v != p) dfs(v, u);
        id[tick++] = u;
    }
    private final static long hilbert_order(int x, int y) {
        int logn = (31 - Integer.numberOfLeadingZeros((y << 1) + 1)) | 1, maxn = (1 << logn) - 1;
        long res = 0;
        for(int s = 1 << (logn - 1); s != 0 ; s >>= 1) {
            boolean rx = (x & s) != 0, ry = (y & s) != 0;
            res = (res << 2) | (rx ? ry ? 2 : 1 : ry ? 3 : 0);
            if(!rx) {
                if(ry) { x ^= maxn; y ^= maxn; }
                x = x ^ y; y = x ^ y; x = x ^ y;
            }
        }
        return res;
    }
    private final static class Tree{
        private int tick = -1;
        private final int[][] d;
        private final int[] depth, in;
        private final ArrayList<Integer>[] adj;
        private final int op(int l, int r) { return depth[l] < depth[r] ? l : r; }
        // (O(nlog(n)))
        public Tree(ArrayList<Integer>[] adj) {
            this.adj = adj;
            int n = adj.length, size = (n << 1) - 1, log = 32 - Integer.numberOfLeadingZeros(size);
            depth = new int[n];
            in = new int[n];
            d = new int[log][size];
            dfs(0, -1);
            for(int j = 1; j < log; ++j)
                for(int i = 0; i + (1 << j) <= size; ++i)
                    d[j][i] = op(d[j - 1][i], d[j - 1][i + (1 << (j - 1))]);
        }
        public final void dfs(int u, int p) {
            d[0][in[u] = ++tick] = u;
            for(int v: adj[u])
                if(v != p) {
                    depth[v] = depth[u] + 1;
                    dfs(v, u);
                    d[0][++tick] = u;
                }
        }
        public final int lca(int a, int b) {
            a = in[a]; 
            b = in[b];
            if(a > b) { a = a ^ b; b = a ^ b; a = a ^ b; }
            int x = 31 - Integer.numberOfLeadingZeros(b - a + 1);
            return op(d[x][a], d[x][b + 1 - (1 << x)]);
        }
    }
}
class Query{
    int l, r, i, extra;
    long index;
    public Query(int u, int v, int i) {
        l = u;
        r = v;
        this.i = i;
    }
}