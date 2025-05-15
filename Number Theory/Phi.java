class Phi {
    // phi[i] is number of comprimes to i < it ((O(n)))
    public final static int[] phi(int n) {
        int[] phi = new int[n + 1];
        for(int i = 1; i <= n; ++i) phi[i] = i;
        for(int i = 2; i <= n; ++i)
            if(phi[i] == i){
                phi[i] = i - 1;
                for(int j = i << 1; j <= n; j += i)
                    phi[j] = (phi[j] / i) * (i - 1);
            }
        return phi;
    }
}
