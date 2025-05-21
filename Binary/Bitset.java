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
        count = b.count;
    }
    public Bitset(int n){
        this.n = n;
        word = new long[(n + offset) >> shift];
        tail = (n & offset) == 0 ? ones : (1L << (n & offset)) - 1;
    }
    public final boolean at(int i){ return ((word[i >>> shift] >>> (i & offset)) & 1) == 1; }
    public final void set(int i){
        int index = i >>> shift, off = i & offset;
        if(((word[index] >>> off) & 1) == 0){
            ++count;
            word[index] ^= 1L << (off);
        } 
    }
    public final void unset(int i){
        int index = i >>> shift, off = i & offset;
        if(((word[index] >>> off) & 1) == 1){
            ++count;
            word[index] ^= 1L << (off);
        }
    }
    public final void toggle(int i){
        int index = i >>> shift, off = i & offset;
        if(((word[index] >>> off) & 1) == 0) ++count;
        else --count;
        word[index] ^= 1L << off;
    }
    // below functions don't alter instances
    public final Bitset not(){
        Bitset res = new Bitset(n);
        for(int i = 0; i < word.length - 1; ++i) res.word[i] = ~word[i];
        res.word[word.length - 1] = (~word[word.length - 1]) & tail;
        res.count = n - count; 
        return res;
    }
    public final Bitset or(Bitset b){
        Bitset res = new Bitset(n);
        for(int i = 0; i < word.length; ++i) res.count += Long.bitCount(res.word[i] = word[i] | b.word[i]);
        return res;
    }
    public final Bitset xor(Bitset b){
        Bitset res = new Bitset(n);
        for(int i = 0; i < word.length; ++i) res.count += Long.bitCount(res.word[i] = word[i] ^ b.word[i]);
        return res;
    }
    public final Bitset and(Bitset b){
        Bitset res = new Bitset(n);
        for(int i = 0; i < word.length; ++i) res.count += Long.bitCount(res.word[i] = word[i] & b.word[i]);
        return res;
    }
    public final Bitset shiftUp(int x){
        if(x < 0) return shiftDown(-x);
        int diff = x >> shift, off = x & offset;
        Bitset res = new Bitset(n);
        if(off == 0) {
            for(int i = 0; i + diff < word.length - 1; ++i) res.count += Long.bitCount(res.word[i + diff] = word[i]);
            if(diff < word.length) res.count += Long.bitCount(res.word[word.length - 1] = word[word.length - 1 - diff] & tail);
        } 
        else if(diff  == word.length - 1) res.count = Long.bitCount(res.word[diff] = (word[0] << off) & tail);
        else if(x < n) {
            res.count = Long.bitCount(res.word[diff] = res.word[0] << off);
            int off2 = (off ^ offset) + 1;
            for(int i = 1; i + diff < word.length - 1; ++i) res.count += Long.bitCount(res.word[i + diff] = (word[i - 1] << off2) | (word[i] << off));
            res.count += Long.bitCount(res.word[word.length - 1] = ((word[word.length - 2 - diff] << off2) | (word[word.length - 1 - diff] << off)) & tail);
        }
        return res;
    }
    public final Bitset shiftDown(int x) {
        if(x < 0) return shiftUp(-x);
        int diff = x >> shift, off = x & offset;
        Bitset res = new Bitset(n);
        if (off == 0) {
            for (int i = diff; i < word.length - 1; ++i) res.count += Long.bitCount(res.word[i - diff] = word[i]);
            if (diff < word.length) res.count += Long.bitCount(res.word[word.length - 1 - diff] = word[word.length - 1] & tail);
        } 
        else if (diff == word.length - 1) res.count = Long.bitCount(res.word[0] = (word[diff] >>> off));
        else if (x < n) {
            res.count = Long.bitCount(res.word[0] = word[diff] >>> off);
            int off2 = (off ^ offset) + 1;
            for (int i = 1; i + diff < word.length - 1; ++i) res.count += Long.bitCount(res.word[i] = (word[i + diff] >>> off) | (word[i + diff - 1] >>> off2));
            res.count += Long.bitCount(res.word[word.length - 1 - diff] = ((word[word.length - 1] >>> off) | (word[word.length - 2] >>> off2)) & tail);
        }
        return res;
    }
    // below functions alter instance
    public final void orEqual(Bitset b){
        count = 0; 
        for(int i = 0; i < word.length; ++i) count += Long.bitCount(word[i] = word[i] | b.word[i]);
    }
    public final void xorEqual(Bitset b){
        count = 0;
        for(int i = 0; i < word.length; ++i) count += Long.bitCount(word[i] = word[i] ^ b.word[i]);
    }
    public final void andEqual(Bitset b){
        count = 0;
        for(int i = 0; i < word.length; ++i) count += Long.bitCount(word[i] = word[i] & b.word[i]);
    }
}