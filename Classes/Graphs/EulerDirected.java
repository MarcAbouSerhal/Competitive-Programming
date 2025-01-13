class EulerDirected{
    // gets euler cycle if it exists (indegree[u] = outdegree[u] for every u), and null if it doesn't exist  (O(n + m))
    public final static int[] getCycle(ArrayList<Integer>[] adj_, int start){
        final int n = adj_.length;
        final int[] indegree = new int[n];
        int m = 0;
        for(int u = 0; u < n; ++u){
            m += adj_[u].size();
            for(int v: adj_[u])
                ++indegree[v];
        }
        for(int u = 0; u < n; ++u)
            if(indegree[u] != adj_[u].size()) 
                return null;
        return hierholzer(adj_, m, start);
    }
    // gets euler path if it exists ( atmost one u s.t. outdegree[u] - indegree[u] = 1, and 
    // atmost v s.t. indegree[v] - outdegree[v] = 1, with rest having indegree[w] = outdegree[w] for every other vertex)
    // and null if it doesn't exist (O(n + m))
    public final static int[] getPath(ArrayList<Integer>[] adj_){
        final int n = adj_.length;
        final int[] indegree = new int[n];
        int m = 0;
        for(int u = 0; u < n; ++u){
            m += adj_[u].size();
            for(int v: adj_[u])
                ++indegree[v];
        }
        int start = -1, end = -1;
        for(int u = 0; u < n; ++u){
            if(indegree[u] == adj_[u].size()) continue;
            else if(indegree[u] + 1 == adj_[u].size()){
                if(start == -1) start = u;
                else return null;
            }
            else if(indegree[u] == adj_[u].size() + 1){
                if(end == -1) end = u;
                else return null;
            }
            else return null;
        }
        return hierholzer(adj_, m, start == -1 ? 0 : start);
    }
    private final static int[] hierholzer(ArrayList<Integer>[] adj_, int m, int start){
        final Stack[] adj = copy(adj_);
        final int[] result = new int[m + 1];
        int tick = m + 1;
        Stack st = new Stack(m + 1);
        st.add(start);
        int u;
        while(!st.isEmpty()){
            u = st.top();
            if(adj[u].isEmpty()) result[--tick] = st.pop();
            else st.add(adj[u].pop());
        }
        if(tick == 0) return result;
        else return null;
    }
    private final static Stack[] copy(ArrayList<Integer>[] adj_){
        final int n = adj_.length;
        Stack[] adj = new Stack[n];
        for(int u = 0; u < n; ++u)
            adj[u] = new Stack(adj_[u].size());
        for(int u = 0; u < n; ++u)
            for(int v: adj_[u])
                adj[u].add(v);
        return adj;
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
