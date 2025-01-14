class WaveletTree{
    private static final class List{ // reduce overhead of ArrayList<Integer>
        int n = 0;
        final int[] get;
        public List(int n){ get = new int[n]; }
        public final void add(int x){ get[n++] = x; }
    }
    private final long[] a;
    private final class Node{
        final long low, high;
        final Node left, right;
        final int[] b;
        public Node(List indices, long lo, long hi){
            int n = indices.n;
            b = new int[n];
            if(n == 0 || lo == hi){
                if(lo == hi) for(int i = 0; i < n; ++i) b[i] = i + 1;
                left = right = null;
                low = lo; high = hi;
                return;
            }
            long min = hi, max = lo;
            for(int i: indices.get){
                min = min < a[i] ? min : a[i];
                max = max > a[i] ? max : a[i];
            }
            long mid = (lo + hi) / 2;
            // compress travels by making sure this node has 2 children or no children
            while(lo != hi && (max <= mid || mid < min)){
                if(mid < min) lo = mid + 1;
                else hi = mid;
                mid = (lo + hi) / 2;
            }
            low = lo; high = hi;
            List leftIndices = new List(n), rightIndices = new List(n);
            // note that you could use indices as leftIndices to reduce time but in my tests it somehow gave worse time
            // you'd do indices.n = 0 and replace every occurence of leftIndices with indices
            
            int i0 = indices.get[0];
            if(a[i0] > mid) rightIndices.add(i0);
            else{
                b[0] = 1;
                leftIndices.add(i0);
            }
            for(int i = 1; i < n; ++i){
                int j = indices.get[i];
                b[i] = b[i - 1];
                if(a[j] > mid) rightIndices.add(j);
                else{
                    leftIndices.add(j);
                    ++b[i];
                }
            }
            left = new Node(leftIndices, low, mid);
            right = new Node(rightIndices, mid + 1, high);
        }
    }
    private final Node root;
    public WaveletTree(long[] a, long min, long max){
        this.a = a;
        int n = a.length;
        List indices = new List(n);
        for(int i = 0; i < n; ++i) indices.add(i);
        root = new Node(indices, min, max);
    }
    // returns number of elements <=x in [l,r] (O(log(n)))
    public final int getLeq(int l, int r, long x){
        Node node = root;
        int count = 0;
        while(node != null && l <= r){
            long m = (node.low + node.high) / 2;
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
            long m = (node.low + node.high) / 2;
            if(m == x && node.low == m)
                return l == 0 ? node.b[r] : node.b[r] - node.b[l - 1];
            else if(m < x){
                if(l == 0)
                    l = l - node.b[l] + node.b[0];
                else
                    l = l - node.b[l] + node.b[l] - node.b[l - 1];
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
}
