class RollbackDSU {
    private final int[] p;
    private Node history;
    int components;
    public RollbackDSU(int n) { p = new int[components = n]; for (int i = 0; i < n; ++i) p[i] = -1; }
    public int find(int a) { return p[a] < 0 ? a : find(p[a]); }
    public void join(int a, int b) {
        a = find(a); b = find(b);
        history = new Node(a, p[a], b, p[b], components, history);
        if (a == b) return;
        components--;
        if(-p[a] > -p[b]){ a = a ^b ; b = a ^ b; a = a ^ b; }
        p[b] += p[a];
        p[a] = b;
    }
    public int size(int a) { return -p[find(a)]; }
    public void rollback() {
        components = history.components;
        p[history.a] = history.pa;
        p[history.b] = history.pb;
        history = history.next;
    }
    private static final class Node {
        final int a, pa, b, pb, components;
        final Node next;
        public Node(int a, int pa, int b, int pb, int components, Node next) {
            this.a = a;
            this.pa = pa;
            this.b = b;
            this.pb = pb;
            this.components = components;
            this.next = next;
        }
    } 
}
