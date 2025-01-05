class ZeroOneSSSP{
    private static final class Node{
        final int u;
        Node next;
        public Node(int u, Node next){
            this.u = u; this.next = next;
        }
    }
    private static final class DQ{
        Node head, tail;
        public DQ(){}
        public final void addBack(int u){
            head = new Node(u, head);
            if(tail == null) tail = head;
        }
        public final void addFront(int u){
            if(head == null) addBack(u);
            else tail = (tail.next = new Node(u, null));
        }
        public final int pop(){
            int u = head.u;
            head = head.next;
            return u;
        }
    }
    private static final int inf = Integer.MAX_VALUE;
    final int[] d, p;
    public ZeroOneSSSP(int s, ArrayList<Edge>[] adj){
        final int n = adj.length;
        d = new int[n];
        p = new int[n];
        for(int i = 0; i < s; ++i) d[i] = inf;
        for(int i = s + 1; i < n; ++i) d[i] = inf;
        p[0] = -1;
        final DQ q = new DQ();
        q.addBack(s);
        while(q.head != null){
            int u = q.pop();
            for(Edge e: adj[u])
                if(d[u] + e.w < d[e.v]){
                    p[e.v] = u;
                    d[e.v] = d[u] + e.w;
                    if(e.w == 1) q.addFront(e.v);
                    else q.addBack(e.v);
                }
        }
    } 
}
class Edge{
    final int v, w;
    public Edge(int v, int w){ this.v = v; this.w = w; }
}
