class SparseTable{
    int[][] table;
    int[] floorPow;
    private int operation(int a, int b){
        // define associative operation here (op(op(a,b).c)=op(a,op(b,c)
    }
    public SparseTable(int[] a){
        int log;
        for(int i=0; ; ++i) 
            if(a.length<=(1<<i)){
                log = i+1;
                break;
            }
        table = new int[a.length][log];
        for(int i=0; i<a.length; ++i) table[i][0]=a[i];
        for(int j=1; j<log; ++j)
            for(int i=0; i+(1<<j-1)<a.length; ++i)
                table[i][j]=operation(table[i][j-1],table[i+(1<<(j-1))][j-1]);
        floorPow= new int[a.length+1];
        floorPow[0]=-1;
        for(int i=1; i<=a.length; ++i){
            floorPow[i]=floorPow[i-1];
            if((i&(i-1))==0) floorPow[i]++;
        }
    }
    public int get(int l, int r){
        int x = floorPow[r-l+1];
        // if operation is idempotent (op(a,a)=a) (like min, max, gcd, lcm...)
        return operation(table[l][x],table[r-(1<<x)+1][x]); // O(f(x))
        // if operation is not idempotent
        int ans = table[l][x];
        l+=1<<x;
        while(l<=r){
            x = floorPow[r-l+1];
            ans = operation(ans, table[l][x]);
            l+=1<<x;
        }
        return ans; // O(lg(r-l+1).f(x))
    }
}
