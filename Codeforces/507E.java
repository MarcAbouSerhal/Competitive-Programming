import java.util.*;
import java.io.*;
public class E {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int n = in.nextInt(), m =in.nextInt();
        ArrayList<Pair>[] adj = new ArrayList[n];
        for(int i=0; i<n; ++i) adj[i] = new ArrayList<>();
        int goodRoads = 0;
        for(int i=0; i<m; ++i){
            int x = in.nextInt()-1, y = in.nextInt()-1, z = in.nextInt();
            goodRoads+=z;
            adj[x].add(new Pair(y, z));
            adj[y].add(new Pair(x, z));
        }
        int[] d1 = new int[n], d2 = new int[n];
        Arrays.fill(d1,-1);
        Arrays.fill(d2,-1);
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        d1[0] = 0;
        while(!q.isEmpty()){
            int u = q.poll();
            for(Pair edge: adj[u]){
                int v = edge.x;
                if(d1[v]==-1){
                    d1[v] = d1[u] + 1;
                    q.add(v);
                }
            }
        }
        q.add(n-1);
        d2[n-1] = 0;
        while(!q.isEmpty()){
            int u = q.poll();
            for(Pair edge: adj[u]){
                int v = edge.x;
                if(d2[v]==-1){
                    d2[v] = d2[u] + 1;
                    q.add(v);
                }
            }
        }
        int d = d1[n-1];
        ArrayList<Integer>[] good = new ArrayList[d+1];
        for(int i=0; i<=d; ++i) good[i] = new ArrayList<>();
        for(int i=0; i<n; ++i)
            if(d1[i]+d2[i]==d)
                good[d1[i]].add(i);
        int[] dp = new int[n];
        Arrays.fill(dp,-1);
        dp[0] = 0;
        int[] prev = new int[n];
        prev[0] = -1;
        for(int i=1; i<=d; ++i)
            for(int u: good[i])
                for(Pair edge: adj[u]){
                    int v = edge.x;
                    if(d1[u]==d1[v]+1 && d2[u]==d2[v]-1)
                        if(dp[u]<dp[v]+edge.y){
                            prev[u] = v;
                            dp[u] = dp[v]+edge.y;
                        }
                }
        HashSet<Pair> used = new HashSet<>();
        int current = n-1;
        while(prev[current]!=-1){
            used.add(new Pair(min(current,prev[current]), max(current,prev[current])));
            current = prev[current];
        }
        out.println(goodRoads-dp[n-1]+d-dp[n-1]);
        for(int u=0; u<n; ++u)
            for(Pair v: adj[u]){
                if(u>v.x) continue;
                Pair edge = new Pair(u, v.x);
                if(!used.contains(edge) && v.y==1){
                    out.println((u+1)+" "+(v.x+1)+" 0");
                }
                else if(used.contains(edge) && v.y==0){
                    out.println((u+1)+" "+(v.x+1)+" 1");
                }
            }
        out.flush();
    }
    public static int min(int a, int b){ return a<b ? a : b; }
    public static int max(int a, int b){ return a>b ? a : b; }
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
    int x,y;
    public Pair(int x,int y){this.x=x;this.y=y;}
    public String toString(){return x+" "+y;};
    public int hashCode(){return x+47*y;}
    public boolean equals(Object obj){return (obj instanceof Pair) && ((Pair)obj).x==x && ((Pair)obj).y==y;}
}
