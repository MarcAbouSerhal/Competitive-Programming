// use this for Mo's algorithm and sort by query.index
class Query{
    int l,r,i,extra=-1;
    long index;
    public Query(int l, int r, int i) {
        this.l = l;
        this.r = r;
        this.i = i;
        index = hilbert_order(l,r);
    }
    public Query(int l, int r, int extra, int i) {
        this(l,r,i);
        this.extra = extra;
    }
    public static long hilbert_order(int x, int y){
        int logn = lg2(1+(y<<1))|1;
        int maxn = (1<<logn)-1;
        long res = 0;
        for(int s = 1<<(logn-1); s!=0 ; s>>=1){
            boolean rx = (x&s)!=0, ry=(y&s)!=0;
            res=(res<<2)|(rx?ry?2:1:ry?3:0);
            if(!rx){
                if(ry){
                    x^=maxn; y^=maxn;
                }
                x=x^y;y=x^y;x=x^y;
            }
        }
        return res;
    }
    public static int lg2(int x){
        for(int i=21; i>0; --i)
            if((x&(1<<i))>0) return i;
        return 0;
    }
}
