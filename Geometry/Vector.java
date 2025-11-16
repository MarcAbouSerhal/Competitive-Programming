class Vector {
    final double x, y;
    public Vector(double x, double y) { this.x = x; this.y = y; }
    public Vector(Vector v) { this(v.x, v.y); }
    public Vector(Point end) { this(end.x, end.y); }
    public Vector(Point start, Point end) { this(end.x - start.x, end.y - start.y); }
    public final double norm() { return Math.sqrt(x * x + y * y); }
    public final Vector rotation(double a) {
        double sin = Math.sin(a), cos = Math.cos(a);
        return new Vector(x * cos - y * sin, x * sin + y * cos);
    }
    public final Vector negative() { return new Vector(-x, -y); }
}