class ClosestPairEuclidean {
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
    private static final int comp(Point a, Point b){ return a.x == b.x ? Long.compare(a.y, b.y) : Long.compare(a.x, b.x); }
    private static final long square(long x) { return x * x; } 
}