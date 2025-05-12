class PointsUtil{
    // returns {p, q} that minimize euclidean distance between p and q in pts (O(nlog(n)))
    public static final Point[] closestPairEuclidean(Point[] pts) {
        final int n = pts.length;
        Arrays.sort(pts, (a, b) -> comp(a, b));
        final TreeSet<Point> s = new TreeSet<>((a, b) -> comp(a, b));
        long bestDist = Long.MAX_VALUE;
        final Point[] result = new Point[2];
        int j = 0;
        for(int i = 0; i < n; ++i){
            long d = (long)Math.ceil(Math.sqrt(bestDist));
            while(j < n && pts[i].x - pts[j].x >= d){
                s.remove(new Point(pts[j].y, pts[j].x, 0));
                ++j;
            }
            Point p1 = s.ceiling(new Point(pts[i].y - d, pts[i].x, 0));
            final Point p2 = s.higher(new Point(pts[i].y + d, pts[i].x, 0));
            while(p1 != null && (p2 == null || p1.x != p2.x || p1.y != p2.y)){
                long dist = square(pts[i].x - p1.y) + square(pts[i].y - p1.x);
                if(dist < bestDist){
                    bestDist = dist;
                    result[0] = pts[i];
                    result[1] = new Point(p1.y, p1.x, p1.i);
                }
                p1 = s.higher(p1);
            }
            s.add(new Point(pts[i].y, pts[i].x, pts[i].i));
        }
        return result;
    }
    // returns convex hull of pts in clockwise order starting from leftmost bottommost point (O(nlog(n)))
    public static final ArrayList<Point> convexHull(Point[] pts, boolean includeCollinear) {
        int n = pts.length;
        Point p0 = pts[0];
        for(int i = 1; i < n; ++i) if(comp(pts[i], p0) < 0) p0 = pts[i];
        final Point p = p0;
        Arrays.sort(pts, (a, b) -> {
            int o = orientation(p, a, b);
            if(o == 0) return Long.compare(square(p.x - a.x) + square(p.y - a.y), 
                                            square(p.x - b.x) + square(p.y - b.y));
            return o;
        });
        if(includeCollinear) {
            int i = n - 1;
            while(i >= 0 && collinear(p0, pts[i], pts[n - 1])) --i;
            for(int j = i + 1; j < n && j << 1 < n - 1; ++j) {
                Point temp = pts[j];
                pts[j] = pts[n - 1 - j];
                pts[n - 1 - j] = temp;
            }
        }
        ArrayList<Point> st = new ArrayList<>(n);
        for(int i = 0; i < n; ++i) {
            while(st.size() > 1 && !cw(st.get(st.size() - 2), st.get(st.size() - 1), pts[i], includeCollinear)) st.removeLast();
            st.add(pts[i]);
        }
        if(!includeCollinear && st.size() == 2 && st.get(0) == st.get(1)) st.removeLast();
        return st;
    }
    // returns {p, q} that maximize euclidean distance between p and q in pts (O(nlog(n)))
    public static final Point[] farthestPairEuclidean(Point[] pts) {
        ArrayList<Point> hull = convexHull(pts, false);
        int n = hull.size();
        if(n == 1) return null;
        if(n == 2) return new Point[] {hull.get(0), hull.get(1)};
        long maxDist = 0;
        Point[] res = {hull.get(0), hull.get(1)};
        for(int i = 0, j = 1; i < n; ++i) {
            while(squareDist(hull.get(i), hull.get((j + 1) % n)) > squareDist(hull.get(i), hull.get(j))) j = (j + 1) % n;
            long dist = squareDist(hull.get(i), hull.get(j));
            if(dist > maxDist) {
                res[0] = hull.get(i);
                res[1] = hull.get(j);
                maxDist = dist;
            }
        }
        return res;
    }
    // returns {p, q} that maximize manhattan distance between p and q in pts (O(nlog(n)))
    public static final Point[] farthestPairManhattan(Point[] pts) {
        Arrays.sort(pts, (a, b) -> Long.compare(a.x + a.y, b.x + b.y));
        Point p1 = pts[0], q1 = pts[pts.length - 1];
        Arrays.sort(pts, (a, b) -> Long.compare(a.x - a.y, b.x - b.y));
        Point p2 = pts[0], q2 = pts[pts.length - 1];
        if(abs(p1.x - q1.x) + abs(p1.y - q1.y) > abs(p2.x - q2.x) + abs(p2.y - q2.y)) return new Point[] {p1, q1};
        else return new Point[] {p2, q2};
    }
    private static final boolean cw(Point a, Point b, Point c, boolean includeCollinear) {
        int o = orientation(a, b, c);
        return o < 0 || (includeCollinear && o == 0);
    }
    private static final boolean collinear(Point a, Point b, Point c) { return orientation(a, b, c) == 0; }
    private static final int orientation(Point a, Point b, Point c) {
        Long v = a.x * (b.y - c.y) + b.x * (c.y - a.y) + c.x * (a.y - b.y);
        return v < 0 ? -1 : v > 0 ? 1 : 0; // -1 for clockwise, 1 for counter-clockwise
    }
    private static final int comp(Point a, Point b){ return a.x == b.x ? Long.compare(a.y, b.y) : Long.compare(a.x, b.x); }
    private static final long square(long x) { return x * x; } 
    private static final long abs(long x) { return x < 0 ? -x : x; }
    private static final long squareDist(Point p, Point q) { return square(p.x - q.x) + square(p.y - q.y); }
}
class Point{
    final long x, y;
    final int i;
    public Point(long x, long y, int i){
        this.x = x;
        this.y = y;
        this.i = i;
    }
}