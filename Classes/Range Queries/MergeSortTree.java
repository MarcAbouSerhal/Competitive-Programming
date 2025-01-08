class MergeSortTree{
    private final int[][] tree;
    private int leaves;
    // O(nlog(n))
    public MergeSortTree(int[] a){
        leaves = a.length;
        if((leaves & (leaves - 1)) != 0)
            leaves = Integer.highestOneBit(leaves) << 1;
        tree = new int[(leaves << 1) - 1][];
        for(int i = 0; i < a.length; ++i) tree[i + leaves - 1] = new int[] { a[i] };
        for(int i = leaves - 2; i >= 0; --i) tree[i] = merge(tree[(i << 1) + 1], tree[(i + 1) << 1]);
    }
    // returns number of elements <=k in [l,r] (O(log^2(n)))
    public final int leq(int l, int r, int k){
        return leq(l, r, k, 0, 0, leaves - 1);
    }
    // returns number of occurences of k in [l,r] (O(log^2(n)))
    public final int eq(int l, int r, int k){
        return eq(l, r, k, 0, 0, leaves - 1);
    }
    // sets a[x] to v, not recommended (O(n))
    public final void set(int x, int v){ 
        x += leaves - 1;
        tree[x][0] = v;
        while(x != 0){
            x = (x - 1) >> 1;
            tree[x] = merge(tree[(x << 1) + 1],tree[(x + 1) << 1]);
        }
    }
    private final static int countLeq(int[] a, int k){
        if(a.length == 0 || a[0] > k) return 0;
        int low = 0, high = a.length - 1;
        while(low < high){
            if(low == high - 1){
                if(a[high] <= k) low = high;
                break;
            }
            int mid = (low + high) >> 1;
            if(a[mid] <= k) low = mid;
            else high = mid - 1;
        }
        return low + 1;
    }
    private final static int countEq(int[] a, int k){
        if(a.length == 0) return 0;
        int low1 = 0, high1 = a.length - 1, low2 = 0, high2 = a.length - 1;
        while(low1 < high1){
            if(low1 == high1 - 1){
                if(a[high1] <= k) low1 = high1;
                break;
            }
            int mid = (low1 + high1) >> 1;
            if(a[mid] <= k) low1 = mid;
            else high1 = mid - 1;
        }
        if(a[low1] != k) return 0;
        while(low2 < high2){
            int mid = (low2 + high2) >> 1;
            if(a[mid] <= k) high2 = mid;
            else low2 = mid + 1;
        }
        return low1 - low2 + 1;
    }
    private final static int[] merge(int[] a, int[] b){
        if(a == null) return b;
        if(b == null) return a;
        int[] c = new int[a.length + b.length];
        int i = 0, j = 0;
        while(i < a.length && j < b.length){
            if(a[i] <= b[j]) c[i + j] = a[i++];
            else c[i + j] = b[j++];
        }
        while(i < a.length) c[i + j] = a[i++];
        while(j < b.length) c[i + j] = b[j++];
        return c;
    }
    private final int leq(int l, int r, int k, int x, int lx, int rx){ 
        if(lx >= l && rx <= r) return countLeq(tree[x], k);
        if(rx < l || lx > r) return 0;
        int mid = (lx + rx) >> 1;
        return leq(l, r, k, (x << 1) + 1, lx, mid) + leq(l, r, k, (x + 1) << 1, mid + 1, rx);
    }
    private final int eq(int l, int r, int k, int x, int lx, int rx){ 
        if(lx >= l && rx <= r) return countEq(tree[x], k);
        if(rx < l || lx > r) return 0;
        int mid = (lx + rx) >> 1;
        return eq(l, r, k, (x << 1) + 1, lx, mid) + eq(l, r, k, (x + 1) << 1, mid + 1, rx);
    }
}
