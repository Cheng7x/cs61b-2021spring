package deque;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void ValueTest() {
        int N = 32;
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        assertTrue(deque.isEmpty());
        for (int i = 0; i < N; i += 1) {
            if (i % 2 == 0)
                deque.addFirst(i);
            else
                deque.addLast(i);
            assertEquals(deque.size(), i + 1);
        }
        deque.printDeque();
        for (int i = 0; i < N; i += 1) {
            if (i % 2 == 0)
                deque.removeLast();
            else
                deque.removeFirst();
            assertEquals(deque.size(), N - i - 1);
        }
    }

    @Test
    public void getTest() {
        int N = 10;
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for (int i = 0; i < N; i += 1) {
            arr.addLast(i);
        }
        assertEquals(0, (int) arr.get(0));
        assertEquals(5, (int) arr.get(5));
        assertEquals(8, (int) arr.get(8));
    }

    public static void main(String[] args) {
        int N = 10;
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for (int i = 128; i < 128 + N; i += 1) {
            arr.addLast(i);
        }
        for (int i : arr) {
            System.out.println(i);
        }
    }
}