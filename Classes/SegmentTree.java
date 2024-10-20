class SegmentTree{
    long[] tree;
    static long none = Long.MAX_VALUE;
    private long operation(long a, long b){
        if(a==none) return b;
        if(b==none) return a;
        // define associative operation here (f(f(a,b),c)=f(a,f(b,c)))
    }
    public SegmentTree(long[] a){
        int leaves=a.length;
        if((leaves&(leaves-1))!=0){
            int log=-1;
            while(leaves>0){
                leaves>>=1;
                log++;
            }
            leaves=1<<(log+1);
        }
        tree= new long[2*leaves-1];
        for(int i=0; i<a.length; ++i) tree[i+leaves-1]=a[i];
        for(int i=leaves-2; i>=0; --i) tree[i]=operation(tree[2*i+1],tree[2*i+2]);
    }
    private long get(int l, int r, int x,int lx, int rx){
        if(lx>=l && rx<=r) return tree[x];
        if(rx < l || lx > r) return none;
        return operation(get(l,r,2*x+1,lx,(rx+lx)/2),get(l,r,2*x+2,(rx+lx)/2+1,rx));
    }
    public long get(int l, int r){
        return get(l,r,0,0,tree.length/2);
    }
    public void set(int i, int x){
        tree[i+tree.length/2]=x;
        i+=tree.length/2;
        while(i>0){
            i=(i-1)/2;
            tree[i]=operation(tree[2*i+1],tree[2*i+2]);
        }
    }
}
