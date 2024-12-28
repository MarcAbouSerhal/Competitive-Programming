// NOTE: EQ NOT TESTED
class WaveletTree{
    private static class List{ // reduce overhead of ArrayList<Integer>
        int n = 0;
        int[] get;
        public List(int n){ get = new int[n]; }
        public void add(int x){ get[n++] = x; }
        public void clear() { n = 0; }
    }
    long[] a;
    private class Node{
        long low, high;
        Node left, right;
        int[] b;
        public Node(List indices, long lo, long hi){
            low = lo;
            high = hi;
            int n = indices.n;
            b = new int[n];
            if(n == 0 || low == high) return;

            long mid = (low + high) / 2;
            List leftIndices = new List(n), rightIndices = new List(n);
            
            int i0 = indices.get[0];
            if(a[i0] <= mid){
                b[0] = 1;
                leftIndices.add(i0);
            }
            else rightIndices.add(i0);
            for(int i = 1; i < n; ++i){
                int j = indices.get[i];
                b[i] = b[i - 1];
                if(a[j] <= mid){
                    leftIndices.add(j);
                    ++b[i];
                }
                else rightIndices.add(j);
            }
            // compressing travels
            // while this has 1 child, make it that child
            while((b[n - 1] == 0 || b[n - 1] == n) && low != high){
                if(b[n - 1] == 0) low = mid + 1;
                else high = mid;
                mid = (low + high) / 2;
                leftIndices.clear();
                rightIndices.clear();
                
                i0 = indices.get[0];
                if(a[i0] <= mid){
                    b[0] = 1;
                    leftIndices.add(i0);
                }
                else{
                    b[0] = 0; // old b[0] value might be 1
                    rightIndices.add(i0);
                }
                for(int i = 1; i < n; ++i){
                    int j = indices.get[i];
                    b[i] = b[i - 1];
                    if(a[j] <= mid){
                        leftIndices.add(j);
                        ++b[i];
                    }
                    else rightIndices.add(j);
                }
            }
            left = new Node(leftIndices, low, mid);
            right = new Node(rightIndices, mid + 1, high);
        }
    }
    Node root;
    public WaveletTree(long[] a, int min, int max){
        this.a = a;
        int n = a.length;
        List indices = new List(n);
        for(int i = 0; i < n; ++i) indices.add(i);
        root = new Node(indices, min, max);
    }
    private int getLeq(int r, long x){
        if(r == -1) return 0;
        Node node = root;
        int count = 0;
        while(node != null && r != -1){
            long m = (node.low + node.high) / 2;
            if(m == x)
                return count + node.b[r];
            else if(m < x){
                count += node.b[r];
                r = r - node.b[r];
                node = node.right;
            }
            else{
                r = node.b[r] - 1;
                node = node.left;
            }
        }
        return count;
    }
    public int getLeq(int l, int r, long x){
        return getLeq(r, x) - getLeq(l - 1, x);
    }
    private int getEq(int r, long x){
        if(r == -1) return 0;
        Node node = root;
        while(node != null && r != -1){
            long m = (node.low + node.high) / 2;
            if(m == x)
                return node.b[r];
            else if(m < x){
                r = r - node.b[r];
                node = node.right;
            }
            else{
                r = node.b[r] - 1;
                node = node.left;
            }
        }
        return 0;
    }
    public int getEq(int l, int r, long x){
        return getEq(r, x) - getEq(l - 1, x);
    }
    public long kthInRange(int l, int r, int k){
        Node node = root;
        while(true){
            if(node.low == node.high) return node.high;
            int cnt = l == 0 ? node.b[r] : node.b[r] - node.b[l - 1];
            if(cnt >= k){
                l = node.b[l] - (l == 0 ? node.b[0] : node.b[l] - node.b[l - 1]);
                r = node.b[r] - 1;
                node = node.left;
            }
            else{
                k -= cnt;
                l = l - node.b[l] + (l == 0 ? node.b[0] : node.b[l] - node.b[l - 1]);
                r = r - node.b[r];
                node = node.right;
            }
        }
    }
}
