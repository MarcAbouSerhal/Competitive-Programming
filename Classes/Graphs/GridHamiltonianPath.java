class GridHamiltonianPath{
    static int n, m;
    static int sx, sy, ex, ey;
    static final boolean[][] visited = new boolean[n][m];
    static final int[] dx = {0, 0, -1, 1};
    static final int[] dy = {-1, 1, 0, 0};
    static final char[] c = {'L', 'R', 'U', 'D'};
    public final static boolean isIn(int x, int y){ return x >= 0 && x < n && y >= 0 && y < m; }
    public final static void rec(int x, int y, int idx){
        if(idx == n * m - 1){
            // we have found a full path
        }
        else if(!(x ==  ex && y == ey) && isNotSplitPoint(x, y)){
            // if we're not at the final square too early
            // and we're not at a split point
            visited[x][y] = true;
            for(int i = 0; i < 4; ++i)
                if(isIn(x + dx[i], y + dy[i]) && !visited[x + dx[i]][y + dy[i]] && isDeadEnd(x + dx[i], y + dy[i])){
                    // found a dead end, only go to it and no other direction
                    rec(x + dx[i], y + dy[i], idx + 1);
                    visited[x][y] = false;
                    return;
                }
            for(int i = 0; i < 4; ++i)
                if(isIn(x + dx[i], y + dy[i]) && !visited[x + dx[i]][y + dy[i]])
                    rec(x + dx[i], y + dy[i], idx + 1);
            visited[x][y] = false;
        }
    }
    public final static boolean isNotSplitPoint(int x, int y){
        // optimization: checks if 2 opposing sides are blocked and other two are open
        // so must choose one but won't reach the other
        boolean[] c = new boolean[4];
        for(int i = 0; i < 4; ++i) c[i] = (!isIn(x + dx[i], y + dy[i]) || visited[x + dx[i]][y + dy[i]]);
        // c[i] means direction i isn't allowed
        return !(c[0] && c[1] && !c[2] && !c[3]) && !(!c[0] && !c[1] && c[2] && c[3]);
    }
    public final static boolean isDeadEnd(int x, int y){
        // optimization: checks if neighbor is blocked by 3 sides meaning it'd create a dead end
        // therefore we have to visit it for it not to create one (but it's ok for end to create one)
        int c = 0;
        for(int i = 0; i < 4; ++i) c += (isIn(x + dx[i], y + dy[i]) && visited[x + dx[i]][y + dy[i]]) ? 1 : 0;
        return c >= 3 && !(x == ex && y == ey);
    }
}
