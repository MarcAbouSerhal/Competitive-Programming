class FrequencySegmentTree{
    int[] sum, min;
    private final static int min(int x, int y){ return x < y ? x : y; }
    public FrequencySegmentTree(int n, int value){
        int leaves = n;
        if((leaves&(leaves-1))!=0){
            int log = -1;
            while(leaves > 0){
                leaves >>= 1;
                log++;
            }
            leaves = 1 << (log + 1);
        }
        sum = new int[(leaves << 1) - 1];
        min = new int[(leaves << 1) - 1];
        for(int i = leaves - 1; i + 1 < n + leaves; ++i) sum[i] = min[i] = value;
        for(int i = leaves - 2; i >= 0; --i){
            sum[i] = sum[(i << 1) + 1] + sum[(i + 1) << 1];
            min[i] = min(min[(i << 1) + 1], min[(i + 1) << 1]);
        }
    }
    private final int get(int l, int r, int x,int lx, int rx){
        if(lx >= l && rx <= r) return sum[x];
        if(rx < l || lx > r) return 0;
        return get(l, r, (x << 1) + 1, lx, (rx +lx) >> 1) + get(l, r, (x + 1) << 1, ((rx + lx) >> 1) + 1, rx);
    }
    public final int elementsInRange(int l, int r){
        return get(l, r, 0, 0, sum.length >> 1);
    }
    public final void add(int i){
        i += sum.length >> 1;
        ++sum[i];
        while(i > 0){
            i = (i - 1) >> 1;
            sum[i] = sum[(i << 1) + 1] + sum[(i + 1) << 1];
        }
    }
    public final void remove(int i){
        i += sum.length >> 1;
        --sum[i];
        while(i > 0){
            i = (i - 1) >> 1;
            sum[i] = sum[(i << 1) + 1] + sum[(i + 1) << 1];
        }
    }
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
        return i - (sum.length >> 1);
    }
    public final int mex(){
        int i = 0;
        while((i << 1) + 1 < sum.length)
            if(min[(i << 1) + 1] == 0) i = (i << 1) + 1;
            else i = (i + 1) << 1;
        return i - (sum.length >> 1);
    }
}
