class Kitamasa {
    static long[][] kitamasaMatrix(long[] c, long n, long mod){
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
    static long[] kitamasaCoefficients(long[] c, long n, long mod){
        int k = c.length;
        long[] temp = xPowNModG(n, c, k, mod);
        for(int i = 0; i < k; ++i) temp[i] = (temp[i] + mod) % mod;
        return temp;
    }
    static long[] xPowNModG(long n, long[] g, int k, long mod){
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
}
