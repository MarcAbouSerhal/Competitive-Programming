// O((n+m)log(n)) instead of typical O((n+m)log(m)) because decrease_key is used instead of inserting
// which keeps size of pq at most n 

import java.util.ArrayList;

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
    private static final long inf = Long.MAX_VALUE;
    final long[] d;
    final int[] p;
    public Dijkstra(int s, ArrayList<Edge>[] adj){
        int n = adj.length;
        d = new long[n];
        p = new int[n];
        p[s] = -1;
        for(int i = 1; i < n; ++i) d[i] = inf;
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
    // use this if there are states
    // node u at state i is at [u + i*n]
    public Dijkstra(int s, int initiaState, int states, ArrayList<Edge>[] adj){
        int n = adj.length, n_ = n * states;
        s += initiaState * n;
        d = new long[n_];
        p = new int[n_];
        p[s] = -1;
        for(int i = 1; i < n_; ++i) d[i] = inf;
        PQ pq = new PQ(s, d);
        int v;
        long w;
        while(pq.size != 0){
            if(d[pq.v[0]] == inf) break;
            int u = pq.v[0], state = u / n;
            u %= n;
            pq.remove_min();
            // Now do case work depending on state
            // for(Edge edge: adj[u]){
            //     v = edge.v;
            //     w = edge.w;
            //     if(d[v] > d[u] + w){
            //         p[v] = u;
            //         d[v] = d[u] + w;
            //         pq.decrease_key(v);
            //     }        
            // }
            // Remember to call pq.decrease_key(x) after updating p[x] and d[x]
        }
    }
}
class Edge{
    final int v;
    final long w;
    public Edge(int v, long w){ this.v = v; this.w = w; }
}
