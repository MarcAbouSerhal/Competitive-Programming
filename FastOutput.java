class FastOutput {
    private final PrintStream out;
    private final char[][] buffers = new char[bufferCount][];
    private int c1 = 0, c2 = 0;
    public FastOutput() { out = System.out; buffers[0] = new char[bufferSize]; }
    public FastOutput(OutputStream out) { this.out = new PrintStream(out); buffers[0] = new char[bufferSize]; }
    private static final int bufferSize = 1 << 18, bufferCount = 1 << 12;
    public final void print(char c) { buffers[c1][c2++] = c; if(c2 == bufferSize) { buffers[++c1] = new char[bufferSize]; c2 = 0; } }
    private static final long[] power = {
        1L, 10L, 100L, 1000L, 10000L, 100000L, 1000000L, 10000000L, 100000000L, 1000000000L,
        10000000000L, 100000000000L, 1000000000000L, 10000000000000L, 100000000000000L,
        1000000000000000L, 10000000000000000L, 100000000000000000L, 1000000000000000000L
    };
    public final void print(int x) {
        if (x == 0) print('0');
        else if (x > 0) {
            int size = 0;
            for (; power[size + 1] <= x; ++size);
            char[] digits = new char[size + 1];
            while (x != 0) {
                digits[size--] = (char) ('0' + (x % 10));
                x /= 10;
            }
            for(char c: digits) print(c);
        } 
        else {
            int size = 0;
            for (; -power[size + 1] >= x; ++size);
            char[] digits = new char[size + 1];
            while (x != 0) {
                digits[size--] = (char) ('0' - (x % 10));
                x /= 10;
            }
            print('-');
            for(char c: digits) print(c);
        }
    }
    public final void print(long x) {
        if (x == 0) print('0');
        else if (x > 0) {
            int l = 0, r = 18;
            while (l < r) {
                int mid = (l + r + 1) >> 1;
                if (power[mid] <= x) l = mid;
                else r = mid - 1;
            }
            char[] digits = new char[l + 1];
            while (x != 0) {
                digits[l--] = (char) ('0' + (x % 10));
                x /= 10;
            }
            for(char c: digits) print(c);
        } 
        else {
            int l = 0, r = 18;
            while (l < r) {
                int mid = (l + r + 1) >> 1;
                if (-power[mid] >= x) l = mid;
                else r = mid - 1;
            }
            char[] digits = new char[l + 1];
            while (x != 0) {
                digits[l--] = (char) ('0' - (x % 10));
                x /= 10;
            }
            print('-');
            for(char c: digits) print(c);
        }
    }
    public final void print(boolean b) {
        if(b) { print('t'); print('r'); print('u'); print('e'); }
        else { print('f'); print('a'); print('l'); print('s'); print('e'); }
    }
    public final void print(String s) { int n = s.length(); for(int i = 0; i < n; ++i) print(s.charAt(i)); }
    public final void print(char[] s) { for(char c: s) print(c); }
    public final void print(int[] a) { for(int i: a) { print(i); print(' '); } }
    public final void print(long[] a) { for(long i: a) { print(i); print(' '); } }
    public final void println(char c) { print(c); print('\n'); }
    public final void println(int x) { print(x); print('\n'); }
    public final void println(long x) { print(x); print('\n'); }
    public final void println(boolean b) { print(b); print('\n'); }
    public final void println(String s) { print(s); print('\n'); }
    public final void println(char[] s) { print(s); print('\n'); }
    public final void println(int[] a) { print(a); print('\n'); }
    public final void println(long[] a) { print(a); print('\n'); }
    public final void println() { print('\n'); }
    public final void endl() { print('\n'); }
    public final void space() { print(' '); }
    public final void flush() {
        for(int i = 0; i < c1; ++i) out.print(buffers[i]);
        if(c2 != 0) {
            char[] last = new char[c2];
            for(int i = 0; i < c2; ++i) last[i] = buffers[c1][i];
            out.print(last);
        }
        c1 = c2 = 0;
    }
}
