package deque;

public class LinkedListDeque<T> {
    private class Node {
        T item;
        Node prev;
        Node next;

        Node (T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size = 0;
    private final Node head;
    private final Node tail;

    public LinkedListDeque() {
        head = new Node(null, null, null);
        tail = new Node(null, null, null);
        head.next = tail;
        tail.prev = head;
    }

    public LinkedListDeque(T val) {
        Node node = new Node(val, null, null);
        head = new Node(null, null, node);
        tail = new Node(null, node, null);
        node.prev = head;
        node.next = tail;
        size = 1;
    }

    public void addFirst(T val) {
        Node node = new Node(val, head, head.next);
        head.next = node;
        node.next.prev = node;
        size += 1;
    }

    public void addLast(T val) {
        Node node = new Node(val, tail.prev, tail);
        tail.prev = node;
        node.prev.next = node;
        size += 1;
    }

    public boolean isEmpty() {
        return head.next == tail;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        Node temp = head.next;
        while (temp != tail) {
            if (temp.next != tail) {
                System.out.print(temp.item + " -> ");
            }
            else {
                System.out.println(temp.item);
            }
            temp = temp.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        T val = head.next.item;
        head.next.next.prev = head;
        head.next = head.next.next;
        size -= 1;
        return val;
    }

    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        T val = tail.prev.item;
        tail.prev.prev.next = tail;
        tail.prev = tail.prev.prev;
        size -= 1;
        return val;
    }

    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        Node temp = head.next;
        for (int i = 0; i < index; i += 1) {
            temp = temp.next;
        }
        return temp.item;
    }

    private T getRecursiveHelper(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelper(node.next, index - 1);
    }

    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(head.next, index);
    }
}
