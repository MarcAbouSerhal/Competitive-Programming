// id[u] < id[v] -> surely u can't reach v 
class TarjanSCC{
    static class Stack{
        int[] s;
        int size = 0;
        public Stack(int size){ s = new int[size]; }
        public void add(int u){ s[size++] = u; }
        public int pop(){ return s[--size]; }
    }
    ArrayList<Integer>[] adj;
    int[] id, t;
    Stack s;
    int tick = 0, group_id = 0;
    private static int min(int a, int b){ return a<b ? a : b; }
    public TarjanSCC( ArrayList<Integer>[] adj){
        this.adj = adj;
        int n = adj.length;
        id = new int[n]; for(int i=0; i<n; ++i) id[i] = -1;
        t = new int[n];
        s = new Stack(n);
        for(int i=0; i<n; ++i)
            if(t[i]==0)
                dfs(i);
    }
    public int dfs(int u){
        int low = t[u] = ++tick;
        s.add(u);
        for(int v: adj[u])
            if(id[v] == -1)
                low = min(low, t[v] != 0 ? t[v] : dfs(v));
        if(low == t[u]){
            int v = -1;
            while(v != u) id[v = s.pop()] = group_id;
            ++group_id;
        }
        return low;
    }
}
