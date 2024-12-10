class Dijkstra{
    static class PQ{
        int[] v;
        long[] d;
        int[] rev;
        int size;
        public PQ(int n, int s, long[] d){
            this.d = d;
            v = new int[n];
            rev = new int[n];
            size = n;
            v[0] = s;
            int iter = 1;
            for(int i=0; i<n; ++i)
                if(i != s){
                    v[rev[i] = iter++] = i;
                }
        }
        public void decrease_key(int u){
            int i = rev[u], j = (i-1)>>1;
            while(i>0 && d[v[j]] > d[v[i]]){
                v[i] ^= v[j]; v[j] ^= v[i]; v[i] ^= v[j];
                rev[v[i]] ^= rev[v[j]]; rev[v[j]] ^= rev[v[i]]; rev[v[i]] ^= rev[v[j]];
                i = j;
                j = (i-1)>>1;
            }
        }
        public void remove_min(){
            rev[v[0] = v[--size]] = 0;
            int i = 0, min = 0, l = 1, r = 2;
            while(l<size){
                if(d[v[l]]<d[v[min]]) min = l;
                if(r<size && d[v[r]]<d[v[min]]) min = r;
                if(i != min){
                    v[i] ^= v[min]; v[min] ^= v[i]; v[i] ^= v[min];
                    rev[v[i]] ^= rev[v[min]]; rev[v[min]] ^= rev[v[i]]; rev[v[i]] ^= rev[v[min]];
                    i = min;
                    l = (i<<1) + 1;
                    r = (i<<1) + 2;
                }
                else break;
            }
        }
    }
    long[] d;
    int[] p;
    public Dijkstra(int s, ArrayList<Pair>[] adj){
        d = new long[adj.length];
        p = new int[adj.length];
        p[s] = -1;
        Arrays.fill(d, Long.MAX_VALUE);
        d[s] = 0;
        PQ pq = new PQ(adj.length, s, d);
        while(pq.size != 0){
            if(d[pq.v[0]] == Long.MAX_VALUE) break;
            int u = pq.v[0];
            pq.remove_min();
            for(Pair edge: adj[u]){
                if(d[edge.x] > d[u] + edge.y){
                    p[edge.x] = u;
                    d[edge.x] = d[u] + edge.y;
                    pq.decrease_key(edge.x);
                }        
            }
        }
    }
}
class Pair{
    int x;
    long y;
    public Pair(int x,long y){this.x=x;this.y=y;}
}
