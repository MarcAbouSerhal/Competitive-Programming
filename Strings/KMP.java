class KMP {
    // returns pi where pi[i] = max( 0<=k<=i ){ k: s[0...k-1] = s[i-(k-1)...i] } (O(n))
    public final static int[] kmp(char[] s){
        final int[] pi = new int[s.length];
        int j;
        for(int i = 1; i < s.length; ++i){
            j = pi[i-1];
            while(j > 0 && s[i] != s[j]) j = pi[j - 1];
            if(s[i] == s[j]) ++j;
            pi[i] = j;
        }
        return pi;
    }
}
