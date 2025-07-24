// use this for sliding window, queries [l,r] where both l and r are non-decreasing
class SlidingDeque { 
    private int n1 = 0, n2 = 0;
    private final X[] s1, s2, opS1, opS2;
    // k is max size of window, op is lambda function
    public SlidingDeque(int k) { 
        s1 = new int[k];
        s2 = new int[k];
        opS1 = new int[k];
        opS2 = new int[k];
    }
    // clears the dq (O(1))
    public final void clear() { n1 = n2 = 0; }
    public final int size() {  return n1 + n2; }
    // adds to the back of the dq (O(1))
    public final void addBack(int e){
        s1[n1] = e;
        if(n1 == 0) opS1[n1++] = e;
        else opS1[n1] = op(opS1[n1++ - 1], e);
    }
    // adds to the front of the dq (O(1))
    public final void addFront(int e){
        s2[n2] = e;
        if(n2 == 0) opS2[n2++] = e;
        else opS2[n2] =op(opS2[n2++ - 1], e);
    }
    // removes from the back of the dq and returns it (O(1) amortized)
    public final int removeBack(){
        if(n1 == 0) {
            for(--n2; n2 >= 0; --n2) addBack(s2[n2]);
            n2 = 0;
        }
        return s1[--n1];
    }
    // removes from the front of the dq and returns it (O(1) amortized)
    public final int removeFront(){
        if(n2 == 0) {
            for(--n1; n1 >= 0; --n1) addFront(s1[n1]);
            n1 = 0;
        }
        return s2[--n2];
    }
    // returns op(elements of the dq) (O(T(op)))
    public final int getOp(){
        if(n1 == 0) return opS2[n2 - 1];
        if(n2 == 0) return opS1[n1 - 1];
        return op(opS1[n1 - 1], opS2[n2 - 1]);
    }
    private static final X op(X a, X b) { /* define associative operation here (f(a, f(b, c)) = f(f(a, b), c)) */ }
}
