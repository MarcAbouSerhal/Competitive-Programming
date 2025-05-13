class Booth {
    // returns k that minimizes s[k...n - 1] + s[0...k - 1] (O(n))
    public final static int leastRotation(char [] s) {
        int n = s.length, j, si, k = 0;
        int[] f = new int[n << 1]; f[0] = -1;
        for(int i = 1; i < n << 1; ++i) {
            j = f[i - k - 1];
            si = s[i % n];
            while(j != -1 && si != s[(k + j + 1) % n]) {
                if(si < s[(k + j + 1) % n]) k = i - j - 1;
                j = f[j];
            }
            if(si != s[(k + j + 1) % n]) {
                if(si < s[k]) k = i;
                f[i - k] = -1;
            }
            else f[i - k] = j + 1;
        }
        return k;
    }
    public final static int leastRotation(String s) { return leastRotation(s.toCharArray()); }
}