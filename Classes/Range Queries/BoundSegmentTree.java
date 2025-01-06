// takes two arrays a and b of same size where b is static and can do range updates on a
// (get a MinSparseTable)
// can check if a[i] <= b[i] for all i in a given range [l,r]
// to check if b[i] <= a[i] for all i in [l,r], switch inequalities and turn MinSparseTable to a MaxSparseTable
class BoundSegmentTree{
    private final boolean[] tree;
    private final Integer[] change;
    private final int[] b;
    private int leaves;
    private final MinSparseTable query;
    public BoundSegmentTree(int[] a, int[] b){
        this.b = b;
        query = new MinSparseTable(b);
        leaves = a.length;
        if((leaves & (leaves - 1)) != 0)
            leaves = Integer.highestOneBit(leaves) << 1;
        tree = new boolean[(leaves << 1) - 1];
        change = new Integer[(leaves << 1) - 1];
        for(int i = 0; i < a.length; ++i) tree[i + leaves - 1] = a[i] <= b[i];
        for(int i = leaves - 2; i >= 0; --i) tree[i] = tree[(i << 1) + 1] & tree[(i + 1) << 1];
    }
    // returns whether a[i] <= b[i] for all i in [l,r] (O(log(n)))
    public final boolean get(int l, int r){
        return get(l, r, 0, 0, leaves - 1);
    }
    // sets a[x] to v (O(log(n)))
    public final void set(int x, int v){
        x += leaves - 1;
        tree[x] = v <= b[x];
        change[x] = null;
        int lx = x - leaves + 1, rx = x - leaves + 1;
        while(x != 0){
            if((x & 1) == 0) lx -= rx - lx + 1;
            else rx += rx - lx + 1;
            x = (x - 1) >> 1;
            int mid = (lx + rx) >> 1;
            clean((x << 1) + 1, lx, mid);
            clean((x + 1) << 1, mid + 1, rx);
            tree[x] = tree[(x << 1) + 1] & tree[(x + 1) << 1];
        }
    }
    // sets a[x] to v for all x in [l,r] (O(log(n)))
    public final void set(int l, int r, int v){
        set(l, r, 0, 0, leaves - 1, v);
    }
    private final void set(int l, int r, int x, int lx, int rx, int v){
        if(rx < l || lx > r) return;
        clean(x, lx, rx);
        if(lx >= l && rx <= r){
            change[x] = v;
            clean(x, lx, rx);
            return;
        }
        int mid = (lx + rx) >> 1;
        set(l, r, (x << 1) + 1, lx, mid, v);
        set(l, r, (x + 1) << 1, mid + 1, rx, v);
        tree[x] = tree[(x << 1) + 1] & tree[(x + 1) << 1]; 
    }
    private final void clean(int x, int lx, int rx){
        if(change[x] != null){
            tree[x] = change[x] <= query.get(lx, rx);
            if(rx != lx){
                change[(x << 1) + 1] = change[x];
                change[(x + 1) << 1] = change[x];
            }
            change[x] = null;
        }
    }
    private final boolean get(int l, int r, int x, int lx, int rx){
        if(rx < l || lx > r) return true;
        clean(x, lx, rx);
        if(lx >= l && rx <= r) return tree[x];
        int mid = (lx + rx) >> 1;
        return get(l, r, (x << 1) + 1, lx, mid) & get(l, r, (x + 1) << 1, mid + 1, rx);
    }
}
