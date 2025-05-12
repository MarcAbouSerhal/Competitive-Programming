class Trie{
    static  final class Node{
        final Node[] children;
        int ends;
        int pref;
        public Node(){
            children = new Node[26];
            ends=0;
            pref=0;
        }
    }
    final Node head;
    public Trie(){
        head = new Node();
    }
    // adds s to T (O(len(s)))
    public final void add(String s){
        Node[] curr = head.children;
        final int n = s.length();
        for(int i = 0; i < n; ++i){
            if(curr[s.charAt(i) - 'a'] == null)
                curr[s.charAt(i) - 'a'] = new Node();
            ++curr[s.charAt(i) - 'a'].pref;
            if(i == n - 1)
                ++curr[s.charAt(i) - 'a'].ends;
            curr = curr[s.charAt(i) - 'a'].children;
        }
    }
    // removes s from T (assumes it's in it) (O(len(s)))
    public final void remove(String s){
        Node[] curr = head.children;
        final int n = s.length();
        for(int i = 0; i < n; ++i){
            if(curr[s.charAt(i) - 'a'].pref == 1){
                curr[s.charAt(i) - 'a'] = null;
                return;
            }
            --curr[s.charAt(i) - 'a'].pref;
            if(i == n - 1)
                --curr[s.charAt(i) - 'a'].ends;
            curr = curr[s.charAt(i) - 'a'].children;
        }
    }
    // returns # strings with s as a prefix (O(len(s)))
    public final int haveAsPref(String s){
        Node[] curr = head.children;
        final int n = s.length();
        for(int i = 0; i < n; ++i){
            if(curr[s.charAt(i) - 'a'] == null)
                return 0;
            if(i == n - 1)
                return curr[s.charAt(i) - 'a'].pref;
            curr = curr[s.charAt(i) - 'a'].children;
        }
        return 0;
    }
    // returns LCP of s and some string in the trie (other than s if s is in the trie) (O(len(s)))
    public final int LCP(String s, boolean isInTrie){
        Node[] curr = head.children;
        final int n = s.length();
        for(int i = 0; i < n; ++i){
            if(curr[s.charAt(i) - 'a'] == null || (isInTrie && curr[s.charAt(i) - 'a'].pref < 2))
                return i;
            curr = curr[s.charAt(i) - 'a'].children;
        }
        return n;
    } 
    private final boolean contains(int k, int l, Node curr){
        if(curr == null) return false;
        if(l == 1)
            return curr.pref >= k;
        boolean ans = false;
        for(int i = 0; i < 26; ++i){
            ans |= contains(k, l - 1, curr.children[i]);
        }
        return ans;
    }
    // returns if there are >=k strings with LCP >= l (O(sum( s in T ){ len(s) }))
    public final boolean contains(int k, int l){
        boolean ans = false;
        for(int i = 0; i < 26; ++i)
            ans |= contains(k, l, head.children[i]);
        return ans;
    }
    private static final int prefixes(Node node){
        int count = 1;
        for(Node child: node.children){
            if(child == null) continue;
            count += prefixes(child);
        }
        return count;
    }
    // returns # unique prefixes (# nodes) (O(sum( s in T ){ len(s) }))
    public final int prefixes(){
        return prefixes(head)-1;
    }
}
