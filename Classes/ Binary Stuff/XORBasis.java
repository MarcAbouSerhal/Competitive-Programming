class XORBasis{
    // means x < (1<<log)
    int log;
    int size = 0;
    long[] basis;
    public XORBasis(int log){
        this.log = log;
        basis = new long[log];
    }
    public void insert(long x){
        for(int i=log-1; i>=0; --i){
            if((x&(1<<i))==0) continue;
            if(basis[i]==0){
                basis[i] = x;
                ++size;
                return;
            }
            x ^= basis[i];
        }
    }
    public boolean contains(long x){
        for(int i=log-1; i>=0; --i){
            if((x&(1<<i))==0) continue;
            if(basis[i]==0){
                return false;
            }
            x ^= basis[i];
        }
        return true;
    }
    public long minSubsequenceWith(long x){
        for(int i=log-1; i>=0; --i)
            if((x&(1<<i))>0) 
                x ^= basis[i];
        return x;
    }
    public long maxSubsequenceWith(long x){
        for(int i=log-1; i>=0; --i)
            if((x&(1<<i))==0) 
                x ^= basis[i];
        return x;
    }
    public long maxSubsequence(){ return maxSubsequenceWith(0); }
    public long kthBiggestSubsequence(long k){
        long x = 0, tot = 1<<size;
        for(int i=log-1; i>=0; --i)
            if(basis[i]!=0){
                long low = tot>>1;
                if((low<k && (x&(1<<i))==0) || (low>=k && (x&(1<<i))>0)) x ^= basis[i];
                if(low<k) k -= low;
                tot >>= 1;
            }
        return x;
    }
    public XORBasis copy(){
        XORBasis copy = new XORBasis(log);
        copy.size = size;
        for(int i=0; i<log; ++i) copy.basis[i] = basis[i];
        return copy;
    }
    public static XORBasis merge(XORBasis b1, XORBasis b2){
        XORBasis res = b1.copy();
        for(long i: b2.basis)
            if(i!=0) 
                res.insert(i);
        return res;
    }
}
class Edge{
    int x, i;
    long y;
    public Edge(int x,long y,int i){this.x=x;this.y=y;this.i=i;}
}
