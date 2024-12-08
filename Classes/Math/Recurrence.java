// O(k^2.log(n))
class Recurrence{
    public static long solve(long n, int k, long[] c, long[] f, long mod){
        long[] g = new long[k+1];
        g[k] = 1;
        for(int i=1; i<=k; ++i) g[k-i] = (-c[i-1]+mod)%mod;
        long[] b = xPowNModG(n, g, k, mod);
        long ans = 0;
        for(int i=0; i<k; ++i)
            ans = (ans+b[i]*f[i])%mod;
        return ans;
    }
    public static long[] xPowNModG(long n, long[] g, int k, long mod){
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
                for(int j=0; j<=k; ++j)
                    last[i-j] = (last[i-j] - m*g[k-j])%mod;
            }
        for(int i=0; i<k; ++i) half[i] = (last[i]+mod)%mod;
        return half;
    }
}
