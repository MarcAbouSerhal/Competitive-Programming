// if type is -1, add the range (l,r)
// if type is 0, consider the point at (l)
// if type is 1, remove the range (l,r)
// just do .sort(intervals) and it sets up for sweeping line
class Interval implements Comparable<Interval>{
    int l, r, type, index;
    public Interval(int x, int index){
        l = x;
        type = 0;
        this.index = index;
    }
    public Interval(int x){
        l = x;
    }
    public Interval(int l,int r, boolean add, int index){ 
        this.l = l;
        this.r = r;
        type = add ? -1 : 1;
        this.index = index;
    }
    public Interval(int l,int r, boolean add){ 
        this.l = l;
        this.r = r;
        type = add ? -1 : 1;
    }
    public final int hashCode(){ return l + 47 * r; }
    public final boolean equals(Object other){
        if(!(other instanceof Interval)) return false;
        Interval i2 = (Interval)other;
        return i2.l == l && i2.r == r;
    }
    public final int compareTo(Interval i2){
        if(type <=0 && i2.type <= 0)
            return l == i2.l ? (type == i2.type ? i2.r - r : type - i2.type ): l - i2.l; // smallest l first, and of those, biggest r first
        if(type <= 0)
            return l == i2.r ? -1 : l - i2.r;
        if(i2.type <= 0)
            return r == i2.l ? 1 : r - i2.l;
        return r - i2.r;
    }
}
