class MathUtil{
    // div[i] is sorted list of divisors of i (O(nlog(n)))
    public final static ArrayList<Integer>[] divisorsUpTo(int n){
        ArrayList<Integer>[] div = new ArrayList[n + 1];
        for(int i = 1; i <= n; ++i) div[i] = new ArrayList<>();
        for(int i = 1; i <= n; ++i)
            for(int j = i; j <= n; j += i)
                div[j].add(i);
        return div;
    }
    // finds divisors of a (O(sqrt(a)))
    public final  static ArrayList<Integer> divisors(int a){
        ArrayList<Integer> factors = new ArrayList<>();
        factors.add(1);
        int i = 2;
        while(i * i<=a){
            if(a % i == 0) factors.add(i);
            i++;
        }
        for(int j = factors.get(factors.size() - 1) * factors.get(factors.size() - 1) == a ? factors.size() - 2 : factors.size() - 1; j>=0; --j)
            factors.add(a/factors.get(j));
        return factors;
    }
}
