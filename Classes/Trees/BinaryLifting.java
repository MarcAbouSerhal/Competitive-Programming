class Tree{
    // [][] propUp
    // add any necessary members needed for prop
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
        // // propUp = new ...
        // for(int i=1; i<a.length; ++i){
        //     // propUp[i][0] = prop(up[i][0])
        // }
        // for(int j=1; j<log; ++j)
        //     for(int i=0; i<adj.length; ++i)
        //         if(up[i][j]!=-1)
        //             // propUp[i][j] = merge(propUp[i][j-1],propUp[up[i][j-1]][j-1])
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
    // public ... propOfPath(int u, int v){
    //     if(depth[u]<depth[v]){
    //         u=u^v; v=u^v; u=u^v;
    //     }
    //     int k = depth[u]-depth[v];
    //     // res = prop(a[u],a[v])
    //     for(int i=0; i<log; ++i)
    //         if((k&(1<<i))>0){
    //             // include propUp[u][i] with res
    //             u = up[u][i];
    //         }
    //     if(u==v) return res;
    //     for(int i=log-1; i>=0; --i)
    //         if(up[u][i]!=up[v][i]){
    //             // include propUo[u][i] and propUp[v][i] with res
    //             u = up[u][i];
    //             v = up[v][i];
    //         }
    //     // include up[u][0] with res
    //     return res;
    // }
}
