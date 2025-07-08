class Z {
    // returs z where z[i] = max( 0<=k<=n-i ){ k: s[0...k-1] = s[i...i+k-1] } (except z[0] = 0) (O(n))
    public final static int[] z(char[] s) {
        final int[] z = new int[s.length];
        int l = 0, r = 0;
        for(int i = 1; i < s.length; ++i){
            if(i < r) z[i] = Math.min(r - i, z[i - l]);
            while(i + z[i] < s.length && s[z[i]] == s[i + z[i]]) ++z[i];
            if(i + z[i] > r){
                l = i;
                r = i + z[i];
            }
        }
        return z;
    }   
    public final static int[] z(String s){ return z(s.toCharArray()); } 
}
