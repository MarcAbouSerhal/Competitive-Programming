class ModuloEquations {
    // returns x such that
    // (a[0][0]x[0]     + a[0][1]x[1]     + ... + a[0][n - 1]x[n - 1]    ) % mod = b[0]
    // (a[1][0]x[0]     + a[1][1]x[1]     + ... + a[1][n - 1]x[n - 1]    ) % mod = b[1]
    // .....
    // (a[m - 1][0]x[0] + a[m - 1][1]x[1] + ... + a[m - 1][n - 1]x[n - 1]) % mod = b[m - 1]
    // where 0 <= a[i], b[i], x[i] < mod
    // or null if there is no solution
    public final static long[] systemOfEquationsModulo(long[][] a, long[] b, long mod) {
        int m = b.length;
        int n = a[0].length;
        long[][] mat = new long[m][n + 1];
        for (int i = 0; i < m; ++i) {
            for(int j = 0; j < n; ++j) mat[i][j] = a[i][j];
            mat[i][n] = b[i];
        }
        int rank = 0;
        for (int col = 0; col < n && rank < m; ++col) {
            int pivot = -1;
            for (int row = rank; row < m; ++row)
                if (mat[row][col] % mod != 0) {
                    pivot = row;
                    break;
                }
            if (pivot == -1) continue;
            long[] tmp = mat[rank];
            mat[rank] = mat[pivot];
            mat[pivot] = tmp;
            long inv = modInverse(mat[rank][col], mod);
            if (inv == -1) return null; 
            for (int j = col; j <= n; ++j) 
                mat[rank][j] = (mat[rank][j] * inv) % mod;
            for (int row = 0; row < m; ++row) 
                if (row != rank && mat[row][col] != 0) {
                    long factor = mat[row][col];
                    for (int j = col; j <= n; ++j) {
                        mat[row][j] = (mat[row][j] - factor * mat[rank][j]) % mod;
                        if (mat[row][j] < 0) mat[row][j] += mod;
                    }
                }
            rank++;
        }
        for (int row = rank; row < m; ++row) 
            if (mat[row][n] != 0) return null;
        long[] x = new long[n];
        for (int i = 0; i < rank; ++i) {
            int firstNonZero = -1;
            for (int j = 0; j < n; ++j) 
                if (mat[i][j] != 0) {
                    firstNonZero = j;
                    break;
                }
            if (firstNonZero != -1)
                x[firstNonZero] = mat[i][n];
        }
        return x;
    }
    // returns x such that (a[0]x[0] + a[1]x[1] + ... + a[n - 1]x[n - 1])%mod = gcd(a)
    // where 0 <= a[i], x[i] < mod
    public static final long[] linearEquationModulo(long[] a, long mod){
        int n = a.length;
        if(n == 1) return new long[] {a[0] > 0 ? 1 : mod - 1};
        else if(n == 2){
            long[] xy = extendedEuclidean(a[0], a[1]);
            return new long[] {xy[0] % mod, xy[1] % mod};
        }
        else{
            long[] x = new long[n];
            long[] xy = extendedEuclidean(a[0], a[1]);
            x[0] = xy[0] % mod;
            x[1] = xy[1] % mod;
            long[] leftProduct = new long[n - 2];
            for(int i = 2; i < n; ++i){
                xy = extendedEuclidean(xy[2], a[i]);
                leftProduct[i - 2] = xy[0] % mod;
                x[i] = xy[1] % mod;
            }
            for(int i = n - 4; i >= 0; --i) 
                leftProduct[i] = (leftProduct[i] * leftProduct[i + 1]) % mod;
            x[0] = (x[0] * leftProduct[0]) % mod;
            for(int i = 1; i < n - 1; ++i) x[i] = (x[i] * leftProduct[i - 1]) % mod;
            return x;
        }
    }
    // returns x such that (a.x) % mod = 1 (-1 if no such x exists) (O(log(min(a, m))))
    public static final long modInverse(long a, long mod) {
        long[] xygcd = extendedEuclidean(a, mod);
        return xygcd[2] == 1 ? ((xygcd[0] % mod) + mod) % mod  : -1;
    }
    private static final long[] extendedEuclidean(long a, long b){
        long x = 1, y = 0, x1 = 0, y1 = 1, temp;
        while(b != 0){
            long q = a / b;
            temp = x;
            x = x1;
            x1 = temp - q * x1;
            temp = y;
            y = y1;
            y1 = temp - q * y1;
            temp = a;
            a = b;
            b = temp % b;
        }
        return new long[] {x, y, a};
    }
}