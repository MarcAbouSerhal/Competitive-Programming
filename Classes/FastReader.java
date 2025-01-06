class FastReader { 
    private final BufferedReader br; 
    private StringTokenizer st; 
    public FastReader() { 
        br = new BufferedReader(new InputStreamReader(System.in)); 
    } 
    public final String next() { 
        while (st == null || !st.hasMoreElements()) { 
            try { st = new StringTokenizer(br.readLine()); } 
            catch (IOException e){ } 
        } 
        return st.nextToken(); 
    } 
    public final int nextInt() { 
        String s = next();
        int off = s.charAt(0) == '-' ? 0 : -1;
        int answer = 0, pow = - (off << 1) - 1;
        for(int i = s.length() - 1; i > off; --i){
            answer += (s.charAt(i) - '0') * pow;
            pow *= 10;
        }
        return answer;
    } 
    public final long nextLong() { 
        String s = next();
        int off = s.charAt(0) == '-' ? 0 : -1;
        long answer = 0, pow = - (off << 1) - 1;
        for(int i = s.length() - 1; i > off; --i){
            answer += (s.charAt(i) - '0') * pow;
            pow *= 10;
        }
        return answer;
    } 
    public final double nextDouble() { return Double.parseDouble(next()); } 
    public final String nextLine() { 
        String str = ""; 
        try { 
            if(st.hasMoreTokens()) str = st.nextToken("\n"); 
            else str = br.readLine(); 
        } 
        catch (IOException e) { } 
        return str; 
    } 
} 
