// uses way less memory than sorting sets and doing normal polynomial hash
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
    static long[] p1_pow, p2_pow;
    private long h1 = 0, h2 = 0;
    // initializes an empty set
    public SetDoubleHash(){}
    // (O(n))
    public SetDoubleHash(ArrayList<Integer> s){
        for(int i: s){
            h1 += p1_pow[i];
            h2 += p2_pow[i];
        }
        h1 %= m1;
        h2 %= m2;
    }
    // below are all O(1)
    public final long get(){
        return (h1 << 32) | h2;
    }
    public final long getWithout(int x){
        return (((h1 - p1_pow[x] + m1) % m1) << 32) | ((h2 - p2_pow[x] + m2) % m2);
    }
    public final void remove(int x){
        h1 = (h1 - p1_pow[x] + m1) % m1;
        h2 = (h2 - p2_pow[x] + m2) % m2;
    }
    public final void add(int x){
        h1 = (h1 + p1_pow[x]) % m1;
        h2 = (h2 + p2_pow[x]) % m2;
    }
}
