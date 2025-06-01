class GridHamiltonianPath{
    private static int n, m, sx, sy, ex, ey;
    private static boolean[][] visited;
    private static final int[] dx = {0, 0, -1, 1};
    private static final int[] dy = {-1, 1, 0, 0};
    private static final char[] c = {'L', 'R', 'U', 'D'};
    private static char[] path;
    public static final char[] findPath(int n, int m, int sx, int sy, int ex, int ey) {
        GridHamiltonianPath.n = n;
        GridHamiltonianPath.m = m;
        GridHamiltonianPath.sx = sx;
        GridHamiltonianPath.sy = sy;
        GridHamiltonianPath.ex = ex;
        GridHamiltonianPath.ey = ey;
        visited = new boolean[n][m];
        path = new char[n * m - 1];
        if(rec(sx, sy, 0)) return path;
        else return null;
    }
    private static final boolean isIn(int x, int y){ return x >= 0 && x < n && y >= 0 && y < m; }
    private static final boolean rec(int x, int y, int idx){
        if(idx == n * m - 1) return true; // we have found a full path
        else if((x !=  ex || y != ey) && !isSplitPoint(x, y)){
            // if we're not at the final square too early, or a split point
            visited[x][y] = true;
            for(int i = 0; i < 4; ++i)
                if(isIn(x + dx[i], y + dy[i]) && !visited[x + dx[i]][y + dy[i]] && isDeadEnd(x + dx[i], y + dy[i])){
                    // found a dead end, only go to it and no other direction
                    path[idx] = c[i];
                    if(rec(x + dx[i], y + dy[i], idx + 1)) return true;
                    return visited[x][y] = false;
                }
            for(int i = 0; i < 4; ++i)
                if(isIn(x + dx[i], y + dy[i]) && !visited[x + dx[i]][y + dy[i]]) {
                    path[idx] = c[i];
                    if(rec(x + dx[i], y + dy[i], idx + 1)) return true;
                }
            return visited[x][y] = false;
        }
        else return false;
    }
    private static final boolean isSplitPoint(int x, int y){
        // optimization: checks if 2 opposing sides are blocked and other two are open
        // so must choose one but won't reach the other (only holds if start is at some border)
        boolean[] c = new boolean[4];
        for(int i = 0; i < 4; ++i) c[i] = !isIn(x + dx[i], y + dy[i]) || visited[x + dx[i]][y + dy[i]];
        // c[i] means direction i isn't allowed
        return  (sx == 0 || sy == 0 || sx == n - 1 || sy == m - 1) && ((c[0] && c[1] && !c[2] && !c[3]) || (!c[0] && !c[1] && c[2] && c[3]));
    }
    private static final boolean isDeadEnd(int x, int y){
        // optimization: checks if neighbor is blocked by 3 sides meaning it'd create a dead end
        // therefore we have to visit it for it not to create one (but it's ok for end to create one)
        int c = 0;
        for(int i = 0; i < 4; ++i) c += (!isIn(x + dx[i], y + dy[i]) || visited[x + dx[i]][y + dy[i]]) ? 1 : 0;
        return c >= 3 && (x != ex || y != ey);
    }
}
