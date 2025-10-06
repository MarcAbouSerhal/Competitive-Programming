class Treap {
    int cnt, priority;
    long value, lazy = id, rangeOp;
    Treap l, r;
    boolean isLazy = false, rev = false;
    private static final Random rand = new Random();
    public Treap(long value) {
        cnt = 1;
        this.value = value;
        priority = rand.nextInt();
        rangeOp = value;
    }
    public Treap(long[] a) { this(a, 0, a.length - 1); }
    private Treap(long[] a, int la, int ra) {
        priority = rand.nextInt();
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
    public static final Treap merge(Treap l, Treap r) {
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
    public static final TreapCollection split(Treap t, int i) { return split(t, i, 0); }
    // returns (t[, l - 1], t[l, r], t[r + 1,]) (O(log(n)))
    public static final TreapCollection cutRange(Treap t, int l, int r) { TreapCollection ans = split(t, l - 1), temp = split(ans.t2, r); ans.t2 = temp.t1; ans.t3 = temp.t2; return ans; }
    public final void reverse() { isLazy = true; rev ^= true; }
    // apply update x
    public final void update(long x) { isLazy = true; /* apply x to lazy */ }
    // private methods
    private static final TreapCollection split(Treap t, int i, int add) {
        if(t == null) return new TreapCollection(null, null, null);
        push(t);
        int cur_key = add + cnt(t.l);
        TreapCollection temp;
        if(i < cur_key) {
            temp = split(t.l, i, add);
            t.l = temp.t2;
            temp.t2 = t;
        }
        else {
            temp = split(t.r, i, add + 1 + cnt(t.l));
            t.r = temp.t1;
            temp.t1 = t;
        }
        upd_cnt(t); upd_op(t);
        return temp;
    } 
    private static final int cnt(Treap t) { return t == null ? 0 : t.cnt; }
    private static final void upd_cnt(Treap t) { if(t != null) t.cnt = 1 + cnt(t.l) + cnt(t.r); }
    private static final long rangeOp(Treap t) { return t == null ? 0 : t.rangeOp; }
    private static final void upd_op(Treap t) { if(t != null) t.rangeOp = op(op(t.value, rangeOp(t.l)), rangeOp(t.r)); }
    private static final void heapify(Treap t) {
        if(t == null) return;
        Treap max = t;
        if(t.l != null && t.l.priority > max.priority) max = t.l;
        if(t.r != null && t.r.priority > max.priority) max = t.r;
        if(max != t) {
            int temp = t.priority;
            t.priority = max.priority;
            max.priority = temp;
            heapify(max);
        }
    }
    // CHANGE THESE
    static final private int noUpdate = 0, id = 0;
    private static final void push(Treap t) {
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
    private static final long op(long a, long b) { /* define associative operation here (op(op(a, b), c) = op(a, op(b, c))) */ }
}
class TreapCollection {
    Treap t1, t2, t3;
    public TreapCollection(Treap t1, Treap t2, Treap t3) { this.t1 = t1; this.t2 = t2; this.t3 = t3; }
    public Treap merge() { return t3 == null ? Treap.merge(t1, t2) : Treap.merge(t1, Treap.merge(t2, t3)); }
}
