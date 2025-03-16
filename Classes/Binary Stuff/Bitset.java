class Bitset{
    public final long[] word;
    public int count = 0;
    public final long tail;
    public final int n;

    private static final int shift = 6;
    private static final int offset = 63;
    private static final long ones = ~0L;
    public Bitset(Bitset b){
        n = b.n;
        tail = b.tail;
        int m = b.word.length;
        word = new long[m];
        for(int i = 0; i < m; ++i) word[i] = b.word[i];
    }
    public Bitset(int n){
        this.n = n;
        word = new long[(n + offset) >> shift];
        if((n & offset) == 0) tail = ones;
        else tail = (1 << (n & offset)) - 1;
    }
    public final int get(int i){
        return (int)((word[i >>> shift] >>> (i & offset)) & 1);
    }
    public final void set(int i){
        if(((word[i >>> shift] >>> (i & offset)) & 1) == 0){
            ++count;
            word[i >>> shift] |= 1 << (i & offset);
        } 
    }
    public final void unset(int i){
        if(((word[i >>> shift] >>> (i & offset)) & 1) != 0){
            --count;
            word[i >>> shift] ^= 1 << (i & offset);
        }
    }
    public final void toggle(int i){
        if(((word[i >>> shift] >>> (i & offset)) & 1) == 0) ++count;
        else --count;
        word[i >>> shift] ^= 1 << (i & offset);
    }
    public final static Bitset not(Bitset b){
        final int m = b.word.length;
        int count = 0;
        final Bitset res = new Bitset(b.n);
        for(int i = 0; i < m - 1; ++i)
            res.word[i] = ~b.word[i])
        res.word[m - 1] = (~b.word[m - 1] & res.tail);
        res.count = b.n - b.count; 
        return res;
    }
    public final static Bitset or(Bitset b1, Bitset b2){
        final int m = b1.word.length;
        int count = 0;
        final Bitset res = new Bitset(b1.n);
        for(int i = 0; i < m; ++i)
            count += Long.bitCount(res.word[i] = b1.word[i] | b2.word[i]);
        res.count = count;
        return res;
    }
    public final static Bitset xor(Bitset b1, Bitset b2){
        final int m = b1.word.length;
        int count = 0;
        final Bitset res = new Bitset(b1.n);
        for(int i = 0; i < m; ++i)
            count += Long.bitCount(res.word[i] = b1.word[i] ^ b2.word[i]);
        res.count = count;
        return res;
    }
    public final static Bitset and(Bitset b1, Bitset b2){
        final int m = b1.word.length;
        int count = 0;
        final Bitset res = new Bitset(m << shift);
        for(int i = 0; i < m; ++i)
            count += Long.bitCount(res.word[i] = b1.word[i] & b2.word[i]);
        res.count = count;
        return res;
    }
    public final static Bitset shiftUp(Bitset b, int x){
        final int m = b.word.length, diff = x >>> shift, off = x & offset;
        int count = 0;
        final Bitset res = new Bitset(b.n);
        if(off != 0){
            final int off2 = (offset ^ off) + 1;
            for(int i = 0; i + diff + 1 < m; ++i)
                count += Long.bitCount(res.word[i + diff + 1] = (b.word[i + 1] << off) | (b.word[i] >>> off2));
            count += Long.bitCount(res.word[diff] = b.word[0] << off);
        }
        else
            for(int i = 0; i + diff < m; ++i)
                count += Long.bitCount(res.word[i + diff] = b.word[i]);
        count -= Long.bitCount(res.word[m - 1]);
        res.count = count + Long.bitCount(res.word[m - 1] = res.word[m - 1] & res.tail);
        return res;
    }
    public final static Bitset shiftDown(Bitset b, int x){
        final int m = b.word.length, diff = x >>> shift, off = x & offset;
        int count = 0;
        final Bitset res = new Bitset(b.n);
        if(off != 0){
            final int off2 = (offset ^ off) + 1;
            for(int i = 1; i + diff < m; ++i)
                count += Long.bitCount(res.word[i] = (b.word[i + diff] << off) | (b.word[i + diff - 1] >>> off2));
            count += Long.bitCount(res.word[0] = b.word[diff] << off);
        }
        else
            for(int i = 0; i + diff < m; ++i)
                count += Long.bitCount(res.word[i] = b.word[i + diff]);
        res.count = count;
        return res;
    }
}
