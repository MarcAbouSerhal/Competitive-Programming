// allows for queries of the form
// f({p[i]: l <= i <= r, a[i] <= or >= x})
class PairMergeSortTree{
    private final int[][] tree;
    private final static int[][] tree2;
    private int leaves;
    // O(nlog(n))
    public PairMergeSortTree(int[] a, int[] p){
        leaves = a.length;
        if((leaves & (leaves - 1)) != 0)
            leaves = Integer.highestOneBit(leaves) << 1;
        tree = new int[(leaves << 1) - 1][];
        for(int i = 0; i < a.length; ++i) init(i + leaves - 1, a[i], p[i]);
        for(int i = leaves - 2; i >= 0; --i) merge(i);
    }
    // change X to return type, and DS to type of data structure needed to query op
    private final DS[] ds; 
    // and implement id, merge, setupQueries and query
    private static final X id;
    private static final X merge(X a, X b){
        // merge answers X and Y
    }
    private static final X query(DS ds, int l, int r){
        // return op([l...r]) 
    }
    private static final DS setupQueries(int[] c){
        // setup queries for c and return the data structure (don't alter c)
    }
    // returns op({p[i]: l <= i <= r, a[i] >= x}) (O(log^2(n) + log(n).T(op)))
    public final X opGeq(int l, int r, int k){
        return opGeq(l, r, k, 0, 0, leaves - 1);
    }
    // returns op({p[i]: l <= i <= r, a[i] <= x}) (O(log^2(n) + log(n).T(op)))
    public final X opLeq(int l, int r, int k){
        return opLeq(l, r, k, 0, 0, leaves - 1);
    }
    private final X opLeq(int l, int r, int k, int x, int lx, int rx){
        if(lx >= l && rx <= r){
            if(tree[x][0] > k) return id;
            int low = 0, high = rx - lx;
            int[] a = tree[x];
            while(low < high){
                if(low == high - 1){
                    if(a[high] <= k) low = high;
                    break;
                }
                int mid = (low + high) >> 1;
                if(a[mid] <= k) low = mid;
                else high = mid - 1;
            }
            return query(ds[x], 0, low);
        }
        if(rx < l || lx > r) return id;
        int mid = (lx + rx) >> 1;
        return merge(opLeq(l, r, k, (x << 1) + 1, lx, mid), opLeq(l, r, k, (x + 1) << 1, mid + 1, rx));
    }
    private final X opGeq(int l, int r, int k, int x, int lx, int rx){
        if(lx >= l && rx <= r){
            if(tree[x][rx - lx] < k) return id;
            int low = 0, high = rx - lx;
            int[] a = tree[x];
            while(low < high){
                int mid = (low + high) >> 1;
                if(a[mid] >= k) high = mid;
                else low = mid + 1;
            }
            return query(ds[x], low, rx - lx);
        }
        if(rx < l || lx > r) return id;
        int mid = (lx + rx) >> 1;
        return merge(opGeq(l, r, k, (x << 1) + 1, lx, mid), opGeq(l, r, k, (x + 1) << 1, mid + 1, rx));
    }
    private final void merge(int index){
        int[] a = tree[(index << 1) + 1], b = tree[(index + 1) << 1];
        if(a == null){
            tree[index] = b;
            tree2[index] = tree2[(index + 1) << 1];
            ds[index] = ds[(index + 1) << 1];
            return;
        }
        if(b == null){
            tree[index] = a;
            tree2[index] = tree2[(index << 1) + 1];
            ds[index] = ds[(index << 1) + 1];
            return;
        }
        int[] c1 = tree2[(index << 1) + 1], c2 = tree2[(index + 1) << 1], merged = new int[a.length + b.length], c = new int[a.length + b.length];
        int i = 0, j = 0;
        while(i < a.length && j < b.length){
            if(a[i] <= b[j]){
                c[i + j] = c1[i];
                merged[i + j] = a[i++];
            }
            else{
                c[i + j] = c2[j];
                merged[i + j] = b[j++];
            }
        }
        while(i < a.length){
            c[i + j] = c1[i];
            merged[i + j] = a[i++];
        }
        while(j < b.length){
            c[i + j] = c2[j];
            merged[i + j] = b[j++];
        }
        tree[i] = merged;
        tree2[i] = c;
        ds[i] = setupQueries(c);
    }
    private final void init(int i, int x, int y){
        tree[i] = new int[] {x};
        tree2[i] = new int[] {y};
        ds[i] = setupQueries(tree2[i]);
    }
}
