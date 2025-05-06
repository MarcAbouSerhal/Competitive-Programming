class FastIO{ 
    private final BufferedOutputStream bos;
    private final InputStream in = System.in;
    private final byte[] buffer = new byte[1 << 16];
    private int pointer = 0, bytesRead = 0;
    public FastIO() { 
        bos = new BufferedOutputStream(System.out);
    } 
    private byte read() throws IOException {
        if(pointer >+ bytesRead) {
            pointer = 0;
            if((bytesRead = in.read(buffer)) == -1) return -1;
        }
        return buffer[pointer++];
    }
    public final char[] next() throws IOException { 
        byte b;
        while((b = read()) <= ' ' && b != -1);
        char[] token = new char[10];
        int len = 0;
        do {
            if(len == token.length) {
                char[] newToken = new char[len << 1];
                for(int i = 0; i < len; ++i) newToken[i] = token[i];
                token = newToken;
            }
            token[len++] = (char)b;
        } while((b = read()) > ' ');
        if(len == token.length) return token;
        else {
            char[] resizedToken = new char[len];
            for(int i = 0; i < len; ++i) resizedToken[i] = token[i];
            return resizedToken;
        }
    } 
    public final String nextString() throws IOException { return new String(next()); }
    public final int nextInt() throws IOException { 
        int num = 0;
        byte b;
        boolean neg = false;
        while ((b = read()) <= ' ') {
            if (b == -1) return -1;
        }
        if (b == '-') {
            neg = true;
            b = read();
        }
        do {
            num = num * 10 + (b - '0');
        } while ((b = read()) >= '0' && b <= '9');
        return neg ? -num : num;
    } 
    public final long nextLong() throws IOException { 
        long num = 0;
        byte b;
        boolean neg = false;
        while ((b = read()) <= ' ') {
            if (b == -1) return -1;
        }
        if (b == '-') {
            neg = true;
            b = read();
        }
        do {
            num = num * 10 + (b - '0');
        } while ((b = read()) >= '0' && b <= '9');
        return neg ? -num : num;
    } 
    public final double nextDouble() throws Exception { return Double.parseDouble(new String(next())); } 
    public final char[] nextLine() throws IOException { 
        byte b = read();
        if(b == -1) return new char[0];
        char[] token = new char[128];
        int len = 0;
        do {
            if(len == token.length) {
                char[] newToken = new char[len << 1];
                for(int i = 0; i < len; ++i) newToken[i] = token[i];
                token = newToken;
            }
            token[len++] = (char)b;
        } while((b = read()) != '\n' && b != -1);
        if(len == token.length) return token;
        else {
            char[] resizedToken = new char[len];
            for(int i = 0; i < len; ++i) resizedToken[i] = token[i];
            return resizedToken;
        }
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
            final byte[] temp = new byte[11];
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
            final byte[] temp = new byte[10];
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
    public final void print(char c) throws IOException { bos.write(c); }
    public final void println(char c) throws IOException { bos.write(c); bos.write(ENDL); }
    public final void print(String s) throws IOException { bos.write(bytes(s)); }
    public final void println(String s) throws IOException { bos.write(bytes(s)); bos.write(ENDL); }
    public final void print(int x) throws IOException { bos.write(bytes(x)); }
    public final void println(int x) throws IOException { bos.write(bytes(x)); bos.write(ENDL); }
    public final void print(long x) throws IOException { bos.write(bytes(x)); }
    public final void println(long x) throws IOException { bos.write(bytes(x)); bos.write(ENDL); }
    public final void print(double x) throws IOException { bos.write(bytes(Double.toString(x))); }
    public final void println(double x) throws IOException { bos.write(bytes(Double.toString(x))); bos.write(ENDL); }
    public final void print(boolean b) throws IOException { bos.write(b ? TRUE : FALSE); }
    public final void println(boolean b) throws IOException { bos.write(b ? TRUE : FALSE); bos.write(ENDL); }
    public final void print(Object obj) throws IOException { if(obj == null) print(NULL); else print(obj.toString()); }
    public final void println(Object obj) throws IOException { if(obj == null) println(NULL); else println(obj.toString()); }
    public final void print(int[] array) throws IOException { for(int i: array){ bos.write(bytes(i)); bos.write(SPACE); } }
    public final void println(int[] array) throws IOException { for(int i: array){ bos.write(bytes(i)); bos.write(SPACE); } bos.write(ENDL); }
    public final void print(long[] array) throws IOException { for(long i: array){ bos.write(bytes(i)); bos.write(SPACE); } }
    public final void println(long[] array) throws IOException { for(long i: array){ bos.write(bytes(i)); bos.write(SPACE); } bos.write(ENDL); }
    public final void print(char[] s) throws IOException { bos.write(bytes(s)); }
    public final void println(char[] s) throws IOException { bos.write(bytes(s)); bos.write(ENDL); }
    public final void space() throws IOException { bos.write(SPACE); }
    public final void endl() throws IOException { bos.write(ENDL); }
    public final void flush() throws IOException { bos.flush(); }
}
