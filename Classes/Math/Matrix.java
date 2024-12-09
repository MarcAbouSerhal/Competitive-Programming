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
}
