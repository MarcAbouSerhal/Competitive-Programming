// NOTE: EQ NOT TESTED
class WaveletTree{
    long[] a;
    private class Node{
        long low, high;
        Node left, right;
        int[] b;
        public Node(ArrayList<Integer> indices, long low, long high){
            this.low = low;
            this.high = high;
            int n = indices.size();
            b = new int[n];
            if(n == 0 || low == high) return;

            long mid = (low + high) / 2;
            ArrayList<Integer> leftIndices = new ArrayList<>(n), rightIndices = new ArrayList<>(n);
            
            int i0 = indices.get(0);
            if(a[i0] <= mid){
                b[0] = 1;
                leftIndices.add(i0);
            }
            else rightIndices.add(i0);
            for(int i = 1; i < n; ++i){
                int j = indices.get(i);
                b[i] = b[i - 1];
                if(a[j] <= mid){
                    leftIndices.add(j);
                    ++b[i];
                }
                else rightIndices.add(j);
            }
            left = new Node(leftIndices, low, mid);
            right = new Node(rightIndices, mid + 1, high);
        }
    }
    Node root;
    public WaveletTree(long[] a, long min, long max){
        this.a = a;
        int n = a.length;
        ArrayList<Integer> indices = new ArrayList<>(n);
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
        while(node != null){
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
            if(l < 0) l = 0;
        }
        return 0;
    }
}
