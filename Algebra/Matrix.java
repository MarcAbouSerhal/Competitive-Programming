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
    // returns T^n iff T is of the form
    // (  0   1   0   0   ...   0   0  )
    // (  0   0   1   0   ...   0   0  )
    // (  0   0   0   1   ...   0   0  )
    // (  .   .   .   .   ...   .   .  )
    // (  0   0   0   0   ...   1   0  )
    // (  0   0   0   0   ...   0   1  )
    // ( ck  ck-1 .....   ...   c2  c1 )
    // or generally, if c is the characteristic polynomial of T (O(k^2.log(n)))
    public final static long[][] TpowN(long[] c, long n, long mod){
        int k = c.length;
        long[][] ans = new long[k][k]; 
        long[] temp = xPowNModG(n, c, k, mod);
        for(int i = 0; i < k; ++i) ans[0][i] = (temp[i] + mod) % mod;
        for(int i = 1; i < k; ++i){
            long m = ans[i - 1][k - 1];
            for(int j = 1; j < k; ++j) ans[i][j] = (ans[i - 1][j - 1] + m * c[k - 1 - j]) % mod;
            ans[i][0] = (m * c[k - 1]) % mod;
        }
        return ans;
    }
    private final static long[] xPowNModG(long n, long[] g, int k, long mod){
        if(n < k){
            long[] res = new long[k];
            res[(int)n] = 1;
            return res;
        }
        long[] half = xPowNModG(n >> 1, g, k, mod);
        long[] last = new long[k << 1];
        for(int i = 0; i < k; ++i)
            for(int j = 0; j < k; ++j)
                last[i + j] = (last[i + j] + half[i] * half[j]) % mod;
        if((n & 1) == 1){
            for(int i = (k << 1) - 1; i > 0; --i) last[i] = last[i - 1];
            last[0] = 0;
        }
        for(int i = (k << 1) - 1; i >= k; --i)  
            if(last[i] != 0){
                long m = last[i];
                for(int j = 1; j <= k; ++j) last[i - j] = (last[i - j] + m * g[j - 1]) % mod;
            }
        return last;
    }
    private final static int max(int a, int b){ return a > b ? a : b; }
    private final static long pow(long x, long n, long m){
        long res = 1;
        while(n != 0){
            if((n & 1) != 0) res = (res * x) % m;
            x = (x * x) % m;
            n >>= 1;
        }
        return res;
    }
     // returns smallest possible linear recurrence for s
    public final static long[] berlekampMassey(long[] s, long mod){
        long[] c = new long[0], oldC = new long[0];
        int f = -1;
        for(int i = 0; i < s.length; ++i){
            long delta = s[i];
            for(int j = 1; j <= c.length; ++j)
                delta = ((delta - c[j - 1] * s[i - j]) % mod + mod) % mod; 
            if(delta == 0) continue;
            if(f == -1){
                c = new long[i + 1];
                f = i;
            }
            else{
                long[] d = new long[oldC.length + 1];
                d[0] = 1;
                for(int j = 1; j <= oldC.length; ++j)
                    d[j] = (mod - oldC[j - 1]) % mod;
                long df1 = 0;
                for(int j = 0; j < d.length; ++j)
                    df1 = (df1 + d[j] * s[f - j]) % mod;
                long coef = (delta * pow(df1, mod - 2, mod)) % mod; 
                for(int j = 0; j < d.length; ++j) d[j] = (d[j] * coef) % mod;
                long[] temp = c;
                // imagine there are i - f - 1 zeros to the left of d
                c = new long[max(c.length, d.length + i - f - 1)];
                for(int j = 0; j < temp.length; ++j) c[j] = temp[j];
                for(int j = 0; j < d.length; ++j) c[i - f - 1 + j] = (c[i - f - 1 + j] + d[j] + mod) % mod;
                if(i - temp.length > f - oldC.length){
                    oldC = temp;
                    f = i;
                }
            }
        }
        return c;
    }
}
