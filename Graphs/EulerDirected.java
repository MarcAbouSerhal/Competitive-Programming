class EulerDirected{
    // gets euler cycle if it exists (indegree[u] = outdegree[u] for every u), and null if it doesn't exist  (O(n + m))
    public final static int[] getCycle(ArrayList<Integer>[] adj, int start){
        final int n = adj.length;
        final int[] indegree = new int[n];
        int m = 0;
        for(int u = 0; u < n; ++u){
            m += adj[u].size();
            for(int v: adj[u])
                ++indegree[v];
        }
        for(int u = 0; u < n; ++u)
            if(indegree[u] != adj[u].size()) 
                return null;
        return hierholzer(adj, m, start);
    }
    // gets euler path if it exists (atmost one u s.t. outdegree[u] - indegree[u] = 1, and 
    // atmost v s.t. indegree[v] - outdegree[v] = 1, with indegree[w] = outdegree[w] for every other vertex w)
    // and null if it doesn't exist (O(n + m))
    public final static int[] getPath(ArrayList<Integer>[] adj){
        int[] info = helper(adj);
        return info == null ? null : hierholzer(adj, info[2], info[0]);
    }
    // gets euler path starting from s if it exists and null if it doesn't exist (O(n + m))
    public final static int[] getPath(ArrayList<Integer>[] adj, int s){
        int[] info = helper(adj);
        return info == null ? null : info[0] == s || info[0] == info[1] ? hierholzer(adj, info[2], s) : null;
    }
    // gets euler path from s to e if it exists and null if it doesn't exist (O(n + m))
    public final static int[] getPath(ArrayList<Integer>[] adj, int s, int e){
        int[] info = helper(adj);
        return info == null ? null : (info[0] == s && info[1] == e) || (s == e && info[0] == info[1]) ? hierholzer(adj, info[2], s) : null;
    }
    // returns {potential start, potential end, number of edges}
    private final static int[] helper(ArrayList<Integer>[] adj){
        int n = adj.length, m = 0, nonEmpty = 0;
        int[] indegree = new int[n];
        for(int u = 0; u < n; ++u){
            m += adj[u].size();
            for(int v: adj[u]) ++indegree[v];
            if(adj[u].size() != 0) nonEmpty = u;
        }
        int start = -1, end = -1;
        for(int u = 0; u < n; ++u){
            if(indegree[u] == adj[u].size()) continue;
            else if(indegree[u] + 1 == adj[u].size()){
                if(start == -1) start = u;
                else return null;
            }
            else if(indegree[u] == adj[u].size() + 1){
                if(end == -1) end = u;
                else return null;
            }
            else return null;
        }
        return start == -1 ? new int[] {nonEmpty, nonEmpty, m} : new int[] {start, end, m};
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