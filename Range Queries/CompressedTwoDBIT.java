class CompressedTwoDBIT {
    private final long[][] tree;
    private final ArrayList<Integer>[] ys;
    public CompressedTwoDBIT(int rows, ArrayList<Cell> cells) {
        tree = new long[rows][];
        ys = new ArrayList[rows];
        for(int i = 0; i < rows; ++i) ys[i] = new ArrayList<>();
        for(Cell cell: cells) 
            for(int i = cell.i; i < rows; i = i | (i + 1)) ys[i].add(cell.j);
        for(int i = 0; i < rows; ++i) 
            if(ys[i].size() != 0) {
                ArrayList<Integer> oldys = ys[i], newys = new ArrayList<>(oldys.size());
                Collections.sort(oldys);
                newys.add(oldys.get(0));
                for(int j = 1; j < oldys.size(); ++j)
                    if(oldys.get(j) != oldys.get(j - 1)) newys.add(oldys.get(j));
                ys[i] = newys;
                tree[i] = new long[newys.size()];
            }
    }
    public final void add(int x, int y, long v) { for(; x < tree.length; x = x | (x + 1)) if(tree[x] != null) add(tree[x], index(ys[x], y), v); }
    public final long get(int x1, int y1, int x2, int y2) { return prefSum(x2, y2) + prefSum(x1 - 1, y1 - 1) - prefSum(x2, y1 - 1) - prefSum(x1 - 1, y2); }
    private final long prefSum(int i, int j) { long s = 0; for(; i >= 0; i = (i & (i + 1)) - 1) s += sum(tree[i], index(ys[i], j)); return s; }
    private final void add(long[] b, int i, long v) { if(i == -1) return; for(; i < b.length; i = i | (i + 1)) b[i] += v; }
    private final long sum(long[] b, int i) { long s = 0; for(; i >= 0; i = (i & (i + 1)) - 1) s += b[i]; return s; }
    private final static int index(ArrayList<Integer> ys, int y) {
        if(ys.size() == 0 || ys.get(0) > y) return -1;
        int lo = 0, hi = ys.size() - 1, index = -1;
        while(lo <= hi) {
            int mid = (lo + hi) >> 1;
            if(ys.get(mid) <= y) { index = mid; lo = mid + 1; }
            else hi = mid - 1;
        }
        return index;
    }
}
class Cell {
    final int i, j;
    public Cell(int i, int j) { this.i = i; this.j = j; }
}
