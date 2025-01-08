// use this for sliding window
// queries [l,r] where both l and r are non-decreasing
class Dequeue{
    private int n1 = 0, n2 = 0;
    private final X[] s1, s2, opS1, opS2;
    // k is max size of window
    public Dequeue(int k){ 
        s1 = new X[k];
        s2 = new X[k];
        opS1 = new X[k];
        opS2 = new X[k];
    }
    // clears the dq (O(1))
    public final void clear(){
        n1 = n2 = 0;
    }
    // adds to the back of the dq (O(1))
    public final void addBack(X x){
        s1[n1] = x;
        if(n1 == 0) opS1[n1++] = x;
        else opS1[n1] = op(opS1[n1++ - 1], x);
    }
    // adds to the front of the dq (O(1))
    public final void addFront(X x){
        s2[n2] = x;
        if(n2 == 0) opS2[n2++] = x;
        else opS2[n2] = op(opS2[n2++ - 1], x);
    }
    // removes from the back of the dq and returns it (O(1) amortized)
    public final X removeBack(){
        if(n1 == 0)
            for(--n2; n2 >= 0; --n2)
                addBack(s2[n2]);
        ++n2;
        return s1[--n1];
    }
    // removes from the front of the dq and returns it (O(1) amortized)
    public final X removeFront(){
        if(n2 == 0)
            for(--n1; n1 >= 0; --n1)
                addFront(s1[n1]);
        ++n1;
        return s2[--n2];
    }
    // returns op(elements of the dq) (O(T(op)))
    public final X getOp(){
        if(n1 == 0) return opS2[n2 - 1];
        if(n2 == 0) return opS1[n1 - 1];
        return op(opS1[n1 - 1], opS2[n2 - 1]);
    }
    // CHANGE THESE FUNCTIONS
    private static final X op(X x, X y){
        // define property here
    }
}
