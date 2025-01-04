// can query hash of set of elements in range [l,r] or [l1,r1] U [l2,r2]
class SetDoubleHash{
    public final static void init(int n){
        p1_pow = new long[n];
        p2_pow = new long[n];
        p1_pow[0] = p2_pow[0] = 1;
        for(int i = 1; i < n; ++i){
            p1_pow[i] = (p1_pow[i-1] * p1)%m1;
            p2_pow[i] = (p2_pow[i-1] * p2)%m2;
        }
    }
    static final long m1 = 3030000073l, m2 = 3030000097l;
    static final long p1 = 29, p2 = 31;
    static long[] inv_p1_pow, inv_p2_pow;
    static long[] p1_pow, p2_pow;
    private final long[] h1, h2;
    // (O(n))
    public SetDoubleHash(ArrayList<Integer> s){
        int n = s.size();
        h1 = new long[n]; h2 = new long[n];
        h1[0] = p1_pow[s.get(0)];
        h2[0] = p2_pow[s.get(0)];
        for(int i = 1; i < n; ++i){
            h1[i] = h1[i - 1] + p1_pow[s.get(i)];
            h2[i] = h2[i - 1] + p2_pow[s.get(i)];
        }
    }
    // below are all O(1)
    public final long get(int l, int r){
        if(l == 0) return ((h1[r] % m1) << 32) | (h2[r] % m2);
        return (((h1[r] - h1[l - 1]) % m1)<< 32) | (h2[r] - h2[l - 1]) % m2; 
    }
    public final long get(int l1, int r1, int l2, int r2){
        return ((((l1 == 0 ? h1[r1] : h1[r1] - h1[l1 - 1]) + h1[r2] - h1[l2 - 1]) % m1) << 32)  | ((l1 == 0 ? h2[r1] : h2[r1] - h2[l1 - 1]) + h2[r2] - h2[l2 - 1]) % m2;
    } 
}