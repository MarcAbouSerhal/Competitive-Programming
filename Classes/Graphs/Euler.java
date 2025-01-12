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
        tick = 0;
        copy(adj_);
        final int[] result = new int[m + 1];
        tick = 0;
        final boolean[] removed = new boolean[m];
        Stack st = new Stack(m + 1);
        st.add(start);
        int v, i;
        while(!st.isEmpty()){
            int u = st.top();
            while(!adj[u].isEmpty() && removed[index[u].top()]){ // remove already removed edges from adj[u]
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
    private static int tick;
    private static Stack[] adj;
    private static Stack[] index;
    private final static void copy(ArrayList<Integer>[] adj_){
        final int n = adj_.length;
        adj = new Stack[n];
        index = new Stack[n];
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
