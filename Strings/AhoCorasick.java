class AhoCorasick {
    private static final int k = 26, base = 'a';
    final ArrayList<Vertex> trie = new ArrayList<>();
    public AhoCorasick() { trie.add(new Vertex()); }
    public final void add(char[] s) { 
        int i = 0;
        Vertex v = trie.get(0);
        for (char ch : s) {
            int c = ch - base;
            if (v.next[c] == -1) {
                v.next[c] = trie.size();
                trie.add(new Vertex(c, v.len + 1, i));
            }
            v = trie.get(i = v.next[c]);
        }
        v.terminal = true;
    }
    public final void add(String s) { add(s.toCharArray()); }
    public final int link(int i) {
        Vertex v = trie.get(i);
        if(v.link == -1)  v.link = v.p == 0 ? 0 : next(link(v.p), v.c);
        return v.link;
    }
    public final int next_terminal(int i) {
        Vertex v = trie.get(i);
        if(v.next_terminal == -1) v.next_terminal = trie.get(link(i)).terminal ? v.link : next_terminal(v.link);
        return v.next_terminal;
    }
    public final int next(int i, int c) {
        Vertex v = trie.get(i);
        if(v.next[c] == -1) v.next[c] = next(link(i), c);
        return v.next[c];
    }
    public static final class Vertex {
        final int[] next = new int[k];
        boolean terminal = false;
        int len = 0, link = -1, next_terminal = -1, c, p;
        public Vertex() {
            terminal = true;
            link = next_terminal = 0;
        }
        public Vertex(int c, int len, int p) {
            for(int i = 0; i < k; ++i) next[i] = -1; 
            this.c = c; 
            this.len = len; 
            this.p = p; 
        }
    }
}
