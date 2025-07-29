interface Polynomial {
    static final double pi = Math.PI;
    // (O(nlog(n)))
    public static long[] multiply(long[] a, long[] b) {
        int n = 1, d = a.length + b.length - 1;
        while(n < d) n <<= 1;
        double[] re = new double[n], im = new double[n];
        for(int i = 0; i < a.length; ++i) re[i] = a[i];
        for(int i = 0; i < b.length; ++i) im[i] = b[i];
        DFT(re, im, n);
        double temp;
        re[0] *= im[0] / n;
        im[0] = 0;
        for(int i = 1, j = n - 1; i <= n >> 1; ++i, --j) {
            temp = (re[i] * im[i] + re[j] * im[j]) / (n << 1); 
            im[i] = -(im[j] = (re[j] * re[j] + im[i] * im[i] - im[j] * im[j] - re[i] * re[i]) / (n << 2));
            re[i] = re[j] = temp;
        }
        DFT(re, im, n);
        long[] res = new long[d];
        for(int i = 0; i < d; ++i) res[i] = Math.round(re[i]);
        return res;
    }
    // O(nlog(n))
    public static long[] multiply(long[] a, long[] b, long mod) {
        int n = 1, d = a.length + b.length - 1, mask = (1 << 15) - 1;
        while(n < d) n <<= 1;
        double[] aRe = new double[n], aIm = new double[n], bRe = new double[n], bIm = new double[n], aaRe = new double[n], aaIm = new double[n], bbRe = new double[n], bbIm = new double[n];
        for(int i = 0; i < a.length; ++i) {
            aRe[i] = a[i] >> 15;
            aIm[i] = a[i] & mask;
        }
        for(int i = 0; i < b.length; ++i) {
            bRe[i] = b[i] >> 15;
            bIm[i] = b[i] & mask;
        }
        DFT(aRe, aIm, n);
        DFT(bRe, bIm, n);
        int twon = n << 1;
        for(int i = 0, j = 0; i < n; ++i, j = n - i) {
            aaRe[j] = ((aRe[i] + aRe[j]) * bRe[i] + (aIm[j] - aIm[i]) * bIm[i]) / twon;
            aaIm[j] = ((aRe[i] + aRe[j]) * bIm[i] + (aIm[i] - aIm[j]) * bRe[i]) / twon;
            bbRe[j] = ((aRe[i] - aRe[j]) * bIm[i] + (aIm[i] + aIm[j]) * bRe[i]) / twon;
            bbIm[j] = ((aRe[j] - aRe[i]) * bRe[i] + (aIm[i] + aIm[j]) * bIm[i]) / twon;
        }
        DFT(aaRe, aaIm, n);
        DFT(bbRe, bbIm, n);
        long[] res = new long[d];
        for(int i = 0; i < d; ++i) 
            res[i] = ((((((Math.round(aaRe[i]) % mod) << 15) + Math.round(aaIm[i]) + Math.round(bbRe[i])) % mod) << 15) + Math.round(bbIm[i])) % mod;
        return res;
    }
    // a(x) + b(x) (O(n + m))
    public static long[] plus(long[] a, long[] b) {
        int n = a.length, m = b.length;
        long[] c = new long[Math.max(n, m)];
        for(int i = 0; i < n; ++i) c[i] = a[i];
        for(int i = 0; i < m; ++i) c[i] += b[i];
        return c;
    }
    // a(x) - b(x) (O(n + m))
    public static long[] minus(long[] a, long[] b) {
        int n = a.length, m = b.length;
        long[] c = new long[Math.max(n, m)];
        for(int i = 0; i < n; ++i) c[i] = a[i];
        for(int i = 0; i < m; ++i) c[i] -= b[i];
        return c;
    }
    // a(x) mod x ^ k (O(k))
    public static long[] mod(long[] a, int k) {
        long[] a_ = new long[k];
        for(int i = 0; i < Math.min(k, a.length); ++i) a_[i] = a[i];
        return a_;
    }
    // (O(n))
    public static long[] derivative(long[] a) {
        int size = a.length;
        if(size == 1) return new long[] {0};
        long[] b = new long[size - 1];
        for(int i = 0; i + 1 < size; ++i) b[i] = (i + 1) * a[i + 1];
        return b;
    }
    // (O(n))
    public static long[] reverse(long[] a) {
        long[] b = new long[a.length];
        for(int i = 0; i < a.length; ++i) b[i] = a[a.length - i - 1];
        return b;
    }
    // a^-1(x) modulo x ^ k (O(klog(k)))
    public static long[] inverse(long[] a, long mod, int k) {
        if(k == 1) return new long[] {inv(a[0], mod)};
        a = mod(a, k);
        long[] a_ = new long[k];
        for(int i = 0; i < Math.min(a.length, k); ++i) a_[i] = (i & 1) == 0 ? a[i] : mod - a[i];
        long[] b = multiply(a, a_, mod), t = new long[k];
        for(int i = 0; i < k; ++i) t[i] = b[i << 1];
        b = inverse(t, mod, k >> 1);
        for(int i = 0; i < k; ++i) t[i] = (i & 1) == 0 ? b[i >> 1] : 0;
        return mod(multiply(a_, t), k);
    }
    // d(x) = floor(a(x) / b(x)) such that a(x) = b(x)d(x) + r(x) with deg(R) < deg(B) (O(nlog(n)))
    public static long[] div(long[] a, long[] b, long mod) {
        int n = a.length - 1, m = b.length - 1;
        if(n < m) return new long[] {0};
        return reverse(mod(multiply(reverse(a), inverse(reverse(b), mod, n - m + 1), mod), n - m + 1));
    }
    // r(x) = a(x) mod b(x) = a(x) - b(x).floor(a(x) / b(x)) (O(nlog(n)))
    public static long[] mod(long[] a, long[] b, long mod) { return mod(minus(a, multiply(b, div(a, b, mod), mod)), b.length); }
    private static void DFT(double[] pRe, double[] pIm, int n){
        int bit;
        double temp;
        for(int i = 1, j = 0; i < n; ++i) {
            bit = n >> 1;
            for(; (j & bit) != 0; bit >>= 1) j ^= bit;
            j ^= bit;
            if(i < j){
                temp = pRe[i];
                pRe[i] = pRe[j];
                pRe[j] = temp;
                temp = pIm[i];
                pIm[i] = pIm[j];
                pIm[j] = temp;
            }
        }
        for(int len = 2; len <= n; len <<= 1) {
            int halfLen = len >> 1;
            double ang = 2 * pi / len;
            double w_deltaRe = Math.cos(ang), w_deltaIm = Math.sin(ang);
            for(int i = 0; i < n; i += len){
                double wRe = 1, wIm = 0;
                for(int j = 0; j < halfLen; ++j){
                    double uRe = pRe[i + j], uIm = pIm[i + j], vRe = pRe[i + j + halfLen] * wRe - pIm[i + j + halfLen] * wIm, vIm = pRe[i + j + halfLen] * wIm + pIm[i + j + halfLen] * wRe;
                    pRe[i + j] = uRe + vRe;
                    pIm[i + j] = uIm + vIm;
                    pRe[i + j + halfLen] = uRe - vRe;
                    pIm[i + j + halfLen] = uIm - vIm;
                    temp = wRe * w_deltaRe - wIm * w_deltaIm;
                    wIm = wRe * w_deltaIm + wIm * w_deltaRe;
                    wRe = temp;
                }
            }
        }
    }
    private static long pow(long x, long n, long mod) {
        long ans = 1;
        while(n != 0) {
            if((n & 1) == 1) ans = (ans * x) % mod;
            x = (x * x) % mod;
            n >>= 1;
        }
        return ans;
    }
    private static long inv(long x, long mod) { return pow(x, mod - 2, mod); }
}
