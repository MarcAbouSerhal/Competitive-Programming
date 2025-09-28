class Mo{
    private static final boolean LEFT = true, RIGHT = false;
    // extra stuff here
    private static int[] a;
    // (O(n.sqrt(q).(T(add or remove or finding answer)))
    public final static int[] solve(Query[] queries, int[] a){
        //extra stuff here 
        Mo.a = a;
        Arrays.sort(queries, (x, y) -> Long.compare(x.hilbert_index, y.hilbert_index));
        final int[] res = new int[queries.length];
        int curr_l = 0, curr_r = -1;
        for(Query q: queries){
            while(curr_r < q.r) add(++curr_r, RIGHT);
            while(curr_l > q.l) add(--curr_l, LEFT);
            while(curr_r > q.r) remove(curr_r--, RIGHT);
            while(curr_l < q.l) remove(curr_l++, LEFT);
            res[q.i] = 1; // get answer of this range
        }
        return res;
    }
    public final static void add(int i, boolean direction){ /* adds ith element */ }
    public final static void remove(int i, boolean direction){  /* removes ith element */ }
}
class Query{
    final int l, r, i;
    final long hilbert_index;
    public Query(int l, int r, int i) {
        this.l = l;
        this.r = r;
        this.i = i;
        hilbert_index = hilbert_order(l, r);
    }
    private final static long hilbert_order(int x, int y){
        final int logn = (31 - Integer.numberOfLeadingZeros((y << 1) + 1)) | 1, maxn = (1 << logn) - 1;
        long res = 0;
        for(int s = 1 << (logn - 1); s != 0 ; s >>= 1){
            boolean rx = (x & s) != 0, ry = (y & s) != 0;
            res = (res << 2) | (rx ? ry ? 2 : 1 : ry ? 3 : 0);
            if(!rx){
                if(ry){ x ^= maxn; y ^= maxn; }
                x = x ^ y; y = x ^ y; x = x ^ y;
            }
        }
        return res;
    }
}
