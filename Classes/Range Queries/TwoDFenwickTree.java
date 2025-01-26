class TwoDFenwickTree {
    // replace every +/- with ^ to make it a XOR fenwick tree
    private final long[][] tree;
    // (O(n.m))
    public TwoDFenwickTree(int rows, int columns) {
        tree = new long[rows][columns];
    }
    // (O(n.m))
    public TwoDFenwickTree(long[][] a) {
        final int n = a.length, m = a[0].length;
        tree = new long[n][m];
        for (int i = 0; i < n; i++)
            for(int j = 0; j < m; ++j){
                tree[i][j] += a[i][j];
                int k = j | (j + 1);
                if(k < m) tree[i][k] += tree[i][j];
            }
        // now every row is good
        for(int j = 0; j < m; ++j)
            for(int i = 0; i < n; ++i){
                int k = i | (i + 1);
                if(k < n) tree[k][j] += tree[i][j];
            }
    }
    // returns sum(a[x1...x2][y1...y2]) (O(log(n).log(m)))
    public final long get(int x1, int x2, int y1, int y2) {
        long result = 0;
        int i = x2;
        while(i >= 0){
            int j = y2;
            while(j >= 0){
                result += tree[i][j];
                j = (j & (j + 1)) - 1;
            }
            i = (i & (i + 1)) - 1;
        }
        i = x2;
        while(i >= 0){
            int j = y1 - 1;
            while(j >= 0){
                result -= tree[i][j];
                j = (j & (j + 1)) - 1;
            }
            i = (i & (i + 1)) - 1;
        }
        i = x1 - 1;
        while(i >= 0){
            int j = y2;
            while(j >= 0){
                result -= tree[i][j];
                j = (j & (j + 1)) - 1;
            }
            i = (i & (i + 1)) - 1;
        }
        i = x1 - 1;
        while(i >= 0){
            int j = y1 - 1;
            while(j >= 0){
                result += tree[i][j];
                j = (j & (j + 1)) - 1;
            }
            i = (i & (i + 1)) - 1;
        }
        return result;
    }
    // adds v to a[x][y] (or xors a[x][y] with v) (O(log(n).log(m)))
    public void add(int x, int y, long v) {
        final int n = tree.length, m = tree[0].length;
        while (x < n) {
            int j = y;
            while(j < m){
                tree[x][j] += v;
                j = j | (j + 1);
            }
            x = x | (x + 1);
        }
    }
}
