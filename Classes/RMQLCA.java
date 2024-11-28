class Tree{
    int tick = -1;
    int[][][] d;
    int[] in;
    int[] floorPow;
    ArrayList<Integer>[] adj;
    private static int[] op(int[] l, int[] r){ return l[1]<r[1] ? l : r; }
    public Tree(ArrayList<Integer>[] adj){
        this.adj = adj;
        in = new int[adj.length];
        floorPow= new int[2*adj.length];
        floorPow[0]=-1;
        for(int i=1; i<floorPow.length; ++i){
            floorPow[i]=floorPow[i-1];
            if((i&(i-1))==0) floorPow[i]++;
        }
        int log = floorPow[2*adj.length-1]+1;
        d = new int[2*adj.length-1][log][];
        dfs(0,-1,0);
        for(int j=1; j<log; ++j)
            for(int i=0; i+(1<<j)<=2*adj.length-1; ++i)
                d[i][j] = op(d[i][j-1], d[i+(1<<(j-1))][j-1]);
    }
    public void dfs(int u, int p, int h){
        in[u] = ++tick;
        d[tick][0] = new int[] {u,h};
        for(int v: adj[u])
            if(v!=p){
                dfs(v,u,h+1);
                d[++tick][0] = d[in[u]][0];
            }
    }
    public int lca(int a, int b){
        a = in[a]; 
        b = in[b];
        if(a>b){ a=a^b; b=a^b; a=a^b; }
        int x = floorPow[b-a+1];
        return op(d[a][x],d[b+1-(1<<x)][x])[0];
    }
}
