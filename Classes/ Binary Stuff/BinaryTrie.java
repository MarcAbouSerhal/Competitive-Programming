class BinaryTrie{
    private final static class Node{
        Node[] children;
        int pref = 0, unique = 0;
        public Node(){ children = new Node[2]; }
    }
    private final Node head;
    private final int log;
    // means all elements are in [0 .. (1 << log) - 1]
    public BinaryTrie(int log){
        this.log = log;
        head = new Node();
    }
    public final void add(int x){
        Node node = head; ++head.pref;
        boolean foundUnique = false;
        for(int i = log - 1; i >= 0; --i){
            int bit = (x >> i) & 1;
            if(node.children[bit] == null){
                node.children[bit] = new Node();
                foundUnique = true;
            }
            node = node.children[bit];
            ++node.pref;
        }
        if(foundUnique){
            node = head; ++head.unique;
            for(int i = log - 1; i >= 0; --i){
                node = node.children[(x >> i) & 1];
                ++node.unique;
            }
        }
    }
    public final void remove(int x){
        // assumes x was in the trie
        Node node = head; --head.pref;
        boolean foundUnique = false;
        for(int i = log - 1; i >= 0; --i){
            int bit = (x >> i) & 1;
            if(node.children[bit].pref == 1){
                node.children[bit] = null;
                foundUnique = true;
                break;
            }
            node = node.children[bit];
            --node.pref;
        }
        if(foundUnique){
            node = head;
            for(int i = log - 1; i >= 0 && node != null; --i){
                --node.unique;
                node = node.children[(x >> i) & 1];
            }
        }
    }
    
    // Note: if we want to query below 3 functions but only for s's that are <= limit
    // keep isSmaller = false
    // if !isSmaller and !((limit>>i)&1): can't go towards 1
    // if !isSmaller and (limit>>i)&1 and we go towards 0: set isSmaller to true
    
    public final int max(int x){
        // returns s in S that maximizes s ^ x
        int ans = 0;
        Node curr = head;
        for(int i = log - 1; i >= 0; --i){
            int bit = (x >> i) & 1;
            if(curr.children[bit ^ 1] != null){
                ans |= (bit ^ 1) << i;
                curr = curr.children[bit ^ 1];
            }
            else{
                ans |= bit << i;
                curr = curr.children[bit];
            }
        }
        return ans;
    }
    public final int min(int x){
        // returns s in S that minimizes s ^ x
        int ans = 0;
        Node curr = head;
        for(int i = log - 1; i >= 0; --i){
            int bit = (x >> i) & 1;
            if(curr.children[bit] != null){
                ans |= bit << i;
                curr = curr.children[bit];
            }
            else{
                ans |= (bit ^ 1) << i;
                curr = curr.children[bit ^ 1];
            }
        }
        return ans;
    }
    public final int getLeq(int x, int y){
        if(y < 0) return 0;
        // returns number of elements s in S such that s ^ x <= y
        int count = 0;
        Node curr = head;
        for(int i = log - 1; i >= 0 && curr != null; --i){
            int xbit = (x >> i) & 1, ybit = (y >> i) & 1;
            if(ybit == 0) curr = curr.children[xbit];
            else{
                if(curr.children[xbit] != null) count += curr.children[xbit].pref;
                curr = curr.children[xbit ^ 1];
            }
        }
        return curr == null ? count : (count + curr.pref);
    }
    public final int mex(int x){ 
        // returns mex( { s ^ x : s in S } )
        int ans = 0;
        Node curr = head;
        for(int i = log - 1; i >= 0; --i){
            int bit = (x >> i) & 1;
            if(curr.children[bit] == null)
                return ans;
            else if(curr.children[bit].unique != 1 << i)
                curr = curr.children[bit];
            else if(curr.children[bit ^ 1] == null)
                 return ans | (1 << i);
            else if(curr.children[bit ^ 1].unique == 1 << i) 
                return 1 << (i + 1) - 1;
            else{
                ans |= 1 << i;
                curr = curr.children[bit ^ 1];
            }
        }
        return ans;
    }
}
