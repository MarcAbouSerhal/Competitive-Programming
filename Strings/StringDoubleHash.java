class StringDoubleHash{
    private static final long m1 = 3030000179l, m2 = 3030001067l;
    private static final long b1 = new Random().nextLong(2, m1), b2 = new Random().nextLong(2, m2);
    private static long[] inv_b1_pow, inv_b2_pow;
    private static long[] b1_pow, b2_pow;
    private final long[] h1, h2;
    // call this to initialize prime powers, n is size of biggest string
    public final static void init(int n){
        inv_b1_pow = new long[n]; 
        inv_b2_pow = new long[n];
        b1_pow = new long[n];
        b2_pow = new long[n];
        inv_b1_pow[0] = inv_b2_pow[0] = b1_pow[0] = b2_pow[0] = 1;
        long inv_p1 = pow(b1, m1-2, m1), inv_p2 = pow(b2, m2-2, m2);
        for(int i = 1; i < n; ++i){
            b1_pow[i] = (b1_pow[i - 1] * b1) % m1;
            b2_pow[i] = (b2_pow[i - 1] * b2) % m2;
            inv_b1_pow[i] = (inv_b1_pow[i - 1] * inv_p1) % m1;
            inv_b2_pow[i] = (inv_b2_pow[i - 1] * inv_p2) % m2;
        }
    }
    public StringDoubleHash(char[] s){
        int n = s.length;
        h1 = new long[n]; h2 = new long[n];
        h1[0] = h2[0] = s[0] - 'a' + 1;
        for(int i = 1; i < n; ++i){
            h1[i] = h1[i - 1] + (s[i] - 'a' + 1) * b1_pow[i];
            h2[i] = h2[i - 1] + (s[i] - 'a' + 1) * b2_pow[i];
        }
    }

    // note that this doesn't make sure that % value is negative because h1 and h2 are positive
    // because loop doesnt't % the prefix hash

    // below 2 functios are O(1)
    public final long get(int l, int r){ return l == 0 ? ((h1[r] % m1) << 32) | (h2[r] % m2) : ((((h1[r] - h1[l - 1]) % m1 * inv_b1_pow[l]) % m1) << 32) | ((h2[r] - h2[l - 1]) % m2 * inv_b2_pow[l]) % m2;  }
    public final long get(int l1, int r1, int l2, int r2){ return ((((l1 == 0 ? h1[r1] : (h1[r1] - h1[l1 - 1]) % m1 * inv_b1_pow[l1]) % m1 + (h1[r2] - h1[l2 - 1]) % m1 * inv_b1_pow[l2 - (r1 + 1)]) % m1) << 32)  | ((l1 == 0 ? h2[r1] : (h2[r1] - h2[l1 - 1]) % m2 * inv_b2_pow[l1])%m2 + (h2[r2] - h2[l2 -1])%m2 * inv_b2_pow[l2 - (r1 + 1)]) % m2; } 
    
    private static final long pow(long x, long n, long m){
        long res = 1;
        while(n != 0) {
            if((n & 1) == 1) res = (res * x) % m;
            x = (x * x) % m;
            n >>= 1;
        }
        return res;
    }
}
