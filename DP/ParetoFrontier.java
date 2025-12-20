// Keeps pareto frontier for points (x[t], op(y[t, ]))
class ParetoFrontier {
    ArrayList<Pair> pairs = new ArrayList<>();
    public ParetoFrontier() {}
    void addPair(long x, long y) {
        while(!pairs.isEmpty() && op(pairs.getLast().y, y) == y) {
            Pair lastPair = pairs.removeLast();
            x = best(x, lastPair.x);
        }
        while(!pairs.isEmpty() && best(pairs.getLast().x, x) == x) 
            pairs.removeLast();
        pairs.add(new Pair(x, y));
    }
    static long op(long x, long y) { /* define associative operation here */ }
    static long best(long x, long y) { /* returns best value of v */ }
}
class Pair {
    long x, y;
    public Pair(long x, long y) { this.x = x; this.y = y; }
}
