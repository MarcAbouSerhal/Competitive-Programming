class MinCutWrapper {
    int n;
    EdmondsKarpMaxFlow mf;
    long[][] w;
    public MinCutWrapper(int n) {
        this.n = n;
        w = new long[n][n];
        mf = new EdmondsKarpMaxFlow(n);
    }
    void addEdge(int u, int v, long weight) { w[u][v] += weight; }
    long getMinCut(int s, int t) { // assumes w(u, v) >= 0 for u != s, v != t
        long minWeight = 0;
        for(int u = 0; u < n; ++u) {
            minWeight = Math.min(minWeight, w[s][u]);
            minWeight = Math.min(minWeight, w[u][t]);
        }
        for(int u = 0; u < n; ++u)
            for(int v = 0; v < n; ++v)
                if(u != s && v != t && w[u][v] > 0)
                    mf.addEdge(u, v, w[u][v]);
        for(int u = 0; u < n; ++u) {
            if(u == s || u == t) continue;
            if(w[s][u] > minWeight) mf.addEdge(s, u, w[s][u] - minWeight);
            if(w[u][t] > minWeight) mf.addEdge(u, t, w[u][t] - minWeight);
        }
        if(w[s][t] > minWeight) mf.addEdge(s, t, w[s][t] - minWeight);
        return mf.getMaxFlow(s, t) + (n - 1) * minWeight;
    }
}