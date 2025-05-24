class FastInput{ 
    private final InputStream in;
    private final byte[] buffer = new byte[1 << 16];
    private int pointer = 0, bytesRead = 0;
    public FastInput() {
        in = System.in;
    } 
    public FastInput(OutputStream out, InputStream in) {
        this.in = in;
    }
    private byte read() throws IOException {
        if(pointer >= bytesRead) {
            pointer = 0;
            if((bytesRead = in.read(buffer)) == -1) return -1;
        }
        return buffer[pointer++];
    }
    public final char nextChar() throws IOException { 
        byte b;
        while((b = read()) <= ' ' && b != -1);
        return (char)b;
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
    public final void skipToken() throws IOException {
        byte b;
        while((b = read()) <= ' ' && b != -1);
        while((b = read()) > ' ');
    }
}
