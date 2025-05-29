class XORConvolution {
    private final static int mod = 998244353;
    public final static int[] multiply(int[] a, int[] b) {
        int m = a.length > b.length ? a.length : b.length;
        m = m <= 1 ? 1 : 1 << (32 - Integer.numberOfLeadingZeros(m - 1)); 
        int[] a_ = new int[m], b_ = new int[m];
        for(int i = 0; i < a.length; ++i) a_[i] = a[i];
        for(int i = 0; i < b.length; ++i) b_[i] = b[i];
        int[] c = new int[m];
        for (int n = m >> 1; n > 0; n >>= 1)
            for (int i = 0; i < m; i += n << 1)
                for (int j = 0; j < n; ++j) {
                    int x = a_[i + j], y = a_[i + j + n];
                    a_[i + j] = (x + y) % mod;
                    a_[i + j + n] = (x - y + mod) % mod;
                    x = b_[i + j]; y = b_[i + j + n];
                    b_[i + j] = (x + y) % mod;
                    b_[i + j + n] = (x - y + mod) % mod;
                }
        for(int i = 0; i < m; ++i) c[i] = (int)(((long)a_[i] * b_[i]) % mod); 
        for (int n = 1; n < m; n <<= 1)
            for (int i = 0; i < m; i += n << 1)
                for (int j = 0; j < n; ++j) {
                    int x = c[i + j], y = c[i + j + n];
                    c[i + j] = (x + y) % mod;
                    c[i + j + n] = (x - y + mod) % mod;
                }
        int mrev = inverse(m);
        for(int i = 0; i < m; ++i) c[i] = (int)(((long)c[i] * mrev) % mod);
        return c;
    }
    public final static int inverse(int x) { return x == 1 ? 1 : mod - (int)(((long)(mod / x) * inverse(mod % x)) % mod); }
}
