interface GeometryUtil {
    static final double EPS = 1e-10, PI = Math.PI;
    public static double d(Point p1, Point p2) { return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)); }
    public static double orient(Point a, Point b, Point c) { return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y); }
    public static boolean between(Point a, Point b, Point c) { return Math.min(a.x, c.x) <= b.x + EPS && b.x <= Math.max(a.x, c.x) + EPS && Math.min(a.y, c.y) <= b.y + EPS && b.y <= Math.max(a.y, c.y) + EPS; }
    public static Vector add(Vector a, Vector b) { return new Vector(a.x + b.x, a.y + b.y); }
    public static double dot(Vector a, Vector b) { return a.x * b.x + a.y * b.y; }
    public static double det(Vector a, Vector b) { return a.x * b.y - a.y * b.x; }
    public static double angle(Vector a, Vector b) { return fix(Math.atan2(det(a, b), dot(a, b))); }
    public static double d(Line l, Point p) { return Math.abs(l.a * p.x + l.b * p.y - l.c) / Math.sqrt(l.a * l.a + l.b * l.b); }
    public static Point intersection(Line l1, Line l2) {
        int intersects = l1.intersects(l2);
        if(intersects == 0) return null;
        else if(intersects == 2) return l1.randomPoint();
        else {
            double denom = l1.a * l2.b - l1.b * l2.a;
            return new Point((l1.c * l2.b - l2.c * l1.b) / denom, (l1.a * l2.c - l2.a * l1.c) / denom);
        }
    }
    public static double d(Segment s, Point p) { 
        Point q = new Line(s).projection(p);
        if(s.contains(q)) return d(p, q);
        else return Math.min(d(p, s.p1), d(p, s.p2));
    }
    public static Point intersection(Segment s1, Segment s2) {
        double oa = orient(s2.p1, s2.p2, s1.p1), ob = orient(s2.p1, s2.p2, s1.p2),
            oc = orient(s1.p1, s1.p2, s2.p1), od = orient(s1.p1, s1.p2, s2.p2), denom = ob - oa;
        if(sign(oa) * sign(ob) == -1 && sign(oc) * sign(od) == -1)
            return new Point((s1.p1.x * ob - s1.p2.x * oa) / denom, (s1.p1.y * ob - s1.p2.y * oa) / denom);
        return null;
    }
    // returns polygon of points q, where [qc] intersects p once for every point c on s (O(n)) 
    // (Make sure p.pts are in CCW order, and for every segment of p is either fully visible or fully invisible from s)
    public static SimplePolygon visible(SimplePolygon p, Segment s) {
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
    public final static 
    public static Point[] intersections(Circle c, Line l) {
        double d = d(l, c.c);
        int sign = sign(d - c.r);
        if(sign == 1) return new Point[] {};
        else if(sign == 0) return new Point[] {l.projection(c.c)};
        else {
            double h = Math.sqrt(c.r * c.r - d * d), sqrt = Math.sqrt(l.a * l.a + l.b * l.b),
                dx = -l.b / sqrt, dy = l.a / sqrt;
            return new Point[] {new Point(c.c.x + h * dx, c.c.y + h * dy), new Point(c.c.x - h * dx, c.c.y - h * dy)};
        }
    }
    public static Point[] intersections(Circle c1, Circle c2) { // c1 != c2
        double dx = c2.c.x - c1.c.x, dy = c2.c.y - c1.c.y, d = Math.sqrt(dx * dx + dy * dy);
        if(sign(d - (c1.r + c2.r)) == 1 || sign(d - Math.abs(c1.r - c2.r)) == -1) return new Point[] {};
        double a = (c1.r * c1.r - c2.r * c2.r) / (d * 2) + d / 2, h = Math.sqrt(Math.max(0, c1.r * c1.r - a * a));
        if(sign(h) == 0) return new Point[] { new Point(c1.c.x + a * dx / d, c1.c.y + a * dy / d) };
        double x3 = c1.c.x + a * dx / d, y3 = c1.c.y + a * dy / d, rx = - h * dy / d, ry = h * dx / d;
        return new Point[] { new Point(x3 + rx, y3 + ry), new Point(x3 - rx, y3 - ry) };
    }
    public static ConvexPolygon[] cut(ConvexPolygon p, Line l) {
        ArrayList<Point> neg = new ArrayList<>(), pos = new ArrayList<>();
        for(int i = 0; i < p.n; ++i) {
            Point p1 = p.pts.get(i), p2 = p.pts.get((i + 1) % p.n);
            int side1 = l.side(p1), side2 = l.side(p2);
            if(side1 == 0 && side2 == 0) return new ConvexPolygon[] {p};
            if(side1 <= 0) neg.add(p1);
            if(side1 >= 0) pos.add(p1);
            if(side1 + side2 == 0) {
                Point inter = intersection(l, new Line(p1, p2));
                neg.add(inter);
                pos.add(inter);
            }
        }
        return neg.size() <= 2 || pos.size() <= 2 ? new ConvexPolygon[] {p} : new ConvexPolygon[] {new ConvexPolygon(neg), new ConvexPolygon(pos)};
    }
    public static double smallestArc(double a1, double a2) {
        double diff = Math.abs(fix(a1) -  fix(a2));
        return diff >= PI ? 2 * PI - diff : diff;
    }
    private static double fix(double a) { return a < -EPS ? a + 2 * PI : a; } // -2.pi <= a <= 2.pi
    public static int sign(double x) { return Math.abs(x) < EPS ? 0 : x > 0 ? 1 : -1; } 
}
