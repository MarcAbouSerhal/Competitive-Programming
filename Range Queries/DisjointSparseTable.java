class DisjointSparseTable{
    private final X[][] pref, suf; 
    // (O(nlog(n)))
    public DisjointSparseTable(X[] a){
        int n = a.length, log = 31 - Integer.numberOfLeadingZeros(n);
        pref = new X[log][n];
        suf = new X[log][n];
        for(int i = 0, len = 2; i < log; ++i, len <<= 1){
            int index = 0;
            for(; index + len <= n; index += len){
                pref[i][index] = a[index];
                for(int j = index + 1; j < index + len; ++j) pref[i][j] = op(pref[i][j - 1], a[j]);
                suf[i][index + len - 1] = a[index + len - 1];
                for(int j = index + len - 2; j >= index; --j) suf[i][j] = op(a[j], suf[i][j + 1]);
            }
            if(index < n) {
                pref[i][index] = a[index];
                for(int j = index + 1; j < n; ++j) pref[i][j] = op(pref[i][j - 1], a[j]);
            }
        }
        // only need to store last element as a suffix for segment of size 2 because without it, querying [n - 1, n - 1] is buggy
        suf[0][n - 1] = a[n - 1];
    }
    // returns f(a[l, r]) (O(T(op)))
    public final X get(int l, int r){
        if(r - l < 2) return l == r ? ((l & 1) == 0 ? pref[0][l] : suf[0][l]) : (l & 1) == 0 ? pref[0][r] : op(suf[0][l], pref[0][r]);
        int level = 30 - Integer.numberOfLeadingZeros(l ^ r); return op(suf[level][l], pref[level][r]);
    }
    // CHANGE THESE FUNCTIONS
    private final X op(X a, X b){ /* Define associative operation f: f(f(a, b), c) = f(a, f(b, c)) */ }
}
