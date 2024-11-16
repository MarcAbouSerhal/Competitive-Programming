import java.util.*;
import java.io.*;
public class L {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int n = in.nextInt();
        char[] s = in.next().toCharArray();
        int o = 0, l = 0, prefO = 0, prefL = 0;
        for(char c: s)
            if(c=='L') l++;
            else o++;
        for(int i=0; i<n-1; ++i){
            if(s[i]=='L') prefL++;
            else prefO++;
            if(prefL!=l-prefL && prefO!=o-prefO){
                out.println(i+1);
                out.flush();
                return;
            }
        }
        out.println("-1");
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
