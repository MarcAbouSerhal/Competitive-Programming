class BIT {
    private final long[] tree;
    public BIT(int size) { tree = new long[size]; }
    public BIT(long[] a) {
        this.tree = a.clone();
        for (int i = 0; i < tree.length; i++) {
            int j = i | (i + 1);
            if (j < tree.length) tree[j] += tree[i];
        }
    }
    public long get(int l, int r) { return sum(r) - sum(l - 1); }
    public long sum(int r) { long s = 0; for(; r >= 0; r = (r & (r + 1)) - 1) s += tree[r]; return s; }
    public void add(int i, long v) { for(; i < tree.length; i |= i + 1) tree[i] += v; }
}
