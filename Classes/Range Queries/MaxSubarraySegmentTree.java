// find max sum (non-empty) subarray
// remove comments if you care about bound of max sum subarray
class MaxSubarraySegmentTree{
    private final Node[] tree;
    private static final Node init(int x, int i){
        Node result = new Node();
        // result.l = result.r = result.prefIndex = result.sufIndex = i;
        result.sum = result.bestSum = result.pref = result.suf = x;
        return result;
    }
    private static final Node operation(Node a, Node b){
        if(a == null) return b;
        if(b == null) return a;
        Node result = new Node();
 
        result.sum = a.sum + b.sum;
 
        if(a.sum + b.pref > a.pref){
            result.pref = a.sum + b.pref;
            // result.prefIndex = b.prefIndex;
        }
        else{
            result.pref = a.pref;
            // result.prefIndex = a.prefIndex;
        }
 
        if(a.suf + b.sum > b.suf){
            result.suf = a.suf + b.sum;
            // result.sufIndex = a.sufIndex;
        }
        else{
            result.suf = b.suf;
            // result.sufIndex = b.sufIndex;
        }
 
        if(a.suf + b.pref > (a.bestSum > b.bestSum ? a.bestSum : b.bestSum)){
            result.bestSum = a.suf + b.pref;
            // result.l = a.sufIndex;
            // result.r = b.prefIndex;
        }
        else if(a.bestSum > b.bestSum){
            result.bestSum = a.bestSum;
            // result.l = a.l;
            // result.r = a.r;
        }
        else{
            result.bestSum = b.bestSum;
            // result.l = b.l;
            // result.r = b.r;
        }
 
        return result;
    }
    public MaxSubarraySegmentTree(int[] a){
        int leaves=a.length;
        if((leaves&(leaves-1))!=0){
            int log=-1;
            while(leaves>0){
                leaves>>=1;
                log++;
            }
            leaves=1<<(log+1);
        }
        tree= new Node[2*leaves-1];
        for(int i=0; i<a.length; ++i) tree[i+leaves-1]= init(a[i], i);
        for(int i=leaves-2; i>=0; --i) tree[i]=operation(tree[2*i+1],tree[2*i+2]);
    }
    private final Node get(int l, int r, int x,int lx, int rx){
        if(lx>=l && rx<=r) return tree[x];
        if(rx < l || lx > r) return null;
        return operation(get(l,r,2*x+1,lx,(rx+lx)/2),get(l,r,2*x+2,(rx+lx)/2+1,rx));
    }
    public final Node get(int l, int r){
        return get(l,r,0,0,tree.length/2);
    }
    public final void set(int i, int x){
        tree[i+tree.length/2]= init(x,i);
        i+=tree.length/2;
        while(i>0){
            i=(i-1)/2;
            tree[i]=operation(tree[2*i+1],tree[2*i+2]);
        }
    }
}
class Node{
    // int l, r, prefIndex, sufIndex;
    int sum, bestSum, pref, suf;
}
