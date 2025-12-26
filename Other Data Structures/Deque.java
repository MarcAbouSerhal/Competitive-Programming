class Deque<T> {
    private static final int INITIAL_CAPACITY = 16;
    private Object[] a = new Object[INITIAL_CAPACITY];
    private int head, size;
    int size() { return size; }
    boolean isEmpty() { return size == 0; }
    void clear() { Arrays.fill(a, null); head = size = 0; }
    void addFirst(T x) {
        ensureCapacity(size + 1);
        head = (head - 1) & (a.length - 1);
        a[head] = x;
        size++;
    }
    void addLast(T x) {
        ensureCapacity(size + 1);
        a[(head + size) & (a.length - 1)] = x;
        size++;
    }
    T pollFirst() {
        if (size == 0) return null;
        T x = (T) a[head];
        a[head] = null;
        head = (head + 1) & (a.length - 1);
        size--;
        return x;
    }
    T pollLast() {
        if (size == 0) return null;
        int idx = (head + size - 1) & (a.length - 1);
        T x = (T) a[idx];
        a[idx] = null;
        size--;
        return x;
    }
    T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return (T) a[(head + index) & (a.length - 1)];
    }
    T getFromBack(int index) { return get(size - 1 - index); }
    void set(int index, T value) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        a[(head + index) & (a.length - 1)] = value;
    }
    private void ensureCapacity(int needed) {
        if (needed <= a.length) return;
        Object[] na = new Object[a.length << 1];
        for (int i = 0; i < size; i++) na[i] = a[(head + i) & (a.length - 1)];
        a = na;
        head = 0;
    }
}