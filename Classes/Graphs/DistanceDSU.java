class DSU {
    private final int[] p;
    private final int[] dist;
    public DSU(int n) {
        p = new int[n];
        dist = new int[n];
        for (int i = 0; i < n; ++i) p[i] = -1;
    }
    // Both functions are O(log*(n))
    // returns {parent(a),d(a,parent(a))}
    public final int[] find(int a) {
        if(p[a] < 0) return new int[] {a, 0};
        int[] val = find(p[a]);
        p[a] = val[0];
        dist[a] = (val[1] = dist[a] + val[1]);
        return val;
    }
    public final void join(int a, int b) { //must join endpoints of edge as they were, not their respective parents
        // remove lines 23->27 if connection is directed (complexity becomes O(log(n)))
        int[] valA = find(a);
        int[] valB = find(b);
        if (valA[0] == valB[0]) return;
        if(-p[valA[0]] > -p[valB[0]]){
            int[] temp = valA;
            valA = valB;
            valB = temp;
        }
        p[valB[0]] += p[valA[0]];
        p[valA[0]] = valB[0];
        dist[valA[0]] = 1 + valA[1] + valB[1];
    }
    //returns size of the component containing a
    public final int size(int a) { return -p[find(a)[0]]; }
    // returns distance from a to its parent
    public final int distance(int a) { return find(a)[1]; }
}
