class FFT{
    private static final double pi = Math.PI;
    private static final void DFT(double[] pRe, double[] pIm, int n, boolean inverse){
        int bit;
        double temp;
        for(int i = 1, j = 0; i<n; ++i){
            bit = n>>1;
            for(;(j & bit) != 0; bit >>= 1) j ^= bit;
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
        for(int len = 2; len<=n; len <<= 1){
            int halfLen = len >> 1;
            double ang = inverse ? (-2 * pi) / len : (2 * pi) / len;
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
    // (O(nlog(n)))
    public static final long[] multiply(long[] a, long[] b){
        int n = 1, d = a.length + b.length - 1;
        while(n <= d) n <<= 1;
        double[] aRe = new double[n], bRe = new double[n], aIm = new double[n], bIm = new double[n];
        for(int i = 0; i < a.length; ++i) aRe[i] = a[i];
        for(int i = 0; i < b.length; ++i) bRe[i] = b[i];
        DFT(aRe, aIm, n, false);
        DFT(bRe, bIm, n, false);
        double temp;
        for(int i = 0; i < n; ++i){
            temp = aRe[i] * bRe[i] - aIm[i] * bIm[i];
            aIm[i] = aIm[i] * bRe[i] + aRe[i] * bIm[i];
            aRe[i] = temp;
        }
        DFT(aRe, aIm, n,true);
        long[] res = new long[d];
        for(int i = 0; i < d; ++i){
            res[i] = Math.round(aRe[i] / n);
        }
        return res;
    }
}
