class Point {
    final long x, y;
    final int i;
    public Point(long x, long y, int i) { this.x = x; this.y = y; this.i = i; }
    public Point(long x, long y) { this(x, y, -1); }
    public Point(Point p, int i) { this(p.x, p.y, i); }
    public Point(Point p) { this(p.x, p.y, -1); }
    public final static Point translate(Point p, Vector v, int i) { return new Point(p.x + v.x, p.y + v.y, i); }
    public final static Point translate(Point p, Vector v) { return translate(p, v, -1); }
}
class Vector {
    final long x, y;
    final int i;
    public Vector(long x, long y, int i) { this.x = x; this.y = y; this.i = i; }
    public Vector(long x, long y) { this(x, y, -1); }
    public Vector(Vector v, int i) { this(v.x, v.y, i); }
    public Vector(Vector v) { this(v.x, v.y); }
    public Vector(Point end, int i) { this(end.x, end.y, i); }
    public Vector(Point end) { this(end.x, end.y); }
    public Vector(Point start, Point end, int i) { this(end.x - start.x, end.y - start.y, i); }
    public Vector(Point start, Point end) { this(end.x - start.x, end.y - start.y); }
    public final long dot(Vector other) { return x * other.x + y * other.y; }
    public final Vector add(Vector other, int i) { return new Vector(x + other.x, y + other.y, i); }
    public final Vector add(Vector other) { return add(other, -1); }
    public final static long det(Vector a, Vector b) { return a.x * b.y - a.y * b.x; }
    public final static Vector minus(Vector v, int i) { return new Vector(-v.x, -v.y, i); }
    public final static Vector minus(Vector v) { return minus(v, -1); }
}
class Line {
    final long a, b, c;
    final int i;
    public Line(long a, long b, long c, int i) { this.a = a; this.b = b; this.c = c; this.i = i; }
    public Line(long a, long b, long c) { this(a, b, c, -1); }
    public Line(Point p1, Point p2, int i) { a = p1.y - p2.y; b = p2.x - p1.x; c = -(a * p1.x + b * p1.y); this.i=i; }
    public Line(Point p1, Point p2) { this(p1, p2, -1); }
    public Line(Point p, Vector v, int i) { a = -v.y; b = v.x; c = -(a * p.x + b * p.y); this.i = i; }
    public Line(Point p, Vector v) { this(p, v, -1); }
    // 0 if no intersection, 1 if 1 intersection, 2 if infinitely many
    public final int intersects(Line other) { return a * other.b != other.a * b ? 1 : a * other.c == other.a * c && b * other.c == other.b * c ? 2 : 0; }
} 
class SimplePolygon {
    final Point[] p;
    public SimplePolygon(Point[] p) { this.p = p; }
    // note: returns twice the area (O(n))
    public final long area() {
        int n = p.length;
        long ans = p[n - 1].x * p[0].y - p[0].x * p[n - 1].y;
        for(int i = 0; i < n - 1; ++i) ans += p[i].x * p[i + 1].y - p[i + 1].x * p[i].y;
        return abs(ans);
    }
    // -1 if q is outside, 0 if q is on the boundary, 1 if q is inside (O(n))
    public final int position(Point q) {
        int wn = 0, n = p.length;
        for (int i = 0, j = n - 1; i < n; j = i++) {
            long t = cross(p[j], p[i], q);
            if (t == 0 && between(p[j], q, p[i])) return 0;
            if (p[j].y <= q.y && q.y < p[i].y) { if (t > 0) ++wn; }
            else if (p[j].y > q.y && q.y >= p[i].y) { if (t < 0) --wn; }
        }
        return wn == 0 ? -1 : 1;
    }
    private static final long cross(Point a, Point b, Point c) { return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y); }
    private static final boolean between(Point a, Point b, Point c) { return min(a.x, c.x) <= b.x && b.x <= max(a.x, c.x) && min(a.y, c.y) <= b.y && b.y <= max(a.y, c.y); }
    private static final long abs(long x) { return x > 0 ? x : -x; }
    private static final long min(long x, long y) { return x < y ? x : y; }
    private static final long max(long x, long y) { return x > y ? x : y; }
}
