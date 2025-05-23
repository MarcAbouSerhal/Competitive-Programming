class Treap {
    int value, cnt, priority;
    long lazy = 0, rangeOp;
    Treap l, r;
    boolean isLazy = false, rev = false;
    private static final Random rand = new Random();
    // O(1)
    public Treap(int value) {
        cnt = 1;
        this.value = value;
        priority = rand.nextInt();
        rangeOp = value;
    }
    // O(n)
    public Treap(int[] a) { this(a, 0, a.length - 1); }
    // returns l + r O(log(max(n, m)))
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
    // returns pair where pair.t1 = t[0...key] and pair.t2 = t[key + 1...n - 1] (O(log(n)))
    public static final TreapPair split(Treap t, int key) { return split(t, key, 0); }
    // returns t as an array (O(n))
    public static final int[] travel(Treap t) {
        tree = new int[cnt(t)];
        tick = 0;
        visit(t);
        return tree;
    }
    // reverses t[l...r] (O(log(n)))
    public static final Treap reverse(Treap t, int l, int r) {
        TreapPair s = Treap.split(t, r);
        Treap after = s.t2; 
        s = split(s.t1, l - 1);
        Treap at = s.t2;
        at.isLazy = true;
        at.rev ^= true;
        return merge(s.t1, merge(at, after));
    }
    // applies x to t[l...r] (O(log(n)))
    public static final Treap update(Treap t, int l, int r, int x) {
        TreapPair s = Treap.split(t, r);
        Treap after = s.t2; 
        s = split(s.t1, l - 1);
        Treap at = s.t2;
        at.isLazy = true;
        // apply update to at.lazy
        at.lazy += x;
        return merge(s.t1, merge(at, after));
    }
    // cuts [l...r] and adds it to the left (O(log(n)))
    public static final Treap cutRangeToStart(Treap t, int l, int r) {
        TreapPair s = Treap.split(t, r);
        Treap after = s.t2; 
        s = split(s.t1, l - 1);
        return merge(merge(s.t2, s.t1), after);
    }
    // cuts [l...r] and adds it to the right (O(log(n)))
    public static final Treap cutRangeToEnd(Treap t, int l, int r) {
        TreapPair s = Treap.split(t, r);
        Treap after = s.t2; 
        s = split(s.t1, l - 1);
        return merge(merge(s.t1, after), s.t2);
    }
    // private methods
    private static final TreapPair split(Treap t, int key, int add) {
        if(t == null) return new TreapPair(null, null);
        push(t);
        int cur_key = add + cnt(t.l);
        TreapPair temp;
        if(key < cur_key) {
            temp = split(t.l, key, add);
            t.l = temp.t2;
            temp.t2 = t;
        }
        else {
            temp = split(t.r, key, add + 1 + cnt(t.l));
            t.r = temp.t1;
            temp.t1 = t;
        }
        upd_cnt(t); upd_op(t);
        return temp;
    } 
    private Treap(int[] a, int la, int ra) {
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
        rangeOp = op(op(value, rangeOp(l)), rangeOp(r));
    }
    private static final void push(Treap t) {
        if(t != null && t.isLazy) {
            // apply change to values at (don't reset lazy values)
            t.rangeOp += t.lazy * t.cnt;
            if(t.rev) {
                Treap temp = t.l;
                t.l = t.r;
                t.r = temp;
            }
            // push update to children
            if(t.l != null) {
                t.l.isLazy = true;
                t.l.lazy += t.lazy;
                t.l.rev ^= t.rev;
            }
            if(t.r != null) {
                t.r.isLazy = true;
                t.r.lazy += t.lazy;
                t.r.rev ^= t.rev;
            }
            // reset values
            t.isLazy = false;
            t.rev = false;
            t.lazy = 0;
        }
    }
    private static final long op(long a, long b) {
        // define associative operation here (op(op(a, b), c) = op(a, op(b, c)))
        return a + b;
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
    private static int tick;
    private static int[] tree;
    private static final void visit(Treap t) {
        if(t != null) {
            push(t);
            visit(t.l);
            tree[tick++] = t.value;
            visit(t.r);
        }
    }
}
class TreapPair {
    Treap t1, t2;
    public TreapPair(Treap t1, Treap t2) { this.t1 = t1; this.t2 = t2; }
}
