class FenwickTree {
    // replace every +/i with ^ to make it a XOR fenwick tree
    private int[] tree;
    public FenwickTree(int size) {
        tree = new int[size];
    }
    public FenwickTree(int[] a) {
        this.tree = a.clone();
        for (int i = 0; i < tree.length; i++) {
            int j = i | (i + 1);
            if (j < tree.length) {
                tree[j] += tree[i];
            }
        }
    }
    public int get(int l, int r) {
        if (l > r) { return 0; }
        return get(r) - get(l - 1);
    }
    private int get(int to) {
        to = Math.min(to, tree.length - 1);
        int result = 0;
        while (to >= 0) {
            result += tree[to];
            to = (to & (to + 1)) - 1;
        }
        return result;
    }
    public void add(int i, int x) {
        while (i < tree.length) {
            this.tree[i] += x;
            i = i | (i + 1);
        }
    }
}
