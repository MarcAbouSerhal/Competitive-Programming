import java.util.*;
import java.io.*;
public class C {
    static FastReader in = new FastReader();
    static PrintWriter out = new PrintWriter(System.out);
    public static void main(String[] args) {
        int t = in.nextInt();
        for(int tc = 0; tc < t; ++tc){
            int n = in.nextInt();
            long k = in.nextLong();
            char[] s = in.next().toCharArray();
            int[] suf = new int[n];
            suf[n-1] = s[n-1] == '1' ? 1 : -1;
            for(int i=n-2; i>=1; --i)
                suf[i] = (s[i] == '1' ? 1 : -1) + suf[i+1];
            PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
            long maxK = 0;
            for(int i: suf)
                if(i > 0){
                    pq.add(i);
                    maxK += i;
                }
            if(maxK < k) out.println("-1");
            else{
                long score = 0;
                int m = 1;
                while(score < k){
                    ++m;
                    score += pq.poll();
                }
                out.println(m);
            }
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
