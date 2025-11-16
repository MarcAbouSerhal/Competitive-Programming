class Circle {
    final Point c;
    final double r;
    public Circle(Point c, double r) { this.c = c; this.r = r; }
    public final double area() { return PI * r * r; }
    public final double perimeter() { return 2 * PI * r; }
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