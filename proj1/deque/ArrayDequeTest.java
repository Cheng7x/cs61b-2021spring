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
}
