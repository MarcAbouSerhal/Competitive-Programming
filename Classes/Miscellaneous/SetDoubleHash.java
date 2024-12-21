// can query hash of set of elements in range [l,r]
class SetDoubleHash{
    private final static long pow(long x, long n, long m){
        if(n == 0) return 1;
        long res = pow(x, n>>1, m);
        res = (res * res)%m;
        if(n % 2 == 1) res = (res * x)%m;
        return res;
    }
    public final static void init(int n){
        inv_p1_pow = new long[n]; 
        inv_p2_pow = new long[n];
        p1_pow = new long[n];
        p2_pow = new long[n];
        inv_p1_pow[0] = inv_p2_pow[0] = p1_pow[0] = p2_pow[0] = 1;
        final long inv_p1 = pow(p1, m1-2, m1), inv_p2 = pow(p2, m2-2, m2);
        for(int i = 1; i < n; ++i){
            p1_pow[i] = (p1_pow[i-1] * p1)%m1;
            p2_pow[i] = (p2_pow[i-1] * p2)%m2;
            inv_p1_pow[i] = (inv_p1_pow[i-1] * inv_p1)%m1;
            inv_p2_pow[i] = (inv_p2_pow[i-1] * inv_p2)%m2;
        }
    }
    static final long m1 = 3030000073l, m2 = 3030000097l;
    static final long p1 = 29, p2 = 31;
    static long[] inv_p1_pow, inv_p2_pow;
    static long[] p1_pow, p2_pow;
    private final long[] h1, h2;
    public SetDoubleHash(ArrayList<Integer> s){
        int n = s.size();
        h1 = new long[n]; h2 = new long[n];
        h1[0] = p1_pow[s.get(0)];
        h2[0] = p2_pow[s.get(0)];
        for(int i = 1; i < n; ++i){
            h1[i] = h1[i-1] + p1_pow[s.get(i)];
            h2[i] = h2[i-1] + p2_pow[s.get(i)];
        }
    }
    public final long get(int l, int r){
        if(l == 0) return ((h1[r] % m1)<<32) | (h2[r] % m2);
        return ((((h1[r] - h1[l-1])%m1 * inv_p1_pow[l])%m1)<<32) | ((h2[r] - h2[l-1])%m2* inv_p2_pow[l])%m2; 
    }
    public final long get(int l1, int r1, int l2, int r2){
        return ((((l1 == 0 ? h1[r1] : (h1[r1] - h1[l1-1])%m1 * inv_p1_pow[l1])%m1 + (h1[r2] - h1[l2-1]) % m1 * inv_p1_pow[l2-(r1+1)])%m1) << 32)  | ((l1 == 0 ? h2[r1] : (h2[r1] - h2[l1-1])%m2 * inv_p2_pow[l1])%m2 + (h2[r2] - h2[l2-1])%m2 * inv_p2_pow[l2-(r1+1)])%m2;
    } 
}
