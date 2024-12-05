class StringUtil{
    private static int min(int a, int b){ return a<b ? a : b; }
    // pi[i] = max( 0<=k<=i ){ k: s[0...k-1] = s[i-(k-1)...i] }
    public static int[] kmp(char[] s){
        int[] pi = new int[s.length];
        int j;
        for(int i = 1; i < s.length; ++i){
            j = pi[i-1];
            while(j > 0 && s[i] != s[j]) j = pi[j-1];
            if(s[i] == s[j]) ++j;
            pi[i] = j;
        }
        return pi;
    }
    public static int[] kmp(String s){
        int[] pi = new int[s.length()];
        int j;
        for(int i = 1; i < s.length(); ++i){
            j = pi[i-1];
            while(j > 0 && s.charAt(i) != s.charAt(j)) j = pi[j-1];
            if(s.charAt(i) == s.charAt(j)) ++j;
            pi[i] = j;
        }
        return pi;
    }
    // z[i] = max( 0<=k<=n-i ){ k: s[0...k-1] = s[i...i+k-1] } (except z[0] = 0)
    public static int[] z(char[] s){
        int[] z = new int[s.length];
        int l = 0, r = 0;
        for(int i = 1; i < s.length; ++i){
            if(i < r) z[i] = min(r-i, z[i-l]);
            while(i + z[i] < s.length && s[z[i]] == s[i+z[i]]) ++z[i];
            if(i + z[i] > r){
                l = i;
                r = i + z[i];
            }
        }
        return z;
    }   
    public static int[] z(String s){
        int[] z = new int[s.length()];
        int l = 0, r = 0;
        for(int i = 1; i < s.length(); ++i){
            if(i < r) z[i] = min(r-i, z[i-l]);
            while(i + z[i] < s.length() && s.charAt(z[i]) == s.charAt(i + z[i])) ++z[i];
            if(i + z[i] > r){
                l = i;
                r = i + z[i];
            }
        }
        return z;
    }  
}
