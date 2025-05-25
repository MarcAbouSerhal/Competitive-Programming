// works for multigraphs (graphs with duped edges)
class EulerUndirected{
    // gets euler cycle if it exists (degree[u] is even for every u), and null if it doesn't exist (O(n + m))
    public final static int[] getCycle(ArrayList<Integer>[] adj, int start){
        int n = adj.length, m = 0;
        for(int u = 0; u < n; ++u) {
            m += adj[u].size();
            if((adj[u].size() & 1) == 1) return null;
        }
        return hierholzer(adj, m >> 1, start);
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
        return info == null ? null : info[0] == s || info[1] == s || info[0] == info[1] ? hierholzer(adj, info[2], s) : null;
    }
    // gets euler path from s to e if it exists and null if it doesn't exist (O(n + m))
    public final static int[] getPath(ArrayList<Integer>[] adj, int s, int e){
        int[] info = helper(adj);
        return info == null ? null : (info[0] == e && info[1] == s) || (info[0] == s && info[1] == e) || (s == e && info[0] == info[1]) ? hierholzer(adj, info[2], s) : null;
    }
    // returns {potential start, potential end, number of edges}
    private final static int[] helper(ArrayList<Integer>[] adj){
        int n = adj.length, m = 0, start = -1, end = -1, nonEmpty = 0;
        for(int u = 0; u < n; ++u){
            m += adj[u].size();
            if(adj[u].size() != 0) nonEmpty = u;
            if((adj[u].size() & 1) == 0) continue;
            else{
                if(start == -1) start = u;
                else if(end == -1) end = u;
                else return null;
            }
        }
        return start == -1 ? new int[] {nonEmpty, nonEmpty, m >> 1} : new int[] {start, end, m >> 1};
    }
    private final static int[] hierholzer(ArrayList<Integer>[] adj_, int m, int start) {
        int tick = 0, n = adj_.length;
        Stack[] index = new Stack[n];
        int[][] adj = new int[n][];
        for(int u = 0; u < n; ++u) {
            index[u] = new Stack(adj_[u].size());
            adj[u] = new int[adj_[u].size()];
        }
        for(int u = 0; u < n; ++u)
            for(int v: adj_[u]){
                if(v < u) continue; // so not to count an edge twice
                adj[u][index[u].size] = v;
                adj[v][index[v].size] = u;
                index[u].add(tick);
                index[v].add(tick++);
            }
        final int[] result = new int[m + 1];
        tick = 0;
        final boolean[] removed = new boolean[m];
        Stack st = new Stack(m + 1);
        st.add(start);
        while(st.size != 0){
            int u = st.top();
            while(index[u].size != 0 && removed[index[u].top()]) index[u].pop();
            if(index[u].size == 0) result[tick++] = st.pop();
            else{
                removed[index[u].pop()] = true;
                st.add(adj[u][index[u].size]);
            }
        }
        if(tick == m + 1) return result;
        else return null;
    }
    private static final class Stack{
        private final int[] s;
        int size = 0;
        public Stack(int n){ s = new int[n]; }
        public final void add(int u){ s[size++] = u; }
        public final int top(){ return s[size - 1]; }
        public final int pop(){ return s[--size]; }
    }
}