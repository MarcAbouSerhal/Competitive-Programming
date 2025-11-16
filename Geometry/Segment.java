class Segment {
    final Point p1, p2;
    public Segment(Point p1, Point p2) { this.p1 = p1; this.p2 = p2; }
    public Segment(Point p, Vector v) { this(p, p.translation(v)); }
    public Segment(Segment s) { this(s.p1, s.p2); }
    public final Point middle() { return new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2); }
    public final double length() { return d(p1, p2); }
    public final boolean contains(Point p) { return between(p1, p, p2) && sign((p.x - p1.x) * (p2.y - p1.y) + (p.y - p1.y) * (p1.x - p2.x)) == 0; } 
}