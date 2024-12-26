class MergeSortTree{
    int[][] tree;
    static int[] none = {};
    private static int countLeq(int[] a, int k){
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
    private static int countEq(int[] a, int k){
        if(a.length==0) return 0;
        int low1 = 0, high1 = a.length-1, low2 = 0, high2 = a.length-1;
        while(low1<high1){
            if(low1==high1-1){
                if(a[high1]<=k) low1 = high1;
                break;
            }
            int mid = (low1+high1) >> 1;
            if(a[mid]<=k) low1 = mid;
            else high1 = mid - 1;
        }
        if(a[low1] != k) return 0;
        while(low2<high2){
            int mid = (low2+high2) >> 1;
            if(a[mid]<=k) high2 = mid;
            else low2 = mid + 1;
        }
        return low1 - low2 + 1;
    }
    private static int[] merge(int[] a, int[] b){
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
        Arrays.fill(tree,none);
        for(int i=0; i<a.length; ++i) tree[i+leaves-1]=new int[] {a[i]};
        for(int i=leaves-2; i>=0; --i) tree[i] = merge(tree[2*i+1],tree[2*i+2]);
    }
    private int leq(int l, int r, int k, int x,int lx, int rx){ 
        if(lx>=l && rx<=r) return countLeq(tree[x],k);
        if(rx < l || lx > r) return 0;
        return leq(l,r,k,2*x+1,lx,(rx+lx)/2)+leq(l,r,k,2*x+2,(rx+lx)/2+1,rx);
    }
    public int leq(int l, int r, int k){ // returns number of elements <=k in [l,r] O(log^2(n))
        return leq(l,r,k,0,0,tree.length/2);
    }
    private int eq(int l, int r, int k, int x,int lx, int rx){ 
        if(lx>=l && rx<=r) return countEq(tree[x],k);
        if(rx < l || lx > r) return 0;
        return eq(l,r,k,2*x+1,lx,(rx+lx)/2)+eq(l,r,k,2*x+2,(rx+lx)/2+1,rx);
    }
    public int eq(int l, int r, int k){ // returns number of occurences of k in [l,r] O(log^2(n))
        return eq(l,r,k,0,0,tree.length/2);
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
