// O((n+m)log(n)) instead of typical O((n+m)log(m)) because decrease_key is used instead of inserting
// which keeps size of pq at most n 
class Dijkstra{
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
    final long[] d;
    final int[] p;
    public Dijkstra(int s, ArrayList<Edge>[] adj){
        int n = adj.length;
        d = new long[n];
        p = new int[n];
        p[s] = -1;
        for(int i = 1; i < n; ++i)
            d[i] = Long.MAX_VALUE;
        PQ pq = new PQ(s, d);
        while(pq.size != 0){
            if(d[pq.v[0]] == Long.MAX_VALUE) break;
            int u = pq.v[0];
            pq.remove_min();
            for(Edge edge: adj[u]){
                if(d[edge.x] > d[u] + edge.y){
                    p[edge.x] = u;
                    d[edge.x] = d[u] + edge.y;
                    pq.decrease_key(edge.x);
                }        
            }
        }
    }
}
class Edge{
    final int x;
    final long y;
    public Edge(int x, long y){ this.x = x; this.y = y; }
}
