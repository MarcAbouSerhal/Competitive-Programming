class BerlekampMassey {
    // returns smallest possible linear recurrence for s
    static long[] minimalLinearRecurrence(long[] s, long mod){
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
                c = new long[Math.max(c.length, d.length + i - f - 1)];
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
    private final static long pow(long x, long n, long m){
        long res = 1;
        while(n != 0){
            if((n & 1) != 0) res = (res * x) % m;
            x = (x * x) % m;
            n >>= 1;
        }
        return res;
    }
}
