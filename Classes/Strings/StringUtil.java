class StringUtil{
    // pi[i] = max( 0<=k<=i ){ k: s[0...k-1] = s[i-(k-1)...i] }
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
    public final static int[] kmp(String s){ return kmp(s.toCharArray()); }
    // z[i] = max( 0<=k<=n-i ){ k: s[0...k-1] = s[i...i+k-1] } (except z[0] = 0)
    public final static int[] z(char[] s){
        final int[] z = new int[s.length];
        int l = 0, r = 0;
        for(int i = 1; i < s.length; ++i){
            if(i < r) z[i] = min(r - i, z[i - l]);
            while(i + z[i] < s.length && s[z[i]] == s[i + z[i]]) ++z[i];
            if(i + z[i] > r){
                l = i;
                r = i + z[i];
            }
        }
        return z;
    }   
    public final static int[] z(String s){ return z(s.toCharArray()); }  
    public final static boolean isSubsequence(String a, String b){
        int ap = 0, bp = 0;
        while (ap < a.length() && bp < b.length()) {
            if (a.charAt(ap) == b.charAt(bp)) ++ap;
            ++bp;
        }
        return ap == a.length(); 
    } 
    public final static String[] letter = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    // returns all permutations in lexicographical order
    // count[i] = number of occurence of letter i and n = sum(count)
    // complexity can be made better by using a LinkedList<Character>
    public final static ArrayList<String> permutations(int n, int[] count){
        if(n == 1){
            for(int i = 0; i < 26; ++i)
                if(count[i] > 0){
                    ArrayList<String> res = new ArrayList<>();
                    res.add(letter[i]);
                    return res;
                }
            return null;
        }
        ArrayList<String> res = new ArrayList<>();
        for(int i = 0; i < 26; ++i)
            if(count[i] > 0){
                count[i]--;
                for(String s: permutations(n - 1, count))
                    res.add(letter[i] + s);
                count[i]++;
            }
        return res;
    }
    private static int min(int a, int b){ return a<b ? a : b; }
}
