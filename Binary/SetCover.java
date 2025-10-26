class SetCover {
    // finds minimum subsequence whose bitwise OR is the same as the whole array (O(k^2.2^k))
    public final static ArrayList<Integer> findMinCover(boolean[] available, int k) {
        int or = 0, all = (1 << k) - 1;
        long[] a = new long[1 << k], f;
        int[] s;
        for(int i = 0; i < 1 << k; ++i)
            if(available[i]) {
                a[i] = 1;
                or |= i;
            }
        if(or == 0) return new ArrayList<>();
        else if(available[or]) {
            ArrayList<Integer> res = new ArrayList<>(1);
            res.add(or);
            return res;
        }
        for(int i = 0; i < k; ++i)
            for(int j = 0; j < 1 << k; ++j)
                if((j & (1 << i)) != 0) a[j] += a[j ^ (1 << i)];
        s = new int[1 << k];
        f = new long[1 << k];
        s[0] = 1;
        f[0] = a[all];
        for(int i = 1; i < 1 << k; ++i) f[i] = (s[i] = -s[i & (i - 1)]) * a[all ^ i];
        for(int i = 0; i < 1 << k; ++i) s[i] = available[i] ? i : 0;
        for(int i = 0; i < k; ++i)
            for(int j = 0; j < 1 << k; ++j)
                if((j & (1 << i)) == 0 && s[j] == 0) s[j] = s[j ^ (1 << i)];
        ArrayList<long[]> can = new ArrayList<>(k - 1);
        for(int l = 2; l == l; ++l) {
            long[] zeta = new long[1 << k];
            for(int i = 0; i < 1 << k; ++i) zeta[i] = f[i] = f[i] * a[all ^ i];
            for(int i = 0; i < k; ++i)
                for(int j = 0; j < 1 << k; ++j)
                    if((j & (1 << i)) != 0) zeta[j] += zeta[j ^ (1 << i)];
            can.add(zeta);
            if(zeta[or] > 0) {
                ArrayList<Integer> res = new ArrayList<>(l);
                int mask = or;
                for(int i = l - 3; i >= 0; --i) 
                    for(int submask = mask & (mask - 1); submask > 0; submask = mask & (submask - 1)) 
                        if(s[submask] != 0 && can.get(i)[mask ^ submask] > 0) {
                            res.add(s[submask]);
                            mask ^= submask;
                            break;
                        }
                for(int submask = mask & (mask - 1); submask > 0; submask = mask & (submask - 1)) 
                    if(s[submask] != 0 && s[mask ^ submask] != 0) {
                        res.add(s[submask]);
                        res.add(s[mask ^ submask]);
                        break;
                    }
                return res;
            }
        }
        return null;
    } 
}
