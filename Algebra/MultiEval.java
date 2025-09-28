class MultiEval {
    // find f(c[i]) for every i
    public static final long[] multiPointEvel(long[] f, long[] c, long mod) {
        ArrayList<long[]> g = new ArrayList<>(c.length);
        for(int i = 0; i < c.length; ++i) g.add(new long[] {-c[i], 1});
        long[] ans = new long[c.length];
        long[][] mods = multiMod(f, g, mod);
        for(int i = 0; i < c.length; ++i) ans[i] = mods[i][0];
        return ans;
    }
    // find f(x) mod g[i](x) for every i
    public static final long[][] multiMod(long[] f, ArrayList<long[]> g, long mod) {
        PriorityQueue<Node> nodes = new PriorityQueue<>((u, v) -> u.f.length - v.f.length);
        for(int i = 0; i < g.size(); ++i) nodes.add(new Node(g.get(i), i, null, null));
        while(nodes.size() > 1) {
            Node left = nodes.poll(), right = nodes.poll();
            nodes.add(new Node(Polynomial.multiply(left.f, right.f, mod), -1, left, right));
        }
        long[][] answer = new long[g.size()][];
        dfs(nodes.poll(), f, mod, answer);
        return answer;
    }
    private static final void dfs(Node u, long[] f, long mod, long[][] answer) {
        f = Polynomial.mod(f, u.f, mod);
        if(u.index == -1) { dfs(u.left, f, mod, answer); dfs(u.right, f, mod, answer); }
        else answer[u.index] = f;
    }
    private static final class Node {
        final long[] f;
        final int index;
        final Node left, right;
        public Node(long[] f, int index, Node left, Node right) {
            this.f = f;
            this.index = index;
            this.left = left;
            this.right = right;
        }
    }
}
