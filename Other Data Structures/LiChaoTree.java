class LiChaoTree {
    Node root;
    final int min, max;
    public LiChaoTree(int min, int max) {
        this.min = min;
        this.max = max;
        root = new Node(ignored);
    }
    // returns line that maximizes m * x + b (O(log(range)))
    public final Line query(int x) {
        Node curr = root;
        Line ans = root.line;
        int l = min, r = max;
        while(curr != null) {
            ans = best(ans, curr.line, x);
            int mid = l + ((r - l) >> 1);
            if(x <= mid) {
                r = mid;
                curr = curr.ln;
            }
            else {
                l = mid + 1;
                curr = curr.rn;
            }
        }
        return ans;
    }
    // adds line to [l, r] (O(log(range)) amortized)
    public final void add(Line line, int l, int r) { root = insertLine(root, min, max, l, r, line); }
    // switch MIN with MAX and > with < to get minimum instead of maximum
    private static final Line ignored = new Line(0, Long.MIN_VALUE, -1);
    private static final Line best(Line l1, Line l2, int x) { return l1.get(x) > l2.get(x) ? l1 : l2; }
    private final Node insertLine(Node node, int lx, int rx, int l, int r, Line line) {
        if(rx < l || lx > r || lx > rx) return node;
        if(node == null) node = new Node(ignored);
        if(l <= lx && rx <= r) return insertLineKnowingly(node, lx, rx, line);
        int mid = lx + ((rx - lx) >> 1);
        node.ln = insertLine(node.ln, lx, mid, l, r, line);
        node.rn = insertLine(node.rn, mid + 1, rx, l, r, line);
        return node;
    }
    private final Node insertLineKnowingly(Node node, int lx, int rx, Line line) {
        if(node == null) node = new Node(ignored);
        if(best(line, node.line, lx) == line) {
            Line temp = node.line;
            node.line = line;
            line = temp;
        }
        if(best(line, node.line, rx) != line || lx == rx) return node;
        int mid = lx + ((rx - lx) >> 1);
        if(best(node.line, line, mid) == node.line) node.rn = insertLineKnowingly(node.rn, mid + 1, rx, line);
        else {
            Line temp = node.line;
            node.line = line;
            line = temp;
            node.ln = insertLineKnowingly(node.ln, lx, mid, line);
        }
        return node;
    }
}
class Node {
    Line line;
    Node ln, rn;
    public Node(Line line) { this.line = line; }
}
class Line {
    final long m, b;
    final int i;
    public Line(long m, long b, int i) { this.m = m; this.b = b; this.i = i; }
    public Line(long m, long b) { this.m = m; this.b = b; i = -1; }
    public final long get(int x) { return m * x + b; }
}