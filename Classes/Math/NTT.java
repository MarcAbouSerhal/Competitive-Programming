class NTT{
    private final static long mod = 998244353;
    private final static long root = 15311432; // 2^23rd root of 998244353
    private final static long pow(long x, long n){
        if(n == 0) return 1;
        long res = pow(x, n >> 1);
        res = (res * res) % mod;
        if(n % 2 == 1) res = (res * x) % mod;
        return res;
    }
    private final static long inv(long x){
        return pow(x, mod - 2);
    }
    private final static long[] copy(long[] a , int n){
        long[] b = new long[n];
        for(int i = 0; i < a.length; ++i) b[i] = a[i];
        return b;
    }
    private final static long[] DFT(long[] p, int n, boolean inverse){
        for(int i = 1, j = 0; i < n; ++i){
            int bit = n >> 1;
            for(;(j & bit) != 0; bit >>= 1) j ^= bit;
            j ^= bit;
            if(i < j){
                long temp = p[i];
                p[i] = p[j];
                p[j] = temp;
            }
        }
        for(int len = 2; len <= n; len <<= 1){
            long w_delta = inverse ? pow(inv(root),(1 << 23) / len) : pow(root,(1 << 23) / len);
            int halfLen = len >> 1;
            for(int i = 0; i < n; i += len){
                long w = 1;
                for(int j = 0; j < halfLen; ++j){
                    long u = p[i + j], v = (p[i + j + halfLen] * w) % mod;
                    p[i + j] = (u + v) % mod;
                    p[i + j + halfLen] = (u - v + mod) % mod;
                    w = (w * w_delta) % mod;
                }
            }
        }
        return p;
    }
    // (O(nlog(n)))
    public final static long[] multiply(long[] a, long[] b){
        int n = 1;
        while(n < a.length + b.length) n <<= 1;
        long[] fa = DFT(copy(a, n), n, false);
        long[] fb = DFT(copy(b, n), n, false);
        for(int i = 0; i < n; ++i) fa[i] = (fa[i] * fb[i]) % mod;
        fa = DFT(fa, n, true);
        long inv = inv(n);
        for(int i = 0; i < d; ++i) fb[i] = (fa[i] * inv) % mod;
        return fb;
    }
}
