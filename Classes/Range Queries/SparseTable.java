class SparseTable{
    private final X[][] table;
    private final int[] floorPow;
    // (O(nlog(n)))
    public SparseTable(X[] a){
        final int n = a.length, log = 32 - Integer.numberOfLeadingZeros(n);
        table = new X[n][log];
        for(int i = 0; i < n; ++i) table[i][0] = a[i];
        for(int j = 1; j < log; ++j)
            for(int i = 0; i + (1 << j - 1) < n; ++i)
                table[i][j] = op(table[i][j - 1], table[i + (1 << (j - 1))][j - 1]);
        floorPow = new int[n + 1];
        floorPow[0] = -1;
        for(int i = 1; i <= n; ++i){
            floorPow[i] = floorPow[i - 1];
            if((i & (i - 1)) == 0) ++floorPow[i];
        }
    }
    // CHANGE THESE FUNCTIONS
    // returns f(a[l...r])
    public final X get(int l, int r){
        int x = floorPow[r - l + 1];

        // if operation is idempotent (op(a,a)=a) (like min, max, gcd, lcm...)
        return op(table[l][x], table[r - (1 << x) + 1][x]); // (O(T(op)))

        // if operation is not idempotent
        X ans = table[l][x];
        l += 1 << x;
        while(l <= r){
            x = floorPow[r - l + 1];
            ans = op(ans, table[l][x]);
            l += 1 << x;
        }
        return ans; // (O(log(r - l + 1).T(op)))
    }
    private final static X op(X a, X b){
        // define associative operation here (op(op(a,b).c)=op(a,op(b,c)))
    }
}