package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;


public class MaxArrayDequeTest {

    private static class IntComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return a - b;
        }
    }

    private static class StringLengthComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    private static class StringNaturalComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

    @Test
    public void emptyTest() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntComparator());
        assertNull(mad.max());
    }

    @Test
    public void intMaxTest() {
        MaxArrayDeque<Integer> mad = new MaxArrayDeque<>(new IntComparator());
        mad.addFirst(15);
        mad.addLast(20);
        mad.addLast(35);
        mad.addFirst(-1);
        mad.addFirst(25);
        assertEquals(35, (int) mad.max());
    }

    @Test
    public void stringTest() {
        MaxArrayDeque<String> mad = new MaxArrayDeque<>(new StringLengthComparator());
        mad.addLast("hello");
        mad.addLast("hey");
        mad.addLast("strings");
        mad.addLast("hi");
        mad.addLast("zoo");
        assertEquals("strings", mad.max());
        assertEquals("zoo", mad.max(new StringNaturalComparator()));
    }
}
