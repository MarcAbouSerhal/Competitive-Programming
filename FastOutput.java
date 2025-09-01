class FastOutput {
    private static final int bufferSize = 1 << 18, bufferCount = 1 << 12;
    private static final char[] TRUE = {'t', 'r', 'u', 'e'}, FALSE = {'f', 'a', 'l', 's', 'e'}, digitBuffer = new char[19];
    private static int tick = 0;
    private final PrintStream out;
    private final char[][] buffers = new char[bufferCount][];
    private int c1 = 0, c2 = 0;
    public FastOutput(OutputStream out) { this.out = new PrintStream(out); buffers[0] = new char[bufferSize]; }
    public FastOutput() { this(System.out); }
    public FastOutput(String filename) { PrintStream stream; try{ stream = new PrintStream(filename); } catch(Exception e) { stream = new PrintStream(System.out); }; out = stream; }
    public void print(char c) { buffers[c1][c2++] = c; if(c2 == bufferSize) { buffers[++c1] = new char[bufferSize]; c2 = 0; } }
    public void print(int x) {
        if (x == (tick = 0)) { print('0'); return; }
        if (x > 0)  for(; x != 0; x /= 10)  digitBuffer[tick++] = (char) ('0' + (x % 10));
        else { print('-'); for(; x != 0; x /= 10)  digitBuffer[tick++] = (char) ('0' - (x % 10)); }
        for(--tick; tick >= 0; --tick) print(digitBuffer[tick]);
    }
    public void print(long x) {
        if (x == (tick = 0)) { print('0'); return; }
        if (x > 0)  for(; x != 0; x /= 10)  digitBuffer[tick++] = (char) ('0' + (x % 10));
        else { print('-'); for(; x != 0; x /= 10)  digitBuffer[tick++] = (char) ('0' - (x % 10)); }
        for(--tick; tick >= 0; --tick) print(digitBuffer[tick]);
    }
    public void print(double x) { print(Double.toString(x)); }
    public void print(double x, int places, boolean rounded) { print(rounded ? String.format("%." + places + "f", x) : (new BigDecimal(Double.toString(x))).setScale(places, RoundingMode.DOWN).toPlainString()); }
    public void print(boolean b) { print(b ? TRUE : FALSE); }
    public void print(String s) { int n = s.length(); for(int i = 0; i < n; ++i) print(s.charAt(i)); }
    public void print(char[] s) { for(char c: s) print(c); }
    public void print(int[] a) { for(int i: a) { print(i); print(' '); } }
    public void print(long[] a) { for(long i: a) { print(i); print(' '); } }
    public void println(char c) { print(c); print('\n'); }
    public void println(int x) { print(x); print('\n'); }
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
        for(tick = 0; tick < c1; ++tick) out.print(buffers[tick]);
        if(c2 != 0)  out.print(Arrays.copyOf(buffers[c1], c2));
        c1 = c2 = 0;
    }
}
