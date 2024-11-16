import java.util.*;
import java.io.*;
public class A {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int t = in.nextInt();
        for(int tc=0; tc<t; ++tc){
            int n = in.nextInt();
            int[] a = new int[n];
            for(int i=0; i<n; ++i) a[i] = in.nextInt();
            int answer = 0;
            int prefMax = a[0];
            // we want a[i]>=max(a[0...i-1])
            // so if a[i]<max(a[0...i-1])
            // we only make necessary operations so a[i] = max(a[0...i-1])
            for(int i=1; i<n; ++i){
                if(prefMax>a[i]) answer = max(answer,lg(prefMax-a[i])+1);
                prefMax = max(prefMax,a[i]);
            }
            out.println(answer);
        }
        out.flush();
    }
    public static int max(int a, int b){ return a>b ? a : b; }
    public static int lg(int a){
        for(int b=30; b>=0; --b)
            if((a&(1<<b))>0) return b;
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
