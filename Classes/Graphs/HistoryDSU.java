// not a fully persistent, only looks at old versions, can't branch off from them
// version v has v contributing edges / n - v components
class HistoryDSU {
    private final List[] p;
    private final int[] d;
    int components;
    public HistoryDSU(int n) {
        p = new List[components = n];
        for (int i = 0; i < n; ++i) p[i] = new List();
        d = new int[n];
    }
    // return p(a) in the specified version of the graph
    public final int find(int a, int version) {
        int next = p[a].get(version);
        return next < 0 ? a : find(next, version);
    }
    // connects a and b, results in a new version if # components decreases
    public final void join(int a, int b) {
        int lastVersion = p.length - components;
        a = find(a, lastVersion);
        b = find(b, lastVersion);
        if (a == b) return;
        components--;
        int sizeA = -p[a].top(), sizeB = -p[b].top();
        if(sizeA < sizeB){
            a ^= b ; b ^= a; a ^= b;
            sizeA ^= sizeB; sizeB ^= sizeA; sizeA ^= sizeB;
        }
        p[a].add(-(sizeA + sizeB), lastVersion + 1);
        p[b].add(a, lastVersion + 1);
        d[b] = sizeA;
    }
    // returns size of components containing u, in the specified version of the graph
    public final int size(int u, int version) { return -p[find(u, version)].get(version); }
    
    // returns permutation of [0, 1, ..., n-1] where component containing u at version v
    // is at [p(u, v) ... p(u, v) + size(u, v) - 1]
    public final int[] permutation(){
        int n = p.length;
        int[] perm = new int[n], pos = new int[n];
        for(int i = 0; i < n; ++i) pos[i] = -1;
        perm[0] = find(0, n - 1);
        pos[perm[0]] = 0;
        for(int i = 0; i < n; ++i)
            if(pos[i] == -1)
                dfs(i, pos, perm);
        return perm;
    }
    private final void dfs(int u, int[] pos, int[] perm){
        int next = p[u].top();
        if(pos[next] == -1) dfs(next, pos, perm);
        perm[pos[u] = pos[next] + d[u]] = u;
    }

    private final static class List{
        int size = 1;
        private int[] p = new int[15], v = new int[15];
        public List() { p[0] = -1; }
        public final void add(int x, int version){
            if(size == p.length){
                int[] biggerP = new int[(size * 3) >> 1], biggerV = new int[(size * 3) >> 1];
                for(int i = 0; i < size; ++i){
                    biggerP[i] = p[i];
                    biggerV[i] = v[i];
                }
                p = biggerP;
                v = biggerV;
            }
            p[size] = x;
            v[size++] = version;
        }
        // returns value at version
        public final int get(int version){
            if(v[size - 1] <= version) return p[size - 1];
            int l = 0, r = size - 1;
            while(l < r){
                if(l == r - 1){
                    if(v[r] <= version) l = r;
                    break;
                }
                int mid = (l + r) >> 1;
                if(v[mid] <= version) l = mid;
                else r = mid - 1;
            }
            return p[l];
        }
        public final int top(){
            return p[size - 1];
        }
    }
}
