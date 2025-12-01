class ModuloUtil {
    static long pow(long a, long b, long mod) {
        long res = 1;
        while(b != 0) {
            if((b & 1) == 1) res = (res * a) % mod;
            a = (a * a) % mod;
            b >>= 1;
        }
        return res;
    }
    static long log(long base, long a, long mod) {
        long m = (long)Math.sqrt(mod) + 5, u = pow(base, mod - 1 - m, mod), cur = a;
        TreeMap<Long, Long> findI = new TreeMap<>();
        for(int i = 0; i < m; ++i) {
            findI.put(cur, (long)i);
            cur = (cur * u) % mod;
        }
        cur = 1;
        for(int j = 0; j < m; ++j) {
            if(findI.containsKey(cur)) return (findI.get(cur) * m + j) % (mod - 1);
            cur = (cur * base) % mod;
        }
        return -1;
    }
}
