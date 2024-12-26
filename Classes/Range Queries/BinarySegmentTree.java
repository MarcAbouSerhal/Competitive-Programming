class BinarySegmentTree{
    int[] tree;
    public BinarySegmentTree(int n, int value){
        int leaves = n;
        if((leaves&(leaves-1))!=0){
            int log = -1;
            while(leaves > 0){
                leaves >>= 1;
                log++;
            }
            leaves = 1 << (log + 1);
        }
        tree = new int[(leaves << 1) - 1];
        for(int i = 0; i < n; ++i) tree[i + leaves - 1] = value;
        for(int i = leaves - 2; i >= 0; --i) tree[i] = tree[(i << 1) + 1] + tree[(i + 1) << 1];
    }
    private int get(int l, int r, int x,int lx, int rx){
        if(lx >= l && rx <= r) return tree[x];
        if(rx < l || lx > r) return 0;
        return get(l, r, (x << 1) + 1, lx, (rx +lx) >> 1) + get(l, r, (x + 1) << 1, ((rx + lx) >> 1) + 1, rx);
    }
    public int get(int l, int r){
        return get(l, r, 0, 0, tree.length >> 1);
    }
    public void toggle(int i){
        i += tree.length >> 1;
        tree[i] ^= 1;
        while(i > 0){
            i = (i - 1) >> 1;
            tree[i] = tree[(i << 1) + 1] + tree[(i + 1) << 1];
        }
    }
    // k is 1-indexed
    public int kthOne(int k){
        int i = 0;
        while((i << 1) + 1 < tree.length){
            int left = tree[(i << 1) + 1];
            if(left >= k) i = (i << 1) + 1;
            else{
                k -= left;
                i = (i + 1) << 1;
            }
        }
        return i - (tree.length >> 1);
    }
}
