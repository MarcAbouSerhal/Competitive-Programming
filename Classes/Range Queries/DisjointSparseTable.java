// replace X with type of property (or Tuple of properties)
class DisjointSparseTable{
    private final X[][][] pref, suf; 
    // (O(nlog(n)))
    public DisjointSparseTable(X[] a){
        final int n = a.length, log = 31 - Integer.numberOfLeadingZeros(n);
        pref = new X[log][][];
        suf = new X[log][][];
        int len = 1;
        for(int i = 0; i < log; ++i){
            len <<= 1;
            int segments = (n + len - 1) >> (i + 1);
            pref[i] = new X[segments][len];
            suf[i] = new X[segments][len];
            int index = -len;
            for(int segment = 0; segment < segments - 1; ++segment){
                index += len;
                pref[i][segment][0] = a[index];
                for(int j = 1; j < len; ++j) pref[i][segment][j] = op(pref[i][segment][j - 1], a[index + j]);
                suf[i][segment][len - 1] = a[index + len - 1];
                for(int j = len - 2; j >= 0; --j) suf[i][segment][j] = op(a[index + j], suf[i][segment][j + 1]);
            }
            // deal with last segment seperately (just calculate pref, no need for suf)
            --segments;
            index += len;
            pref[i][segments][0] = a[index];
            for(int j = 1; j < len && index + j < n; ++j) pref[i][segments][j] = op(pref[i][segments][j - 1], a[index + j]);
        }
        // only need to store last element as a suffix for segment of size 2 because without it, querying [n - 1, n - 1] is buggy
        suf[0][((n + 1) >> 1) - 1] = new X[] {a[n - 1], a[n - 1]};
    }
    // returns f(a[l...r]) (O(T(op)))
    public final X get(int l, int r){
        if(r - l < 2)
            return l == r ? ((l & 1) == 0 ? pref[0][l >> 1][0] : suf[0][l >> 1][1]) : (l & 1) == 0 ? pref[0][l >> 1][1] : op(suf[0][l >> 1][1], pref[0][1 + l >> 1][0]);
        int level = 30 - Integer.numberOfLeadingZeros(l ^ r), segment = r >> level + 1;
        return op(suf[level][segment - 1][(2 << level) + l - (segment << level + 1)], pref[level][segment][r - (segment << level + 1)]);
    }
    // CHANGE THESE FUNCTIONS
    private final X op(X a, X b){
        // define associative operation here (op(op(a,b).c)=op(a,op(b,c)))
    }
}