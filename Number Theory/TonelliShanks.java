class TonelliShanks {
    // returns r: r * r % p = a % p, null if it doesn't exist (O(f_2(p - 1).log^3(p)))
    public static final BigInteger squareRoot(BigInteger a, BigInteger p) {
        if(a.equals(BigInteger.ZERO) || a.equals(BigInteger.ONE)) return a;
        if(a.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
            BigInteger root = a.modPow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
            if(!root.modPow(BigInteger.TWO, p).equals(a)) return null;
            return root;
        }
        BigInteger s = BigInteger.ONE, q = p.subtract(BigInteger.ONE).divide(BigInteger.TWO), z = BigInteger.TWO;
        while(q.and(BigInteger.ONE).equals(BigInteger.ZERO)) {
            s = s.add(BigInteger.ONE);
            q = q.shiftRight(1);
        }
        while(true) {
            BigInteger e = z.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.TWO), p);
            if(e.equals(BigInteger.ONE)) {
                z = z.add(BigInteger.ONE);
                continue;
            }
            if(e.equals(p.subtract(BigInteger.ONE))) break;
            return null;
        }
        BigInteger m = s, c = z.modPow(q, p), t = a.modPow(q, p), r = a.modPow(q.add(BigInteger.ONE).divide(BigInteger.TWO), p);
        while(!t.equals(BigInteger.ONE)) {
            int i = 0, mVal = m.intValue();
            BigInteger pow = BigInteger.ONE;
            for(i = 0; i < mVal; ++i) {
                if(t.modPow(pow, p).equals(BigInteger.ONE)) break;
                pow = pow.shiftLeft(1);
            }
            if(i == mVal) return null;
            BigInteger exp = BigInteger.TWO.pow(mVal - i - 1), b = c.modPow(exp, p);
            m = BigInteger.valueOf(i);
            c = b.modPow(BigInteger.TWO, p);
            t = t.multiply(b.pow(2)).mod(p);
            r = r.multiply(b).mod(p);
        }
        if(!r.modPow(BigInteger.TWO, p).equals(a)) return null;
        return r;
    }
}
