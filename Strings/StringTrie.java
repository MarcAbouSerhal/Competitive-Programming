class Trie{
    private static final int k = 26, base = 'a';
    final Node head;
    public Trie(){ head = new Node(); }
    // adds s to T (O(len(s)))
    public final void add(char[] s){
        Node[] curr = head.children;
        int n = s.length;
        for(int i = 0; i < n; ++i){
            if(curr[s[i] - base] == null)
                curr[s[i] - base] = new Node();
            ++curr[s[i] - base].prefs;
            if(i == n - 1)
                ++curr[s[i] - base].ends;
            curr = curr[s[i] - base].children;
        }
    }
    // removes s from T (assumes it's in it) (O(len(s)))
    public final void remove(char[] s){
        Node[] curr = head.children;
        int n = s.length;
        for(int i = 0; i < n; ++i){
            if(curr[s[i] - base].prefs == 1){
                curr[s[i] - base] = null;
                return;
            }
            --curr[s[i] - base].prefs;
            if(i == n - 1)
                --curr[s[i] - base].ends;
            curr = curr[s[i] - base].children;
        }
    }
    static final class Node{
        final Node[] children = new Node[k];
        int ends = 0, prefs = 0;
        public Node() { }
    }
}
