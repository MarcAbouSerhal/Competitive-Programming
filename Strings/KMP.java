class KMP {
    // returns pi where pi[i] = max( 0<=k<=i ){ k: s[0...k-1] = s[i-(k-1)...i] } (O(n))
    public final static int[] kmp(char[] s){
        int[] pi = new int[s.length];
        int j;
        for(int i = 1; i < s.length; ++i){
            j = pi[i - 1];
            while(j > 0 && s[i] != s[j]) j = pi[j - 1];
            pi[i] = s[i] == s[j] ? j + 1 : j;
        }
        return pi;
    }
    // returns sufCount where sufCount[i] = #{j: s[j...j + n - 1 - i] = s[i...n - 1]} (O(n))
    public final static int[] sufCount(int[] pi) {
        int n = pi.length;
        int[] sufCount = new int[n];
        for(int i: pi) if(i > 0) ++sufCount[n - i];
        for(int i = n - 1; i > 0; ++i) {
            if(pi[i - 1] > 0) sufCount[n - pi[i - 1]] += sufCount[n - i];
            ++sufCount[n - i - 1];
        }
        ++sufCount[n - 1];
        return sufCount;
    }
}