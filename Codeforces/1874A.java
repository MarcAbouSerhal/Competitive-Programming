import java.util.*;
import java.io.*;
public class A {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int t = in.nextInt();
        for(int tc=0; tc<t; ++tc){
            int n = in.nextInt(), m = in.nextInt(), k = in.nextInt();
            int[] a = new int[n], b = new int[m];
            for(int i=0; i<n; ++i) a[i] = in.nextInt();
            for(int i=0; i<m; ++i) b[i] = in.nextInt();
            // after 2 moves they start shuffling smallest and biggest element
            // so we just care abt 1st or 2nd move
            for(int i=1; i<=2-(k%2); ++i){
                if(i%2==1){
                    int aMin = 0;
                    for(int j=1; j<n; ++j)
                        if(a[j] < a[aMin])
                            aMin = j;
                    int bMax = 0;
                    for(int j=1; j<m; ++j)
                        if(b[j] > b[bMax])
                            bMax = j;
                    if(a[aMin] < b[bMax]){
                        int temp = b[bMax];
                        b[bMax] = a[aMin];
                        a[aMin] = temp;
                    }
                }
                else{
                    int aMax = 0;
                    for(int j=1; j<n; ++j)
                        if(a[j] > a[aMax])
                            aMax = j;
                    int bMin = 0;
                    for(int j=1; j<m; ++j)
                        if(b[j] < b[bMin])
                            bMin = j;
                    if(a[aMax] > b[bMin]){
                        int temp = b[bMin];
                        b[bMin] = a[aMax];
                        a[aMax] = temp;
                    }
                }
            }
            long sum = 0;
            for(int x: a) sum += x;
            out.println(sum);
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
