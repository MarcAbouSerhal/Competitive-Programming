class Eppstein{
    // (I think it's O(mlogm + klogk))
    public final static ArrayList<Long> kShortestPaths(ArrayList<Edge>[] adj, int start, int end, int k){
        final int n = adj.length;
        final ArrayList<Edge>[] rev_adj = new ArrayList[n];
        for(int u = 0; u < n; ++u) rev_adj[u] = new ArrayList<>();
        for(int u = 0; u < n; ++u)
            for(Edge e: adj[u])
                rev_adj[e.v].add(new Edge(u, e.w));
        final Dijkstra dijkstra = new Dijkstra(end, rev_adj);
        final long[] d = dijkstra.d; 
        final int[] p = dijkstra.p;
        if(d[start] == inf) return new ArrayList<>();
        final ArrayList<Integer>[] tree = new ArrayList[n];
        for(int u = 0; u < n; ++u) tree[u] = new ArrayList<>();
        for(int u = 0; u < n; ++u)
            if(p[u] != -1) tree[p[u]].add(u);
        final LeftistHeap[] h = new LeftistHeap[n];
        final Queue<Integer> q = new LinkedList<>(); q.add(end);
        long w;
        while(!q.isEmpty()){
            final int u = q.poll();
            boolean seen_p = false;
            for(Edge e: adj[u]){
                int v = e.v;
                w = e.w;
                if(d[v] == inf) continue;
                final long c = w + d[v] - d[u];
                if(!seen_p && v == p[u] && c == 0){
                    seen_p = true;
                    continue;
                }
                h[u] = LeftistHeap.insert(h[u], c, v);
            }
            for(int v: tree[u]){
                h[v] = h[u];
                q.add(v);
            }
        }
        final ArrayList<Long> ans = new ArrayList<>(k); ans.add(d[start]);
        if(h[start] == null) return ans;
        final PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(d[start] + h[start].key, h[start]));
        while(!pq.isEmpty() && ans.size() < k){
            Pair polled = pq.poll();
            long cd = polled.d; LeftistHeap ch = polled.h;
            ans.add(cd);
            if(h[ch.value] != null) pq.add(new Pair(cd + h[ch.value].key, h[ch.value]));
            if(ch.left != null) pq.add(new Pair(cd + ch.left.key - ch.key, ch.left));
            if(ch.right != null) pq.add(new Pair(cd + ch.right.key - ch.key, ch.right));
        }
        return ans;
    }
    private final static long inf = Long.MAX_VALUE;
    private final static class PQ{
        private final int[] v;
        private final long[] d;
        private final int[] rev;
        private int size;
        public PQ(int s, long[] d){
            this.d = d;
            v = new int[size = d.length];
            rev = new int[size];
            v[0] = s;
            for(int i = 0; i < s; ++i)
                v[rev[i] = i + 1] = i;
            for(int i = s + 1; i < size; ++i)
                v[rev[i] = i] = i;
        }
        public final void decrease_key(int x){
            int i = rev[x], j = (i - 1) / 2, y = v[j];
            while(i != 0 && d[y] > d[x]){
                v[i] ^= v[j]; v[j] ^= v[i]; v[i] ^= v[j];
                rev[x] ^= rev[y]; rev[y] ^= rev[x]; rev[x] ^= rev[y];
                i = j;
                j = (i - 1) / 2;
                y = v[j];
            }
        }
        public final void remove_min(){
            rev[v[0] = v[--size]] = 0;
            int i = 0, min = 0, l = 1, r = 2;
            while(l < size){
                if(d[v[l]] < d[v[min]]) min = l;
                if(r < size && d[v[r]] < d[v[min]]) min = r;
                if(i != min){
                    v[i] ^= v[min]; v[min] ^= v[i]; v[i] ^= v[min];
                    rev[v[i]] ^= rev[v[min]]; rev[v[min]] ^= rev[v[i]]; rev[v[i]] ^= rev[v[min]];
                    i = min;
                    l = (i << 1) + 1;
                    r = (i + 1) << 1;
                }
                else break;
            }
        }
    }
    private static final class Dijkstra{
        final long[] d;
        final int[] p;
        public Dijkstra(int s, ArrayList<Edge>[] adj){
            int n = adj.length;
            d = new long[n];
            p = new int[n];
            int i = 0;
            for(; i < s; ++i) d[i] = inf;
            for(i = s + 1; i < n; ++i) d[i] = inf;
            p[s] = -1;
            PQ pq = new PQ(s, d);
            int v;
            long w;
            while(pq.size != 0){
                if(d[pq.v[0]] == inf) break;
                int u = pq.v[0];
                pq.remove_min();
                for(Edge edge: adj[u]){
                    v = edge.v;
                    w = edge.w;
                    if(d[v] > d[u] + w){
                        p[v] = u;
                        d[v] = d[u] + w;
                        pq.decrease_key(v);
                    }        
                }
            }
        }
    }
    private final static class LeftistHeap{
        final int rank, value;
        final long key;
        final LeftistHeap left, right;
        public LeftistHeap(int rank_, long key_, int value_, LeftistHeap left_, LeftistHeap right_){
            rank = rank_;
            key = key_;
            value = value_;
            left = left_;
            right = right_;
        }
        public static final LeftistHeap insert(LeftistHeap a, long k, int v){
            if(a == null || k <= a.key) return new LeftistHeap(1, k, v, a, null);
            else{
                LeftistHeap l = a.left, r = insert(a.right, k, v);
                if(l == null || r.rank > l.rank){
                    LeftistHeap temp = l;
                    l = r;
                    r = temp;
                }
                return new LeftistHeap(r == null ? 0 : r.rank + 1, a.key, a.value, l, r);
            }
        }
    }
    private final static class Pair implements Comparable<Pair>{
        final long d;
        final LeftistHeap h;
        public Pair(long d_, LeftistHeap h_){ d = d_; h = h_; }
        public int compareTo(Pair other){ return Long.compare(d, other.d); };
    }
}
class Edge{
    final int v;
    final long w;
    public Edge(int v, long w){ this.v = v; this.w = w; }
}
