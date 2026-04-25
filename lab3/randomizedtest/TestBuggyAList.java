package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> L1 = new AListNoResizing<>();
        BuggyAList<Integer> L2 = new BuggyAList<>();

        L1.addLast(4); L2.addLast(4);
        L1.addLast(5); L2.addLast(5);
        L1.addLast(6); L2.addLast(6);

        assertEquals(L1.size(), L2.size());
        for (int i = 0; i < 3; i++) {
            assertEquals(L1.removeLast(), L2.removeLast());
        }
    }
    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> M = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                M.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int sizeL = L.size();
                int sizeM = M.size();
                assertEquals(sizeL, sizeM);
            } else if (operationNumber == 2) {
                int lastL = -1, lastM = -1;
                if (L.size() != 0) {
                    lastL = L.getLast();
                }
                if (M.size() != 0) {
                    lastM = M.getLast();
                }
                assertEquals(lastL, lastM);
            } else if (operationNumber == 3) {
                int lastL = -1, lastM = -1;
                if (L.size() != 0) {
                    lastL = L.removeLast();
                }
                if (M.size() != 0) {
                    lastM = M.removeLast();
                }
                assertEquals(lastL, lastM);
            }
        }
    }
}
