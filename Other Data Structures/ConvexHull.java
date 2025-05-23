// add lines y = m * x + b where m's are monotonic, query x returns [m,b] that max/minimizes m * x + b
// if m's increasing: it returns [m[i],b[i]] that gives max(m*x+b)
// if m's decreasing: it returrns [m[i],b[i]] that gives min(m*x+b)
// Note: if m's order gives the opposite of what we want, insert [-m,-b] instead of [m,b]
class ConvexHull{
    private final static double inf = Double.MAX_VALUE;
    private final ArrayList<Line> lines;
    private final ArrayList<Double> left;
    public ConvexHull(){
        lines = new ArrayList<>();
        left = new ArrayList<>();
    }
    // adds the Line to the hull (O(1) amortized)
    public final void add(Line l){
        for(int i = lines.size() - 1; i >= 0; --i){
            long m2 = lines.get(i).m, b2 = lines.get(i).b;
            if(l.m == m2){
                if(l.b <= b2) return; 
                // make this <= if we don't care about a lower line (slopes are increasing)
                // and make it >= if we don't care about a higher line (slopes are decreasing)
                left.remove(i);
                lines.remove(i);
                continue;
            }
            double x = (b2 - l.b + 0.0) / (l.m - m2);
            if(left.get(i) < x){
                lines.add(l);
                left.add(x);
                return;
            }
            else{
                left.remove(i);
                lines.remove(i);
            }
        }
        lines.add(l);
        left.add(-inf);
    }
    // returns the line that minimizes/maximizes m*x + b (O(log(n)))
    public final Line query(double x){
        int lo = 0, hi = left.size() - 1;
        while(lo <= hi){
            int mid = (lo + hi) >> 1;
            if(x >= left.get(mid)) hi = mid;
            else lo = mid + 1;
        }
        return lines.get(lo);
    }
    // returns lines where lines[i] minimizes/maximizes m*x[i] + b where x is sorted (O(n + m))
    public final Line[] queries(double[] x) {
        int m = x.length, n = lines.size();
        Line[] ans = new Line[m];
        for(int i = 0, j = 0; i < n; ++i) {
            double r = i + 1 < n ? left.get(i + 1) : inf;
            while(j < m && x[j] <= r) ans[j++] = lines.get(i);
        }
        return ans;
    }
}
class Line{
    final long m, b;
    final int i;
    public Line(long m, long b){ this.m = m; this.b = b; i = -1; }
    public Line(long m, long b, int i){ this.m = m; this.b = b; this.i = i; }
}
