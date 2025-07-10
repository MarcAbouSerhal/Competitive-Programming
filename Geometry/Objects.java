class Point {
    final double x, y;
    public Point(double x, double y) { this.x = x; this.y = y; }
    public Point(Point p) { this(p.x, p.y); }
    public final double distanceTo(Point other) { return Math.sqrt((x - other.x) * (x - other.x) + (y - other.y) * (y - other.y)); }
    public final static Point translate(Point p, Vector v) { return new Point(p.x + v.x, p.y + v.y); }
    public final static Point intersection(Line l1, Line l2) {
        int intersects = l1.intersects(l2);
        if(intersects == 0) return null;
        else if(intersects == 2) return l1.randomPoint();
        else {
            double denom = l1.a * l2.b - l1.b * l2.a;
            return new Point((l1.c * l2.b - l2.c * l1.b) / denom, (l1.a * l2.c - l2.a * l1.c) / denom);
        }
    }
}
class Vector {
    final double x, y;
    public Vector(double x, double y) { this.x = x; this.y = y; }
    public Vector(Vector v) { this(v.x, v.y); }
    public Vector(Point end) { this(end.x, end.y); }
    public Vector(Point start, Point end) { this(end.x - start.x, end.y - start.y); }
    public final double norm() { return Math.sqrt(x * x + y * y); }
    public final double dot(Vector other) { return x * other.x + y * other.y; }
    public final Vector add(Vector other) { return new Vector(x + other.x, y + other.y); }
    public final static double det(Vector a, Vector b) { return a.x * b.y - a.y * b.x; }
    public final static Vector minus(Vector v) { return new Vector(-v.x, -v.y); }
}
class Line {
    final double a, b, c; // a.x + b.y = c
    final int i;
    public Line(double a, double b, double c, int i) { this.a = a; this.b = b; this.c = c; this.i = i; }
    public Line(double a, double b, double c) { this(a, b, c, -1); }
    public Line(Point p1, Point p2, int i) { a = p1.y - p2.y; b = p2.x - p1.x; c = a * p1.x + b * p1.y; this.i=i; }
    public Line(Point p1, Point p2) { this(p1, p2, -1); }
    public Line(Point p, Vector v, int i) { a = -v.y; b = v.x; c = a * p.x + b * p.y; this.i = i; }
    public Line(Point p, Vector v) { this(p, v, -1); }
    // 0 if no intersection, 1 if 1 intersection, 2 if infinitely many
    public final int intersects(Line other) { return a * other.b != other.a * b ? 1 : a * other.c == other.a * c && b * other.c == other.b * c ? 2 : 0; }
    public final double distanceFrom(Point p) { return Math.abs(a * p.x + b * p.y - c) / Math.sqrt(a * a + b * b); }
    public final Point randomPoint() { return b == 0 ? new Point(c / a, 0) : new Point(0, c / b); }
    public final Vector direction() { return new Vector(b, -a); }
} 
class SimplePolygon {
    final ArrayList<Point> pts;
    final int n;
    public SimplePolygon(ArrayList<Point> pts) { this.pts = pts; n = pts.size(); }
    // returns area of polygon (O(n))
    public final double area() {
        if(n <= 2) return 0;
        double ans = pts.get(n - 1).x * pts.get(0).y - pts.get(0).x * pts.get(n - 1).y;
        for(int i = 0; i < n - 1; ++i) ans += pts.get(i).x * pts.get(i + 1).y - pts.get(i + 1).x * pts.get(i).y;
        return Math.abs(ans) / 2;
    }
    // -1 if q is outside, 0 if q is on the boundary, 1 if q is inside (O(n))
    public final int position(Point q) {
        if(n <= 1) return -1;
        int wn = 0;
        for (int i = 0, j = n - 1; i < n; j = i++) {
            double t = cross(pts.get(j), pts.get(i), q);
            if (t == 0 && between(pts.get(j), q, pts.get(i))) return 0;
            if (pts.get(j).y <= q.y && q.y < pts.get(i).y) { if (t > 0) ++wn; }
            else if (pts.get(j).y > q.y && q.y >= pts.get(i).y) { if (t < 0) --wn; }
        }
        return wn == 0 ? -1 : 1;
    }
    // returns polygon of points q, where [qc] intersects p once for every point c on [ab] (O(n)) 
    // (Make sure p.pts are in CCW order, and for every segment of p is either fully visible or fully invisible from [ab])
    public static final SimplePolygon visible(SimplePolygon p, Point a, Point b) {
        ArrayList<Point> pts = new ArrayList<>(p.n << 1);
        Vector v = new Vector(a, b);
        Line l = new Line(a, b);
        for(int i = 0; i < p.n; ++i) {
            int j = (i + 1) % p.n;
            boolean in1 = Vector.det(v, new Vector(a, p.pts.get(i))) > 1e-9, in2 = Vector.det(v, new Vector(a, p.pts.get(j))) > 1e-9;
            if(in1) pts.add(p.pts.get(i));
            if(in1 ^ in2) pts.add(Point.intersection(l, new Line(p.pts.get(i), p.pts.get(j))));
        }
        return new SimplePolygon(pts);
    }
    private static final double cross(Point a, Point b, Point c) { return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y); }
    private static final boolean between(Point a, Point b, Point c) { return Math.min(a.x, c.x) <= b.x && b.x <= Math.max(a.x, c.x) && Math.min(a.y, c.y) <= b.y && b.y <= Math.max(a.y, c.y); }
}
