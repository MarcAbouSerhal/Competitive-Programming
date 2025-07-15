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
    // returns # strings with s as a prefix (O(len(s)))
    public final int haveAsPref(char[] s){
        Node[] curr = head.children;
        int n = s.length;
        for(int i = 0; i < n; ++i){
            if(curr[s[i] - base] == null)
                return 0;
            if(i == n - 1)
                return curr[s[i] - base].prefs;
            curr = curr[s[i] - base].children;
        }
        return 0;
    }
    // returns LCP of s and some string in the trie (other than s if s is in the trie) (O(len(s)))
    public final int LCP(char[] s, boolean isInTrie){
        Node[] curr = head.children;
        int n = s.length;
        for(int i = 0; i < n; ++i){
            if(curr[s[i] - base] == null || (isInTrie && curr[s[i] - base].prefs < 2))
                return i;
            curr = curr[s[i] - base].children;
        }
        return n;
    } 
    private final boolean contains(int k, int l, Node curr){
        if(curr == null) return false;
        if(l == 1)
            return curr.prefs >= k;
        boolean ans = false;
        for(int i = 0; i < k; ++i){
            ans |= contains(k, l - 1, curr.children[i]);
        }
        return ans;
    }
    // returns if there are >=k strings with LCP >= l (O(sum( s in T ){ len(s) }))
    public final boolean contains(int k, int l){
        boolean ans = false;
        for(int i = 0; i < k; ++i)
            ans |= contains(k, l, head.children[i]);
        return ans;
    }
    static  final class Node{
        final Node[] children = new Node[k];
        int ends = 0, prefs = 0;
        public Node() { }
    }
}
