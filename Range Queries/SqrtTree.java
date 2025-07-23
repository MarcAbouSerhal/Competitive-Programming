// replace X with type of property (or Tuple of properties)
class SqrtTree{
    private final int n, lg;
    private final X[] a;
    private final int[] onLayer;
    private final ArrayList<Integer> layers = new ArrayList<>();
    private final X[][] pref, suf, between;
    // (O(nloglog(n)))
    public SqrtTree(X[] arr){
        a = arr;
        n = a.length;
        lg = 32 - Integer.numberOfLeadingZeros(n);
        onLayer = new int[lg + 1];
        int tlg = lg;
        while(tlg > 1) {
            onLayer[tlg] = layers.size();
            layers.add(tlg);
            tlg = (tlg + 1) >> 1;
        }
        for(int i = lg - 1; i >= 0; --i) onLayer[i] = Math.max(onLayer[i], onLayer[i + 1]);
        pref = new X[layers.size()][n];
        suf = new X[layers.size()][n];
        between = new X[layers.size()][1 << lg];
        build(0, 0, n);
    }
    // (O(1))
    public final X get(int l, int r) {
        if(l == r) return a[l];
        else if(l + 1 == r) return op(a[l], a[r]);
        int layer = onLayer[31 - Integer.numberOfLeadingZeros(l ^ r)], layerSz = layers.get(layer);
        int bSzLog = (layerSz + 1) >> 1;
        int bCntLog = layerSz >> 1;
        int lBound = (l >> layerSz) << layerSz;
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
            int r = Math.min(l + bSz, rBound);
            pref[layer][l] = a[l];
            for (int i = l + 1; i < r; ++i) pref[layer][i] = op(pref[layer][i - 1], a[i]);
            suf[layer][r - 1] = a[r - 1];
            for (int i = r - 2; i >= l; --i) suf[layer][i] = op(a[i], suf[layer][i + 1]);
            build(layer + 1, l, r);
        }
        for (int i = 0; i < bCnt; ++i) {
            int shift = lBound + (i << bCntLog);
            between[layer][shift + i] = suf[layer][lBound + (i << bSzLog)];
            for(int j = i + 1; j < bCnt; ++j) between[layer][shift + j] = op(between[layer][shift + j - 1], suf[layer][lBound + (j << bSzLog)]);
        }
    }
    // CHANGE THESE FUNCTIONS
    private static final X op(X a, X b) {
        if(a == null) return b;
        if(b == null) return a;
        // define associative operation here (f(f(a, b), c)=f(a, f(b, c)))
    }
}
