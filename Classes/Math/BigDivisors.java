// use this for x >= 1e7 
class BigDivisors{
    private final static class Pair{
        final long x;
        final int y;
        public Pair(long x, int y){ this.x = x; this.y = y; }
    }
    private final ArrayList<Long> primes = new ArrayList<>();
    // here n is the square root of the max value of x
    public BigDivisors(int n){ 
        BitSet isPrime = new BitSet(n + 1);
        isPrime.set(0, n);
        primes.add(2l);
        for(int i = 4; i <= n; i += 2) isPrime.clear(i);
        for(int i = 3; i * i <= n; i += 2)
            if(isPrime.get(i))
                for(int j = i * i; j <= n; j += i << 1)
                    isPrime.clear(j);
        for(int i = 3; i <= n; i += 2)
            if(isPrime.get(i)) primes.add((long)i);
    }
    // returns list of (p,e) where x = sum(p^e) (O(sqrt(x) / log(x)))
    public final ArrayList<Pair> primeFactors(long x){
        ArrayList<Pair> res = new ArrayList<>();
        for(long y: primes){
            if(y * y > x) break;
            if(x % y != 0) continue;
            int cnt = 0;
            while(x % y == 0){
                cnt++;
                x /= y;
            }
            res.add(new Pair(y, cnt));
        }
        if(x != 1) res.add(new Pair(x, 1));
        return res;
    }
    // returns unsorted list of all divisors of x (O(sqrt(x) / log(x) + d(x)))
    public final ArrayList<Long> divisors(long x){
        ArrayList<Pair> pfs = primeFactors(x);
        ArrayList<Long> ans = new ArrayList<>();
        f(1l, 0, pfs, ans);
        ans.add(1l);
        return ans;
    }
    private static final void f(long x, int i, ArrayList<Pair> pfs, ArrayList<Long> ans){
        if(i == pfs.size()) return;
        f(x, i + 1, pfs, ans);
        for(int cnt = 1; cnt <= pfs.get(i).y; ++cnt){
            x *= pfs.get(i).x;
            ans.add(x);
            f(x, i + 1, pfs, ans);
        }
    }
}
