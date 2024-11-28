// takes two arrays a and b of same size, 
// b is static, can set ranges to a specific value in a
// checks if b[i]>=a[i] for all i in [l,r]
// to check if b[i]<=a[i] for all i in [l,r], switch inequalities and turn MinSparseTable to a MaxSparseTable
class BoundSegmentTree{
    boolean[] tree;
    int[] change;
    static int none = Integer.MIN_VALUE;
    MinSparseTable query;
    private void clean(int x, int lx, int rx){
        if(change[x]!=none){
            tree[x]= change[x]<=query.get(lx,rx);
            if(rx!=lx){
                change[2*x+1]=change[x];
                change[2*x+2]=change[x];
            }
            change[x] = none;
        }
    }
    public BoundSegmentTree(int[] a, int[] b){
        query = new MinSparseTable(b);
        int leaves=a.length;
        if((leaves&(leaves-1))!=0){
            int log=-1;
            while(leaves>0){
                leaves>>=1;
                log++;
            }
            leaves=1<<(log+1);
        }
        tree= new boolean[2*leaves-1];
        change = new int[2*leaves-1];
        Arrays.fill(change,none);
        for(int i=0; i<a.length; ++i) tree[i+leaves-1]= a[i]<=b[i];
        for(int i=leaves-2; i>=0; --i) tree[i]=tree[2*i+1]&tree[2*i+2];
    }
    private boolean get(int l, int r, int x,int lx, int rx){
        clean(x,lx,rx);
        if(lx>=l && rx<=r) return tree[x];
        if(rx < l || lx > r) return true;
        return get(l,r,2*x+1,lx,(rx+lx)/2)&get(l,r,2*x+2,(rx+lx)/2+1,rx);
    }
    public boolean get(int l, int r){
        return get(l,r,0,0,tree.length/2);
    }
    public void set(int i, int x){
        tree[i+tree.length/2]=x<=query.get(i,i);
        i+=tree.length/2;
        while(i>0){
            i=(i-1)/2;
            tree[i]=tree[2*i+1]&tree[2*i+2];
        }
    }
    private void set(int l, int r, int x, int lx, int rx, int u){
        clean(x,lx,rx);
        if(lx>=l && rx<=r){
            change[x]=u;
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
                tree[x]=tree[2*x+1]&tree[2*x+2];
            }
            return;
        }
        if(rx<l || lx>r) return;
        set(l,r,2*x+1,lx,(rx+lx)/2,u);
        set(l,r,2*x+2,(rx+lx)/2+1,rx,u);
    }
    public void set(int l, int r, int x){
        set(l,r,0,0,tree.length/2,x);
    }
}
