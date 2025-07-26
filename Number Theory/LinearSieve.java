// use this for divisors of numbers <= 1e7
class LinearSieve{
    final int[] gp; // sp[i] = smallest prime divisor of i
    // (O(c))
    public LinearSieve(int c){
        gp = new int[c + 1];
        for(int i = 2; i <= c; ++i)
            if(gp[i] == 0)
                for(int j = i; j <= c; j += i) gp[j] = i;
    }
    // (O(log(x)/log(log(x))))
    public final ArrayList<PrimeFactor> primeFactors(int x){
        ArrayList<PrimeFactor> res = new ArrayList<>();
        while(x != 1){
            int p = gp[x], e = 1;
            while(gp[x = x / p] == p) ++e;
            res.add(new PrimeFactor(p, e));
        }
        return res;
    }
    // (O(2 ^ pf[x])
    public final int[] uniquePrimeProductDivisors(int x){
        ArrayList<Integer> primes = new ArrayList<>();
        while(x != 1){
            int p = gp[x];
            while(gp[x = x / p] == p);
            primes.add(p);
        }
        int[] res = new int[1 << primes.size()];
        res[0] = 1;
        for(int i = 0; i < primes.size(); ++i) res[1 << i] = primes.get(i);
        for(int i = 1; i < 1 << primes.size(); ++i) res[i] = res[i & -i] * res[i & (i - 1)];
        return res;
    }
    // (O(d(x)))
    public final ArrayList<Integer> divisors(int x){
        ArrayList<Integer> ans = new ArrayList<>();
        f(1, 0, primeFactors(x),  ans);
        ans.add(1);
        return ans;
    }
    private static final void f(int x, int i, ArrayList<PrimeFactor> pfs,ArrayList<Integer> ans){
        if(i >= pfs.size()) return;
        f(x, i + 1, pfs, ans);
        for(int cnt = 1; cnt <= pfs.get(i).e; ++cnt){
            x *= pfs.get(i).p;
            ans.add(x);
            f(x, i + 1, pfs, ans);
        }
    }
}
class PrimeFactor{
    final int p, e;
    public PrimeFactor(int p, int e){ this.p = p; this.e = e; }
}
