class Manacher{
    private final int[] d;
    // (O(n))
    public Manacher(char[] t) {
        int n = t.length;
        char[] s = new char[(n << 1) + 3];
        d = new int[(n << 1) + 3];
        s[0] = '$'; s[s.length - 1] = '^';
        for(int i = 0; i < n; ++i) s[(i + 1) << 1] = t[i];
        n = (n << 1) + 1;
        int l = 1, r = 1;
        for(int i = 1; i <= n; ++i){
            d[i] = max(0, min(r - i, d[l + r - i]));
            while(s[i - d[i]] == s[i + d[i]]) ++d[i];
            if(i + d[i] > r){
                l = i - d[i];
                r = i + d[i];
            }
        }
    }
    // returns whether t[l...r] is a palindrome (O(1))
    public final boolean isPalindrome(int l, int r) { return d[l + r + 2] > r - l; }
    // returns [l, r] that is the longest palindromic substring of t (O(n))
    public final int[] maxPalindrome() {
        int center = 0;
        for(int i = 1; i + 1 < d.length; ++i)
            if(d[i] > d[center])
                center = i;
        int maxLen = d[center] - 1, start = (center - maxLen) >> 1;
        return new int[] {start, start + maxLen - 1};
    }
    // returns length of longest palindromic substring centered at i (O(1))
    public final int maxOddAt(int i) { return d[(i + 1) << 1] - 1; }
    // returns length of longest palindromic substring centered at i,i+1 (O(1))
    public final int maxEvenAt(int i) { return d[3 + (i << 1)] - 1; }
    // returns array where array[i] is length of longest palindromic substring ending at i (O(n))
    public final int[] maxEnding() {
        int n = (d.length - 3) >> 1, last = 0;
        int[] res = new int[n];
        for(int i = 0; i < n; ++i)
            for(int j = max(last, i); j < i + (d[(i + 1) << 1] >> 1); last = ++j)
                res[j] = 1 + ((j - i) << 1); 
        last = 0;
        for(int i = 1; i < n; ++i)
            for(int j = max(last, i); j < i + (d[1 + (i << 1)] >> 1); last = ++j)
                res[j] = max(res[j], (j - i + 1) << 1);
        return res;
    }
    private final static int min(int x, int y) { return x < y ? x : y; }
    private final static int max(int x, int y) { return x > y ? x : y; }
}
