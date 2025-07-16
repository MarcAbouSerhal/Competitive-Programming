
class CyclicShiftSort {
    private final static int alphabet = 128;
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
    // returns string t where t[i] is end of ith smallest cyclic shift of s + '$' (O(nlog(n)))
    public static final char[] BWT(char[] s) {
        int n = s.length;
        char[] s_ = new char[n + 1], t = new char[n + 1];
        for(int i = 0; i < n; ++i) s_[i] = s[i];
        s_[n] = '$';
        int[] res = sortCyclicShifts(s_);
        for(int i = 0; i < n + 1; ++i) t[i] = s_[(res[i] + n) % (n + 1)];
        return t;
    }
    // returns s where t = BWT(s) (O(nlog(n)))
    public static final char[] inverseBWT(char[] t) {
        int lenT = t.length;
        char[] sortedT = new char[lenT];
        int[] lShift = new int[lenT];
        for(int i = 0; i < lenT; ++i) sortedT[i] = t[i];
        Arrays.sort(sortedT);
        int x;
        for(x = 0; t[x] != '$'; ++x);
        LinkedList<Integer>[] arr = new LinkedList[alphabet];
        for(int i = 0; i < alphabet; ++i) arr[i] = new LinkedList<>();
        for(int i = 0; i < lenT; ++i) arr[t[i]].add(i);
        for(int i = 0; i < lenT; ++i) {
            lShift[i] = arr[sortedT[i]].get(0);
            arr[sortedT[i]].remove(0);
        }
        char[] s = new char[lenT - 1];
        for(int i = 0; i < lenT - 1; ++i) s[i] = t[x = lShift[x]];
        return s;
    }
}
