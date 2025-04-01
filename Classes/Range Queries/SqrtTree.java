// replace X with type of property (or Tuple of properties)
// if X is primitive: replace null by value that will be ignored by op
class SqrtTree{
    private final int n, lg;
    private final X[] a;
    private final int[] floorLog, onLayer;
    private final ArrayList<Integer> layers = new ArrayList<>();
    private final X[][] pref, suf, between;
    // (O(nloglog(n)))
    public SqrtTree(X[] arr){
        a = arr;
        n = a.length;
        lg = 32 - Integer.numberOfLeadingZeros(n);
        floorLog = new int[1 << lg];
        for(int i = 1; i < floorLog.length; ++i) floorLog[i] = 1 + floorLog[i >> 1];
        onLayer = new int[lg + 1];
        int tlg = lg;
        while(tlg > 1) {
            onLayer[tlg] = layers.size();
            layers.add(tlg);
            tlg = (tlg + 1) >> 1;
        }
        for(int i = lg - 1; i >= 0; --i) onLayer[i] = max(onLayer[i], onLayer[i + 1]);
        pref = new X[layers.size()][n];
        suf = new X[layers.size()][n];
        between = new X[layers.size()][1 << lg];
        build(0, 0, n);
    }
    // (O(1))
    public final X get(int l, int r) {
        if(l == r) return a[l];
        else if(l + 1 == r) return op(a[l], a[r]);
        int layer = onLayer[floorLog[l ^ r]];
        int bSzLog = (layers.get(layer) + 1) >> 1;
        int bCntLog = layers.get(layer) >> 1;
        int lBound = (l >> layers.get(layer)) << layers.get(layer);
        int lBlock = ((l - lBound) >> bSzLog) + 1;
        int rBlock = ((r - lBound) >> bSzLog) - 1;
        X ans = suf[layer][l];
        if(lBlock <= rBlock) ans = op(ans, between[layer][lBound + (lBlock << bCntLog) + rBlock]);
        ans = op(ans, pref[layer][r]);
        return ans;
    }
    private final void build(int layer, int lBound, int rBound) {
        if (layer >= layers.size()) return;
        int bSzLog = (layers.get(layer) + 1) >> 1;
        int bCntLog = layers.get(layer) >> 1;
        int bSz = 1 << bSzLog;
        int bCnt = 0;
        for (int l = lBound; l < rBound; l += bSz) {
            bCnt++;
            int r = min(l + bSz, rBound);
            pref[layer][l] = a[l];
            for (int i = l + 1; i < r; ++i) pref[layer][i] = op(pref[layer][i - 1], a[i]);
            suf[layer][r-1] = a[r-1];
            for (int i = r - 2; i >= l; --i) suf[layer][i] = op(a[i], suf[layer][i + 1]);
            build(layer + 1, l, r);
        }
        for (int i = 0; i < bCnt; ++i) {
            X ans = id();
            for (int j = i; j < bCnt; ++j) {
                X add = suf[layer][lBound + (j << bSzLog)];
                ans = (i == j) ? add : op(ans, add);
                between[layer][lBound + (i << bCntLog) + j] = ans;
            }
        }
    }
    private static final int max(int a, int b) { return a > b ? a : b; }
    private static final int min(int a, int b) { return a < b ? a : b; }
    private static final X id() {
        // return identity here, such that op(id, x) = op(x, id) = x
        return null;
    }
    private static final X op(X a, X b) {
        if(a == null) return b;
        if(b == null) return a;
        // define associative operation here (f(f(a, b), c)=f(a, f(b, c)))
    }
}
