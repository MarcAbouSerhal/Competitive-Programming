// replace X with type of property (or Tuple of properties)
// if X is primitive: replace null by value that will be ignored by op
class PersistentSegmentTree{
    private final ArrayList<Vertex> roots = new ArrayList<>();
    private final int n;
    // note that a[v] means vth version of a
    public PersistentSegmentTree(X[] a){ roots.add(build(a, 0, n = a.length - 1)); }
    // returns f(a[v][l...r]) (O(log(n)*T(op)))
    public final X get(int l, int r, int v){ return get(l, r, roots.get(v), 0, n); }
    // sets a[v][pos] to val (O(log(n)*T(op)))
    public final void set(int pos, X val, int v){ roots.set(v, update(roots.get(v), 0, n, pos, val)); }
    // creates new version of a[v] with a[v][pos] = val (O(log(n)*T(op)))
    public final void update(int v, int pos, X val){ roots.add(update(roots.get(v), 0, n, pos, val)); }
    // copies a[v]
    public final void copy(int v){ Vertex vx = roots.get(v); roots.add(new Vertex(vx.l, vx.r)); }
    private final X get(int l, int r, Vertex v, int lx, int rx){
        if(rx < l || lx > r) return null;
        if(lx >= l && rx <= r) return v.x;
        int mid = (lx + rx) >> 1;
        return op(get(l, r, v.l, lx, mid), get(l, r, v.r, mid + 1, rx));
    }
    private final Vertex update(Vertex v, int l, int r, int pos, X val){
        if(l == r) return new Vertex(val);
        int mid = (l + r) >> 1;
        if(pos <= mid) return new Vertex(update(v.l, l, mid, pos, val), v.r);
        else return new Vertex(v.l, update(v.r, mid + 1, r, pos, val));
    }
    private static final class Vertex{
        final Vertex l,r;
        final X x;
        public Vertex(X x){ this.x = x; l = r = null; }
        public Vertex(Vertex l, Vertex r){
            this.l = l;
            this.r = r;
            x = op(l.x, r.x);
        }
    }
    public final static Vertex build(X[] a, int l, int r){
        if(l == r) return new Vertex(a[l]);
        int mid = (l + r) >> 1;
        return new Vertex(build(a, l, mid), build(a, mid + 1, r));
    }
    // CHANGE THESE FUNCTIONS
    private static final X op(X a, X b){
        if(a == null) return b;
        if(b == null) return a;
        // define associative operation here (f(f(a,b),c)=f(a,f(b,c)))
    }
}
