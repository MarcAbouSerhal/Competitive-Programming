class DSU {
    int[] p;
    int[] dist;
    public DSU(int n) {
        p = new int[n];
        dist = new int[n];
        for (int i = 0; i < n; ++i) {
            p[i] = -1;
        }
    }
    public int[] find(int a) { //returns {parent(a),d(a,parent(a))}
        if(p[a]<0) return new int[] {a,0};
        int[] val = find(p[a]);
        p[a] = val[0];
        dist[a] = val[1] = dist[a] + val[1];
        return val;
    }
    public void join(int a, int b) { //must join endpoints of edge as they were, not their respective parents
        // remove lines 25->29 if connection is directed (complexity becomes O(log(n)))
        int[] valA = find(a);
        int[] valB = find(b);
        if (valA[0] == valB[0]) {
            return;
        }
        if(-p[valA[0]]>-p[valB[0]]){
            int[] temp = valA;
            valA = valB;
            valB = temp;
        }
        p[valB[0]] += p[valA[0]];
        p[valA[0]] = valB[0];
        dist[valA[0]] = 1+valA[1]+valB[1];
    }
    public int size(int a) {return -p[find(a)[0]];} //returns size of the component containing a
    public int distance(int a) {return find(a)[1];}
}
