class LinearSieve{
    final int c;
    final int[] sp; // sp[i] = smallest prime divisor of i
    final ArrayList<Integer> primes = new ArrayList<>();
    // (O(c))
    public LinearSieve(int c){
        this.c = c;
        sp = new int[c + 1];
        for(int i = 2; i <= c; ++i) {
            if(sp[i] == 0) {
                sp[i] = i;
                primes.add(i);
            }
            for(int p: primes) {
                if (p > sp[i] || p * i > c) break;
                sp[p * i] = p;
            }
        }
    }
    public final ArrayList<PrimeFactor> primeFactors(int x){
        ArrayList<PrimeFactor> res = new ArrayList<>();
        if(x <= c) {
            while(x != 1){
                int p = sp[x], e = 1;
                while(sp[x = x / p] == p) ++e;
                res.add(new PrimeFactor(p, e));
            }
        }
        else {
            for(int p: primes) {
                if(p * p > x) break;
                if(x % p != 0) continue;
                int e = 1;
                while(sp[x = x / p] == p) ++e;
                res.add(new PrimeFactor(p, e));
            }
            if(x != 1) res.add(new PrimeFactor(x, 1));
        }
        return res;
    }
    public final int[] squareFreeDivisors(int x){
        ArrayList<PrimeFactor> primeFactors = primeFactors(x);
        int[] res = new int[1 << primes.size()];
        res[0] = 1;
        for(int i = 0; i < primes.size(); ++i) res[1 << i] = primeFactors.get(i).p;
        for(int i = 1; i < 1 << primes.size(); ++i) res[i] = res[i & -i] * res[i & (i - 1)];
        return res;
    }
    public final ArrayList<Integer> divisors(int x){
        ArrayList<Integer> ans = new ArrayList<>();
        f(1, 0, primeFactors(x),  ans);
        ans.add(1);
        return ans;
    }
    private static final void f(int x, int i, ArrayList<PrimeFactor> pfs, ArrayList<Integer> ans){
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