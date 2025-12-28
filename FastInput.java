class FastInput { 
    private static final int BUFFER_SIZE = 1 << 18, INITIAL_TOKEN_SIZE = 16;
    private InputStream in;
    private byte[] buffer = new byte[BUFFER_SIZE];
    private int pointer = 0, bytesRead = 0;
    FastInput() { this.in = System.in; } 
    FastInput(InputStream in) { this.in = in; }
    private byte read() throws IOException {
        if(pointer >= bytesRead) {
            pointer = 0;
            if((bytesRead = in.read(buffer)) == -1) throw new IOException("EOF character was encountered.");
        }
        return buffer[pointer++];
    }
    char nextChar() throws IOException { 
        byte b;
        while ((b = read()) <= ' ');
        return (char)b;
    }
    char[] next() throws IOException { 
        byte b;
        while ((b = read()) <= ' ');
        char[] token = new char[INITIAL_TOKEN_SIZE];
        int len = 0;
        do {
            if(len == token.length) token = Arrays.copyOf(token, len << 1);
            token[len++] = (char)b;
        } while((b = read()) > ' ');
        if(len == token.length) return token;
        else return Arrays.copyOf(token, len);
    } 
    int nextInt() throws IOException { 
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
    long nextLong() throws IOException { 
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
    double nextDouble() throws IOException { return Double.parseDouble(new String(next())); } 
    char[] nextLine() throws IOException { 
        byte b = read();
        char[] token = new char[INITIAL_TOKEN_SIZE];
        int len = 0;
        do {
            if(len == token.length) token = Arrays.copyOf(token, len << 1);
            token[len++] = (char)b;
        } while((b = read()) != '\n');
        if(len == token.length) return token;
        else return Arrays.copyOf(token, len);
    } 
    void skipToken() throws IOException {
        while (read() <= ' ');
        while (read() > ' ');
    }
}
