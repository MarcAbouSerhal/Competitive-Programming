class FenwickTree {
    // replace every +/- with ^ to make it a XOR fenwick tree
    private final long[] tree;
    public FenwickTree(int size) {
        tree = new long[size];
    }
    // (O(n))
    public FenwickTree(long[] a) {
        this.tree = a.clone();
        for (int i = 0; i < tree.length; i++) {
            int j = i | (i + 1);
            if (j < tree.length)
                tree[j] += tree[i];
        }
    }
    // returns prop(a[l...r]) (O(log(n)))
    public final long get(int l, int r) {
        long result = 0;
        while (r >= 0) {
            result += tree[r];
            r = (r & (r + 1)) - 1;
        }
        --l;
        while (l >= 0) {
            result -= tree[l];
            l = (l & (l + 1)) - 1;
        }
        return result;
    }
    // adds v to a[x] (or xors v with a[x]) (O(log(n)))
    public void add(int x, long v) {
        while (x < tree.length) {
            tree[x] += v;
            x = x | (x + 1);
        }
    }
}
