// use this for sliding window
// queries [l,r] where both l and r are non-decreasing
class Dequeue{
    private static final int prop(int x, int y){
        // define property here
    }
    private int n1 = 0, n2 = 0;
    private final int[] s1, s2, propS1, propS2;
    // k is max size of window
    public Dequeue(int k){ 
        s1 = new int[k];
        s2 = new int[k];
        propS1 = new int[k];
        propS2 = new int[k];
    }
    public final void clear(){
        n1 = n2 = 0;
    }
    public final void addBack(int x){
        s1[n1] = x;
        if(n1 == 0) propS1[n1++] = x;
        else propS1[n1] = prop(propS1[n1++ - 1], x);
    }
    public final void addFront(int x){
        s2[n2] = x;
        if(n2 == 0) propS2[n2++] = x;
        else propS2[n2] = prop(propS2[n2++ - 1], x);
    }
    public final int removeBack(){
        if(n1 == 0)
            for(--n2; n2 >= 0; --n2)
                addBack(s2[n2]);
        ++n2;
        return s1[--n1];
    }
    public final int removeFront(){
        if(n2 == 0)
            for(--n1; n1 >= 0; --n1)
                addFront(s1[n1]);
        ++n1;
        return s2[--n2];
    }
    // returns prop(elements of the dq)
    public final int getProp(){
        if(n1 == 0) return propS2[n2 - 1];
        if(n2 == 0) return propS1[n1 - 1];
        return prop(propS1[n1 - 1], propS2[n2 - 1]);
    }
}
