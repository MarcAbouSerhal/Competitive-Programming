import java.io.*;
import java.util.*;
public class Main {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int n = in.nextInt(), m = in.nextInt();
        int[] w = new int[n];
        for(int i=0; i<n; ++i) w[i] = in.nextInt();
        adj = new ArrayList[n];
        for(int i=0; i<n; ++i) adj[i] = new ArrayList<>();
        for(int i=0; i<n-1; ++i){
            int u = in.nextInt()-1, v = in.nextInt()-1;
            adj[u].add(v);
            adj[v].add(u);
        }
        p = new int[n];
        dp = new int[n][20];
        l = new int[n];
        r = new int[n];
        id = new int[2*n];
        dist = new int[n];
        dfs(0,-1);
        for(int i=0; i<n; ++i) dp[i][0] = p[i];
        for(int j=1; j<20; ++j)
            for(int i=0; i<n; ++i)
                dp[i][j] = dp[i][j-1]==-1 ? -1 : dp[dp[i][j-1]][j-1];
        int[] a = new int[2*n];
        for(int i=0; i<2*n; ++i)
            a[i] = w[id[i]];
        Query[] query = new Query[m];
        for(int i=0; i<m; ++i){
            int u = in.nextInt()-1, v = in.nextInt()-1;
            if(l[u]>l[v]){
                int temp = u;
                u = v;
                v = temp;
            }
            int lca = LCA(u,v);
            if(v==lca){
                query[i] = new Query(i, l[u], l[v]);
            }
            else{
                query[i] = new Query(i, r[u], l[v], l[lca]);
            }
        }
        if(m==0) return;
        // now we do Mo's algorithm
        HashMap<Integer,Integer> multiset = new HashMap<>();
        int[] ans = new int[m];
        int b = (int)Math.ceil(Math.sqrt(n));
        Arrays.sort(query, (x,y) -> x.l/b!=y.l/b ? x.l/b - y.l/b : x.r-y.r);
        int l_curr = 0, r_curr = -1;
        for(Query q: query){
            while(r_curr<q.r) add(multiset, a[++r_curr]);
            while(r_curr>q.r) remove(multiset, a[r_curr--]);
            while(l_curr<q.l) remove(multiset, a[l_curr++]);
            while(l_curr>q.l) add(multiset, a[--l_curr]);
            if(q.extra==-1){
                ans[q.index] = multiset.size();
            }
            else{
                add(multiset, a[q.extra]);
                ans[q.index] = multiset.size();
                remove(multiset, a[q.extra]);
            }
        }
        for(int i: ans) out.println(i);
        out.flush();
    }
    static ArrayList<Integer>[] adj;
    static int[] dist;
    static int[] p;
    static int[][] dp;
    static int curr = 0;
    static int[] l, r;
    static int[] id;
    public static void dfs(int u, int par){
        if(u!=0) dist[u] = dist[par]+1;
        p[u] = par;
        id[curr] = u;
        l[u] = curr++;
        for(int v: adj[u]){
            if(v!=par){
                dfs(v,u);
            }
        }
        id[curr] = u;
        r[u] = curr++;
    }
    public static int kthAncestor(int v, int k){
        for(int i=19; i>=0; --i)
            if(k>=(1<<i)){
                v = dp[v][i];
                k-=1<<i;
            }
        return v;
    }
    public static int LCA(int u, int v){
        if(dist[u]<dist[v]){
            int temp = u;
            u = v;
            v = temp;
        }
        u = kthAncestor(u,dist[u]-dist[v]);
        if(u==v) return u;
        for(int i=19; i>=0; --i)
            if(dp[u][i]!=dp[v][i]){
                u = dp[u][i];
                v = dp[v][i];
            }
        return dp[v][0];
    }
    public static void add(HashMap<Integer,Integer> multiset, int k){
        if(multiset.containsKey(k)) multiset.put(k,multiset.get(k)+1);
        else multiset.put(k,1);
    }
    public static void remove(HashMap<Integer,Integer> multiset, int k){
        if(multiset.get(k)==1) multiset.remove(k);
        else multiset.put(k,multiset.get(k)-1);
    }
}
class FastReader { 
    BufferedReader br; 
    StringTokenizer st; 
    public FastReader(){ 
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
class Query{
    int l,r;
    int extra=-1;
    int index;
    public Query(int index, int l, int r){
        this.index = index;
        this.l=l;
        this.r=r;
    }
    public Query(int index, int l, int r,int x){
        this(index,l,r);
        extra=x;
    }
}
