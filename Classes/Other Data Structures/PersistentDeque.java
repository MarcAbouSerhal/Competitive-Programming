// is initialized to [[]] with version[0] being an empty list
// and the ith add/remove query results in version[i]
class PersistentDeque<E>{
    private final int[][] up;
    private final int[] depth;
    private final int[] first, last, size;
    private final ArrayList<E> element;
    private final int n, log;
    private int count = 0;
    public PersistentDeque(int queries){
        n = queries + 1;
        up = new int[n][log = 32 - Integer.numberOfLeadingZeros(n)];
        depth = new int[n];

        first = new int[n];
        last = new int[n];
        size = new int[n];
        element = new ArrayList<>(n); element.add(null);
    }
    // returns size of version[v]
    public final int size(int v){
        return size[v];
    }
    // adds [e] + version[v] 
    public final void addToLeft(int v, E e){
        int start = first[v], end = last[v], u = element.size();
        ++count;
        first[count] = u;
        last[count] = end;
        size[count] = size[v] + 1;
        element.add(e);

        up[u][0] = start;
        depth[u] = depth[start] = 1;
        for(int i = 1; i < log; ++i) up[u][i] = up[up[u][i - 1]][i - 1];
    }
    // adds [e] + version[v][l ... r] 
    // if r < l, adds [e]
    public final void addToLeft(int v, int l, int r, E e){
        int start = r < l ? 0 : ith(first[v], last[v], size[v], l), end = r < l ? 0 : ith(first[v], last[v], size[v], r), u = element.size();
        ++count;
        first[count] = u;
        last[count] = end;
        size[count] = r < l ? 1 : r - l + 2;
        element.add(e);

        up[u][0] = start;
        depth[u] = depth[start] = 1;
        for(int i = 1; i < log; ++i) up[u][i] = up[up[u][i - 1]][i - 1];
    }
    // adds version[v] + [e]
    public final void addToRight(int v, E e){
        int start = first[v], end = last[v], u = element.size();
        ++count;
        first[count] = start;
        last[count] = u;
        size[count] = size[v] + 1;
        element.add(e);

        up[u][0] = end;
        depth[u] = depth[end] = 1;
        for(int i = 1; i < log; ++i) up[u][i] = up[up[u][i - 1]][i - 1];
    }
    // adds [e] + version[v][l ... r] 
    // if r < l, adds [e]
    public final void addToRight(int v, int l, int r, E e){
        int start = r < l ? 0 : ith(first[v], last[v], size[v], l), end = r < l ? 0 : ith(first[v], last[v], size[v], r), u = element.size();
        ++count;
        first[count] = start;
        last[count] = u;
        size[count] = r < l ? 1 : r - l + 2;
        element.add(e);

        up[u][0] = end;
        depth[u] = depth[end] = 1;
        for(int i = 1; i < log; ++i) up[u][i] = up[up[u][i - 1]][i - 1];
    }
    // adds version[v][l ... r] if l <= r, [] otherwise
    public final void add(int v, int l, int r){
        ++count;
        if(l <= r){
            first[count] = ith(first[v], last[v], size[v], l);
            last[count] = ith(first[v], last[v], size[v], r);
            size[count] = r - l + 1;
        }
    }
    // return version[v][i]
    public final E get(int v, int i){
        return element.get(ith(first[v], last[v], size[v], i));
    }
    // returns ith vertex on the simple path [u, ..., v] (O(log(n)))
    private final int ith(int u, int v, int sz, int i){
        // sz is size of path [u, ..., v]
        if(depth[u] < depth[v]){
            u ^= v; v ^= u; u ^= v; i = sz - i - 1;
        }
        int w = kthAncestor(u, depth[u] - depth[v]);
        if(w == v) return kthAncestor(u, i); // lca(u, v) = v
        int x = w, y = v;
        for(int l = log - 1; l >= 0; --l)
            if(up[x][l] != up[y][l]){
                x = up[x][l];
                y = up[y][l];
            }
        x = up[x][0];
        int d1 = depth[u] - depth[x] + (x == 0 ? 0 : 1), d2 = sz - d1;
        return i < d1 ? kthAncestor(u, i) : kthAncestor(v, d2 - 1 - i + d1); 
    } 
    // (O(log(n)))
    private final int kthAncestor(int u, int k){
        for(int i = 0; i < log; ++i)
            if((k & (1 << i)) != 0)
                u = up[u][i];
        return u;
    }
}
