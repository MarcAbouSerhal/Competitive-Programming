class Prufer {
    public static final Edge[] pruferToTree(int[] prufer) {
        int n = prufer.length + 2;
        TreeSet<Integer> s = new TreeSet<>();
        for(int i = 0; i < n; ++i) s.add(i);
        int[] degree = new int[n];
        for(int u: prufer) {
            ++degree[u];
            if(s.contains(u)) s.remove(u);
        }
        Edge[] res = new Edge[n - 1];
        for(int i = 0; i < n - 2; ++i) {
            int v = s.pollFirst();
            res[i] = new Edge(prufer[i], v);
            if(--degree[prufer[i]] == 0) s.add(prufer[i]);
        }
        res[n - 2] = new Edge(s.pollFirst(), s.pollFirst());
        return res;
    }
}
class Edge {
    final int u, v;
    public Edge(int u, int v) { this.u = u; this.v = v; }
}
