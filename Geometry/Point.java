class Point {
    final double x, y;
    public Point(double x, double y) { this.x = x; this.y = y; }
    public Point(Point p) { this(p.x, p.y); }
    public final Point reflection(Point p) { return new Point(2 * x - p.x, 2 * y - p.y); }
    public final Point translation(Vector v) { return new Point(x + v.x, y + v.y); }
}