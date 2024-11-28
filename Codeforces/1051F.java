import java.util.*;
import java.io.*;
public class F {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int n = in.nextInt(), m = in.nextInt();
        adj = new ArrayList[n];
        adj2 = new ArrayList[n];
        // adj2 is for DFS tree rooted at 0
        for(int i=0; i<n; ++i){
            adj[i] = new ArrayList<>();
            adj2[i] = new ArrayList<>();
        }
        for(int i=0; i<m; ++i){
            int u = in.nextInt()-1, v = in.nextInt()-1;
            long d = in.nextInt();
            adj[u].add(new Pair(v, d));
            adj[v].add(new Pair(u, d));
        }
        extra = new TreeSet<>();
        visited = new boolean[n];
        dfs(0,-1,0);
        Tree tree = new Tree(adj2);
        long[][] d = new long[extra.size()][];
        int tick = -1;
        for(int x: extra) d[++tick] = dijkstra(x, adj);
        long[] normalD = treeDijkstra(adj);
        int q  = in.nextInt();
        for(int query=0; query<q; ++query){
            int u = in.nextInt()-1, v = in.nextInt()-1;
            long ans = normalD[u] + normalD[v] - 2*normalD[tree.lca(u,v)];
            for(long[] otherD: d) ans = min(ans, otherD[u]+otherD[v]);
            out.println(ans);
        }
        out.flush();
    }
    static ArrayList<Pair>[] adj;
    static ArrayList<Integer>[] adj2;
    static boolean[] visited;
    static TreeSet<Integer> extra;
    public static void dfs(int u, int p, int h){
        visited[u] = true;
        for(Pair edge: adj[u]){
            int v = edge.x;
            if(v==p) continue;
            if(visited[v]){
                extra.add(v);
            }
            else{
                adj2[u].add(v);
                adj2[v].add(u);
                dfs(v,u,h+1);
            }
        }
    }
    public static long[] dijkstra(int s, ArrayList<Pair>[] adj){
        long[] ans = new long[adj.length];
        Arrays.fill(ans,Long.MAX_VALUE);
        PriorityQueue<Pair> pq = new PriorityQueue<>((x,y)->Long.compare(x.y,y.y));
        pq.add(new Pair(s, 0l));
        while(!pq.isEmpty()){
            Pair curr = pq.poll();
            if(ans[curr.x]<=curr.y) continue;
            ans[curr.x] = curr.y;
            for(Pair edge: adj[curr.x])
                if(ans[edge.x]>=edge.y+curr.y)
                    pq.add(new Pair(edge.x, edge.y+curr.y));
            
        }
        return ans;
    }
    public static long[] treeDijkstra(ArrayList<Pair>[] adj){
        // takes 0 as root and only looks at edges of DFS tree
        long[] d = new long[adj.length];
        visited = new boolean[adj.length];
        dfs2(0,-1,d);
        return d;
    }
    public static void dfs2(int u, int p, long[] d){
        visited[u] = true;
        for(Pair edge: adj[u]){
            int v = edge.x;
            if(v==p) continue;
            if(!visited[v]){
                d[v] = d[u]+edge.y;
                dfs2(v,u,d);
            }
        }
    }
    public static long min(long x, long y){ return x<y ? x : y; }
}
class FastReader { 
    BufferedReader br; 
    StringTokenizer st; 
    public FastReader() { 
        br = new BufferedReader(new InputStreamReader(System.in)); 
    } 
    String next() { 
        while (st == null || !st.hasMoreElements()) { 
            try { 
                st = new StringTokenizer(br.readLine()); 
            } 
            catch (IOException e) { 
                e.printStackTrace(); 
            } 
        } 
        return st.nextToken(); 
    } 
    int nextInt() { return Integer.parseInt(next()); } 
    long nextLong() { return Long.parseLong(next()); } 
    double nextDouble() { return Double.parseDouble(next()); } 
    String nextLine() { 
        String str = ""; 
        try { 
            if(st.hasMoreTokens()){ 
                str = st.nextToken("\n"); 
            } 
            else{ 
                str = br.readLine(); 
            } 
        } 
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return str; 
    } 
} 
class Pair{
    int x;
    long y;
    public Pair(int x,long y){this.x=x;this.y=y;}
}
 
class Tree{
    int tick = -1;
    int[][] d;
    int[] depth;
    int[] in;
    int[] floorPow;
    ArrayList<Integer>[] adj;
    private int op(int l, int r){ return depth[l]<depth[r] ? l : r; }
    public Tree(ArrayList<Integer>[] adj){
        this.adj = adj;
        depth = new int[adj.length];
        in = new int[adj.length];
        floorPow= new int[2*adj.length];
        floorPow[0]=-1;
        for(int i=1; i<floorPow.length; ++i){
            floorPow[i]=floorPow[i-1];
            if((i&(i-1))==0) floorPow[i]++;
        }
        int log = floorPow[2*adj.length-1]+1;
        d = new int[2*adj.length-1][log];
        dfs(0,-1);
        for(int j=1; j<log; ++j)
            for(int i=0; i+(1<<j)<=2*adj.length-1; ++i)
                d[i][j] = op(d[i][j-1], d[i+(1<<(j-1))][j-1]);
    }
    public void dfs(int u, int p){
        in[u] = ++tick;
        d[tick][0] = u;
        for(int v: adj[u])
            if(v!=p){
                depth[v] = depth[u] + 1;
                dfs(v,u);
                d[++tick][0] = d[in[u]][0];
            }
    }
    public int lca(int a, int b){
        a = in[a]; 
        b = in[b];
        if(a>b){ a=a^b; b=a^b; a=a^b; }
        int x = floorPow[b-a+1];
        return op(d[a][x],d[b+1-(1<<x)][x]);
    }
}
