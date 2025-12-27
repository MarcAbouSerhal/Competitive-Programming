class Treap {
    private static final Random rand = new Random(42);
    int cnt;
    long priority, value, lazy = noUpdate, rangeOp;
    Treap l, r;
    boolean isLazy = false, rev = false;
    Treap(long value) {
        cnt = 1;
        this.value = value;
        priority = rand.nextLong();
        rangeOp = value;
    }
    Treap(long[] a) { this(a, 0, a.length - 1); }
    Treap(long[] a, int la, int ra) {
        priority = rand.nextLong();
        if(la == ra) value = a[la];
        else if(la + 1 == ra) {
            value = a[la];
            r = new Treap(a, ra, ra);
        }
        else {
            int mid = (la + ra) >> 1;
            value = a[mid];
            l = new Treap(a, la, mid - 1);
            r = new Treap(a, mid + 1, ra);
        }
        heapify(this);
        cnt = 1 + cnt(l) + cnt(r);
        rangeOp = op(op(rangeOp(l), value), rangeOp(r));
    }
    static Treap merge(Treap l, Treap r) {
        push(l); push(r);
        Treap t;
        if(l == null || r == null) t = l == null ? r : l;
        else if(l.priority > r.priority) {
            l.r = merge(l.r, r);
            t = l;
        }
        else {
            r.l = merge(l, r.l);
            t = r;
        }
        upd_cnt(t); upd_op(t);
        return t;
    }
    // returns (t[, i], t[i + 1,]) (O(log(n)))
    static Treap[] split(Treap t, int i) { return split(t, i, 0); }
    void reverse() { isLazy = true; rev ^= true; }
    // apply update x
    void update(long x) { isLazy = true; /* apply x to lazy */ }
    // private methods
    private static Treap[] split(Treap t, int i, int add) {
        if(t == null) return new Treap[] {};
        push(t);
        int cur_key = add + cnt(t.l);
        Treap[] temp;
        if(i < cur_key) {
            temp = split(t.l, i, add);
            t.l = temp[1];
            temp[1] = t;
        }
        else {
            temp = split(t.r, i, add + 1 + cnt(t.l));
            t.r = temp[0];
            temp[0] = t;
        }
        upd_cnt(t); upd_op(t);
        return temp;
    } 
    private static int cnt(Treap t) { return t == null ? 0 : t.cnt; }
    private static void upd_cnt(Treap t) { if(t != null) t.cnt = 1 + cnt(t.l) + cnt(t.r); }
    private static long rangeOp(Treap t) { return t == null ? id : t.rangeOp; }
    private static void upd_op(Treap t) { if(t != null) t.rangeOp = op(op(t.value, rangeOp(t.l)), rangeOp(t.r)); }
    private static void heapify(Treap t) {
        if(t == null) return;
        Treap max = t;
        if(t.l != null && t.l.priority > max.priority) max = t.l;
        if(t.r != null && t.r.priority > max.priority) max = t.r;
        if(max != t) {
            long temp = t.priority;
            t.priority = max.priority;
            max.priority = temp;
            heapify(max);
        }
    }
    // CHANGE THESE
    private static final long noUpdate = 0, id = 0;
    private static void push(Treap t) {
        if(t != null && t.isLazy) {
            // apply t.lazy at t.rangeOp and t.value
            if(t.rev) {
                Treap temp = t.l;
                t.l = t.r;
                t.r = temp;
            }
            // push update to children
            if(t.l != null) {
                t.l.isLazy = true;
                // apply t.lazy to t.l.lazy
                t.l.rev ^= t.rev;
            }
            if(t.r != null) {
                t.r.isLazy = true;
                // apply t.lazy to t.r.lazy
                t.r.rev ^= t.rev;
            }
            t.isLazy = false;
            t.rev = false;
            t.lazy = noUpdate;
        }
    }
    private static long op(long a, long b) { /* define associative operation here (op(op(a, b), c) = op(a, op(b, c))) */ }
}