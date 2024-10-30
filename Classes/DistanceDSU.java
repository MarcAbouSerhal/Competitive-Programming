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
        return new int[] {p[a]=val[0],dist[a]=dist[a]+val[1]};
    }
    public void join(int a, int b) {
        int[] valA = find(a);
        int[] valB = find(b);
        if (a == b) {
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
}
