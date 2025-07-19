class Geometry {
    private static final double eps = 1e-9, pi = Math.PI;
    public final static class Point {
        final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
        public Point(Point p) { this(p.x, p.y); }
        public final Point reflection(Point p) { return new Point(2 * x - p.x, 2 * y - p.y); }
        public final static Point translate(Point p, Vector v) { return new Point(p.x + v.x, p.y + v.y); }
    }
    public final static double d(Point p1, Point p2) { return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)); }
    public static final double orient(Point a, Point b, Point c) { return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y); }
    public static final boolean between(Point a, Point b, Point c) { return Math.min(a.x, c.x) <= b.x + eps && b.x <= Math.max(a.x, c.x) + eps && Math.min(a.y, c.y) <= b.y + eps && b.y <= Math.max(a.y, c.y) + eps; }
    public final static class Vector {
        final double x, y;
        public Vector(double x, double y) { this.x = x; this.y = y; }
        public Vector(Vector v) { this(v.x, v.y); }
        public Vector(Point end) { this(end.x, end.y); }
        public Vector(Point start, Point end) { this(end.x - start.x, end.y - start.y); }
        public final double norm() { return Math.sqrt(x * x + y * y); }
        public final double dot(Vector other) { return x * other.x + y * other.y; }
        public final Vector add(Vector other) { return new Vector(x + other.x, y + other.y); }
    }
    public final static double det(Vector a, Vector b) { return a.x * b.y - a.y * b.x; }
    public final static double angle(Vector a, Vector b) { return fix(Math.atan2(det(a, b), a.dot(b))); }
    public final static Vector minus(Vector v) { return new Vector(-v.x, -v.y); }
    public final static Vector rotate(Vector v, double a) {
        double sin = Math.sin(a), cos = Math.cos(a);
        return new Vector(v.x * cos - v.y * sin, v.x * sin + v.y * cos);
    }
    public final static class Line {
        final double a, b, c; // a.x + b.y = c
        public Line(double a, double b, double c) { this.a = a; this.b = b; this.c = c; }
        public Line(Point p1, Point p2) { a = p1.y - p2.y; b = p2.x - p1.x; c = a * p1.x + b * p1.y; }
        public Line(Line l) { this(l.a, l.b, l.c); }
        public Line(Segment s) { this(s.p1, s.p2); }
        public Line(Point p, Vector v) { a = -v.y; b = v.x; c = a * p.x + b * p.y; }
        // 0 if no intersection, 1 if 1 intersection, 2 if infinitely many
        public final int intersects(Line other) { return a * other.b != other.a * b ? 1 : a * other.c == other.a * c && b * other.c == other.b * c ? 2 : 0; }
        public final Point randomPoint() { return b == 0 ? new Point(c / a, 0) : new Point(0, c / b); }
        public final Point reflection(Point p) {
            double d = (a * p.x + b * p.y - c) / (a * a + b * b);
            return new Point(p.x - 2 * a * d, p.y - 2 * b * d);
        }
        public final Point projection(Point p) {
            double d = (a * p.x + b * p.y - c) / (a * a + b * b);
            return new Point(p.x - a * d, p.y - b * d);
        }
        public final Point ricochetPoint(Point p1, Point p2) { return intersection(this, new Line(p1, reflection(p2))); }
        public final boolean contains(Point p) { return sign(a * p.x + b * p.y - c) == 0; }
        public final boolean onSameSide(Point p1, Point p2) { return sign(a * p1.x + b * p1.y - c) * sign(a * p2.x + b * p2.y - c) >= 0; }
        public final Vector direction() { return new Vector(b, -a); }
    } 
    public final static double d(Line l, Point p) { return Math.abs(l.a * p.x + l.b * p.y - l.c) / Math.sqrt(l.a * l.a + l.b * l.b); }
    public final static Point intersection(Line l1, Line l2) {
        int intersects = l1.intersects(l2);
        if(intersects == 0) return null;
        else if(intersects == 2) return l1.randomPoint();
        else {
            double denom = l1.a * l2.b - l1.b * l2.a;
            return new Point((l1.c * l2.b - l2.c * l1.b) / denom, (l1.a * l2.c - l2.a * l1.c) / denom);
        }
    }
    public final static class Segment {
        final Point p1, p2;
        public Segment(Point p1, Point p2) { this.p1 = p1; this.p2 = p2; }
        public Segment(Segment s) { this(s.p1, s.p2); }
        public final double length() { return d(p1, p2); }
        public final boolean contains(Point p) { return between(p1, p, p2) && sign((p.x - p1.x) * (p2.y - p1.y) + (p.y - p1.y) * (p1.x - p2.x)) == 0; } 
    }
    public final static Point intersection(Segment s1, Segment s2) {
        double oa = orient(s2.p1, s2.p2, s1.p1), ob = orient(s2.p1, s2.p2, s1.p2),
            oc = orient(s1.p1, s1.p2, s2.p1), od = orient(s1.p1, s1.p2, s2.p2), denom = ob - oa;
        if(sign(oa) * sign(ob) == -1 && sign(oc) * sign(od) == -1)
            return new Point((s1.p1.x * ob - s1.p2.x * oa) / denom, (s1.p1.y * ob - s1.p2.y * oa) / denom);
        return null;
    }
    public final static class SimplePolygon {
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
                int t = sign(orient(pts.get(j), pts.get(i), q));
                if(t == 0 && between(pts.get(j), q, pts.get(i))) return 0;
                if(pts.get(j).y <= q.y && q.y < pts.get(i).y) { if(t == 1) ++wn; }
                else if(pts.get(j).y > q.y && q.y >= pts.get(i).y) { if(t == -1) --wn; }
            }
            return wn == 0 ? -1 : 1;
        }
    }
    // returns polygon of points q, where [qc] intersects p once for every point c on s (O(n)) 
    // (Make sure p.pts are in CCW order, and for every segment of p is either fully visible or fully invisible from s)
    public static final SimplePolygon visible(SimplePolygon p, Segment s) {
        ArrayList<Point> pts = new ArrayList<>(p.n << 1);
        Line l = new Line(s);
        Vector v = new Vector(s.p1, s.p2);
        for(int i = 0; i < p.n; ++i) {
            int j = (i + 1) % p.n;
            boolean in1 = sign(det(v, new Vector(s.p1, p.pts.get(i)))) == 1, in2 = sign(det(v, new Vector(s.p1, p.pts.get(j)))) == 1;
            if(in1) pts.add(p.pts.get(i));
            if(in1 ^ in2) pts.add(intersection(l, new Line(p.pts.get(i), p.pts.get(j))));
        }
        return new SimplePolygon(pts);
    }
    public final static class Circle {
        final Point c;
        final double r;
        public Circle(Point c, double r) { this.c = c; this.r = r; }
        public final double angle(Point p) { return Geometry.angle(new Vector(1, 0), new Vector(c, p)); }
        public final Point at(double a) { return new Point(c.x + r * Math.cos(a), c.y + r * Math.sin(a)); }
        // -1 if p is outside, 0 if p is on the boundary, 1 if p is inside (O(1))
        public final int position(Point p) { return sign(r - d(p, c)); }
        public final double arclength(double a1, double a2) { return r * smallestArc(a1, a2); }
        // Finds points q1 q2 such that [pq1] and [pq2] are tangent to the circle
        public final Point[] tangentPoints(Point p) {
            double d2 = (c.x - p.x) * (c.x - p.x) + (c.y - p.y) * (c.y - p.y), sqrt = Math.sqrt(d2 - r * r), dx = c.x - p.x, dy = c.y - p.y;
            double dxsqrt = dx * sqrt, dxr = dx * r, dysqrt = dy * sqrt, dyr = dy * r;
            Point q1 = new Point(p.x + sqrt * (dxsqrt - dyr) / d2, p.y + sqrt * (dxr + dysqrt) / d2);
            Point q2 = new Point(p.x + sqrt * (dxsqrt + dyr) / d2, p.y + sqrt * (dysqrt - dxr) / d2);
            return new Point[] {q1, q2};
        }
    }
    public static final double smallestArc(double a1, double a2) {
        double diff = Math.abs(fix(a1) -  fix(a2));
        return diff >= pi ? 2 * pi - diff : diff;
    }
    public static final boolean between(double a1, double b, double a2) {
        a1 = fix(a1);
        a2 = fix(a2);
        b = fix(b);
        return sign(a2 - a1) == -1 ? sign(a2 - b) >= 0 || sign(b - a1) >= 0 : sign(b - a1) >= 0 && sign(a2 - b) >= 0;
    }
    private static final double fix(double a) { return a < -eps ? a + 2 * pi : a; } 
    private static final int sign(double x) { return Math.abs(x) < eps ? 0 : x > 0 ? 1 : -1; } 
}
