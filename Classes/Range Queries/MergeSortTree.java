class MergeSortTree{
    int[][] tree;
    static int[] none = {};
    private int count(int[] a, int k){
        if(a.length==0 || a[0]>k) return 0;
        int low = 0, high = a.length-1;
        while(low<high){
            if(low==high-1){
                if(a[high]<=k) low = high;
                break;
            }
            int mid = (low+high)/2;
            if(a[mid]<=k) low = mid;
            else high = mid - 1;
        }
        return low+1;
    }
    private int[] merge(int[] a, int[] b){
        int[] c = new int[a.length+b.length];
        int i=0,j=0;
        while(i<a.length && j<b.length){
            if(a[i]<=b[j])
                c[i+j] = a[i++];
            else c[i+j] = b[j++];
        }
        while(i<a.length) c[i+j] = a[i++];
        while(j<b.length) c[i+j] = b[j++];
        return c;
    }
    public MergeSortTree(int[] a){ //O(nlogn)
        int leaves=a.length;
        if((leaves&(leaves-1))!=0){
            int log=-1;
            while(leaves>0){
                leaves>>=1;
                log++;
            }
            leaves=1<<(log+1);
        }
        tree= new int[2*leaves-1][];
        Arrays.fill(tree,new int[] {});
        for(int i=0; i<a.length; ++i) tree[i+leaves-1]=new int[] {a[i]};
        for(int i=leaves-2; i>=0; --i) tree[i] = merge(tree[2*i+1],tree[2*i+2]);
    }
    private int get(int l, int r, int k, int x,int lx, int rx){ 
        if(lx>=l && rx<=r) return count(tree[x],k);
        if(rx < l || lx > r) return 0;
        return get(l,r,k,2*x+1,lx,(rx+lx)/2)+get(l,r,k,2*x+2,(rx+lx)/2+1,rx);
    }
    public int get(int l, int r, int k){ // returns number of elements <=k in [l,r] O(log^2(n))
        return get(l,r,k,0,0,tree.length/2);
    }
    public void set(int i, int x){ //O(n), not recommended
        tree[i+tree.length/2][0] = x;
        i+=tree.length/2;
        while(i>0){
            i=(i-1)/2;
            tree[i]=merge(tree[2*i+1],tree[2*i+2]);
        }
    }
}
