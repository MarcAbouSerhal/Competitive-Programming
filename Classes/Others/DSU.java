class DSU {
    int[] p;
    int[] max;
    int components;
    public DSU(int n) {
        p = new int[n];
        max = new int[n];
        components=n;
        for (int i = 0; i < n; ++i) {
            p[i] = -1;
            max[i]=i;
        }
    }
    public int find(int a) {
        while(p[a] >= 0 && p[p[a]]>=0 )
            a = p[a] = p[p[a]];
        return p[a] < 0 ? a : p[a];
    }
    public void join(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) {
            return;
        }
        components--;
        if(-p[a]>-p[b]){
            a=a^b;b=a^b;a=a^b;
        }
        p[b] += p[a];
        max[b]= max[a] > max[b] ? max[a] : max[b];
        p[a] = b;
    }
    public int size(int a) {return -p[find(a)];} //returns size of the component containing a
    public int max(int a){return max[find(a)];} //returns max index of node in the same component as a
}
