// if type is -1, add the range (l,r)
// if type is 0, consider the point at (l)
// if type is 1, remove the range (l,r)
class Interval{
    int l, r, type;
    public Interval(int x){
        l = x;
        type = 0;
    }
    public Interval(int l,int r, boolean add){ 
        this.l = l;
        this.r = r;
        type = add ? -1 : 1;
    }
    public int hashCode(){ return l + 47 * r; }
    public boolean equals(Object other){
        if(!(other instanceof Interval)) return false;
        Interval i2 = (Interval)other;
        return i2.l == l && i2.r == r;
    }
    // use this for setting up for sweeping line
    public static int sweepOrder(Interval i1, Interval i2){
        if(i1.type <=0 && i2.type <= 0)
            return i1.l == i2.l ? i1.type - i2.type : i1.l - i2.l;
        if(i1.type <= 0)
            return i1.l == i2.r ? -1 : i1.l - i2.r;
        if(i2.type <= 0)
            return i1.r == i2.l ? 1 : i1.r - i2.l;
        return i1.r - i2.r;
    }
}
