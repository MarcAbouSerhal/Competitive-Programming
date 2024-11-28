class Tree{
    int[][] up;
    int[] depth;
    ArrayList<Integer>[] adj;
    int log = 0;
    public Tree(ArrayList<Integer>[] adj){
        this.adj = adj;
        for(int i=0; ; ++i) 
            if(adj.length<=(1<<i)){
                log = i+1;
                break;
            }
        up = new int[adj.length][log];
        depth = new int[adj.length];
        dfs(0,-1);
        for(int j=1; j<log; ++j)
            for(int i=0; i<adj.length; ++i)
                up[i][j] = up[i][j-1] == -1 ? -1 : up[up[i][j-1]][j-1];
    }
    private void dfs(int u, int p){
        up[u][0] = p; 
        for(int v: adj[u])
            if(v!=p){
                depth[v] = depth[u]+1;
                dfs(v,u);
            }     
    }
    public int kthAncestor(int u, int k){
        for(int i=0; i<log; ++i)
            if((k&(1<<i))>0)
                u = up[u][i];
        return u;
    }
    public int lca(int a, int b){
        if(depth[a]<depth[b]){
            a=a^b; b=a^b; a=a^b;
        }
        a = kthAncestor(a, depth[a]-depth[b]);
        if(a==b) return a;
        for(int i=log-1; i>=0; --i)
            if(up[a][i]!=up[b][i]){
                a = up[a][i];
                b = up[b][i];
            }
        return up[a][0];
    }
}
