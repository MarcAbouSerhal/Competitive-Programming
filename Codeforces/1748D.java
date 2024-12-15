import java.util.*;
import java.io.*;
public class D {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int t = in.nextInt();
        for(int tc = 0; tc < t; ++tc){
            long a = in.nextLong()|in.nextLong(), d = in.nextLong();
            if(LSB(a)<LSB(d)) out.println("-1");
            else{
                long x = 0;
                int lsb = LSB(d);
                for(int i = 0; i < 30; ++i)
                    if((x & (1<<i)) == 0 && (a & (1<<i)) > 0)
                        x += d<<(i-lsb);
                out.println(x);
            }
        }
        out.flush();
    }
    public static int LSB(long x){
        for(int i = 0; i < 60; ++i)
            if((x&(1<<i)) > 0) return i;
        return -1;
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
