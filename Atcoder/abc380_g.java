import java.util.*;
import java.io.*;
public class G {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    static long mod = 998244353;
    public static void main(String[] args) {
        int n = in.nextInt(), k = in.nextInt();
        int[] p = new int[n];
        long inv = 0;
        for(int i=0; i<n; ++i) p[i] = in.nextInt()-1;
        fact = new long[n+1]; fact[0] = 1;
        for(int i=1; i<=n; ++i) fact[i] = (fact[i-1]*i)%mod;
        FenwickTree occ = new FenwickTree(n);
        for(int i=0; i<n; ++i){
            inv = (inv + occ.get(p[i]+1,n-1))%mod;
            occ.add(p[i],1);
        }
        occ = new FenwickTree(n);
        long[] inv2 = new long[n-k+1];
        for(int i=0; i<k; ++i){
            inv2[0] = (inv2[0]+occ.get(p[i]+1,n-1));
            occ.add(p[i],1);
        }
        for(int i=k; i<n; ++i){
            occ.add(p[i-k],-1);
            long diff = occ.get(p[i]+1,n-1)-occ.get(0,p[i-k]-1);
            occ.add(p[i],1);
            inv2[i-k+1] = (inv2[i-k]+diff)%mod;
        }
        long ans = 0;
        for(int i=0; i<n-k+1; ++i) ans = (ans+inv-inv2[i]+inv(2)*c(k,2)+mod)%mod;
        ans = (ans*inv(n-k+1))%mod;
        out.println(ans);
        out.flush();
    }
    static long[] fact;
    public static long pow(long x, long n){
        if(n == 0) return 1;
        long res = pow(x,n>>1);
        res = (res*res)%mod;
        if(n%2==1) res = (res*x)%mod;
        return res;
    }
    public static long inv(long x){ return pow(x,mod-2); }
    public static long c(int n, int k){ return k>n ? 0 : (fact[n]*inv(fact[n-k])%mod*inv(fact[k]))%mod; }
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
class FenwickTree {
    // replace every +/- with ^ to make it a XOR fenwick tree
    private int[] tree;
    public FenwickTree(int size) {
        tree = new int[size];
    }
    public FenwickTree(int[] a) {
        this.tree = a.clone();
        for (int i = 0; i < tree.length; i++) {
            int j = i | (i + 1);
            if (j < tree.length) {
                tree[j] += tree[i];
            }
        }
    }
    public int get(int l, int r) {
        if (l > r) { return 0; }
        return get(r) - get(l - 1);
    }
    private int get(int to) {
        to = Math.min(to, tree.length - 1);
        int result = 0;
        while (to >= 0) {
            result += tree[to];
            to = (to & (to + 1)) - 1;
        }
        return result;
    }
    public void add(int i, int x) {
        while (i < tree.length) {
            this.tree[i] += x;
            i = i | (i + 1);
        }
    }
}
