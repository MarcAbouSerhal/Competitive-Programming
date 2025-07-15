class KMP {
    // returns pi where pi[i] = max( 0<=k<=i ){ k: s[0...k-1] = s[i-(k-1)...i] } (O(n))
    public final static int[] pi(char[] s){
        int[] pi = new int[s.length];
        int j;
        for(int i = 1; i < s.length; ++i){
            j = pi[i - 1];
            while(j > 0 && s[i] != s[j]) j = pi[j - 1];
            pi[i] = s[i] == s[j] ? j + 1 : j;
        }
        return pi;
    }
    // returns prefCount where prefCount[i] = #{j: s[j...j + i] = s[0...i]} (O(n))
    public final static int[] prefCount(int[] pi) {
        int n = pi.length;
        int[] prefCount = new int[n];
        for(int i: pi) if(i > 0) ++prefCount[i - 1];
        for(int i = n - 1; i > 0; --i) {
            if(pi[i - 1] > 0) prefCount[pi[i - 1] - 1] += prefCount[i - 1];
            ++prefCount[i];
        }
        ++prefCount[0];
        return prefCount;
    }
}
