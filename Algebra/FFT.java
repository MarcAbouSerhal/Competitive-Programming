class FFT{
    private static final double pi = Math.PI;
    // (O(nlog(n)))
    public static final long[] multiply(long[] a, long[] b){
        int n = 1, d = a.length + b.length - 1;
        while(n < d) n <<= 1;
        double[] re = new double[n], im = new double[n];
        for(int i = 0; i < a.length; ++i) re[i] = a[i];
        for(int i = 0; i < b.length; ++i) im[i] = b[i];
        DFT(re, im, n);
        double r = -0.25 / n, temp;
        re[0] *= im[0] / n;
        im[0] = 0;
        for(int i = 1, j = n - 1; i <= n >> 1; ++i, --j){
            temp = -2 * r * (re[i] * im[i] + re[j] * im[j]); 
            im[j] = -(im[i] = r * (re[j] * re[j] + im[i] * im[i] - im[j] * im[j] - re[i] * re[i]));
            re[i] = re[j] = temp;
        }
        DFT(re, im, n);
        long[] res = new long[d];
        for(int i = 0; i < d; ++i) res[i] = Math.round(re[i]);
        return res;
    }
    // O(nlog(n))
    public static final long[] multiply(long[] a, long[] b, long mod) {
        int n = 1, d = a.length + b.length - 1, mask = (1 << 15) - 1;
        while(n < d) n <<= 1;
        double[] aRe = new double[n], aIm = new double[n], bRe = new double[n], bIm = new double[n];
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
        double[] aaRe = new double[n], aaIm = new double[n], bbRe = new double[n], bbIm = new double[n];
        for(int i = 0; i < n; ++i) {
            int j = (-i) & (n - 1);
            aaRe[j] = ((aRe[i] + aRe[j]) * bRe[i] - (aIm[i] - aIm[j]) * bIm[i]) / (n << 1);
            aaIm[j] = ((aRe[i] + aRe[j]) * bIm[i] + (aIm[i] - aIm[j]) * bRe[i]) / (n << 1);
            bbRe[j] = ((aRe[i] - aRe[j]) * bIm[i] + (aIm[i] + aIm[j]) * bRe[i]) / (n << 1);
            bbIm[j] = ((aRe[j] - aRe[i]) * bRe[i] + (aIm[i] + aIm[j]) * bIm[i]) / (n << 1);
        }
        DFT(aaRe, aaIm, n);
        DFT(bbRe, bbIm, n);
        long[] res = new long[d];
        for(int i = 1; i < d; ++i) 
            res[i] = ((((((Math.round(aaRe[i]) % mod) << 15) + Math.round(aaIm[i]) + Math.round(bbRe[i])) % mod) << 15) + Math.round(bbIm[i])) % mod;
        return res;
    }
    private static final void DFT(double[] pRe, double[] pIm, int n){
        int bit;
        double temp;
        for(int i = 1, j = 0; i < n; ++i){
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
        for(int len = 2; len <= n; len <<= 1){
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
}
