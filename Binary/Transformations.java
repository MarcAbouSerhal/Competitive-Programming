class Transformations {
    static void subsetTransform(long[] a) { transform(a, 1); }
    static void undoSubsetTransform(long[] a) { undoTransform(a, 1); }
    static void supersetTransform(long[] a) { transform(a, 0); }
    static void undoSupersetTransform(long[] a) { undoTransform(a, 0); }
    static long[] ORConvolution(long[] a, long[] b) {
        int m = Math.max(a.length, b.length);
        m = m <= 1 ? 1 : 1 << (32 - Integer.numberOfLeadingZeros(m - 1));
        a = Arrays.copyOf(a, m); b = Arrays.copyOf(b, m);
        subsetTransform(a); subsetTransform(b);
        for(int i = 0; i < m; ++i) a[i] *= b[i];
        undoSubsetTransform(a);
        return a;
    }
    static long[] ANDConvolution(long[] a, long[] b) {
        int m = Math.max(a.length, b.length);
        m = m <= 1 ? 1 : 1 << (32 - Integer.numberOfLeadingZeros(m - 1));
        a = Arrays.copyOf(a, m); b = Arrays.copyOf(b, m);
        supersetTransform(a); supersetTransform(b);
        for(int i = 0; i < m; ++i) a[i] *= b[i];
        undoSupersetTransform(a);
        return a;
    }
    static long[] XORConvolution(long[] a, long[] b) {
        int m = Math.max(a.length, b.length);
        m = m <= 1 ? 1 : 1 << (32 - Integer.numberOfLeadingZeros(m - 1)); 
        a = Arrays.copyOf(a, m); b = Arrays.copyOf(b, m);
        for (int n = m >> 1; n > 0; n >>= 1)
            for (int i = 0; i < m; i += n << 1)
                for (int j = 0; j < n; ++j) {
                    long x = a[i + j], y = a[i + j + n];
                    a[i + j] = x + y;
                    a[i + j + n] = x - y;
                    x = b[i + j]; y = b[i + j + n];
                    b[i + j] = x + y;
                    b[i + j + n] = x - y;
                }
        for(int i = 0; i < m; ++i) a[i] *= b[i]; 
        for (int n = 1; n < m; n <<= 1)
            for (int i = 0; i < m; i += n << 1)
                for (int j = 0; j < n; ++j) {
                    long x = a[i + j], y = a[i + j + n];
                    a[i + j] = x + y;
                    a[i + j + n] = x - y;
                }
        for(int i = 0; i < m; ++i) a[i] /= m;
        return a;
    }
    static void transform(long[] a, int val) {
        int n = 31 - Integer.numberOfLeadingZeros(a.length);
        for(int i = 0; i < n; ++i)
            for(int j = 0; j < 1 << n; ++j)
                if(((j >> i) & 1) == val) a[j] += a[j ^ (1 << i)];
    }
    static void undoTransform(long[] a, int val) {
        int n = 31 - Integer.numberOfLeadingZeros(a.length);
        for(int i = n - 1; i >= 0; --i)
            for(int j = (1 << n) - 1; j >= 0; --j)
                if(((j >> i) & 1) == val) a[j] -= a[j ^ (1 << i)];
    }
}
