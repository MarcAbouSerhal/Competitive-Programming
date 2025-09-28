class MoUpdates{
    // extra stuff here
    private static int[] a;
    private final ArrayList<Query> queries = new ArrayList<>(); 
    private final ArrayList<Update> updates = new ArrayList<>();
    public MoUpdates(int[] a) {
        // extra stuff here
        MoUpdates.a = a;
    }
    public void query(int l, int r) { queries.add(new Query(l, r, updates.size(), queries.size())); }
    public void update(int i, int x) { updates.add(new Update(i, x)); }
    // O(q.n^(2/3).(T(add or remove or finding answer) + T(update)))
    public final int[] solve(){
        // finding oldX
        for(Update upd: updates){
            upd.oldX = a[upd.i];
            a[upd.i] = upd.x;
        }
        for(int i = updates.size() - 1; i >= 0; --i) a[updates.get(i).i] = updates.get(i).oldX;
        Collections.sort(queries, (x, y) -> x.lb == y.lb ? (x.rb == y.rb ? x.t - y.t : x.rb - y.rb) : x.lb - y.lb);
        int[] res = new int[queries.size()];
        int curr_l = 0, curr_r = -1, t = 0;
        for(Query q: queries){
            while(curr_r < q.r) add(++curr_r);
            while(curr_l > q.l) add(--curr_l);
            while(curr_r > q.r) remove(curr_r--);
            while(curr_l < q.l) remove(curr_l++);
            while(t < q.t){
                int i = updates.get(t).i, x = updates.get(t).x;
                if(curr_l <= i && i <= curr_r){
                    remove(i);
                    a[i] = x;
                    add(i);
                }
                else a[i] = x; 
                ++t;
            }
            while(t > q.t){
                --t;
                int i = updates.get(t).i, x = updates.get(t).oldX;
                if(curr_l <= i && i <= curr_r){
                    remove(i);
                    a[i] = x;
                    add(i);
                }
                else a[i] = x; 
            }
            res[q.i] = 1; // get answer of this range
        }
        return res;
    }
    public final static void add(int i){ /* adds ith element */ }
    public final static void remove(int i){/* removes ith element */ }
    private static final class Query{
        // this is a good block size for 1e5
        // generally try (2*n*n)^(1/3) then n^(2/3) or something in between
        static private final int b = 2714;
        final int l, r, t, i, lb, rb;
        public Query(int l, int r, int t, int i){
            this.l = l;
            this.r = r;
            this.t = t;
            this. i = i;
            lb = l / b;
            rb = r / b;
        }
    }
    private static final class Update{
        final int i, x;
        int oldX;
        public Update(int i, int x){
            this.i = i;
            this.x = x;
        }
    }
}