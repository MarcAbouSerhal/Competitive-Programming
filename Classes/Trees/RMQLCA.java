import java.util.*;

class Tree{
    private int tick = -1;
    private final int[][] d;
    private final int[] depth;
    private final int[] in, out;
    private final int[] floorPow;
    private final ArrayList<Integer>[] adj;
    private final int op(int l, int r){ return depth[l] < depth[r] ? l : r; }
    // (O(nlog(n)))
    public Tree(ArrayList<Integer>[] adj){
        this.adj = adj;
        final int n = adj.length;
        depth = new int[n];
        in = new int[n];
        out = new int[n];
        final int size = (n << 1) - 1;
        floorPow = new int[size + 1];
        floorPow[0] = -1;
        for(int i = 1; i <= size; ++i){
            floorPow[i] = floorPow[i - 1];
            if((i & (i - 1)) == 0) ++floorPow[i];
        }
        final int log = floorPow[size] + 1;
        d = new int[size][log];
        dfs(0, -1);
        for(int j = 1; j < log; ++j)
            for(int i = 0; i + (1 << j) <= size; ++i)
                d[i][j] = op(d[i][j - 1], d[i + (1 << (j - 1))][j - 1]);
    }
    public final void dfs(int u, int p){
        d[in[u] = ++tick][0] = u;
        for(int v: adj[u])
            if(v != p){
                depth[v] = depth[u] + 1;
                dfs(v, u);
                d[++tick][0] = u;
            }
        out[u] = tick;
    }
    // (O(1))
    public final int lca(int a, int b){
        a = in[a]; 
        b = in[b];
        if(a > b){ a = a ^ b; b = a ^ b; a = a ^ b; }
        int x = floorPow[b - a + 1];
        return op(d[a][x], d[b + 1 - (1 << x)][x]);
    }
    // returns whether u is an ancestor of v (O(1))
    public final boolean isAncestor(int u, int v) {
        return in[u] <= in[v] && out[u] >= out[v];
    }
    // returns whether w is on the path from u to v (O(1))
    public final boolean isOnPath(int u, int v, int w) {
        return depth[w] >= depth[lca(u, v)] && (isAncestor(w, u) || isAncestor(w, v));
    }
}
