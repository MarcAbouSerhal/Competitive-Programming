// supports updates
class DynamicHash{
    private static final class FenwickTree {
        private final long[] tree;
        private final long m;
        public FenwickTree(long[] a, long m) {
            this.m = m;
            tree = a.clone();
            for (int i = 0; i < tree.length; i++) {
                int j = i | (i + 1);
                if (j < tree.length) {
                    tree[j] = (tree[j] + tree[i])%m;
                }
            }
        }
        public final long get(int l, int r) {
            long result = 0;
            while (r >= 0) {
                result = (result + tree[r])%m;
                r = (r & (r + 1)) - 1;
            }
            --l;
            while (l >= 0) {
                result = (result - tree[l] + m)%m;
                l = (l & (l + 1)) - 1;
            }
            return result;
        }
        public final void add(int i, long x) {
            while (i < tree.length) {
                tree[i] = (tree[i] + x + m)%m;
                i = i | (i + 1);
            }
        }
    }
    private static final long pow(long x, long n, long m){
        if(n == 0) return 1;
        long res = pow(x, n>>1, m);
        res = (res * res)%m;
        if(n % 2 == 1) res = (res * x)%m;
        return res;
    }
    private final char[] s;
    private final long m1, m2;
    private final long[] inv_p1_pow, inv_p2_pow;
    private final long[] p1_pow, p2_pow;
    private final FenwickTree h1, h2;
    public DynamicHash(char[] s){ this(s, 29, 31, 3030000073l, 3030000097l); }
    public DynamicHash(char[] s, long p1, long p2, long m1, long m2){
        int n = s.length;
        this.s = s.clone();
        this.m1 = m1;
        this.m2 = m2;
        long[] h1 = new long[n], h2 = new long[n];
        h1[0] = h2[0] = s[0] - 'a' + 1;
        inv_p1_pow = new long[n]; inv_p2_pow = new long[n];
        p1_pow = new long[n]; p2_pow = new long[n];
        inv_p1_pow[0] = inv_p2_pow[0] = p1_pow[0] = p2_pow[0] = 1;
        long inv_p1 = pow(p1, m1-2, m1), inv_p2 = pow(p2, m2-2, m2);
        for(int i = 1; i < n; ++i){
            inv_p1_pow[i] = (inv_p1_pow[i-1] * inv_p1)%m1;
            inv_p2_pow[i] = (inv_p2_pow[i-1] * inv_p2)%m2;
            p1_pow[i] = (p1_pow[i-1] * p1)%m1;
            p2_pow[i] = (p2_pow[i-1] * p2)%m2;
            h1[i] = ((s[i] - 'a' + 1)*p1_pow[i])%m1;
            h2[i] = ((s[i] - 'a' + 1)*p2_pow[i])%m2;
        }
        this.h1 = new FenwickTree(h1, m1);
        this.h2 = new FenwickTree(h2, m2);
    }
    public final long get(int l, int r){
        return (((h1.get(l,r) * inv_p1_pow[l] + m1)%m1)<<32) + (h2.get(l,r) * inv_p2_pow[l] + m2)%m2; 
    }
    public final void set(int i, char c){
        h1.add(i, ((c-s[i])*p1_pow[i])%m1);
        h2.add(i, ((c-s[i])*p2_pow[i])%m2);
        s[i] = c;
    }
}
