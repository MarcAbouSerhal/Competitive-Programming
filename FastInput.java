class FastInput { 
    private static final int bufferSize = 1 << 18;
    private final InputStream in;
    private final byte[] buffer = new byte[bufferSize];
    private int pointer = 0, bytesRead = 0;
    public FastInput() { this.in = System.in; } 
    public FastInput(InputStream in) { this.in = in; }
    private final byte read() throws IOException {
        if(pointer >= bytesRead) {
            pointer = 0;
            if((bytesRead = in.read(buffer)) == -1) throw new IOException("EOF character was encountered.");
        }
        return buffer[pointer++];
    }
    public final char nextChar() throws IOException { 
        byte b;
        while ((b = read()) <= ' ');
        return (char)b;
    }
    public final char[] next() throws IOException { 
        byte b;
        while ((b = read()) <= ' ');
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
        int num = 0, sign = 1;
        byte b;
        while ((b = read()) <= ' ');
        if (b == '-') {
            sign = -1;
            b = read();
        }
        do {
            num = num * 10 + sign * (b - '0');
        } while ((b = read()) >= '0' && b <= '9');
        return num;
    } 
    public final long nextLong() throws IOException { 
        long num = 0;
        int sign = 1;
        byte b;
        while ((b = read()) <= ' ');
        if (b == '-') {
            sign = -1;
            b = read();
        }
        do {
            num = num * 10 + sign * (b - '0');
        } while ((b = read()) >= '0' && b <= '9');
        return num;
    } 
    public final double nextDouble() throws IOException { return Double.parseDouble(new String(next())); } 
    public final char[] nextLine() throws IOException { 
        byte b = read();
        char[] token = new char[128];
        int len = 0;
        do {
            if(len == token.length) {
                char[] newToken = new char[len << 1];
                for(int i = 0; i < len; ++i) newToken[i] = token[i];
                token = newToken;
            }
            token[len++] = (char)b;
        } while((b = read()) != '\n');
        if(len == token.length) return token;
        else {
            char[] resizedToken = new char[len];
            for(int i = 0; i < len; ++i) resizedToken[i] = token[i];
            return resizedToken;
        }
    } 
    public final int[] nextIntArray(int n) throws IOException {
        int[] array = new int[n];
        for(int i = 0; i < n; ++i) array[i] = nextInt();
        return array;
    }
    public final long[] nextLongArray(int n) throws IOException {
        long[] array = new long[n];
        for(int i = 0; i < n; ++i) array[i] = nextLong();
        return array;
    }
    public final void skipToken() throws IOException {
        while (read() <= ' ');
        while (read() > ' ');
    }
}
