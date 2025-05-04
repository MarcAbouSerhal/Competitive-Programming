class ReachabilityTree{
    private final int[] dsu, leftChild, rightChild, inVertex, outVertex, inEdge, outEdge, vertexPermutation, edgePermutation, vertexIndex;
    private final int[][] up;
    private final int n, m, log;
    private int next, vertexTick = 0, edgeTick = 0;
    public ReachabilityTree(int n, int  m) {
        dsu = new int[n + m];
        up = new int[log = 32 - Integer.numberOfLeadingZeros(n + m)][n + m];
        leftChild = new int[n + m];
        rightChild = new int[n + m];
        vertexPermutation = new int[n];
        edgePermutation = new int[m];
        vertexIndex = new int[n];
        inVertex = new int[m];
        outVertex = new int[m];
        inEdge = new int[m];
        outEdge = new int[m];
        this.n = n;
        this.m = m;
        next = n;
        for (int i = 0; i < n + m; ++i) 
            leftChild[i] = rightChild[i] = dsu[i] = up[0][i] = -1;
    }
    // adds edge (u,v) (O(log(m + n)))
    public final void addEdge(int u, int v) {
        u = find(u);
        v = find(v);
        if(u == v) {
            dsu[u] = up[0][u] = next;
            leftChild[next++] = u;
        }
        else {
            dsu[u] = dsu[v] = up[0][u] = up[0][v] = next;
            leftChild[next] = u;
            rightChild[next++] = v;
        }
    }
    // returns last edge connected to u up to edge e
    // or -1 if there is none (O(log(n + m)))
    public final int lastEdgeBefore(int u, int e) {
        if(up[0][u] == -1 || up[0][u] > e + n) return -1;
        for(int i = log - 1; i >= 0; --i)
            if(up[i][u] != -1 && up[i][u] <= e + n)
                u = up[i][u];
        return u - n;
    }
    // returns first edge connecting u and v
    // or -1 if there is none (O(log(n + m)))
    public final int firstEdgeConnecting(int u, int v) {
        if(find(u) != find(v)) return -1;
        for(int i = log - 1; i >= 0; --i) {
            int p1 = up[i][u], p2 = up[i][v];
            if(p1 != -1 && p1 == p2) {
                u = p1;
                v = p2;
            }
        }
        return up[0][u] - n;
    }
    // returns last edge added to component containing u (O(log(n + m)))
    public final int lastEdge(int u) { return find(u) - n; }
    // returns range [l,r] of vertices in the permutation
    // that are connected to u up to edge e (O(log(n + m)))
    public final int[] vertexRange(int u, int e) {
        e = lastEdgeBefore(u, e);
        if(e == -1) return new int[] {vertexIndex[u], vertexIndex[u]};
        return new int[] {inVertex[e], outVertex[e]};
    }
    // returns range [l,r] of edges in the permutation
    // that are connected to u up to edge e 
    // or null if there are none (O(log(n + m)))
    public final int[] edgeRange(int u,int e) {
        e = lastEdgeBefore(u, e);
        if(e == -1) return null;
        return new int[] {inEdge[e], outEdge[e]};
    }
    // builds permutations and sets up queries
    public final void build() {
        for(int j = 1; j < log; ++j)
            for(int i = 0; i < n + m; ++i)
                up[j][i] = up[j - 1][i] == -1 ? -1 : up[j - 1][up[j - 1][i]];
        for(int i = 0; i < n + m; ++i)
            if(up[0][i] == -1)
                dfs(i);
    }
    // returns permutation of vertices
    public final int[] getVertexPermutation() { return vertexPermutation; }
    // returns index of vertices in the permutation
    public final int[] getVertexIndex() { return vertexIndex; }
    // returns permutation of edges
    public final int[] getEdgePermutation() {return edgePermutation; }
    // private methods
    private final int find(int a) {
        while(dsu[a] >= 0 && dsu[dsu[a]] >= 0)
            a = dsu[a] = dsu[dsu[a]];
        return dsu[a] < 0 ? a : dsu[a];
    }
    private final void dfs(int u) {
        if(leftChild[u] == -1) {
            vertexPermutation[vertexIndex[u] = vertexTick++] = u;  
        }
        else {
            inVertex[u - n] = vertexTick;
            edgePermutation[inEdge[u - n] = edgeTick++] = u - n;
            dfs(leftChild[u]);
            if(rightChild[u] != -1) dfs(rightChild[u]);
            outVertex[u - n] = vertexTick - 1;
            outEdge[u - n] = edgeTick - 1;
        }
    }
}
