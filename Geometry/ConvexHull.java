class ConvexHull {
    // returns convex hull of pts in clockwise order starting from leftmost bottommost point (O(nlog(n)))
    public static final ArrayList<Point> convexHull(Point[] pts, boolean includeCollinear) {
        Arrays.sort(pts, (x, y) -> comp(x, y));
        int n = pts.length, tol = includeCollinear ? 0 : 1;
        ArrayList<Point> hull = new ArrayList<>();
        for(int i = 0; i < n; ++i) {
            while(hull.size() > 1 && cross(hull.get(hull.size() - 2), hull.get(hull.size() - 1), pts[i]) < tol) hull.remove(hull.size() - 1);
            hull.add(pts[i]);
        }
        int k = hull.size();
        for(int i = n - 2; i > 0; --i) {
            while(hull.size() > k && cross(hull.get(hull.size() - 2), hull.get(hull.size() - 1), pts[i]) < tol) hull.remove(hull.size() - 1);
            hull.add(pts[i]);
        }
        while(hull.size() > k && cross(hull.get(hull.size() - 2), hull.get(hull.size() - 1), pts[0]) < tol) hull.remove(hull.size() - 1);
        return hull;
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
    private static final long cross(Point a, Point b, Point c) { return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y); }
    private static final int comp(Point a, Point b){ return a.x == b.x ? Long.compare(a.y, b.y) : Long.compare(a.x, b.x); }
    private static final long square(long x) { return x * x; } 
    private static final long squareDist(Point p, Point q) { return square(p.x - q.x) + square(p.y - q.y); }
}