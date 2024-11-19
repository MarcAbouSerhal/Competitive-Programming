// way more efficient than int[] + they're hashable
class Pair<X,Y>{
    X x;Y y;
    public Pair(X x,Y y){this.x=x;this.y=y;}
    public String toString(){return x+" "+y;};
    public int hashCode(){return x.hashCode()+47*y.hashCode();}
    public boolean equals(Object obj){return (obj instanceof Pair) && ((Pair)obj).x.equals(x) && ((Pair)obj).y.equals(y);}
}
class Triple<X,Y,Z>{
    X x;Y y;Z z;
    public Triple(X x,Y y,Z z){this.x=x;this.y=y;this.z=z;}
    public String toString(){return x+" "+y+" "+z;};
    public int hashCode(){return x.hashCode()+47*y.hashCode()+47*47*z.hashCode();}
    public boolean equals(Object obj){return (obj instanceof Triple) && ((Triple)obj).x.equals(x) && ((Triple)obj).y.equals(y) && ((Triple)obj).z.equals(z);}
}
