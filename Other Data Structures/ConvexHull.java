// add lines y = m * x + b where m's are monotonic, query x returns [m,b] that max/minimizes m * x + b
// if m's increasing: it returns [m[i],b[i]] that gives max(m*x+b)
// if m's decreasing: it returrns [m[i],b[i]] that gives min(m*x+b)
// Note: if m's order gives the opposite of what we want, insert [-m,-b] instead of [m,b]
class ConvexHull{
    private final static class Range{
        double l, r;
        public Range(double l, double r){ this.l = l; this.r = r; }
    }
    private final static double inf = Double.MAX_VALUE;
    private final ArrayList<Line> lines;
    private final ArrayList<Range> ranges;
    public ConvexHull(){
        lines = new ArrayList<>();
        ranges = new ArrayList<>();
    }
    // adds the line [m, b] to the hull (O(1) amortized)
    public final void add(long m, long b){
        if(lines.isEmpty()){
            lines.add(new Line(m, b));
            ranges.add(new Range(-inf, inf));
        }
        else{
            for(int i = lines.size() - 1; i >= 0; --i){
                long m2 = lines.get(i).m, b2 = lines.get(i).b;
                if(m == m2){
                    if(b <= b2) return; 
                    // make this <= if we don't care about a lower line (slopes are increasing)
                    // and make it >= if we don't care about a higher line (slopes are decreasing)
                    ranges.remove(i);
                    lines.remove(i);
                    continue;
                }
                double x = (b2 - b + 0.0) / (m - m2);
                if(ranges.get(i).l < x){
                    ranges.get(i).r = x;
                    lines.add(new Line(m,b));
                    ranges.add(new Range(x,inf));
                    return;
                }
                else{
                    ranges.remove(i);
                    lines.remove(i);
                }
            }
        }
    }
    // returns the line that minimizes/maximizes m * x + b (O(log(# lines)))
    public final Line query(double x){
        int lo = 0, hi = ranges.size() - 1;
        double r,l;
        while(lo <= hi){
            int mid = (lo + hi) >> 1;
            l = ranges.get(mid).l; r = ranges.get(mid).r;
            if(l <= x && x <= r) return lines.get(mid);  
            else if(x < l) hi = mid - 1;
            else lo = mid + 1;
        }
        return null;
    }
}
class Line{
    final long m, b;
    public Line(long m, long b){ this.m = m; this.b = b; }
}