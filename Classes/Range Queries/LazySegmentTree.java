// replace X with type of property (or Tuple of properties)
// replace Y with object with info about update
// Note: make function clean all that applies all queued updates and another function that returns array of leaves
// makes going through updated leaves take O(n) instead of O(nlog(n))
class LazySegmentTree{
    private final X[] tree;
    private final Y[] change;
    private int leaves;
    // (both O(n*T(op)))
    public LazySegmentTree(int n, X v){
        leaves = n;
        if((leaves & (leaves - 1)) != 0)
            leaves = Integer.highestOneBit(leaves) << 1;
        tree = new X[(leaves << 1) - 1];
        change = new Y[(leaves << 1) - 1];
        for(int i = 0; i < n; ++i) tree[i + leaves - 1] = v;
        for(int i = leaves - 2; i >= 0; --i) tree[i] = op(tree[(i << 1) + 1], tree[(i + 1) << 1]);
    }
    public LazySegmentTree(X[] a){
        leaves = a.length;
        if((leaves & (leaves - 1)) != 0)
            leaves = Integer.highestOneBit(leaves) << 1;
        tree = new X[(leaves << 1) - 1];
        change = new Y[(leaves << 1) - 1];
        for(int i = 0; i < a.length; ++i) tree[i + leaves - 1] = a[i];
        for(int i = leaves - 2; i >= 0; --i) tree[i] = op(tree[(i << 1) + 1], tree[(i + 1) << 1]);
    }
    // returns f(a[l...r]) (O(log(n)*T(op)))
    public final X get(int l, int r){
        return get(l, r, 0, 0, leaves - 1);
    }
    // applies update u to a[l...r] (O(log(n)*(T(op)+T(clean)))
    public void update(int l, int r, Y u){
        update(l, r, 0, 0, leaves - 1, u);
    }
    // sets a[x] to v (O(log(n)*(T(op)+T(clean)))
    public final void set(int x, X v){
        x += leaves - 1;
        tree[x] = v;
        change[x] = null;
        int lx = x - leaves + 1, rx = x - leaves + 1;
        while(x != 0){
            if((x & 1) == 0) lx -= rx - lx + 1;
            else rx += rx - lx + 1;
            x = (x - 1) >> 1;
            int mid = (lx + rx) >> 1;
            clean((x << 1) + 1, lx, mid);
            clean((x + 1) << 1, mid + 1, rx);
            tree[x] = op(tree[(x << 1) + 1], tree[(x + 1) << 1]);
        }
    }
    private final X get(int l, int r, int x,int lx, int rx){
        if(rx < l || lx > r) return null;
        clean(x,lx,rx);
        if(lx >= l && rx <= r) return tree[x];
        int mid = (lx + rx) >> 1;
        return op(get(l, r, (x << 1) + 1, lx, mid), get(l, r, (x + 1) << 1, mid + 1, rx));
    }
    // CHANGE THESE FUNCTIONS
    private final void update(int l, int r, int x, int lx, int rx, Y u){
        if(rx < l || lx > r) return;
        clean(x, lx, rx);
        if(lx >= l && rx <= r){
            // apply u to change[x] which represents [lx, rx] knowing that u is applied on [l, r]
            clean(x, lx, rx);
            return;
        }
        int mid = (lx + rx) >> 1;
        update(l, r, (x << 1) + 1, lx, mid, u);
        update(l, r, (x + 1) << 1, mid + 1, rx, u);
        tree[x] = op(tree[(x << 1) + 1], tree[(x + 1) << 1]); 
    }
    private final void clean(int x, int lx, int rx){
        if(change[x] != null){
            // if we care about range queries
            // apply what's in change[x] to [lx,rx] (tree[x])
            if(rx != lx){
                // push down change[x] to change[(x << 1) + 1] and change[(x + 1) << 1]
                // either override them or try to merge them
            }
            
            //if we don't care about range queries
            if(rx != lx){
                // push down change[x] to change[(x << 1) + 1] and change[(x + 1) << 1]
                // either override them or try to merge them
            }
            else{
                // apply what's in change[x] to a[lx] (tree[x])
            }
            change[x] = null;
        }
    }
    private static final X op(X a, X b){
        if(a == null) return b;
        if(b == null) return a;
        // define associative operation here (f(f(a,b),c)=f(a,f(b,c)))
    }
}
