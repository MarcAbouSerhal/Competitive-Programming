import java.util.*;
import java.io.*;
public class G {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int n = in.nextInt(), m = in.nextInt();
        adj = new ArrayList[n];
        for(int i=0; i<n; ++i) adj[i] = new ArrayList<>();
        xorTo = new long[n];
        for(int i=0; i<m; ++i){
            int u = in.nextInt()-1, v = in.nextInt()-1, w = in.nextInt();
            adj[u].add(new Pair(v, w));
            adj[v].add(new Pair(u, w));
        }
        visited = new boolean[n];
        dfs(0,-1);
        out.println(cycleBasis.minSubsequenceWith(xorTo[n-1]));
        out.flush();
    }
    static XORBasis cycleBasis = new XORBasis(30);
    static ArrayList<Pair>[] adj;
    static long[] xorTo;
    static boolean[] visited;
    public static void dfs(int u, int p){
        visited[u] = true;
        for(Pair edge: adj[u]){
            int v = edge.x;
            long w = edge.y;
            if(v!=p)
                if(visited[v]) cycleBasis.insert(xorTo[u]^xorTo[v]^w);
                else{
                    xorTo[v] = xorTo[u]^w;
                    dfs(v,u);
                }
        }
    }
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
class XORBasis{
    // means x < (1<<log)
    int log;
    int size = 0;
    long[] basis;
    public XORBasis(int log){
        this.log = log;
        basis = new long[log];
    }
    public void insert(long x){
        for(int i=log-1; i>=0; --i){
            if((x&(1<<i))==0) continue;
            if(basis[i]==0){
                basis[i] = x;
                ++size;
                return;
            }
            x ^= basis[i];
        }
    }
    public boolean contains(long x){
        for(int i=log-1; i>=0; --i){
            if((x&(1<<i))==0) continue;
            if(basis[i]==0){
                return false;
            }
            x ^= basis[i];
        }
        return true;
    }
    public long minSubsequenceWith(long x){
        for(int i=log-1; i>=0; --i){
            if((x&(1<<i))==0) continue;
            x ^= basis[i];
        }
        return x;
    }
    public long maxSubsequenceWith(long x){
        for(int i=log-1; i>=0; --i){
            if((x&(1<<i))>0) continue;
            x ^= basis[i];
        }
        return x;
    }
    public long maxSubsequence(){ return maxSubsequenceWith(0); }
    public XORBasis copy(){
        XORBasis copy = new XORBasis(log);
        copy.size = size;
        for(int i=0; i<log; ++i) copy.basis[i] = basis[i];
        return copy;
    }
    public static XORBasis merge(XORBasis b1, XORBasis b2){
        XORBasis res = b1.copy();
        for(long i: b2.basis)
            if(i!=0) 
                res.insert(i);
        return res;
    }
}
class Pair{
    int x;
    long y;
    public Pair(int x,long y){this.x=x;this.y=y;}
}
