// replace X with type of property (or Tuple of properties)
// if X is primitive: replace null by value that will be ignored by op
class SegmentTree{
    private final X[] tree;
    private int leaves;
    // (both O(n.T(op)))
    public SegmentTree(int n, X v){
        leaves = n;
        if((leaves & (leaves - 1)) != 0)
            leaves = Integer.highestOneBit(leaves) << 1;
        tree = new X[(leaves << 1) - 1];
        for(int i = 0; i < n; ++i) tree[i + leaves - 1] = v;
        for(int i = leaves - 2; i >= 0; --i) tree[i] = op(tree[(i << 1) + 1], tree[(i + 1) << 1]);
    }
    public SegmentTree(X[] a){
        leaves = a.length;
        if((leaves & (leaves - 1)) != 0)
            leaves = Integer.highestOneBit(leaves) << 1;
        tree = new X[(leaves << 1) - 1];
        for(int i = 0; i < a.length; ++i) tree[i + leaves - 1] = a[i];
        for(int i = leaves - 2; i >= 0; --i) tree[i] = op(tree[(i << 1) + 1], tree[(i + 1) << 1]);
    }
    // returns f(a[l...r]) (O(log(n)*T(op)))
    public final X get(int l, int r){
        return get(l, r, 0, 0, leaves - 1);
    }
    // sets a[x] to v (O(log(n)*T(op)))
    public final void set(int x, X v){
        x += leaves - 1;
        tree[x] = v;
        while(x != 0){
            x = (x - 1) >> 1;
            tree[x] = op(tree[(x << 1) + 1], tree[(x + 1) << 1]);
        }
    }
    private final X get(int l, int r, int x, int lx, int rx){
        if(rx < l || lx > r) return null;
        if(lx >= l && rx <= r) return tree[x];
        int mid = (lx + rx) >> 1;
        return op(get(l, r, (x << 1) + 1, lx, mid), get(l, r, (x + 1) << 1, mid + 1, rx));
    }
    // CHANGE THESE FUNCTIONS
    private static final X op(X a, X b){
        if(a == null) return b;
        if(b == null) return a;
        // define associative operation here (f(f(a,b),c)=f(a,f(b,c)))
    }
}
