// use this for many primality checks of numbers <=1e18
class MillerRabin{
    private static final int[] bases = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
    // these bases work for n <= 1e18, if n is bigger, might have to add more
    // Note: only necessary to test all bases a <= 2ln^2(n)
    private static final long pow(long x, long y, long m) {
        long result = 1;
        x %= m;
        while(y != 0) {
            if((y & 1) != 0) result = (result * x) % m;
            x = (x * x) % m;
            y >>= 1;
        }
        return result;
    }
    private static final boolean checkComposite(long n, long a, long d, int s) {
        long x = pow(a, d, n);
        if(x == 1 || x == n - 1) return false;
        for(int r = 1; r < s; ++r) {
            x = (x * x) % n;
            if(x == n - 1) return false;
        }
        return true;
    }
    public static final boolean isPrime(long n) {
        if(n < 2) return false;
        int r = 0;
        long d = n - 1;
        while((d & 1) == 0) {
            d >>= 1;
            r++;
        }
        for(int a: bases) 
            if(n == a) return true;
            else if(checkComposite(n, a, d, r)) return false;
        return true;
    }
}
