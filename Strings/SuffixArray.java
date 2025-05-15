class SuffixArray {
    private final static int alphabet = 256;
    // returns p where p[i] is the start of the ith smallest suffix of s (O(nlog(n)))
    public static final int[] suffixArray(char[] s) {
        char[] s_ = new char[s.length + 1];
        for(int i = 0; i < s.length; ++i) s_[i] = s[i];
        s_[s.length] = 65000;
        return sortCyclicShifts(s_);
    }
    public static final int[] suffixArray(String s) { return suffixArray(s.toCharArray()); }
    // returns p where p[i] is the start of the ith smallest cyclic shift of s (O(nlog(n)))
    public static final int[] sortCyclicShifts(char[] s) {
        int n = s.length, classes = 1;
        int[] p = new int[n], c = new int[n], cnt = new int[n > alphabet ? n : alphabet], pn = new int[n], cn = new int[n];
        for (int i = 0; i < n; ++i) ++cnt[s[i]];
        for (int i = 1; i < alphabet; ++i) cnt[i] += cnt[i - 1];
        for (int i = 0; i < n; ++i) p[--cnt[s[i]]] = i;
        c[p[0]] = 0;
        for (int i = 1; i < n; ++i) {
            if (s[p[i]] != s[p[i - 1]]) ++classes;
            c[p[i]] = classes - 1;
        }
        for (int h = 0; (1 << h) < n; ++h) {
            for (int i = 0; i < n; ++i) {
                pn[i] = p[i] - (1 << h);
                if (pn[i] < 0) pn[i] += n;
            }
            for (int i = 0; i < classes; ++i) cnt[i] = 0;
            for (int i = 0; i < n; ++i) ++cnt[c[pn[i]]];
            for (int i = 1; i < classes; ++i) cnt[i] += cnt[i-1];
            for (int i = n - 1; i >= 0; --i) p[--cnt[c[pn[i]]]] = pn[i];
            cn[p[0]] = 0;
            classes = 1;
            for (int i = 1; i < n; ++i) {
                if(c[p[i]] != c[p[i - 1]] || c[(p[i] + (1 << h)) % n] != c[(p[i - 1] + (1 << h)) % n]) ++classes;
                cn[p[i]] = classes - 1;
            }
            int[] temp = c;
            c = cn;
            cn = temp;
        }
        return p;
    }
    public static final int[] sortCyclicShifts(String s) { return sortCyclicShifts(s.toCharArray()); }
}
