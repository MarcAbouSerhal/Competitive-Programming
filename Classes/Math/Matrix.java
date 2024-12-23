class Matrix{
    public static long[][] mul(long[][] a, long[][] b, long mod){
        long[][] c = new long[a.length][b[0].length];
        for(int i=0; i<a.length; ++i)
            for(int k=0; k<b.length; ++k)
                for(int j=0; j<b[0].length; ++j)
                    c[i][j] = (c[i][j] + a[i][k]*b[k][j])%mod;
        for(int i=0; i<a.length; ++i)
            for(int j=0; j<b[0].length; ++j)
                if(c[i][j]<0) c[i][j] += mod;
        return c;
    }
    public static long[][] TpowN(long[] c, long n, long mod){
        // returns T^n in k^2.log(n) time iff T is of the form
        // (  0   1   0   0   ...   0   0  )
        // (  0   0   1   0   ...   0   0  )
        // (  0   0   0   1   ...   0   0  )
        // (  .   .   .   .   ...   .   .  )
        // (  0   0   0   0   ...   1   0  )
        // (  0   0   0   0   ...   0   1  )
        // ( ck  ck-1 .....   ...   c2  c1 )
        // or generally, if c is the characteristic polynomial of T
        int k = c.length;
        long[][] ans = new long[k][]; 
        ans[0] = xPowNModG(n, c, k, mod);
        for(int i=1; i<k; ++i){
            ans[i] = new long[k];
            long m = ans[i-1][k-1];
            for(int j=1; j<k; ++j) 
                ans[i][j] = (ans[i-1][j-1]+m*c[k-1-j]+mod)%mod;
            ans[i][0] = (m*c[k-1]+mod)%mod;
        }
        return ans;
    }
    private static long[] xPowNModG(long n, long[] g, int k, long mod){
        if(n<k){
            long[] res = new long[k];
            res[(int)n] = 1;
            return res;
        }
        long[] half = xPowNModG(n>>1, g, k, mod);
        long[] last = new long[k<<1];
        for(int i=0; i<k; ++i)
            for(int j=0; j<k; ++j)
                last[i+j] = (last[i+j] + half[i]*half[j])%mod;
        if(n%2==1){
            for(int i=(k<<1)-1; i>0; --i) last[i] = last[i-1];
            last[0] = 0;
        }
        for(int i=(k<<1)-1; i>=k; --i)  
            if(last[i]!=0){
                long m = last[i];
                for(int j=1; j<=k; ++j)
                    last[i-j] = (last[i-j] + m*g[j-1])%mod;
            }
        for(int i=0; i<k; ++i) half[i] = (last[i]+mod)%mod;
        return half;
    }
    private final static int max(int a, int b){ return a > b ? a : b; }
    private final static long pow(long x, long n, long m){
        if(n == 0) return 1;
        long res =  pow(x, n >> 1, m);
        res = (res * res) % m;
        if(n % 2 == 1) res = (res * x) % m;
        return res;
    }
    public static long[] berlekampMassey(long[] s, long m){
        // returns smallest possible recurrence for s
        long[] c = new long[0], oldC = new long[0];
        int f = -1;
        for(int i = 0; i < s.length; ++i){
            long delta = s[i];
            for(int j = 1; j <= c.length; ++j)
                delta = (delta + m - c[j - 1] * s[i - j]) % m; 
            if(delta == 0) continue;
            if(f == -1){
                c = new long[i + 1];
                f = i;
            }
            else{
                long[] d = new long[oldC.length + 1];
                d[0] = 1;
                for(int j = 1; j <= oldC.length; ++j)
                    d[j] = (m - oldC[j - 1]) % m;
                long df1 = 0;
                for(int j = 0; j < d.length; ++j)
                    df1 = (df1 + d[j] * s[f - j]) % m;
                long coef = (delta * pow(df1, m-2, m)) % m; 
                for(int j = 0; j < d.length; ++j) d[j] = (d[j] * coef) % m;
                long[] temp = c;
                // imagine there are i - f - 1 zeros to the left of d
                c = new long[max(c.length, d.length + i - f - 1)];
                for(int j = 0; j < temp.length; ++j) c[j] = temp[j];
                for(int j = 0; j < d.length; ++j) c[i - f - 1 + j] = (c[i - f - 1 + j] + d[j] + m) % m;
                if(i - temp.length > f - oldC.length){
                    oldC = temp;
                    f = i;
                }
            }
        }
        return c;
    }
}
