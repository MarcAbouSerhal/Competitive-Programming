class SuffixAutomaton {
    private static final int k = 26, base = 'a';
    final ArrayList<Node> d = new ArrayList<>();
    private int last = 0;
    public SuffixAutomaton() { d.add(new Node()); }
    // (O(n))
    public SuffixAutomaton(char[] s) {
        d.add(new Node());
        for(char c: s) addChar(c);
    }
    // (O(1) amortized)
    public final void addChar(char c) {
        d.add(new Node());
        int curr = d.size() - 1, p = last;
        d.get(curr).sz = d.get(p).sz + 1;
        while (p != -1 && d.get(p).adj[c - base] == -1) {
            d.get(p).adj[c - base] = curr;
            p = d.get(p).link;
        }
        if (p == -1) d.get(curr).link = 0;
        else {
            int q = d.get(p).adj[c - base];
            if (d.get(p).sz + 1 == d.get(q).sz)  d.get(curr).link = q;
            else {
                d.add(new Node());
                int clone = d.size() - 1;
                Node cloneNode = d.get(clone), qNode = d.get(q);
                cloneNode.sz = d.get(p).sz + 1;
                cloneNode.adj = copy(qNode.adj);
                cloneNode.link = qNode.link;
                while (p != -1 && d.get(p).adj[c - base] == q) {
                    d.get(p).adj[c - base] = clone;
                    p = d.get(p).link;
                }
                qNode.link = d.get(curr).link = clone;
            }
        }
        last = curr;
    }
    private static final int[] copy(int[] a) {
        int[] b = new int[k];
        for(int i = 0; i < k; ++i) b[i] = a[i];
        return b;
    }
    static final class Node {
        int sz = 0, link = -1;
        int[] adj = new int[k];
        public Node() { for(int i = 0; i < k; ++i) adj[i] = -1; }
    }
}
