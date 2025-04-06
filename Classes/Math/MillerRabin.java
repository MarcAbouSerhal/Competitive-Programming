// use this for many primality checks of numbers <=1e18
class MillerRabin{
    private static final int[] bases = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
    // these bases work for n <= 1e18, if n is bigger, might have to add more
    // Note: only necessary to test all bases a <= 2ln^2(n)
    private static final boolean checkComposite(long n, long a, long d, int s) {
        BigInteger bigN = BigInteger.valueOf(n);
        BigInteger bigA = BigInteger.valueOf(a).mod(bigN);
        BigInteger bigD = BigInteger.valueOf(d);
        BigInteger x = bigA.modPow(bigD, bigN);

        if (x.equals(BigInteger.ONE) || x.equals(bigN.subtract(BigInteger.ONE))) return false;

        for (int r = 1; r < s; ++r) {
            x = x.multiply(x).mod(bigN);
            if (x.equals(bigN.subtract(BigInteger.ONE))) return false;
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
