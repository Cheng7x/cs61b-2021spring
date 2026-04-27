package deque;

public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    private int plusOne (int index) {
        return (index + 1 + items.length) % items.length;
    }

    private int minusOne (int index) {
        return (index - 1 + items.length) % items.length;
    }

    public void addFirst(T val) {
        if (isNeedBigger()) {
            resize(size * 2);
        }
        items[nextFirst] = val;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(T val) {
        if (isNeedBigger()) {
            resize(size * 2);
        }
        items[nextLast] = val;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public int size() {
        return size;
    }

//    public boolean isEmpty() {
//        return size == 0;
//    }

    public T get(int index) {
        int idx = (nextFirst + 1 + index) % items.length;
        return items[idx];
    }

    public T removeFirst() {
        T val = items[nextFirst];
        items[nextFirst] = null;
        nextFirst = plusOne(nextFirst);
        size -= 1;
        if (isNeedSmaller()) {
            resize(items.length / 4);
        };
        return val;
    }

    public T removeLast() {
        T val = items[nextLast];
        items[nextLast] = null;
        nextLast = minusOne(nextLast);
        size -= 1;
        if (isNeedSmaller()) {
            resize(items.length / 4);
        };
        return val;
    }

    private boolean isNeedBigger() {
        return size == items.length;
    }

    private boolean isNeedSmaller() {
        return size * 4 < items.length && items.length > 8;
    }

    private void resize(int capacity) {
        T[] arr = (T[]) new Object[capacity];
        int first = plusOne(nextFirst);
        for (int i = 0; i < size; i += 1) {
            arr[i] = items[(first + i) % items.length];
        }
        items = arr;
        nextFirst = items.length - 1;
        nextLast = size;
        }

    public void printDeque() {
        for (int i = 0; i < size; i += 1) {
            System.out.print(items[i] + " ");
        }
    }
}

