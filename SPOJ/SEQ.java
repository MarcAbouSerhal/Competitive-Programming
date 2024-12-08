import java.util.*;
import java.io.*;
public class SEQ {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int t = in.nextInt();
        for(int tc=0; tc<t; ++tc){
            int k = in.nextInt();
            long[] f = new long[k];
            for(int i=0; i<k; ++i) f[i] = in.nextLong();
            long[] c = new long[k];
            for(int i=0; i<k; ++i) c[i] = in.nextLong();
            long n = in.nextLong()-1;
            if(n<k) out.println(f[(int)n]);
            else out.println(Recurrence.solve(n,k,c,f,1000000000));
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
class Recurrence{
    public static long solve(long n, int k, long[] c, long[] f, long mod){
        long[] g = new long[k+1];
        g[k] = 1;
        for(int i=1; i<=k; ++i) g[k-i] = (-c[i-1]+mod)%mod;
        long[] b = xPowNModG(n, g, k, mod);
        long ans = 0;
        for(int i=0; i<k; ++i)
            ans = (ans+b[i]*f[i])%mod;
        return ans;
    }
    public static long[] xPowNModG(long n, long[] g, int k, long mod){
        if(n<k){
            long[] res = new long[k];
            res[(int)n] = 1;
            return res;
        }
        long[] half = xPowNModG(n>>1, g, k, mod);
        long[] last = new long[k<<1];
        for(int i=0; i<k; ++i)
            for(int j=0; j<k; ++j)
                last[i+j] = (last[i+j] + half[i]*half[j])%mod;
        if(n%2==1){
            for(int i=(k<<1)-1; i>0; --i) last[i] = last[i-1];
            last[0] = 0;
        }
        for(int i=(k<<1)-1; i>=k; --i)  
            if(last[i]!=0){
                long m = last[i];
                for(int j=0; j<=k; ++j)
                    last[i-j] = (last[i-j] - m*g[k-j])%mod;
            }
        for(int i=0; i<k; ++i) half[i] = (last[i]+mod)%mod;
        return half;
    }
}
