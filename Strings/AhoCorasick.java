class AhoCorasick {
    private static final int k = 26, base = 'a';
    final ArrayList<Vertex> trie = new ArrayList<>();
    public AhoCorasick() { trie.add(new Vertex()); }
    // adds s to the trie (O(k.|s|))
    public final void add(char[] s) { 
        int i = 0;
        Vertex v = trie.get(0);
        for (char ch : s) {
            int c = ch - base;
            if (v.next[c] == -1) {
                v.next[c] = trie.size();
                trie.add(new Vertex(i, c));
            }
            v = trie.get(i = v.next[c]);
        }
        ++v.output;
    }
    public final void add(String s) { add(s.toCharArray()); }
    public final int get_link(int i) {
        Vertex v = trie.get(i);
        if(v.link == -1) {
            if(i == 0 || v.p == 0) v.link = 0;
            else v.link = go(get_link(v.p), v.pch);
        }
        return v.link;
    }
    public final int go(int i, int c) {
        Vertex v = trie.get(i);
        if(v.go[c] == -1) {
            if(v.next[c] != -1) v.go[c] = v.next[c];
            else v.go[c] = i == 0 ? 0 : go(get_link(i), c);
        }
        return v.go[c];
    }
    public static final class Vertex {
        final int[] next = new int[k], go = new int[k];
        int output = 0, p = -1, link = -1, pch = '$';
        public Vertex() { for(int i = 0; i < k; ++i) next[i] = go[i] = -1; }
        public Vertex(int p, int ch) { this(); this.p = p; pch = ch; }
    }
}
