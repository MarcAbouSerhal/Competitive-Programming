class EnhancedBIT {
    private final long[] b1, b2, pref;
    private final int size;
    // (O(n))
    public EnhancedBIT(long[] a) {
        size = a.length;
        b1 = new long[size + 1];
        b2 = new long[size + 1];
        pref = new long[size];
        pref[0] = a[0];
        for(int i = 1; i < size; ++i) pref[i] = pref[i - 1] + a[i];
    }
    // returns sum(a[l, r]) (O(log(n)))
    public final long get(int l, int r) { return sum(r) - sum(l - 1) + pref[r] - (l == 0 ? 0 : pref[l - 1]); }
    // adds v to a[x] (O(log(n)))
    public final void add(int x, long v) { add(b1, x, v); add(b1, x + 1, -v); add(b2, x, v * (x - 1)); add(b2, x + 1, -v * x); }
    // adds v to to each element in a[l, r] (O(log(n)))
    public final void add(int l, int r, long v){ add(b1, l, v); add(b1, r + 1, -v); add(b2, l, v * (l - 1)); add(b2, r + 1, -v * r); }
    private final long sum(int r){
        long result = 0;
        for(int i = r + 1; i > 0; i -= i & (-i)) result += b1[i];
        result *= r;
        for(int i = r + 1; i > 0; i -= i & (-i)) result -= b2[i];
        return result;
    }
    public void add(long[] b, int i, long v) { for(++i; i <= size; i += i & (-i)) b[i] += v; }
}
