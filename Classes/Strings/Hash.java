class Hash{
    private static long pow(long x, long n, long m){
        if(n == 0) return 1;
        long res = pow(x, n>>1, m);
        res = (res * res)%m;
        if(n % 2 == 1) res = (res * x)%m;
        return res;
    }
    long m1, m2;
    long[] inv_p1_pow, inv_p2_pow;
    long[] h1, h2;
    public Hash(char[] s){ this(s, 29, 31, 3030000073l, 3030000097l); }
    public Hash(char[] s, long p1, long p2, long m1, long m2){
        int n = s.length;
        this.m1 = m1;
        this.m2 = m2;
        h1 = new long[n]; h2 = new long[n];
        h1[0] = h2[0] = s[0] - 'a' + 1;
        inv_p1_pow = new long[n]; inv_p2_pow = new long[n];
        inv_p1_pow[0] = inv_p2_pow[0] = 1;
        long inv_p1 = pow(p1, m1-2, m1), inv_p2 = pow(p2, m2-2, m2);
        long p1_pow = 1, p2_pow = 1;
        for(int i = 1; i < n; ++i){
            inv_p1_pow[i] = (inv_p1_pow[i-1] * inv_p1)%m1;
            inv_p2_pow[i] = (inv_p2_pow[i-1] * inv_p2)%m2;
            p1_pow = (p1_pow * p1)%m1;
            p2_pow = (p2_pow * p2)%m2;
            h1[i] = (h1[i-1] + (s[i] - 'a' + 1)*p1_pow + m1)%m1;
            h2[i] = (h2[i-1] + (s[i] - 'a' + 1)*p2_pow + m2)%m2;
        }
    }
    public long get(int l, int r){
        if(l == 0) return (h1[r]<<32) + h2[r];
        return ((((h1[r] - h1[l-1] + m1)%m1 * inv_p1_pow[l] + m1)%m1)<<32) + ((h2[r] - h2[l-1] + m2)%m2* inv_p2_pow[l] + m2)%m2; 
    }
    public long get(int l1, int r1, int l2, int r2){
        return ((((l1 == 0 ? h1[r1] : (h1[r1] - h1[l1-1] + m1)%m1 * inv_p1_pow[l1])%m1 + (h1[r2] - h1[l2-1] + m1) % m1 * inv_p1_pow[l2-(r1+1)])%m1) << 32)  + ((l1 == 0 ? h2[r1] : (h2[r1] - h2[l1-1] +m2)%m2 * inv_p2_pow[l2])%m2 + (h2[r2] - h2[l2-1] + m2)%m2 * inv_p2_pow[l2-(r1+1)])%m2;
    } 
    public static char[] listToString(ArrayList<Integer> list){
        // 'a'->'z' represent digits 0->25 (base 26),  'z'+1 represents a comma
        StringBuilder res = new StringBuilder(6*list.size()+1);
        res.append((char)('z'+1));
        for(int i = 0; i < list.size(); ++i){
            int x = list.get(i);
            String base26x = x == 0 ? "a" : "";
            while(x > 0){
                base26x = (char)((x%26)+'a')+base26x;
                x /= 26;
            }
            res.append(base26x);
            res.append((char)('z'+1));
        }
        return res.toString().toCharArray();
    }
}
