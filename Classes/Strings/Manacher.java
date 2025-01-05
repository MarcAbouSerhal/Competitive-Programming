class Manacher{
    private final static int min(int x, int y){ return x < y ? x : y; }
    private final static int max(int x, int y){ return x > y ? x : y; }
    private final int[] d;
    // O(n)
    public Manacher(char[] t){
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
    // returns whether s[l...r] is a palindrome (O(1))
    public final boolean isPalindrome(int l, int r){
        return d[l + r + 2] > r - l;
    }
}
