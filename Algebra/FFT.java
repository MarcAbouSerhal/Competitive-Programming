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
        for(int i = 0; i <= (n >> 1); ++i){
            int j = (n - i) & (n - 1);
            temp = -2 * r * (re[i] * im[i] + re[j] * im[j]); 
            im[i] = r * (re[j] * re[j] - im[j] * im[j] - (re[i] * re[i] - im[i] * im[i]));
            re[i] = temp;
            if(i != j) {
                re[j] = re[i];
                im[j] = -im[i];
            }
        }
        DFT(re, im, n);
        long[] res = new long[d];
        for(int i = 0; i < d; ++i) res[i] = Math.round(re[i]);
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
