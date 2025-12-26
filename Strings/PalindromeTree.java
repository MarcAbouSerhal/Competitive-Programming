class PalindromeTree {
    private static final int k = 26, base = 'a';
    int[][] to, link;
    int[] len, qlink, par, cnt;
    Deque<Integer> s = new Deque<>(), states = new Deque<>(), diffs = new Deque<>();
    int sz = 2, active = 0;
    public PalindromeTree(int q) {
        q += 2;
        cnt = new int[q];
        len = new int[q];
        par = new int[q];
        qlink = new int[q];
        to = new int[q][k];
        link = new int[q][k];
        for(int i = 0; i < k; ++i) link[0][i] = 1;
        qlink[0] = 1;
        len[1] = -1;
        states.addFirst(0);
    }
    int addLetter(char c, boolean right) {
        c -= base;
        push(s, (int)c ,right);
        int pre = get(states, 0, right), last = makeTo(pre, c, right);
        if(cnt[last]++ == 0) active++;
        int D = 2 + len[pre] - len[last];
        while (D + len[pre] <= len[last]) {
            pop(states, right);
            if (!states.isEmpty()) {
                pre = get(states, 0, right);
                D += get(diffs, 0, right);
                pop(diffs, right);
            } 
            else break;
        }
        if (!states.isEmpty()) push(diffs, D, right);
        push(states, last, right);
        return last; // node for max suffix-palindrome of current string
    }
    void popLetter(boolean right) {
        int last = get(states, 0, right);
        if(--cnt[last] == 0) active--;
        pop(states, right);
        pop(s, right);
        int[][] cands = {{qlink[last], len[last] - len[qlink[last]]}, {par[last], 0}};
        for (int[] pair: cands) {
            int state = pair[0], diff = pair[1];
            if (states.isEmpty()) {
                states.clear(); states.addFirst(state);
                diffs.clear(); diffs.addFirst(diff);
            } 
            else {
                int D = get(diffs, 0, right) - diff, pre = get(states, 0, right);
                if (D + len[state] > len[pre]) {
                    push(states, state, right);
                    pop(diffs, right);
                    push(diffs, D, right);
                    push(diffs, diff, right);
                }
            }
        }
        pop(diffs, right);
    }
    int distinct() { return active; }
    int maxLength() { return Math.max(len[get(states, 0, true)], len[get(states, 0, false)]); }
    private int makeTo(int last, int c, boolean right) {
        if(c != get(s, len[last] + 1, right)) last = link[last][c];
        if (to[last][c] == 0) {
            int u = to[link[last][c]][c];
            qlink[sz] = u;
            for(int i = 0; i < k; ++i) link[sz][i] = link[u][i];
            link[sz][get(s, len[u], right)] = u;
            len[sz] = len[last] + 2;
            par[sz] = last;
            to[last][c] = sz++;
        }
        return to[last][c];
    }
    static void push(Deque<Integer> d, int e, boolean right) { if(right) d.addLast(e); else d.addFirst(e); }
    static void pop(Deque<Integer> d, boolean right) { if(right) d.pollLast(); else d.pollFirst(); }
    static int get(Deque<Integer> d, int idx, boolean right) { return idx >= d.size() ? -1 : right ? d.getFromBack(idx) : d.get(idx); }
}
// to[u][c] = node for c.t_u.c
// link[u][c] = max suffix-palindrome of t_u that can be extended with c
// len[u] = |t_u|
// qlink[u] = node for max suffix-palindrome of t_u
// par[u] = node for t_u with first and last characters removed
