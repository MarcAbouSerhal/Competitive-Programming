class Line {
    final double a, b, c; // a.x + b.y = c
    public Line(double a, double b, double c) { this.a = a; this.b = b; this.c = c; }
    public Line(Point p1, Point p2) { a = p1.y - p2.y; b = p2.x - p1.x; c = a * p1.x + b * p1.y; }
    public Line(Line l) { this(l.a, l.b, l.c); }
    public Line(Segment s) { this(s.p1, s.p2); }
    public Line(Point p, Vector v) { a = -v.y; b = v.x; c = a * p.x + b * p.y; }
    // 0 if no intersection, 1 if 1 intersection, 2 if infinitely many
    public final int intersects(Line other) { return a * other.b != other.a * b ? 1 : a * other.c == other.a * c && b * other.c == other.b * c ? 2 : 0; }
    public final boolean isParallelTo(Line other) { return a * other.b == other.a * b && b * other.c == other.b * c; }
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
    public final boolean contains(Point p) { return side(p) == 0; }
    public final boolean areOnSameSide(Point p1, Point p2) { return sign(a * p1.x + b * p1.y - c) * sign(a * p2.x + b * p2.y - c) >= 0; }
    public final int side(Point p) { return sign(a * p.x + b * p.y - c); }
    public final Vector direction() { return new Vector(b, -a); }
} 