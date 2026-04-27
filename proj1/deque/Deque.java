package deque;

public interface Deque<T> {
    public void addFirst(T val);
    public void addLast(T val);
    public int size();
    public void printDeque();
    public T removeFirst();
    public T removeLast();
    public T get(int index);

    default boolean isEmpty() {
        return this.size() == 0;
    }
}
