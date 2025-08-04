class MultiSuffixAutomaton {
    private static final int k = 26, base = 'a';
    final ArrayList<Node> d = new ArrayList<>();
    // last: index of last non-clone Node
    // lastIndex: index of last added string
    int last = 0, lastIndex = -1;
    public MultiSuffixAutomaton() { d.add(new Node()); }
    // (O(n))
    public final void add(char[] s, int index) { for(char c: s) addChar(c, index); }
    // (O(1) amortized)
    public final void addChar(char c, int index) {
        if(index != lastIndex) {
            lastIndex = index;
            last = 0;
        }
        d.add(new Node());
        int curr = d.size() - 1, p = last;
        d.get(curr).sz = d.get(p).sz + 1;
        while (p != -1 && d.get(p).adj[c - base] == -1) {
            Node pNode = d.get(p);
            pNode.adj[c - base] = curr;
            p = pNode.link;
        }
        if (p == -1) d.get(curr).link = 0;
        else {
            int q = d.get(p).adj[c - base];
            if (d.get(p).sz + 1 == d.get(q).sz)  d.get(curr).link = q;
            else {
                Node cloneNode = new Node(), qNode = d.get(q);
                d.add(cloneNode);
                int clone = curr + 1;
                cloneNode.sz = d.get(p).sz + 1;
                cloneNode.adj = Arrays.copyOf(qNode.adj, k);
                cloneNode.link = qNode.link;
                cloneNode.last = qNode.last;
                cloneNode.indices = qNode.indices;
                while (p != -1 && d.get(p).adj[c - base] == q) {
                    Node pNode = d.get(p);
                    pNode.adj[c - base] = clone;
                    p = pNode.link;
                }
                qNode.link = d.get(curr).link = clone;
            }
        }
        p = curr;
        while(p != -1 && d.get(p).last != index) {
            Node pNode = d.get(p);
            pNode.last = index;
            ++pNode.indices;
            p = pNode.link;
        }
        last = curr;
    }
    static final class Node {
        // sz: length of longest path represented by current node
        // link: longest proper suffix not represented by current node
        // last: last string index that includes all substrings represented by current node
        // indices: # string indices that include all substrings represented by current node
        int sz = 0, link = -1, last = -1, indices = 0;
        int[] adj = new int[k];
        public Node() { for(int i = 0; i < k; ++i) adj[i] = -1; }
    }
}
