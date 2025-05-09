class FastIO{ 
    private final BufferedOutputStream bos;
    private final InputStream in;
    private final byte[] buffer = new byte[1 << 16];
    private int pointer = 0, bytesRead = 0;
    public FastIO() {
        bos = new BufferedOutputStream(System.out);
        in = System.in;
    } 
    public FastIO(OutputStream out, InputStream in) {
        bos = new BufferedOutputStream(out);
        this.in = in;
    }
    private byte read() throws IOException {
        if(pointer >= bytesRead) {
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
    public final double nextDouble() throws IOException { return Double.parseDouble(new String(next())); } 
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
    private static final long[] power = 
    {   1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L, 10000000000L, 100000000000L,
        1000000000000L, 10000000000000L, 100000000000000L, 1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L
    };
    private static final byte[] bytes(int x){ 
        if(x == 0) return ZERO;
        else if(x > 0) {
            int size = 0;
            for(; power[size + 1] <= x; ++size);
            byte[] res = new byte[size + 1];
            while(x != 0) {
                res[size--] = (byte)('0' + (x % 10));
                x /= 10;
            }
            return res;
        }
        else {
            int size = 0;
            for(; -power[size + 1] >= x; ++size);
            byte[] res = new byte[size + 2];
            while(x != 0) {
                res[1 + size--] = (byte)('0' - (x % 10));
                x /= 10;
            }
            res[0] = MINUS;
            return res;
        }
    }
    private static final byte[] bytes(long x){ 
        if(x == 0) return ZERO;
        else if(x > 0) {
            int l = 0, r = 18;
            while(l < r) {
                int mid = (l + r + 1) >> 1;
                if(power[mid] <= x) l = mid;
                else r = mid - 1;
            }
            byte[] res = new byte[l + 1];
            while(x != 0) {
                res[l--] = (byte)('0' + (x % 10));
                x /= 10;
            }
            return res;
        }
        else {
            int l = 0, r = 18;
            while(l < r) {
                int mid = (l + r + 1) >> 1;
                if(power[mid] >= x) l = mid;
                else r = mid - 1;
            }
            byte[] res = new byte[l + 2];
            while(x != 0) {
                res[1 + l--] = (byte)('0' + (x % 10));
                x /= 10;
            }
            res[0] = MINUS;
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
    public final void println() throws IOException { bos.write(ENDL); }
    public final void flush() throws IOException { bos.flush(); }
}