// use this for max subarray range queries
class MaxSubarrayNode{
    // !: comment this out if you want bounds [l...r] of the max subarray 
    // int l, r, prefIndex, sufIndex;
    long sum, bestSum, pref, suf;
    public static final MaxSubarrayNode op(MaxSubarrayNode left, MaxSubarrayNode right){
        if(left == null) return right;
        if(right == null) return left;
        MaxSubarrayNode result = new MaxSubarrayNode();
 
        result.sum = left.sum + right.sum;
 
        // !: use this if you want bounds [l...r] of the max subarray
        // if(left.sum + right.pref > left.pref){
        //     result.pref = left.sum + right.pref;
        //     result.prefIndex = right.prefIndex;
        // }
        // else{
        //     result.pref = left.pref;
        //     result.prefIndex = left.prefIndex;
        // }
 
        // if(a.suf + right.sum > right.suf){
        //     result.suf = left.suf + right.sum;
        //     result.sufIndex = left.sufIndex;
        // }
        // else{
        //     result.suf = right.suf;
        //     result.sufIndex = right.sufIndex;
        // }
 
        // if(left.suf + right.pref > (left.bestSum > right.bestSum ? left.bestSum : right.bestSum)){
        //     result.bestSum = left.suf + right.pref;
        //     result.l = left.sufIndex;
        //     result.r = right.prefIndex;
        // }
        // else if(left.bestSum > right.bestSum){
        //     result.bestSum = left.bestSum;
        //     result.l = left.l;
        //     result.r = left.r;
        // }
        // else{
        //     result.bestSum = right.bestSum;
        //     result.l = right.l;
        //     result.r = right.r;
        // }
 
        // !: use this if you only want the sum of the max subarray
        result.pref = max(left.pref, left.sum + right.pref);
        result.suf = max(right.suf, left.suf + right.sum);
        result.bestSum = max(max(left.bestSum, right.bestSum), left.suf + right.pref);

        return result;
    }
    private static long max(long a, long b) { return a > b ? a : b; }
}