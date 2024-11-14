class LazySegmentTree{
    long[] tree;
    Change[] change;
    static int none = Integer.MIN_VALUE;
    private void clean(int x, int lx, int rx){
        if(change[x]!=null){
            
            // if we care about range queries
            // apply what's in change[x] to [lx,rx] (tree[x])
            if(rx!=lx){
                // push down change[x] to change[2*x+1] and change[2*x+2]
                // either override them or try to merge them
            }
            
            //if we don't care about range queries
            if(rx!=lx){
                // push down change[x] to change[2*x+1] and change[2*x+2]
                // either override them or try to merge them
            }
            else{
                // apply what's in change[x] to a[lx] (tree[x])
            }
            change[x] = null;
        }
    }
    private long operation(long a, long b){
        if(a==none) return b;
        if(b==none) return a;
        // define associative operation here (f(f(a,b),c)=f(a,f(b,c)))
    }
    public LazySegmentTree(long[] a){
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
        change = new Change[2*leaves-1];
        for(int i=0; i<a.length; ++i) tree[i+leaves-1]=a[i];
        for(int i=leaves-2; i>=0; --i) tree[i]=operation(tree[2*i+1],tree[2*i+2]);
    }
    private long get(int l, int r, int x,int lx, int rx){
        if(rx < l || lx > r) return none;
        clean(x,lx,rx);
        if(lx>=l && rx<=r) return tree[x];
        return operation(get(l,r,2*x+1,lx,(rx+lx)/2),get(l,r,2*x+2,(rx+lx)/2+1,rx));
    }
    public long get(int l, int r){
        return get(l,r,0,0,tree.length/2);
    }
    private void set(int l, int r, int x, int lx, int rx, long u){
        if(rx<l || lx>r) return;
        clean(x,lx,rx);
        if(lx>=l && rx<=r){
            // queue change by setting change[x] to ...
            // if operation isn't the same on every element in [l,r]
            // something like a staircase
            // then change[x] will depend of l-lx, r-rx, whatever
            while(x>0){
                if(x%2==0){
                    lx-=rx-lx+1;
                }
                else{
                    rx+=rx-lx+1;
                }
                x=(x-1)/2;
                clean(2*x+1,lx, (lx+rx)/2);
                clean(2*x+2,(rx+lx)/2+1,rx);
                tree[x]=tree[2*x+1]+tree[2*x+2];
            }
            return;
        }
        set(l,r,2*x+1,lx,(rx+lx)/2,u);
        set(l,r,2*x+2,(rx+lx)/2+1,rx,u);
    }
    public void set(int l, int r, long x){
        set(l,r,0,0,tree.length/2,x);
    }
    public void set(int x, long u){
        x += tree.length/2;
        change[x] = null;
        tree[x] = u;
        int lx = x-tree.length/2, rx = x-tree.length/2;
        while(x>0){
            if(x%2==0){
                lx-=rx-lx+1;
            }
            else{
                rx+=rx-lx+1;
            }
            x=(x-1)/2;
            clean(2*x+1,lx, (lx+rx)/2);
            clean(2*x+2,(rx+lx)/2+1,rx);
            tree[x] = operation(tree[2*x+1],tree[2*x+2]);
        }
    }
}
