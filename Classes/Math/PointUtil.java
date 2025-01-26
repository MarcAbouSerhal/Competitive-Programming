import java.util.*;
class PointUtil{
    // returns {p,q} that minimize euclidean distance between p and q (O(nlog(n)))
    public static final Point[] closestPairEuclidean(Point[] pts){
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
    // returns {p,q} that maximize manhattan distance between p and q (O(nlog(n)))
    public static final Point[] farthestPairManhattan(Point[] pts){
        Arrays.sort(pts, (a, b) -> Long.compare(a.x + a.y, b.x + b.y));
        Point p1 = pts[0], q1 = pts[pts.length - 1];
        Arrays.sort(pts, (a, b) -> Long.compare(a.x - a.y, b.x - b.y));
        Point p2 = pts[0], q2 = pts[pts.length - 1];
        if(abs(p1.x - q1.x) + abs(p1.y - q1.y) > abs(p2.x - q2.x) + abs(p2.y - q2.y)) return new Point[] {p1, q1};
        else return new Point[] {p2, q2};
    }
    private static final int comp(Point a, Point b){
        return a.x == b.x ? Long.compare(a.y, b.y) : Long.compare(a.x, b.x);
    }
    private static final long square(long x){
        return x * x;
    }
    private static final long abs(long x){
        return x < 0 ? -x : x;
    }
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
