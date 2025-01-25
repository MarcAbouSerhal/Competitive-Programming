class FastIO{ 
    private final BufferedOutputStream bos;
    private final BufferedReader br; 
    private StringTokenizer st; 
    public FastIO() { 
        bos = new BufferedOutputStream(System.out);
        br = new BufferedReader(new InputStreamReader(System.in)); 
    } 
    public final String next() throws Exception { 
        while (st == null || !st.hasMoreElements())
            st = new StringTokenizer(br.readLine());
        return st.nextToken(); 
    } 
    public final int nextInt() throws Exception { 
        final String s = next();
        final int off = s.charAt(0) == '-' ? 0 : -1;
        int answer = 0, pow = - (off << 1) - 1;
        for(int i = s.length() - 1; i > off; --i){
            answer += (s.charAt(i) - '0') * pow;
            pow *= 10;
        }
        return answer;
    } 
    public final long nextLong() throws Exception { 
        final String s = next();
        final int off = s.charAt(0) == '-' ? 0 : -1;
        long answer = 0, pow = - (off << 1) - 1;
        for(int i = s.length() - 1; i > off; --i){
            answer += (s.charAt(i) - '0') * pow;
            pow *= 10;
        }
        return answer;
    } 
    public final double nextDouble() throws Exception { return Double.parseDouble(next()); } 
    public final String nextLine() throws Exception { 
        if(st.hasMoreTokens()) return st.nextToken("\n"); 
        else return br.readLine(); 
    } 
    private static final byte[] bytes(char[] s){
        final int n = s.length;
        final byte[] res = new byte[n];
        for(int i = 0; i < n; ++i) res[i] = (byte)s[i];
        return res;
    }
    private static final byte[] bytes(String s){
        final int n = s.length();
        final byte[] res = new byte[n];
        for(int i = 0; i < n; ++i) res[i] = (byte)s.charAt(i);
        return res;
    }
    private static final byte[] bytes(int x){ 
        if(x == 0) return ZERO;
        int size = 0;
        if(x < 0){
            final byte[] temp = new byte[10];
            while(x != 0){
                temp[size++] = (byte)('0' - (x % 10));
                x /= 10;
            }
            final byte[] res = new byte[size + 1];
            res[0] = MINUS;
            for(int i = 1; i <= size; ++i) res[i] = temp[size - i];
            return res;
        }
        else{
            final byte[] temp = new byte[9];
            while(x != 0){
                temp[size++] = (byte)('0' + (x % 10));
                x /= 10;
            }
            final byte[] res = new byte[size];
            for(int i = 0; i < size; ++i) res[i] = temp[size - i - 1];
            return res;
        }
    }
    private static final byte[] bytes(long x){ 
        if(x == 0) return ZERO;
        int size = 0;
        if(x < 0){
            final byte[] temp = new byte[19];
            while(x != 0){
                temp[size++] = (byte)('0' - (x % 10));
                x /= 10;
            }
            final byte[] res = new byte[size + 1];
            res[0] = MINUS;
            for(int i = 1; i <= size; ++i) res[i] = temp[size - i];
            return res;
        }
        else{
            final byte[] temp = new byte[18];
            while(x != 0){
                temp[size++] = (byte)('0' + (x % 10));
                x /= 10;
            }
            final byte[] res = new byte[size];
            for(int i = 0; i < size; ++i) res[i] = temp[size - i - 1];
            return res;
        }
    }
    private static final byte[] TRUE = {'t', 'r', 'u', 'e'}, FALSE = {'f', 'a', 'l', 's', 'e'}, NULL = {'n', 'u', 'l', 'l'}, ENDL = "\n".getBytes(), SPACE = {' '}, ZERO = {'0'};
    private static final byte MINUS = '-';
    public final void print(char c) throws Exception { bos.write(c); }
    public final void println(char c) throws Exception { bos.write(c); bos.write(ENDL); }
    public final void print(String s) throws Exception { bos.write(bytes(s)); }
    public final void println(String s) throws Exception { bos.write(bytes(s)); bos.write(ENDL); }
    public final void print(int x) throws Exception { bos.write(bytes(x)); }
    public final void println(int x) throws Exception { bos.write(bytes(x)); bos.write(ENDL); }
    public final void print(long x) throws Exception { bos.write(bytes(x)); }
    public final void println(long x) throws Exception { bos.write(bytes(x)); bos.write(ENDL); }
    public final void print(double x) throws Exception { bos.write(bytes(Double.toString(x))); }
    public final void println(double x) throws Exception { bos.write(bytes(Double.toString(x))); bos.write(ENDL); }
    public final void print(boolean b) throws Exception { bos.write(b ? TRUE : FALSE); }
    public final void println(boolean b) throws Exception { bos.write(b ? TRUE : FALSE); bos.write(ENDL); }
    public final void print(Object obj) throws Exception { if(obj == null) print(NULL); else print(obj.toString()); }
    public final void println(Object obj) throws Exception { if(obj == null) println(NULL); else println(obj.toString()); }
    public final void print(int[] array) throws Exception { for(int i: array){ bos.write(bytes(i)); bos.write(SPACE); } }
    public final void println(int[] array) throws Exception { for(int i: array){ bos.write(bytes(i)); bos.write(SPACE); } bos.write(ENDL); }
    public final void print(long[] array) throws Exception { for(long i: array){ bos.write(bytes(i)); bos.write(SPACE); } }
    public final void println(long[] array) throws Exception { for(long i: array){ bos.write(bytes(i)); bos.write(SPACE); } bos.write(ENDL); }
    public final void print(char[] s) throws Exception { bos.write(bytes(s)); }
    public final void println(char[] s) throws Exception { bos.write(bytes(s)); bos.write(ENDL); }
    public final void space() throws Exception { bos.write(SPACE); }
    public final void endl() throws Exception { bos.write(ENDL); }
    public final void flush() throws Exception { bos.flush(); }
}
