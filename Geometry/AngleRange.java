class AngleRange {
    final double a1, a2;
    public AngleRange(double a1, double a2) { this.a1 = fix(a1); this.a2 = fix(a2); }
    public AngleRange(double a1, double a2, double contained) {
        a1 = fix(a1); a2 = fix(a2); contained = fix(contained);
        if(contained > a1) { this.a1 = a1; this.a2 = a2; }
        else { this.a2 = a1; this.a1 = a2; }
    }
    public final double range() { 
        double diff = Math.abs(a2 - a1);
        return sign(diff) == 0 ? 0 : a2 > a1 ? diff : 2 * PI - diff;
    }
    public final boolean contains(double b) {
        b = fix(b);
        return sign(a2 - a1) == -1 ? sign(a2 - b) >= 0 || sign(b - a1) >= 0 : sign(b - a1) >= 0 && sign(a2 - b) >= 0;
    }
    public final boolean contains(AngleRange other) {
        if(a1 > a2 && other.a1 <= other.a2) return sign(a2 - other.a2) >= 0;
        else return sign(other.a1 - a1) >= 0 && sign(a2 - other.a2) >= 0;
    }
    public final AngleRange complement() { return new AngleRange(a2, a1); }
    public static final AngleRange smallestRange(double a1, double a2) {
        a1 = fix(a1); a2 = fix(a2);
        double diff = Math.abs(a2 - a1);
        return diff >= PI ? new AngleRange(a2, a1) : new AngleRange(a1, a2);
    }
    public static final AngleRange biggestRange(double a1, double a2) { return smallestRange(a1, a2).complement(); }
}