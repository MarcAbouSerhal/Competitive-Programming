class FFT{
    private static class Complex{
        double x, y;
        public Complex(double x, double y){
            this.x=x; this.y=y;
        }
        public Complex(double x){
            this.x=x;
        }
        public Complex(){};
    }
    private static double pi = Math.PI;
    private static Complex mul(Complex a, Complex b){
        return new Complex(a.x*b.x-a.y*b.y,a.x*b.y+a.y*b.x);
    }
    private static Complex add(Complex a, Complex b){
        return new Complex(a.x+b.x,a.y+b.y);
    }
    private static Complex minus(Complex a){
        return new Complex(-a.x,-a.y);
    }
    private static Complex[] copy(long[]a , int n){
        Complex[] b = new Complex[n];
        for(int i=0; i<a.length; ++i) b[i] = new Complex(a[i]);
        for(int i=a.length; i<n; ++i) b[i] = new Complex();
        return b;
    }
    private static Complex[] DFT(Complex[] p, int n, boolean inverse){
        for(int i = 1, j = 0; i<n; ++i){
            int bit = n>>1;
            for(;(j & bit)!=0; bit >>= 1) j ^= bit;
            j ^= bit;
            if(i < j){
                Complex temp = p[i];
                p[i] = p[j];
                p[j] = temp;
            }
        }
        for(int len = 2; len<=n; len <<= 1){
            double ang = inverse ? (-2*pi)/len : (2*pi)/len;
            Complex w_delta = new Complex(Math.cos(ang),Math.sin(ang));
            for(int i=0; i<n; i+=len){
                Complex w = new Complex(1);
                for(int j=0; j<len/2; ++j){
                    Complex u = p[i+j], v = mul(p[i+j+len/2],w);
                    p[i+j] = add(u,v);
                    p[i+j+len/2] = add(u,minus(v));
                    w = mul(w,w_delta);
                }
            }
        }
        return p;
    }
    public static long[] multiply(long[] a, long[] b){
        int n = 1;
        while(n < a.length + b.length) n<<=1;
        Complex[] fa = DFT(copy(a,n),n,false);
        Complex[] fb = DFT(copy(b,n),n,false);
        for(int i=0; i<n; ++i) fa[i] = mul(fa[i],fb[i]);
        fa = DFT(fa,n,true);
        long[] res = new long[n];
        for(int i=0; i<n; ++i){
            res[i] = Math.round(fa[i].x/n);
        }
        return res;
    }
}
