class FrequencySegmentTree{
    private final int[] sum, min;
    private int leaves;
    private final static int min(int x, int y){ return x < y ? x : y; }
    // (O(n))
    public FrequencySegmentTree(int n, int value){
        leaves = n;
        if((leaves & (leaves - 1)) != 0)
            leaves = Integer.highestOneBit(leaves) << 1;
        sum = new int[(leaves << 1) - 1];
        min = new int[(leaves << 1) - 1];
        for(int i = leaves - 1; i + 1 < n + leaves; ++i) sum[i] = min[i] = value;
        for(int i = leaves - 2; i >= 0; --i){
            sum[i] = sum[(i << 1) + 1] + sum[(i + 1) << 1];
            min[i] = min(min[(i << 1) + 1], min[(i + 1) << 1]);
        }
    }
    
    // returns number of elements in the range [l,r] (O(log(n)))
    public final int elementsInRange(int l, int r){
        return get(l, r, 0, 0, leaves - 1);
    }
    // adds i to the multiset (O(log(n)))
    public final void add(int i){
        i += leaves - 1;
        ++sum[i];
        ++min[i];
        while(i != 0){
            i = (i - 1) >> 1;
            min[i] = min(min[(i << 1) + 1], min[(i + 1) << 1]);
            sum[i] = sum[(i << 1) + 1] + sum[(i + 1) << 1];
        }
    }
    // removes i from the multiset (O(log(n)))
    public final void remove(int i){
        i += leaves - 1;
        --sum[i];
        --min[i];
        while(i > 0){
            i = (i - 1) >> 1;
            min[i] = min(min[(i << 1) + 1], min[(i + 1) << 1]);
            sum[i] = sum[(i << 1) + 1] + sum[(i + 1) << 1];
        }
    }
    // returns kth smallest element in the multiset (s[k] is s was sorted) (O(log(n)))
    public final int kth(int k){
        int i = 0;
        while((i << 1) + 1 < sum.length){
            int left = sum[(i << 1) + 1];
            if(left >= k) i = (i << 1) + 1;
            else{
                k -= left;
                i = (i + 1) << 1;
            }
        }
        return i - leaves + 1;
    }
    // returns mex of the multiset (O(log(n)))
    public final int mex(){
        int i = 0;
        while((i << 1) + 1 < sum.length)
            if(min[(i << 1) + 1] == 0) i = (i << 1) + 1;
            else i = (i + 1) << 1;
        return i - leaves + 1;
    }
    private final int get(int l, int r, int x,int lx, int rx){
        if(lx >= l && rx <= r) return sum[x];
        if(rx < l || lx > r) return 0;
        int mid = (lx + rx) >> 1;
        return get(l, r, (x << 1) + 1, lx, mid) + get(l, r, (x + 1) << 1, mid + 1, rx);
    }
}
