class PQ{
    private final int[] v;
    private long[] d;
    private final int[] rev;
    private int size;
    public PQ(int s, long[] d){
        this.d = d;
        v = new int[size = d.length];
        rev = new int[size];
        v[0] = s;
        for(int i = 0; i < s; ++i)
            v[rev[i] = i + 1] = i;
        for(int i = s + 1; i < size; ++i)
            v[rev[i] = i] = i;
    }
    public final void decrease_key(int u, long val){
        d[u] = val;
        int i = rev[u], j = (i - 1) / 2, y = v[j];
        while(i != 0 && d[y] > d[u]){
            v[i] ^= v[j]; v[j] ^= v[i]; v[i] ^= v[j];
            i = rev[u];
            rev[u] = rev[y];
            rev[y] = i;
            i = j;
            j = (i - 1) / 2;
            y = v[j];
        }
    }
    public final int size() { return size; }
    public final int peek() { return v[0]; }
    public final int poll() {
        int ret = v[0]; 
        rev[v[0] = v[--size]] = 0;
        int i = 0, min = 0, l = 1, r = 2;
        while(l < size){
            if(d[v[l]] < d[v[min]]) min = l;
            if(r < size && d[v[r]] < d[v[min]]) min = r;
            if(i != min){
                l = v[i];
                v[i] = v[min];
                v[min] = l;
                l = rev[v[min]];
                rev[v[min]] = rev[v[i]];
                rev[v[i]] = l;
                i = min;
                l = (i << 1) + 1;
                r = (i + 1) << 1;
            }
            else break;
        }
        return ret;
    }
}
