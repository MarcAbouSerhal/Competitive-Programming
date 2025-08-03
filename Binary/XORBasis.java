class XORBasis{
    final int log;
    int size = 0;
    private final int[] basis;
    // means all elements are in [0 .. (1 << log) - 1]
    public XORBasis(int log){ basis = new int[this.log = log]; }
    // adds x to the basis (O(log))
    public final void add(int x){
        for(int i = log - 1; i >= 0; --i){
            if((x & (1 << i)) == 0) continue;
            if(basis[i] == 0){
                basis[i] = x;
                ++size;
                return;
            }
            x ^= basis[i];
        }
    }
    // returns whether or not there is a subset of the elements such that their XOR is equal to x (O(log))
    public final boolean contains(int x){
        for(int i = log - 1; i >= 0; --i){
            if((x & (1 << i)) == 0) continue;
            if(basis[i] == 0) return false;
            x ^= basis[i];
        }
        return true;
    }
    // returns the min value of s ^ x where s is the XOR of a subset of elements (O(log))
    public final int minSubsequenceWith(int x){
        for(int i = log - 1; i >= 0; --i) x = Math.min(x, x ^ basis[i]);
        return x;
    }
    // returns the max value of s ^ x where s is the XOR of a subset of elements (O(log))
    public final int maxSubsequenceWith(int x){
        for(int i = log - 1; i >= 0; --i) x = Math.max(x, x ^ basis[i]);
        return x;
    }
    // returns the max XOR of a subset of elements (O(log))
    public final int maxSubsequence(){ return maxSubsequenceWith(0); }
    // returns the kth biggest XOR of a subset of elements (1-indexed) (O(log))
    public final int kthSmallestSubsequence(int k){
        int x = 0, tot = 1 << size;
        for(int i = log - 1; i >= 0; --i)
            if(basis[i] != 0){
                tot >>= 1;
                if((tot < k && (x & (1 << i)) == 0) || (tot >= k && (x & (1 << i)) > 0)) x ^= basis[i];
                if(tot < k) k -= tot;
            }
        return x;
    }
    // returns basis for {a & x: a in the span} (O(log^2))
    public final XORBasis and(int x) {
        XORBasis b = new XORBasis(log);
        for(int i: basis) if(i != 0) b.add(i & x);
        return b;
    }
    // returns a copy of the current basis (O(log))
    public final XORBasis clone(){
        final XORBasis copy = new XORBasis(log);
        copy.size = size;
        for(int i = 0; i < log; ++i) copy.basis[i] = basis[i];
        return copy;
    }
    // returns the union of the two bases (O(log^2))
    public static final XORBasis merge(XORBasis b1, XORBasis b2){
        final XORBasis res = b1.clone();
        for(int i: b2.basis) if(i != 0) res.add(i);
        return res;
    }
}
