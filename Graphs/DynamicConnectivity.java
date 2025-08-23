class DynamicConnectivity {
    private int t = 0;
    private final int leaves;
    private final int[] x, y;
    private final ArrayList<Triple> e;
    private final ArrayList<Triple>[] d;
    private final RollbackDSU dsu;
    public DynamicConnectivity(int n, int q_) {
        dsu = new RollbackDSU(n);
        leaves = q_ <= 1 ? 1 : 1 << (32 - Integer.numberOfLeadingZeros(q_ - 1)); 
        x = new int[q_];
        y = new int[q_];
        for(int i = 0; i < q_; ++i) x[i] = -2;
        d = new ArrayList[(leaves << 1) - 1];
        for(int i = 0; i < d.length; ++i) d[i] = new ArrayList<>();
        e = new ArrayList<>(q_);
    }
    public final void toggleEdge(int u, int v) { if(u > v) { u ^= v; v ^= u; u ^= v; } e.add(new Triple(u, v, t++)); }
    public final void areConnected(int u, int v) { x[t] = u; y[t++] = v; }
    public final void componentSize(int u) { x[t] = u; y[t++] = -1; }
    public final void components() { x[t] = y[t++] = -1;  }
    // -1 means connected, 0 means not connected, >0 is size of component or number of components (O(qlog^2(q)))
    public final ArrayList<Integer> solve() {
        int m = e.size();
        Collections.sort(e, (t1, t2) -> t1.u != t2.u ? t1.u - t2.u : t1.v != t2.v ? t1.v - t2.v : t1.t - t2.t);
        for(int i = 0; i < m; ++i) {
            Triple t1 = e.get(i);
            if(i == m - 1) apply(t, t1);
            else {
                Triple t2 = e.get(i + 1);
                if(t1.u == t2.u && t1.v == t2.v) { apply(t2.t, t1); ++i; }
                else apply(t, t1);
            }
        }
        ans = new ArrayList<>();
        dfs(0);
        return ans;
    }
    private final void apply(int r, Triple t) {
        int l = t.t + leaves - 1;
        r += leaves - 1;
        while(l < r) {
            if((l & 1) == 0) d[l++].add(t);
            if((r & 1) == 0) d[--r].add(t);
            l = (l - 1) >> 1;
            r = (r - 1) >> 1;
        }
    }
    private ArrayList<Integer> ans;
    private final void dfs(int u) {
        for(Triple t: d[u]) dsu.join(t.u, t.v);
        if(u < leaves - 1) {
            dfs((u << 1) + 1);
            dfs((u + 1) << 1);
        }
        else if(u - leaves + 1 < x.length && x[u - leaves + 1] != -2) {
            if(x[u - leaves + 1] == -1) ans.add(dsu.components);
            else if(y[u - leaves + 1] == -1) ans.add(dsu.size(x[u - leaves + 1]));
            else ans.add(dsu.find(x[u - leaves + 1]) == dsu.find(y[u - leaves + 1]) ? -1 : 0);
        }
        for(int i = 0; i < d[u].size(); ++i) dsu.rollback();
    }
    private final static class Triple {
        final int u, v, t;
        public Triple(int u, int v, int t) { this.u = u; this.v = v; this.t = t; }
    }
}
class RollbackDSU {
    private final int[] p;
    private Node history;
    int components;
    public RollbackDSU(int n) { p = new int[components = n]; for (int i = 0; i < n; ++i) p[i] = -1; }
    public int find(int a) { return p[a] < 0 ? a : find(p[a]); }
    public void join(int a, int b) {
        a = find(a); b = find(b);
        history = new Node(a, p[a], b, p[b], components, history);
        if (a == b) return;
        components--;
        if(-p[a] > -p[b]){ a = a ^b ; b = a ^ b; a = a ^ b; }
        p[b] += p[a];
        p[a] = b;
    }
    public int size(int a) { return -p[find(a)]; }
    public void rollback() {
        components = history.components;
        p[history.a] = history.pa;
        p[history.b] = history.pb;
        history = history.next;
    }
    private static final class Node {
        final int a, pa, b, pb, components;
        final Node next;
        public Node(int a, int pa, int b, int pb, int components, Node next) {
            this.a = a;
            this.pa = pa;
            this.b = b;
            this.pb = pb;
            this.components = components;
            this.next = next;
        }
    } 
}
