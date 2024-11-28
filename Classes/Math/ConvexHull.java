// add lines y=m*x+b where m's are strictly monotonic, query x returns [m,b] that max/minimizes m*x+b
// adding runs in O(1) amortized, querying runs in O(log(# lines))
// if m's increasing: it returns [m[i],b[i]] that gives max(m*x+b)
// if m's decreasing: it returrns [m[i],b[i]] that gives min(m*x+b)
// Note: if m's order gives the opposite of what we want, insert [-m,-b] instea of [m,b]
class ConvexHull{
    static double inf = Double.MAX_VALUE;
    ArrayList<long[]> lines;
    ArrayList<double[]> ranges;
    public ConvexHull(){
        lines = new ArrayList<>();
        ranges = new ArrayList<>();
    }
    public void add(long m, long b){
        if(lines.isEmpty()){
            lines.add(new long[] {m,b});
            ranges.add(new double[] {-inf,inf});
        }
        else{
            for(int i=lines.size()-1; i>=0; --i){
                long m2 = lines.get(i)[0], b2 = lines.get(i)[1];
                double x = (b2-b+0.0)/(m-m2);
                if(ranges.get(i)[0]<x && x<=ranges.get(i)[1]){
                    ranges.get(i)[1]=x;
                    lines.add(new long[] {m,b});
                    ranges.add(new double[] {x,inf});
                    return;
                }
                else{
                    ranges.remove(i);
                    lines.remove(i);
                }
            }
        }
    }
    public long[] query(double x){
        int lo = 0, hi = ranges.size()-1;
        double r,l;
        while(lo<=hi){
            int mid = (lo+hi)/2;
            l = ranges.get(mid)[0]; r = ranges.get(mid)[1];
            if(l<=x && x<=r) return lines.get(mid);  
            else if(x<l) hi = mid-1;
            else lo = mid+1;
        }
        return null;
    }
}
