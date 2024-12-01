class MathUtil{
    public static int[] mobius(int n){
        int[] mobius = new int[n+1], sp = new int[n+1];
        ArrayList<Integer> primes = new ArrayList<>();
        mobius[1] = 1;
        for(int i=2; i<=n; ++i){
            if(sp[i] == 0){
                sp[i] = i;
                mobius[i] = -1;
                primes.add(i);
            }
            for(int j=0; i*primes.get(j) <= n; ++j){
                sp[i*primes.get(j)] = primes.get(j);
                if(i%primes.get(j)!=0)
                    mobius[i*primes.get(j)] = mobius[i] * mobius[primes.get(j)];
                else mobius[i*primes.get(j)] = 0;
                if(primes.get(j) == sp[i]) break;
            }
        }
        return mobius;
    }
    public static int[] phi(int n){
        int[] phi = new int[n+1];
        for(int i=1; i<=n; ++i) phi[i] = i;
        for(int i=2; i<=n; ++i)
            if(phi[i] == i){
                phi[i] = i-1;
                for(int j=2*i; j<=n; j+=i)
                    phi[j] = (phi[j]/i)*(i-1);
            }
        return phi;
    }
    public static ArrayList<Integer>[] divisorsUpTo(int n){
        ArrayList<Integer>[] div = new ArrayList[n+1];
        for(int i=1; i<=n; ++i) div[i] = new ArrayList<>();
        for(int i=1; i<=n; ++i)
            for(int j=i; j<=n; j+=i)
                div[j].add(i);
        return div;
    }
}
