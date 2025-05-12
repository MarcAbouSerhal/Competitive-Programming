class Bezout{
    // returns {x, y, gcd(a, b)} such that ax + by = gcd(a, b) (O(log(min(a,b))))
    public static final long[] extendedEuclidean(long a, long b){
        long x = 1, y = 0, x1 = 0, y1 = 1, temp;
        while(b != 0){
            long q = a / b;
            temp = x;
            x = x1;
            x1 = temp - q * x1;
            temp = y;
            y = y1;
            y1 = temp - q * y1;
            temp = a;
            a = b;
            b = temp % b;
        }
        return new long[] {x, y, a};
    }
    // returns x such that a[0]x[0] + a[1]x[1] + ... + a[n - 1]x[n - 1] = gcd(a)
    public static final long[] linearEquation(long[] a){
        int n = a.length;
        if(n == 1) return new long[] {a[0] > 0 ? 1 : -1};
        else if(n == 2){
            long[] xy = extendedEuclidean(a[0], a[1]);
            return new long[] {xy[0], xy[1]};
        }
        else{
            long[] x = new long[n];
            long[] xy = extendedEuclidean(a[0], a[1]);
            x[0] = xy[0];
            x[1] = xy[1];
            long[] leftProduct = new long[n - 2];
            for(int i = 2; i < n; ++i){
                xy = extendedEuclidean(xy[2], a[i]);
                leftProduct[i - 2] = xy[0];
                x[i] = xy[1];
            }
            for(int i = n - 4; i >= 0; --i) 
                leftProduct[i] *= leftProduct[i + 1];
            x[0] *= leftProduct[0];
            for(int i = 1; i < n - 1; ++i) x[i] *= leftProduct[i - 1];
            return x;
        }
    }
}
