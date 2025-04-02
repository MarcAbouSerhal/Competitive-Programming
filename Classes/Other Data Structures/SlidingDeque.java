// Use:
// import java.util.function.BinaryOperator
// SlidingDeque<Integer> maxdq = new SlidingDeque<>(k, (x, y) -> x > y ? x : y);
// use this for sliding window, queries [l,r] where both l and r are non-decreasing
class SlidingDeque<E>{
    private final BinaryOperator<E> operator;
    private int n1 = 0, n2 = 0;
    private final Object[] s1, s2, opS1, opS2;
    // k is max size of window, op is lambda function
    public SlidingDeque(int k, BinaryOperator<E> op){ 
        s1 = new Object[k];
        s2 = new Object[k];
        opS1 = new Object[k];
        opS2 = new Object[k];
        operator = op;
    }
    // clears the dq (O(1))
    public final void clear(){
        n1 = n2 = 0;
    }
    public final int size(){ 
        return n1 + n2;
    }
    // adds to the back of the dq (O(1))
    public final void addBack(E e){
        s1[n1] = e;
        if(n1 == 0) opS1[n1++] = e;
        else opS1[n1] = op((E)opS1[n1++ - 1], e);
    }
    // adds to the front of the dq (O(1))
    public final void addFront(E e){
        s2[n2] = e;
        if(n2 == 0) opS2[n2++] = e;
        else opS2[n2] = op((E)opS2[n2++ - 1], e);
    }
    // removes from the back of the dq and returns it (O(1) amortized)
    public final E removeBack(){
        if(n1 == 0)
            for(--n2; n2 >= 0; --n2)
                addBack((E)s2[n2]);
        ++n2;
        return (E)s1[--n1];
    }
    // removes from the front of the dq and returns it (O(1) amortized)
    public final E removeFront(){
        if(n2 == 0)
            for(--n1; n1 >= 0; --n1)
                addFront((E)s1[n1]);
        ++n1;
        return (E)s2[--n2];
    }
    // returns op(elements of the dq) (O(T(op)))
    public final E getOp(){
        if(n1 == 0) return (E)opS2[n2 - 1];
        if(n2 == 0) return (E)opS1[n1 - 1];
        return op((E)opS1[n1 - 1], (E)opS2[n2 - 1]);
    }
    private final E op(E x, E y) { return operator.apply(x, y); }
}
