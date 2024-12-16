// O((n+m)log(n)) instead of typical O((n+m)log(m)) because I use decrease_key which keeps pq size <=n 
class Dijkstra{
    static class PQ{n
        int[] v;
        long[] d;
        int[] rev;
        int size;
        public PQ(int s, long[] d){
            this.d = d;
            size = d.length;
            v = new int[size];
            rev = new int[size];
            v[0] = s;
            int iter = 1;
            for(int i=0; i<size; ++i)
                if(i != s)
                    v[rev[i] = iter++] = i;
                
        }
        public void decrease_key(int x){
            int i = rev[x], j = (i-1)/2, y = v[j];
            while(i!=0 && d[y] > d[x]){
                v[i] ^= v[j]; v[j] ^= v[i]; v[i] ^= v[j];
                rev[x] ^= rev[y]; rev[y] ^= rev[x]; rev[x] ^= rev[y];
                i = j;
                j = (i-1)/2;
                y = v[j];
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
        int n = adj.length;
        d = new long[n];
        p = new int[n];
        p[s] = -1;
        for(int i=1; i<n; ++i)
            d[i] = Long.MAX_VALUE;
        PQ pq = new PQ(s, d);
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
