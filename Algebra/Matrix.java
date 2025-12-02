class Matrix{
    // returns A*B (O(mnp))
    public final static long[][] mul(long[][] a, long[][] b, long mod){
        long[][] c = new long[a.length][b[0].length];
        for(int i = 0; i < a.length; ++i)
            for(int k = 0; k < b.length; ++k)
                for(int j = 0; j < b[0].length; ++j)
                    c[i][j] = (c[i][j] + a[i][k] * b[k][j]) % mod;
        for(int i = 0; i < a.length; ++i)
            for(int j = 0; j < b[0].length; ++j)
                if(c[i][j] < 0) c[i][j] += mod;       
        return c;
    }
    // finds A^n (O(k^3.log(n)))
    public final static long[][] pow(long[][] a, long n, long mod){
        int k = a.length;
        long[][] res = new long[k][k];
        for(int i = 0; i < k; ++i) res[i][i] = 1;
        while(n != 0){
            if((n & 1) != 0) res = mul(res, a, mod);
            a = mul(a, a, mod);
            n >>= 1;
        }
        return res;
    }
    // finds sum(0 <= i <= n){ A^i } (O(k^3.log(n)))
    public final static long[][] sumPows(long[][] a, long n, long mod){ return sumPowsHelper(a, n, mod)[0]; }
    private final static long[][][] sumPowsHelper(long[][] a, long n, long mod){
        int k = a.length;
        if(n == 0){
            long[][][] res = new long[2][k][k];
            for(int i = 0; i < k; ++i) res[0][i][i] = res[1][i][i] = 1;
            return res; 
        }
        long[][][] res = sumPowsHelper(a, n >> 1, mod);
        for(int i = 0; i < k; ++i) res[1][i][i] = (res[1][i][i] + 1) % mod;
        res[0] = mul(res[0], res[1], mod);
        for(int i = 0; i < k; ++i) res[1][i][i] = (res[1][i][i] - 1 + mod) % mod;
        for(int i = 0; i < k; ++i)
            for(int j = 0; j < k; ++j)
                res[0][i][j] = (res[0][i][j] - res[1][i][j] + mod) % mod;
        res[1] = mul(res[1], res[1], mod);
        if((n & 1) == 1){
            res[1] = mul(res[1], a, mod);
            for(int i = 0; i < k; ++i)
                for(int j = 0; j < k; ++j)
                    res[0][i][j] = (res[0][i][j] + res[1][i][j]) % mod;
        }
        return res;
    }
}