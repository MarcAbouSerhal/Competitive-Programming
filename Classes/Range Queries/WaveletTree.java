class WaveletTree{
    private final Node root;
    private final long[] a;
    // (O(nlog(n)))
    public WaveletTree(long[] a, long min, long max){
        this.a = a;
        final int n = a.length;
        List indices = new List(n);
        for(int i = 0; i < n; ++i) indices.add(i);
        root = new Node(indices, min, max);
    }
    // returns number of elements <=x in [l,r] (O(log(n)))
    public final int getLeq(int l, int r, long x){
        Node node = root;
        int count = 0;
        while(node != null && l <= r){
            long m = node.low + ((node.high - node.low) >> 1);
            if(m == x)
                return count + (l == 0 ? node.b[r] : node.b[r] - node.b[l - 1]);
            else if(m < x){
                if(l == 0){
                    count += node.b[r];
                    l = l - node.b[l] + node.b[0];
                }
                else{
                    count += node.b[r] - node.b[l - 1];
                    l = l - node.b[l] + node.b[l] - node.b[l - 1];
                }
                r = r - node.b[r];
                node = node.right;
            }
            else{
                r = node.b[r] - 1;
                l = node.b[l] - (l == 0 ? node.b[0] : node.b[l] - node.b[l - 1]);
                node = node.left;
            }
        }
        return count;
    }
    // returns number of occurences of x in [l,r] (O(log(n)))
    public final int getEq(int l, int r, long x){
        Node node = root;
        while(node != null && l <= r){
            long m = node.low + ((node.high - node.low) >> 1);
            if(m == x && node.low == m)
                return l == 0 ? node.b[r] : node.b[r] - node.b[l - 1];
            else if(m < x){
                l = l - node.b[l] + (l == 0 ? node.b[0] : node.b[l] - node.b[l - 1]);
                r = r - node.b[r];
                node = node.right;
            }
            else{
                r = node.b[r] - 1;
                l = node.b[l] - (l == 0 ? node.b[0] : node.b[l] - node.b[l - 1]);
                node = node.left;
            }
        }
        return 0;
    }
    // returns kth smallest element in [l,r] (O(log(n)))
    public final long kthInRange(int l, int r, int k){
        Node node = root;
        while(true){
            if(node.low == node.high) return node.high;
            int cnt = l == 0 ? node.b[r] : node.b[r] - node.b[l - 1];
            if(cnt < k){
                k -= cnt;
                l = l - node.b[l] + (l == 0 ? node.b[0] : node.b[l] - node.b[l - 1]);
                r = r - node.b[r];
                node = node.right;
            }
            else{
                l = node.b[l] - (l == 0 ? node.b[0] : node.b[l] - node.b[l - 1]);
                r = node.b[r] - 1;
                node = node.left;
            }
        }
    }
    private static final class List{ // reduce overhead of ArrayList<Integer>
        int n = 0;
        final int[] get;
        public List(int n){ get = new int[n]; }
        public final void add(int x){ get[n++] = x; }
    }
    private final class Node{
        final long low, high;
        final Node left, right;
        final int[] b;
        public Node(List indices, long lo, long hi){
            final int n = indices.n;
            b = new int[n];
            if(n == 0 || lo > hi){
                left = right = null;
                low = lo; high = hi;
                return;
            }
            long min = hi, max = lo;
            for(int i: indices.get){
                min = min < a[i] ? min : a[i];
                max = max > a[i] ? max : a[i];
            }
            long mid = lo + ((hi - lo) >> 1);
            // compress travels by making sure this node has 2 children or no children
            while(lo != hi && (max <= mid || mid < min)){
                if(mid < min) lo = mid + 1;
                else hi = mid;
                mid = lo + ((hi - lo) >> 1 );
            }
            low = lo; high = hi;
            if(low == high){
                for(int i = 0; i < n; ++i) b[i] = i + 1;
                left = right = null;
                return;
            }
            indices.n = 0;
            List rightIndices = new List(n);
            
            int i0 = indices.get[0];
            if(a[i0] > mid) rightIndices.add(i0);
            else{
                b[0] = 1;
                indices.add(i0);
            }
            for(int i = 1; i < n; ++i){
                int j = indices.get[i];
                b[i] = b[i - 1];
                if(a[j] > mid) rightIndices.add(j);
                else{
                    indices.add(j);
                    ++b[i];
                }
            }
            left = new Node(indices, low, mid);
            right = new Node(rightIndices, mid + 1, high);
        }
    }
}
