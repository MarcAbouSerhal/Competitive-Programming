class DSU {
    private final int[] p;
    int components;
    public DSU(int n) {
        p = new int[components = n];
        for (int i = 0; i < n; ++i) p[i] = -1;
    }
    // Both functions are O(log*(n))
    public final int find(int a) {
        while(p[a] >= 0 && p[p[a]] >= 0)
            a = p[a] = p[p[a]];
        return p[a] < 0 ? a : p[a];
    }
    public final void join(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) return;
        components--;
        if(-p[a] > -p[b]){
            a = a ^b ; b = a ^ b; a = a ^ b;
        }
        p[b] += p[a];
        p[a] = b;
    }
    //returns size of the component containing a
    public final int size(int a) { return -p[find(a)]; } 
}
