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
    // below functions return copies and do not alter this
    public final Bitset not(){
        final int m = word.length;
        final Bitset res = new Bitset(n);
        for(int i = 0; i < m - 1; ++i)
            res.word[i] = ~word[i];
        res.word[m - 1] = (~word[m - 1] & res.tail);
        res.count = n - count; 
        return res;
    }
    public final Bitset or(Bitset b){
        final int m = word.length;
        int count = 0;
        final Bitset res = new Bitset(n);
        for(int i = 0; i < m; ++i)
            count += Long.bitCount(res.word[i] = word[i] | b.word[i]);
        res.count = count;
        return res;
    }
    public final Bitset xor(Bitset b){
        final int m = word.length;
        int count = 0;
        final Bitset res = new Bitset(n);
        for(int i = 0; i < m; ++i)
            count += Long.bitCount(res.word[i] = word[i] ^ b.word[i]);
        res.count = count;
        return res;
    }
    public final Bitset and(Bitset b){
        final int m = word.length;
        int count = 0;
        final Bitset res = new Bitset(m << shift);
        for(int i = 0; i < m; ++i)
            count += Long.bitCount(res.word[i] = word[i] & b.word[i]);
        res.count = count;
        return res;
    }
    public final Bitset shiftUp(int x){
        final int m = word.length, diff = x >>> shift, off = x & offset;
        int count = 0;
        final Bitset res = new Bitset(n);
        if(off != 0){
            final int off2 = (offset ^ off) + 1;
            for(int i = 0; i + diff + 1 < m; ++i)
                count += Long.bitCount(res.word[i + diff + 1] = (word[i + 1] << off) | (word[i] >>> off2));
            count += Long.bitCount(res.word[diff] = word[0] << off);
        }
        else
            for(int i = 0; i + diff < m; ++i)
                count += Long.bitCount(res.word[i + diff] = word[i]);
        count -= Long.bitCount(res.word[m - 1]);
        res.count = count + Long.bitCount(res.word[m - 1] = res.word[m - 1] & res.tail);
        return res;
    }
    public final Bitset shiftDown(int x){
        final int m = word.length, diff = x >>> shift, off = x & offset;
        int count = 0;
        final Bitset res = new Bitset(n);
        if(off != 0){
            final int off2 = (offset ^ off) + 1;
            for(int i = 1; i + diff < m; ++i)
                count += Long.bitCount(res.word[i] = (word[i + diff] << off) | (word[i + diff - 1] >>> off2));
            count += Long.bitCount(res.word[0] = word[diff] << off);
        }
        else
            for(int i = 0; i + diff < m; ++i)
                count += Long.bitCount(res.word[i] = word[i + diff]);
        res.count = count;
        return res;
    }
}
