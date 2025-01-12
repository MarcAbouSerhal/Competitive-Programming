class Euler{
    // Note: this class is for undirected multigraphs (could have multiple edges)
    // gets euler cycle if it exists (degree[u] is even for every u), and null if it doesn't exist  (O(n + m))
    public final static int[] getCycle(ArrayList<Integer>[] adj_, int start){
        final int n = adj_.length;
        int m = 0;
        for(int u = 0; u < n; ++u){
            if((adj_[u].size() & 1) == 1) return null;
            m += adj_[u].size();
        }
        m >>= 1;
        final Stack[][] stacks = copy(adj_);
        final Stack[] adj = stacks[0], index = stacks[1];
        final int[] result = new int[m + 1];
        int tick = 0;
        final boolean[] removed = new boolean[m];
        Stack st = new Stack(m + 1);
        st.add(start);
        int v, i;
        while(!st.isEmpty()){
            int u = st.top();
            while(!adj[u].isEmpty() && removed[index[u].top()]){
                adj[u].pop();
                index[u].pop();
            }
            if(adj[u].isEmpty()){
                result[tick++] = u;
                st.pop();
            }
            else{
                v = adj[u].pop();
                i = index[u].pop();
                removed[i] = true;
                st.add(v);
            }
        }
        if(tick == m + 1) return result;
        else return null;
    }
    // gets euler path if it exists (degree[u] is odd for 0 or 2 u's), and null if it doesn't exist  (O(n + m))
    public final static int[] getPath(ArrayList<Integer>[] adj_, int start){
        final int n = adj_.length;
        int m = 0, x = -1, y = -1;
        for(int u = 0; u < n; ++u){
            if((adj_[u].size() & 1) == 1){
                if(x == -1) x = u;
                else if(y == -1) y = u;
                else return null;
            }
            m += adj_[u].size();
        }
        if(x != -1 && y == -1) return null;
        else{
            if(x != -1){
                adj_[x].add(y);
                adj_[y].add(x);
            }
            int[] result = getCycle(adj_, start);
            if(x != -1){
                adj_[x].removeLast();
                adj_[y].removeLast();
            }
            return result;
        }
    }
    private final static Stack[][] copy(ArrayList<Integer>[] adj_){
        int tick = 0;
        final int n = adj_.length;
        Stack[] adj = new Stack[n];
        Stack[] index = new Stack[n];
        for(int u = 0; u < n; ++u){
            adj[u] = new Stack(adj_[u].size());
            index[u] = new Stack(adj_[u].size());
        }
        for(int u = 0; u < n; ++u){
            for(int v: adj_[u]){
                if(v < u) continue; // so not to count an edge twice
                adj[u].add(v);
                adj[v].add(u);
                index[u].add(tick);
                index[v].add(tick++);
            }
        }
        return new Stack[][] {adj, index};
    }
    private static final class Stack{
        private final int[] s;
        private int size = 0;
        public Stack(int n){ s = new int[n]; }
        public final void add(int u){ s[size++] = u; }
        public final int top(){ return s[size - 1]; }
        public final int pop(){ return s[--size]; }
        public final boolean isEmpty(){ return size == 0; }
    }
}
