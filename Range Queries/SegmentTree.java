// replace X with type of property (or Tuple of properties)
class SegmentTree{
    private final X[] tree;
    private final int leaves;
    // (O(n.T(op)))
    public SegmentTree(X[] a) {
        leaves = a.length <= 1 ? 1 : 1 << (32 - Integer.numberOfLeadingZeros(a.length - 1)); 
        tree = new X[(leaves << 1) - 1];
        for(int i = 0; i < a.length; ++i) tree[i + leaves - 1] = a[i];
        for(int i = leaves - 2; i >= 0; --i) tree[i] = op(tree[(i << 1) + 1], tree[(i + 1) << 1]);
    }
    // returns f(a[l...r]) (O(log(n)*T(op)))
    public final X get(int l, int r) {
        X lOp = id, rOp = id;
        l += leaves - 1;
        r += leaves;
        while(l < r) {
            if((l & 1) == 0) lOp = op(lOp, tree[l++]);
            if((r & 1) == 0) rOp = op(tree[--r], rOp);
            l = (l - 1) >> 1;
            r = (r - 1) >> 1;
        }
        return op(lOp, rOp);
    }
    // sets a[x] to v (O(log(n)*T(op)))
    public final void set(int x, int v) {
        x += leaves - 1;
        tree[x] = v;
        while(x != 0){
            x = (x - 1) >> 1;
            tree[x] = op(tree[(x << 1) + 1], tree[(x + 1) << 1]);
        }
    }
    // CHANGE THESE 
    private static final X id;
    private static final X op(X a, X b) {
        // define associative operation here (f(f(a,b),c)=f(a,f(b,c)))
    }

    // WALKING ON SEGTREE (optional)
    // // returns smallest i in [l...r] such that prop(a[i]), -1 if there is no such element (O(log(n)))
    // public final int first(int l, int r){ return first(l, r, 0, 0, leaves - 1); }
    // // returns biggest i in [l...r] such that prop(a[i]), -1 if there is no such element (O(log(n)))
    // public final int last(int l, int r){ return last(l, r, 0, 0, leaves - 1); }
    // // fix this pseudocode
    // private final int first(int l, int r, int x, int lx, int rx){
    //     if(rx < l || lx > r) return -1;
    //     if(lx >= l && rx <= r){
    //         if([lx...rx] could contain an element i such that prop(a[i])){ // by checking tree[x] = op(a[lx...rx])
    //             while(lx != rx){
    //                 int mid = (lx + rx) >> 1;
    //                 if([lx...mid] could contain an element i such that prop(a[i])){ // by checking tree[(x << 1) + 1] = op(a[lx...mid])
    //                     rx = mid;
    //                     x = (x << 1) + 1;
    //                 }
    //                 else{
    //                     lx = mid + 1;
    //                     x = (x + 1) << 1;
    //                 }
    //             }
    //             return lx;
    //         }
    //         else return -1;
    //     }
    //     int mid = (lx + rx) >> 1;
    //     int left = first(l, r, (x << 1) + 1, lx, mid);
    //     if(left == -1) return first(l, r, (x + 1) << 1, mid + 1, rx);
    //     else return left;
    // }
    // // fix this pseudocode
    // private final int last(int l, int r, int x, int lx, int rx){
    //     if(rx < l || lx > r) return -1;
    //     if(lx >= l && rx <= r){
    //         if([lx...rx] could contain an element i such that prop(a[i])){ // by checking tree[x] = op(a[lx...rx])
    //             while(lx != rx){
    //                 int mid = (lx + rx) >> 1;
    //                 if([mid + 1...rx] could contain an element i such that prop(a[i])){ // by checking tree[(x + 1) << 1] = op(a[mid + 1..rx])
    //                     lx = mid + 1;
    //                     x = (x + 1) << 1;
    //                 }
    //                 else{
    //                     rx = mid;
    //                     x = (x << 1) + 1;
    //                 }
    //             }
    //             return lx;
    //         }
    //         else return -1;
    //     }
    //     int mid = (lx + rx) >> 1;
    //     int right = last(l, r, (x + 1) << 1, mid + 1, rx);
    //     if(right == -1) return last(l, r, (x << 1) + 1, lx, mid);
    //     else return right;
    // }
}