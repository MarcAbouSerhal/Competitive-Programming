class FastOutput {
    private static final int bufferSize = 1 << 18, bufferCount = 1 << 12;
    private static final char[] digitBuffer = new char[19];
    private final PrintStream out;
    private final char[][] buffers = new char[bufferCount][];
    private int c1 = 0, c2 = 0;
    public FastOutput(OutputStream out) { this.out = new PrintStream(out); buffers[0] = new char[bufferSize]; }
    public FastOutput() { this(System.out); }
    public FastOutput(String filename) { PrintStream stream; try{ stream = new PrintStream(filename); } catch(Exception e) { stream = new PrintStream(System.out); }; out = stream; }
    public void print(char c) { buffers[c1][c2++] = c; if(c2 == bufferSize) { buffers[++c1] = new char[bufferSize]; c2 = 0; } }
    public void print(long x) {
        if (x == 0) { print('0'); return; }
        int size = 0;
        if (x > 0) {
            while (x != 0) {
                digitBuffer[size++] = (char) ('0' + (x % 10));
                x /= 10;
            }
        } 
        else {
            while (x != 0) {
                digitBuffer[size++] = (char) ('0' - (x % 10));
                x /= 10;
            }
        }
        for(--size; size >= 0; --size) print(digitBuffer[size]);
    }
    public void print(double x) { print(Double.toString(x)); }
    public void print(double x, int places, boolean rounded) { 
        if(rounded) print(String.format("%." + places + "f", x)); 
        else print((new BigDecimal(Double.toString(x))).setScale(places, RoundingMode.DOWN).toPlainString());
    }
    public void print(boolean b) {
        if(b) { print('t'); print('r'); print('u'); print('e'); }
        else { print('f'); print('a'); print('l'); print('s'); print('e'); }
    }
    public void print(String s) { int n = s.length(); for(int i = 0; i < n; ++i) print(s.charAt(i)); }
    public void print(char[] s) { for(char c: s) print(c); }
    public void print(int[] a) { for(int i: a) { print(i); print(' '); } }
    public void print(long[] a) { for(long i: a) { print(i); print(' '); } }
    public void println(char c) { print(c); print('\n'); }
    public void println(long x) { print(x); print('\n'); }
    public void println(double x) { print(Double.toString(x)); print('\n'); }
    public void println(double x, int places, boolean rounded) { print(x, places, rounded); print('\n'); }
    public void println(boolean b) { print(b); print('\n'); }
    public void println(String s) { print(s); print('\n'); }
    public void println(char[] s) { print(s); print('\n'); }
    public void println(int[] a) { print(a); print('\n'); }
    public void println(long[] a) { print(a); print('\n'); }
    public void println() { print('\n'); }
    public void endl() { print('\n'); }
    public void space() { print(' '); }
    public void flush() {
        for(int i = 0; i < c1; ++i) out.print(buffers[i]);
        if(c2 != 0) {
            char[] last = new char[c2];
            for(int i = 0; i < c2; ++i) last[i] = buffers[c1][i];
            out.print(last);
        }
        c1 = c2 = 0;
    }
}
