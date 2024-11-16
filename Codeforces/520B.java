import java.util.*;
import java.io.*;
public class B {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int x = in.nextInt(), y = in.nextInt();
        // we will think about it in reverse
        // operations become dividing by 2 or adding by 1
        out.println(minClicks(x,y));
        out.flush();
    }
    public static int min(int a, int b){ return a<b ? a : b; }
    public static int minClicks(int m, int n){
        if(m>=n){
            return m-n;
        }
        else if(n==2){
            return m;
        }
        else if(n==3){
            return 4-m;
        }
        else if(n%2==0){
            return 1+min(minClicks(m,n/2), 1+minClicks(m, n/2+1));
        }
        return 1+minClicks(m,n+1);
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
