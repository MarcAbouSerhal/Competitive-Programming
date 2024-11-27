import java.util.*;
import java.io.*;
public class CSES2417 {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    static int c = 1000000;
    public static void main(String[] args) {
        int n = in.nextInt();
        int[] cnt = new int[c+1];
        for(int i=0; i<n; ++i) cnt[in.nextInt()]++;
        long[] mul = new long[c+1]; // mul[i] = # multiples of i
        for(int i=1; i<=c; ++i)
            for(int j=i; j<=c; j+=i)
                mul[i] += cnt[j];
        long[] dp = new long[c+1]; // dp[i] = pairs (i,j) s.t. i<j and gcd(a[i],a[j])=x
        for(int i=c; i>0; --i){
            dp[i] = (mul[i]*(mul[i]-1))>>1;
            for(int j=2*i; j<=c; j+=i)
                dp[i] -= dp[j];
        }
        // pairs p,q such that gcd(p,q) = x are pairs such that x|p and x|q but gcd(p,q)!=i.x for i=2,3...
        out.println(dp[1]);
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
