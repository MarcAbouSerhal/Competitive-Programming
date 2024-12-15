class Trie{
    private class Node{
        Node[] children;
        int ends;
        int pref;
        public Node(){
            children = new Node[26];
            ends=0;
            pref=0;
        }
    }
    Node head;
    public Trie(){
        head = new Node();
    }
    public void add(String s){//add s to the trie
        Node[] curr = head.children;
        for(int i=0; i<s.length(); ++i){
            if(curr[s.charAt(i)-'a']==null)
                curr[s.charAt(i)-'a'] = new Node();
            curr[s.charAt(i)-'a'].pref++;
            if(i==s.length()-1)
                curr[s.charAt(i)-'a'].ends++;
            curr=curr[s.charAt(i)-'a'].children;
        }
    }
    public void remove(String s){//remove s from the trie (assumes it's in it)
        Node[] curr = head.children;
        for(int i=0; i<s.length(); ++i){
            if(curr[s.charAt(i)-'a'].pref==1){
                curr[s.charAt(i)-'a']=null;
                return;
            }
            curr[s.charAt(i)-'a'].pref--;
            if(i==s.length()-1)
                curr[s.charAt(i)-'a'].ends--;
            curr=curr[s.charAt(i)-'a'].children;
        }
    }
    public int haveAsPref(String s){//returns # strings with s as a prefix
        Node[] curr = head.children;
        for(int i=0; i<s.length(); ++i){
            if(curr[s.charAt(i)-'a']==null)
                return 0;
            if(i==s.length()-1)
                return curr[s.charAt(i)-'a'].pref;
            curr=curr[s.charAt(i)-'a'].children;
        }
        return 0;
    }
    public int LCP(String s, boolean isInTrie){//returns LCP of s and some string in the trie (other than s if s is in the trie)
        Node[] curr = head.children;
        for(int i=0; i<s.length(); ++i){
            if(curr[s.charAt(i)-'a']==null || (isInTrie && curr[s.charAt(i)-'a'].pref<2))
                return i;
            curr=curr[s.charAt(i)-'a'].children;
        }
        return s.length();
    } 
    private boolean contains(int k, int l, Node curr){
        if(curr==null) return false;
        if(l==1)
            return curr.pref>=k;
        boolean ans = false;
        for(int i=0; i<26; ++i){
            ans|=contains(k,l-1,curr.children[i]);
        }
        return ans;
    }
    public boolean contains(int k, int l){//returns if there are >=k strings with LCP>=l
        boolean ans=false;
        for(int i=0; i<26; ++i)
            ans|=contains(k,l,head.children[i]);
        return ans;
    }
    private int prefixes(Node node){
        int count=1;
        for(Node child: node.children){
            if(child==null) continue;
            count+=prefixes(child);
        }
        return count;
    }
    public int prefixes(){//returns # unique prefixes (# nodes)
        return prefixes(head)-1;
    }
}
