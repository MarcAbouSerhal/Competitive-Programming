class KMP {
    // returns pi where pi[i] = max{0 <= k < i: s[0, k - 1] = s[i - (k - 1), i]} (O(n))
    public final static int[] pi(char[] s){
        int[] pi = new int[s.length];
        int j;
        for(int i = 1; i < s.length; ++i){
            j = pi[i - 1];
            while(j > 0 && s[i] != s[j]) j = pi[j - 1];
            if(s[i] == s[j]) pi[i] = j + 1; 
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
    // returns transtions of KMP automaton of s (O(n.k))
    public static final int[][] automaton(char[] s, int k, char base) {
        int n = s.length;
        int[] pi = pi(s);
        int[][] aut = new int[n + 1][k];
        for(int st = 0; st <= n; ++st)
            for(int c = 0; c < k; ++c)
                if(st < n && s[st] == base + c) aut[st][c] = st + 1;
                else if (st == 0) aut[st][c] = 0;
                else aut[st][c] = aut[pi[st - 1]][c];
        return aut;
    }
}
