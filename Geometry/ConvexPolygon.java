class SimplePolygon {
    final Point[] pts;
    final int n;
    public SimplePolygon(Point[] pts) { this.pts = pts; n = pts.length; }
    public final double area() {
        if(n <= 2) return 0;
        double ans = pts[n - 1].x * pts[0].y - pts[0].x * pts[n - 1].y;
        for(int i = 0; i < n - 1; ++i) ans += pts[i].x * pts[i + 1].y - pts[i + 1].x * pts[i].y;
        return Math.abs(ans) / 2;
    }
    public final double perimeter() {
        if(n == 1) return 0;
        double ans = d(pts[0], pts[n - 1]);
        for(int i = 0; i < n - 1; ++i) ans += d(pts[i], pts[i + 1]);
        return ans;
    }
    // -1 if q is outside, 0 if q is on the boundary, 1 if q is inside (O(n))
    public final int position(Point q) {
        if(n <= 1) return -1;
        int wn = 0;
        for (int i = 0, j = n - 1; i < n; j = i++) {
            int t = sign(orient(pts[j], pts[i], q));
            if(t == 0 && between(pts[j], q, pts[i])) return 0;
            if(pts[j].y <= q.y && q.y < pts[i].y) { if(t == 1) ++wn; }
            else if(pts[j].y > q.y && q.y >= pts[i].y) { if(t == -1) --wn; }
        }
        return wn == 0 ? -1 : 1;
    }
}
public final static class ConvexPolygon extends SimplePolygon {
    public ConvexPolygon(Point[] pts) { super(pts); }
}