import java.util.*;
import java.io.*;
public class F {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    static long mod = 1000000007;
    public static void main(String[] args) {
        int n = in.nextInt(), q = in.nextInt();
        int[] a = new int[n];
        for(int i=0; i<n; ++i) a[i] = in.nextInt();
        long[] pow = new long[n+1];
        pow[0] = 1;
        for(int i=1; i<=n; ++i) pow[i] = (pow[i-1]<<1)%mod;
        XORBasis[] prefBasis = new XORBasis[n];
        prefBasis[0] = new XORBasis(20);
        prefBasis[0].insert(a[0]);
        for(int i=1; i<n; ++i){
            prefBasis[i] = prefBasis[i-1].copy();
            prefBasis[i].insert(a[i]);
        }
        for(int query = 0; query < q; ++query){
            int l = in.nextInt(), x = in.nextInt();
            if(prefBasis[l-1].contains(x)) out.println(pow[l-prefBasis[l-1].size]);
            else out.println("0");
        }
        out.flush();
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
    public XORBasis copy(){
        XORBasis copy = new XORBasis(log);
        copy.size = size;
        for(int i=0; i<log; ++i) copy.basis[i] = basis[i];
        return copy;
    }
}
