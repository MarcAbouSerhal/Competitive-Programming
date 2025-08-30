class XORConvolution {
    public static long[] multiply(long[] a, long[] b) {
        int m = a.length > b.length ? a.length : b.length;
        m = m <= 1 ? 1 : 1 << (32 - Integer.numberOfLeadingZeros(m - 1)); 
        long[] a_ = new long[m], b_ = new long[m];
        for(int i = 0; i < a.length; ++i) a_[i] = a[i];
        for(int i = 0; i < b.length; ++i) b_[i] = b[i];
        long[] c = new long[m];
        for (int n = m >> 1; n > 0; n >>= 1)
            for (int i = 0; i < m; i += n << 1)
                for (int j = 0; j < n; ++j) {
                    long x = a_[i + j], y = a_[i + j + n];
                    a_[i + j] = x + y;
                    a_[i + j + n] = x - y;
                    x = b_[i + j]; y = b_[i + j + n];
                    b_[i + j] = x + y;
                    b_[i + j + n] = x - y;
                }
        for(int i = 0; i < m; ++i) c[i] = a_[i] * b_[i]; 
        for (int n = 1; n < m; n <<= 1)
            for (int i = 0; i < m; i += n << 1)
                for (int j = 0; j < n; ++j) {
                    long x = c[i + j], y = c[i + j + n];
                    c[i + j] = x + y;
                    c[i + j + n] = x - y;
                }
        for(int i = 0; i < m; ++i) c[i] /= m;
        return c;
    }
}
