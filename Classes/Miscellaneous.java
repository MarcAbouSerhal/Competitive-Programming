// way more efficient than int[] + they're hashable
class Pair{
    int x,y;
    public Pair(int x,int y){this.x=x;this.y=y;}
    public String toString(){return x+" "+y;};
    public int hashCode(){return x+47*y;}
    public boolean equals(Object obj){return (obj instanceof Pair) && ((Pair)obj).x==x && ((Pair)obj).y==y;}
}
class Triple{
    int x,y,z;
    public Triple(int x,int y,int z){this.x=x;this.y=y;this.z=z;}
    public String toString(){return x+" "+y+" "+z;};
    public int hashCode(){return x+47*y+47*47*z;}
    public boolean equals(Object obj){return (obj instanceof Triple) && ((Triple)obj).x==x && ((Triple)obj).y==y && ((Triple)obj).z==z;}
}
