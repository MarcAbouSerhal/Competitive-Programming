// finds max sum (non-empty) subarray
class MaxSubarraySegmentTree{
    private final Node[] tree;
    private int leaves;
    // (O(n))
    public MaxSubarraySegmentTree(long[] a){
        leaves = a.length;
        if((leaves & (leaves - 1)) != 0)
            leaves = Integer.highestOneBit(leaves) << 1;
        tree= new Node[(leaves << 1) - 1];
        for(int i = 0; i < a.length; ++i) tree[i + leaves - 1]= init(a[i], i);
        for(int i = leaves - 2; i >= 0; --i) tree[i] = op(tree[(i << 1) + 1],tree[(i + 1) << 1]);
    }
    // find max sum subarray inside [l,r] (O(log(n)))
    public final Node get(int l, int r){
        return get(l, r, 0, 0, leaves - 1);
    }
    // sets a[x] to v (O(log(n)))
    public final void set(int x, long v){
        x += leaves - 1;
        tree[x]= init(v , x - leaves + 1);
        while(x != 0){
            x = (x - 1) >> 1;
            tree[x] = op(tree[(x << 1) + 1],tree[(x + 1) << 1]);
        }
    }
    private static final Node init(long x, int i){
        Node result = new Node();
        // result.l = result.r = result.prefIndex = result.sufIndex = i;
        result.sum = result.bestSum = result.pref = result.suf = x;
        return result;
    }
    private static final long max(long x, long y){ return x > y ? x : y; }
    private static final Node op(Node a, Node b){
        if(a == null) return b;
        if(b == null) return a;
        Node result = new Node();
 
        result.sum = a.sum + b.sum;
 
        // use this if you want bounds [l...r] of the max subarray
        // if(a.sum + b.pref > a.pref){
        //     result.pref = a.sum + b.pref;
        //     result.prefIndex = b.prefIndex;
        // }
        // else{
        //     result.pref = a.pref;
        //     result.prefIndex = a.prefIndex;
        // }
 
        // if(a.suf + b.sum > b.suf){
        //     result.suf = a.suf + b.sum;
        //     result.sufIndex = a.sufIndex;
        // }
        // else{
        //     result.suf = b.suf;
        //     result.sufIndex = b.sufIndex;
        // }
 
        // if(a.suf + b.pref > (a.bestSum > b.bestSum ? a.bestSum : b.bestSum)){
        //     result.bestSum = a.suf + b.pref;
        //     result.l = a.sufIndex;
        //     result.r = b.prefIndex;
        // }
        // else if(a.bestSum > b.bestSum){
        //     result.bestSum = a.bestSum;
        //     result.l = a.l;
        //     result.r = a.r;
        // }
        // else{
        //     result.bestSum = b.bestSum;
        //     result.l = b.l;
        //     result.r = b.r;
        // }
 
        // use this if you only want the sum of the max subarray
        result.pref = max(a.pref, a.sum + b.pref);
        result.suf = max(b.suf, a.suf + b.sum);
        result.bestSum = max(max(a.bestSum, b.bestSum), a.suf + b.pref);

        return result;
    }
    private final Node get(int l, int r, int x,int lx, int rx){
        if(lx >= l && rx <= r) return tree[x];
        if(rx < l || lx > r) return null;
        int mid = (lx + rx) >> 1;
        return op(get(l, r, (x << 1) + 1, lx, mid), get(l, r, (x + 1) << 1, mid + 1, r));
    }
}
class Node{
    // comment this out if you want bounds [l...r] of the max subarray
    // int l, r, prefIndex, sufIndex;
    long sum, bestSum, pref, suf;
}
