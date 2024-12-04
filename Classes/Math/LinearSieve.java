class LinearSieve{
    static class Pair{
        int x,y;
        public Pair(int x, int y){ this.x=x; this.y=y; }
    }
    int[] sp;
    int[] pf;
    public LinearSieve(int c){
        sp = new int[c+1];
        pf = new int[c+1];
        for(int i=2; i<=c; ++i)
            if(sp[i] == 0)
                for(int j=1; i*j<=c; ++j)
                    if(sp[i*j] == 0) 
                        pf[i*j] = sp[j] == (sp[i*j] = i) ? pf[j] : pf[j] + 1;
    }
    public ArrayList<Pair> primeFactors(int x){
        ArrayList<Pair> res = new ArrayList<>();
        while(x>1){
            int p = sp[x], cnt = 0;
            while(sp[x] == p){
                x /= p;
                cnt++;
            }
            res.add(new Pair(p, cnt));
        }
        return res;
    }
    public int[] uniquePrimeProductDivisors(int x){
        ArrayList<Integer> primes = new ArrayList<>();
        while(x>1){
            int p = sp[x];
            while(sp[x] == p)
                x /= p;
            primes.add(p);
        }
        int[] res = new int[1<<primes.size()];
        for(int i=0; i<1<<primes.size(); ++i){
            res[i] = 1;
            for(int j=0; j<primes.size(); ++j)
                if((i&(1<<j))>0) res[i] *= primes.get(j);
        }
        return res;
    }
    public ArrayList<Integer> divisors(int x){
        ArrayList<Pair> pfs = primeFactors(x);
        ArrayList<Integer> ans = new ArrayList<>();
        f(1,0,pfs,ans);
        ans.add(1);
        return ans;
    }
    private static void f(int x, int i, ArrayList<Pair> pfs,ArrayList<Integer> ans){
        if(i>=pfs.size()) return;
        f(x,i+1,pfs,ans);
        for(int cnt=1; cnt<=pfs.get(i).y; ++cnt){
            x *= pfs.get(i).x;
            ans.add(x);
            f(x,i+1,pfs,ans);
        }
    }
}
