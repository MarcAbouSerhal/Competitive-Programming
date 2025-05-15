class StringUtil{
    public final static String[] letter = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    // returns all permutations in lexicographical order
    // count[i] = number of occurence of letter i and n = sum(count)
    // complexity can be made better by using a LinkedList<Character>
    public final static ArrayList<String> permutations(int n, int[] count){
        if(n == 1){
            for(int i = 0; i < 26; ++i)
                if(count[i] > 0){
                    ArrayList<String> res = new ArrayList<>();
                    res.add(letter[i]);
                    return res;
                }
            return null;
        }
        ArrayList<String> res = new ArrayList<>();
        for(int i = 0; i < 26; ++i)
            if(count[i] > 0){
                count[i]--;
                for(String s: permutations(n - 1, count))
                    res.add(letter[i] + s);
                count[i]++;
            }
        return res;
    }
}
