class Mo{
    // extra stuff here

    // (O(n.sqrt(q).(T(add or remove or finding answer)))
    public final static X[] mo(Query[] queries){
        Arrays.sort(queries, (x,y) -> Long.compare(x.index, y.index));
        long[] res = new long[queries.length];
        int curr_l = 0, curr_r = -1;
        for(Query q: queries){
            while(curr_r < q.r) add(++curr_r);
            while(curr_l > q.l) add(--curr_l);
            while(curr_r > q.r) remove(curr_r--);
            while(curr_l < q.l) remove(curr_l++);
            res[q.i] = ; // get answer of this range
        }
        return res;
    }
    public final static void add(int i){
        // adds ith element
    }
    public final static void remove(int i){
        // removes ith element
    }
    // Note: if direction from which we're adding/removing matters, add int direction to add/remove functions, (0 for left, 1 for right)
}
class Query{
    final int l, r, i;
    final long index;
    public Query(int l, int r, int i) {
        this.l = l;
        this.r = r;
        this.i = i;
        index = hilbert_order(l, r);
    }
    public final static long hilbert_order(int x, int y){
        final int logn = (31 - Integer.numberOfLeadingZeros((y << 1) + 1)) | 1;
        final int maxn = (1 << logn) - 1;
        long res = 0;
        for(int s = 1 << (logn - 1); s != 0 ; s >>= 1){
            boolean rx = (x & s) != 0, ry = (y & s) != 0;
            res = (res << 2) | (rx ? ry ? 2 : 1 : ry ? 3 : 0);
            if(!rx){
                if(ry){
                    x ^= maxn; y ^= maxn;
                }
                x = x ^ y; y = x ^ y; x = x ^ y;
            }
        }
        return res;
    }
}
