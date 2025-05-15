class MathUtil{
    // div[i] is sorted list of divisors of i (O(nlog(n)))
    public final static ArrayList<Integer>[] divisorsUpTo(int n){
        ArrayList<Integer>[] div = new ArrayList[n + 1];
        for(int i = 1; i <= n; ++i) div[i] = new ArrayList<>();
        for(int i = 1; i <= n; ++i)
            for(int j = i; j <= n; j += i)
                div[j].add(i);
        return div;
    }
}
